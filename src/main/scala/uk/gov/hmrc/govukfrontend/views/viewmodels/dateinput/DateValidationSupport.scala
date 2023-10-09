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

import java.time.LocalDate
import java.time.Month.{of => _, _}
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}
import java.time.temporal.ChronoField.MONTH_OF_YEAR
import java.util.Locale
import scala.util.{Success, Try}

object DateValidationSupport {

  trait MonthValidator {
    def validate(monthAsString: String): Option[Int]
  }

  class FormatterMonthValidator extends MonthValidator {
    val welshLocale = new Locale("cy")
    val fullMonthFormat = "MMMM"
    val abbrMonthFormat = "MMM"
    val fullMonthFormatter: DateTimeFormatter = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .appendPattern(fullMonthFormat)
      .toFormatter()
    val abbreviatedMonthFormatter: DateTimeFormatter = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .appendPattern(abbrMonthFormat)
      .toFormatter()
    val welshFullMonthFormatter: DateTimeFormatter = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .appendPattern(fullMonthFormat)
      .toFormatter(welshLocale)
    val welshAbbreviatedMonthFormatter: DateTimeFormatter = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .appendPattern(abbrMonthFormat)
      .toFormatter(welshLocale)

    override def validate(monthAsString: String): Option[Int] = {
      def tryFormatMonth(dateTimeFormatter: DateTimeFormatter): Try[Int] = {
        Try(dateTimeFormatter.parse(monthAsString)).map(_.get(MONTH_OF_YEAR))
      }

      val fullMonthTry = tryFormatMonth(fullMonthFormatter)
      val abbreviatedMonthTry = tryFormatMonth(abbreviatedMonthFormatter)
      val welshFullMonthTry = tryFormatMonth(welshFullMonthFormatter)
      val welshAbbreviatedMonthTry = tryFormatMonth(welshAbbreviatedMonthFormatter)
      val numericMonthTry = Try(monthAsString.toInt).filter(month => month >= 1 && month <= 12)

      fullMonthTry.orElse(abbreviatedMonthTry).orElse(welshFullMonthTry).orElse(welshAbbreviatedMonthTry).orElse(numericMonthTry).toOption
    }
  }

  val monthValidator = new FormatterMonthValidator
  val monthFormatter: FieldMapping[Int] = of[Int](new Formatter[Int] {
    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], Int] =
      data.get(key).map(_.trim).filter(_.nonEmpty) match {
        case None => Left(Seq(FormError(s"$key", "error.required")))
        case Some(month) =>
          monthValidator.validate(month) match {
            case None => Left(Seq(FormError(s"$key", "error.invalid")))
            case Some(validMonth) => Right(validMonth)
          }
      }

    override def unbind(key: String, value: Int): Map[String, String] = Map(
      s"$key" -> value.toString
    )
  })

  def yearBefore(beforeDate: LocalDate): Constraint[LocalDate] = Constraint("year.before") { govukDate =>
    // TODO localise error message
    if (govukDate.isBefore(beforeDate))
      Valid
    else
      Invalid(Seq(ValidationError(s"Date is not before ${beforeDate.getYear}")))
  }

  def govukDate(implicit messagesApi: MessagesApi): Mapping[LocalDate] =
    Forms
      .tuple(
        "year" -> number(min = 1900, max = 2100),
        "month" -> monthFormatter,
        "day" -> number(min = 1, max = 31)
      )
      .verifying(isValidDate)
      .transform(
        { case (year, month, day) => LocalDate.of(year, month, day) },
        (date: LocalDate) => (date.getYear, date.getMonthValue, date.getDayOfMonth)
      )

  private def isValidDate()(implicit messagesApi: MessagesApi): Constraint[(Int, Int, Int)] =
    Constraint("constraints.date") { ymd: (Int, Int, Int) =>
      Try(LocalDate.of(ymd._1, ymd._2, ymd._3)) match {
        case Success(_) => Valid
        case _ => Invalid(Seq(ValidationError("error.invalid")))
      }
    }

}
