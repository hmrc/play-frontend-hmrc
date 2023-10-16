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
import uk.gov.hmrc.govukfrontend.views.viewmodels.date.{GovUkDate, MonthEntered}

import java.time.LocalDate
import java.time.Month.{of => _}
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}
import java.time.temporal.ChronoField.MONTH_OF_YEAR
import java.util.Locale
import scala.util.{Failure, Success, Try}

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

      Try(monthAsString.toInt)
        .filter(month => month >= 1 && month <= 12)
        .orElse(tryFormatMonth(abbreviatedMonthFormatter))
        .orElse(tryFormatMonth(fullMonthFormatter))
        .orElse(tryFormatMonth(welshAbbreviatedMonthFormatter))
        .orElse(tryFormatMonth(welshFullMonthFormatter))
        .toOption
    }
  }

  private val monthValidator = new FormatterMonthValidator

  // TODO this would be parameterised so that teams can pass a descriptive name, eg.
  // "Your date of birth" => "Your date of birth must include a month"
  private val dateDescription = "Date"

  // this version includes the GOVUK-recommended error for when all 3 fields are empty
  // but it's not pretty
  def govukDate(
    invalidDateError: String = "govuk.dateInput.error.date.invalid",
    missingDateError: String = "govuk.dateInput.error.date.missing",
    missingDayError: String = "govuk.dateInput.error.day.missing",
    missingMonthError: String = "govuk.dateInput.error.month.missing",
    missingYearError: String = "govuk.dateInput.error.year.missing"
  ): Mapping[GovUkDate] =
    applyGovukDateValidation(invalidDateError, missingDateError, missingYearError, missingMonthError, missingDayError)
      .transform(
        { case (year, month, day) => GovUkDate(day, month, year) },
        (govUkDate: GovUkDate) => (govUkDate.entered.year, govUkDate.entered.month, govUkDate.entered.day)
      )

  private def applyGovukDateValidation(
    invalidDateError: String,
    missingDateError: String,
    missingYearError: String,
    missingMonthError: String,
    missingDayError: String
  ): Mapping[(String, MonthEntered, String)] =
    Forms.of(new Formatter[(String, MonthEntered, String)] {
      def bind(key: String, data: Map[String, String]): Either[Seq[FormError], (String, MonthEntered, String)] =
        (
          data.get(s"$key.year").map(_.trim),
          data.get(s"$key.month").map(_.trim),
          data.get(s"$key.day").map(_.trim)
        ) match {

          case (Some(""), Some(""), Some("")) =>
            Left(Seq(FormError(key, missingDateError)))

          // TODO similar checks for any combination of two fields empty

          case (Some(year), Some(month), Some(day)) =>
            val maybeValidMonth = monthValidator.validate(month)

            val validationChecks = Seq[(Boolean, String, String)](
              (year.isEmpty, "year", missingYearError),
              (Try(year.toInt).isFailure, "year", invalidDateError),
              (month.isEmpty, "month", missingMonthError),
              (maybeValidMonth.isEmpty, "month", invalidDateError),
              (day.isEmpty, "day", missingDayError),
              (Try(day.toInt).isFailure, "day", invalidDateError),
              (Try(day.toInt < 1 || day.toInt > 31).getOrElse(true), "day", invalidDateError)
            )

            val fieldLevelErrors = validationChecks.collect { case (true, field, error) =>
              FormError(s"$key.$field", error, Seq(dateDescription))
            }

            if (fieldLevelErrors.nonEmpty) {
              val distinctFieldLevelErrors = fieldLevelErrors
                .groupBy(_.key)
                .flatMap { case (_, fieldErrors) => fieldErrors.headOption }
                .toSeq
              Left(distinctFieldLevelErrors)
            } else {
              Try(LocalDate.of(year.toInt, maybeValidMonth.get, day.toInt)) match {
                case Failure(_) => Left(Seq(FormError(key, invalidDateError, Seq(dateDescription))))
                case Success(_) => Right((year, MonthEntered(month, maybeValidMonth.get), day))
              }
            }
        }

      def unbind(key: String, ymd: (String, MonthEntered, String)): Map[String, String] =
        Map(s"$key.year" -> ymd._1, s"$key.month" -> ymd._2.entered, s"$key.day" -> ymd._3)
    })

  // this version doesn't include the GOVUK-recommended error for when all 3 fields are empty
  // and it might be difficult/impossible to add some GOVUK-recommended errors, eg. for multiple missing fields
  // but it uses more idiomatic Play verification, which may make it more maintainable
  def govukDateAlt(
    invalidDateError: String = "govuk.dateInput.error.date.invalid",
    missingDayError: String = "govuk.dateInput.error.day.missing",
    missingMonthError: String = "govuk.dateInput.error.month.missing",
    missingYearError: String = "govuk.dateInput.error.year.missing"
  ): Mapping[GovUkDate] =
    Forms
      .tuple(
        "year"  -> text.verifying(isValidYear(invalidDateError, missingYearError)),
        "month" -> monthMapping(invalidDateError, missingMonthError),
        "day"   -> text.verifying(isValidDay(invalidDateError, missingDayError))
      )
      .verifying(isValidDate(invalidDateError))
      .transform(
        { case (year, month, day) => GovUkDate(day, month, year) },
        (govUkDate: GovUkDate) => (govUkDate.entered.year, govUkDate.entered.month, govUkDate.entered.day)
      )

  private def isValidYear(invalidDateError: String, missingYearError: String): Constraint[String] =
    Constraint("constraints.year") {
      case empty if empty.trim.isEmpty => Invalid(Seq(ValidationError(missingYearError, dateDescription)))
      case nonEmpty                    =>
        Try(nonEmpty.toInt) match {
          case Success(_) => Valid
          case _          => Invalid(Seq(ValidationError(invalidDateError, dateDescription)))
        }
    }

  private def monthMapping(invalidDateError: String, missingMonthError: String): FieldMapping[MonthEntered] =
    of[MonthEntered](new Formatter[MonthEntered] {
      override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], MonthEntered] =
        data.get(key).map(_.trim).filter(_.nonEmpty) match {
          case None          => Left(Seq(FormError(s"$key", missingMonthError, Seq(dateDescription))))
          case Some(entered) =>
            monthValidator.validate(entered) match {
              case None             => Left(Seq(FormError(s"$key", invalidDateError, Seq(dateDescription))))
              case Some(validMonth) => Right(MonthEntered(entered, validMonth))
            }
        }

      override def unbind(key: String, value: MonthEntered): Map[String, String] = Map(
        s"$key" -> value.entered
      )
    })

  private def isValidDay(invalidDateError: String, missingDayError: String): Constraint[String] =
    Constraint("constraints.day") {
      case empty if empty.trim.isEmpty => Invalid(Seq(ValidationError(missingDayError, dateDescription)))
      case nonEmpty                    =>
        Try(nonEmpty.toInt) match {
          case Success(day) if day >= 1 && day <= 31 => Valid
          case _                                     => Invalid(Seq(ValidationError(invalidDateError, dateDescription)))
        }
    }

  private def isValidDate(invalidDateError: String): Constraint[(String, MonthEntered, String)] =
    Constraint("constraints.date") { ymd: (String, MonthEntered, String) =>
      Try(LocalDate.of(ymd._1.toInt, ymd._2.value, ymd._3.toInt)) match {
        case Success(_) => Valid
        case _          => Invalid(Seq(ValidationError(invalidDateError, dateDescription)))
      }
    }

}
