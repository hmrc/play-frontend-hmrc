/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput

import play.api.data.Forms._
import play.api.data.format.Formatter
import play.api.data.validation._
import play.api.data.{FieldMapping, FormError, Forms, Mapping}
import play.api.i18n.MessagesApi

import java.time.Month.{of => _, _}
import java.time.{LocalDate, Month}
import scala.util.{Success, Try}

object DateValidationSupport {

  trait MonthValidator {
    def validate(monthAsString: String): Option[Month]
  }

  class HardCodedMonthValidator extends MonthValidator {

    private val numericMonths = Map(
      "1" -> JANUARY,
      "2" -> FEBRUARY,
      "3" -> MARCH,
      "4" -> APRIL,
      "5" -> MAY,
      "6" -> JUNE,
      "7" -> JULY,
      "8" -> AUGUST,
      "9" -> SEPTEMBER,
      "10" -> OCTOBER,
      "11" -> NOVEMBER,
      "12" -> DECEMBER,
    )

    private val englishMonths = Map(
      "jan" -> JANUARY,
      "feb" -> FEBRUARY,
      "mar" -> MARCH,
      "apr" -> APRIL,
      "may" -> MAY,
      "jun" -> JUNE,
      "jul" -> JULY,
      "aug" -> AUGUST,
      "sep" -> SEPTEMBER,
      "oct" -> OCTOBER,
      "nov" -> NOVEMBER,
      "dec" -> DECEMBER,
      "january" -> JANUARY,
      "february" -> FEBRUARY,
      "march" -> MARCH,
      "april" -> APRIL,
      "may" -> MAY,
      "june" -> JUNE,
      "july" -> JULY,
      "august" -> AUGUST,
      "september" -> SEPTEMBER,
      "october" -> OCTOBER,
      "november" -> NOVEMBER,
      "december" -> DECEMBER,
    )

    private val welshMonths = Map(
      // TODO all the proper values
      "foo" -> JANUARY,
      "bar" -> FEBRUARY,
      "mar" -> MARCH,
      "apr" -> APRIL,
      "may" -> MAY,
      "jun" -> JUNE,
      "jul" -> JULY,
      "aug" -> AUGUST,
      "sep" -> SEPTEMBER,
      "oct" -> OCTOBER,
      "nov" -> NOVEMBER,
      "dec" -> DECEMBER,
    )

    override def validate(monthAsString: String): Option[Month] = {
      val lowercaseMonth = monthAsString.toLowerCase
      numericMonths.get(lowercaseMonth) orElse englishMonths.get(lowercaseMonth) orElse welshMonths.get(lowercaseMonth)
    }
  }

  val mv = new HardCodedMonthValidator

  val monthFormatter: FieldMapping[Int] = of[Int](new Formatter[Int] {
    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], Int] = {
      data.get(key).map(_.trim).filter(_.nonEmpty) match {
        case None => Left(Seq(FormError(s"$key", "error.required")))
        case Some(month) => mv.validate(month) match {
          case None => Left(Seq(FormError(s"$key", "error.invalid")))
          case Some(validMonth) => Right(validMonth.getValue)
        }
      }
    }

    override def unbind(key: String, value: Int): Map[String, String] = Map(
      s"$key" -> value.toString,
    )
  })


  def yearBefore(beforeDate: LocalDate): Constraint[LocalDate] = Constraint("year.before") { govukDate =>
    // TODO localise error message
    if (govukDate.isBefore(beforeDate))
      Valid
    else
      Invalid(Seq(ValidationError(s"Date is not before ${beforeDate.getYear}")))
  }

  private def validDate()(implicit messagesApi: MessagesApi): Constraint[(Int, Int, Int)] =
    Constraint("constraints.date") { ymd: (Int, Int, Int) =>
      Try(LocalDate.of(ymd._1, ymd._2, ymd._3)) match {
        case Success(_) => Valid
        case _ => Invalid(Seq(ValidationError("error.invalid")))
      }
    }

  def govukDate()(implicit messagesApi: MessagesApi): Mapping[LocalDate] =
    Forms
      .tuple(
        "year" -> number(min = 1900, max = 2100),
        "month" -> monthFormatter,
        "day" -> number(min = 1, max = 31)
      )
      .verifying(validDate)
      .transform(
        { case (day, month, year) => LocalDate.of(year, month, day) },
        (date: LocalDate) => (date.getDayOfMonth, date.getMonthValue, date.getYear)
      )
}

