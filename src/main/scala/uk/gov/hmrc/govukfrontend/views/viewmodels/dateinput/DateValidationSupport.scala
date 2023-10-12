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
import uk.gov.hmrc.govukfrontend.views.viewmodels.date.{GovUkDate, MonthEntered}

import java.time.LocalDate
import java.time.Month.{of => _}
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}
import java.time.temporal.ChronoField.MONTH_OF_YEAR
import java.util.Locale
import scala.util.{Success, Try}

object DateValidationSupport {

  private trait MonthValidator {
    def validate(monthAsString: String): Option[Int]
  }

  private class FormatterMonthValidator extends MonthValidator {
    private final val abbrMonthFormat = "MMM"
    private final val fullMonthFormat = "MMMM"
    private final val welshLocale     = new Locale("cy")

    private final val fullMonthFormatter: DateTimeFormatter = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .appendPattern(fullMonthFormat)
      .toFormatter()

    private final val abbreviatedMonthFormatter: DateTimeFormatter = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .appendPattern(abbrMonthFormat)
      .toFormatter()

    private final val welshFullMonthFormatter: DateTimeFormatter = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .appendPattern(fullMonthFormat)
      .toFormatter(welshLocale)

    private final val welshAbbreviatedMonthFormatter: DateTimeFormatter = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .appendPattern(abbrMonthFormat)
      .toFormatter(welshLocale)

    def validate(monthAsString: String): Option[Int] = {
      def tryFormatMonth(dateTimeFormatter: DateTimeFormatter): Try[Int] =
        Try(dateTimeFormatter.parse(monthAsString)).map(_.get(MONTH_OF_YEAR))

      Try(monthAsString.toInt).filter(month => month >= 1 && month <= 12)
        .orElse(tryFormatMonth(abbreviatedMonthFormatter))
        .orElse(tryFormatMonth(fullMonthFormatter))
        .orElse(tryFormatMonth(welshAbbreviatedMonthFormatter))
        .orElse(tryFormatMonth(welshFullMonthFormatter))
        .toOption
    }
  }

  private val monthValidator = new FormatterMonthValidator

  private val monthMapping: FieldMapping[MonthEntered] = of[MonthEntered](new Formatter[MonthEntered] {
    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], MonthEntered] =
      data.get(key).map(_.trim).filter(_.nonEmpty) match {
        case None          => Left(Seq(FormError(s"$key", "error.required")))
        case Some(entered) =>
          monthValidator.validate(entered) match {
            case None             => Left(Seq(FormError(s"$key", "error.invalid")))
            case Some(validMonth) => Right(MonthEntered(entered, validMonth))
          }
      }

    override def unbind(key: String, value: MonthEntered): Map[String, String] = Map(
      s"$key" -> value.entered
    )
  })

  // TODO how to customise error messages for number constraints?
  def govukDate: Mapping[GovUkDate] =
    Forms
      .tuple(
        "year"  -> number(min = 1900, max = 2100),
        "month" -> monthMapping,
        "day"   -> number(min = 1, max = 31)
      )
      .verifying(isValidDate)
      .transform(
        { case (year, month, day) => GovUkDate(day, month, year) },
        (govUkDate: GovUkDate) => (govUkDate.entered.year, govUkDate.entered.month, govUkDate.entered.day)
      )

  private def isValidDate: Constraint[(Int, MonthEntered, Int)] =
    Constraint("constraints.date") { ymd: (Int, MonthEntered, Int) =>
      Try(LocalDate.of(ymd._1, ymd._2.value, ymd._3)) match {
        case Success(_) => Valid
        case _          => Invalid(Seq(ValidationError("error.invalid")))
      }
    }

  // TODO move to a separate trait?
  def yearBefore(beforeDate: LocalDate): Constraint[LocalDate] = Constraint("year.before") { govukDate =>
    // TODO localise error message
    if (govukDate.isBefore(beforeDate))
      Valid
    else
      Invalid(Seq(ValidationError(s"Date is not before ${beforeDate.getYear}")))
  }

}
