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

import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.wordspec.AnyWordSpec
import play.api.data.Forms.mapping
import play.api.data.{Form, FormError}
import uk.gov.hmrc.govukfrontend.views.viewmodels.date.{DateEntered, GovUkDate, MonthEntered}
import uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput.DateValidationSupport._
import uk.gov.hmrc.helpers.MessagesSupport

import java.time.LocalDate

class DateValidationSupportSpec extends AnyWordSpec with Matchers with MessagesSupport with TableDrivenPropertyChecks {

  trait Setup {
    case class SomeFormWithDate(dateOfBirth: GovUkDate)

    val testForm: Form[SomeFormWithDate] = Form[SomeFormWithDate](
      mapping(
        "dateOfBirth" -> govUkDate()
//          .verifying("my.own.error.before", _.isBefore(LocalDate.now()))
      )(SomeFormWithDate.apply)(SomeFormWithDate.unapply)
    )

    def formData(day: String, month: String, year: String): Map[String, String] = Map(
      "dateOfBirth.day"   -> day,
      "dateOfBirth.month" -> month,
      "dateOfBirth.year"  -> year
    )
  }

  "DateValidationSupport.govukDate" when {
    "date is valid" should {

      "convert valid date with various months to LocalDate" should {
        val months = Table(
          ("month", "expectedMonth"),
          // English month names
          ("January", 1),
          ("February", 2),
          ("March", 3),
          ("April", 4),
          ("May", 5),
          ("June", 6),
          ("July", 7),
          ("August", 8),
          ("September", 9),
          ("October", 10),
          ("November", 11),
          ("December", 12),
          // English abbreviated month names, where different
          ("Jan", 1),
          ("Feb", 2),
          ("Mar", 3),
          ("Apr", 4),
          ("Jun", 6),
          ("Jul", 7),
          ("Aug", 8),
          ("Sep", 9),
          ("Oct", 10),
          ("Nov", 11),
          ("Dec", 12),
          ("3", 3),
          // Welsh month names
          ("Ionawr", 1),
          ("Chwefror", 2),
          ("Mawrth", 3),
          ("Ebrill", 4),
          ("Mai", 5),
          ("Mehefin", 6),
          ("Gorffennaf", 7),
          ("Awst", 8),
          ("Medi", 9),
          ("Hydref", 10),
          ("Tachwedd", 11),
          ("Rhagfyr", 12),
          // Welsh abbreviated month names, where different (according to the library we're using)
          ("Ion", 1),
          ("Chwef", 2),
          ("Maw", 3),
          ("Meh", 6),
          ("Gorff", 7),
          ("Hyd", 10),
          ("Tach", 11),
          ("Rhag", 12)
        )

        forAll(months) { (enteredMonth: String, expectedMonth: Int) =>
          s"work for month $enteredMonth" in new Setup {
            val day                          = "1"
            val year                         = "2023"
            val form: Form[SomeFormWithDate] = testForm.bind(formData(day, enteredMonth, year))
            form.errors should be(Nil)
            form.value  should be(
              Some(
                SomeFormWithDate(
                  GovUkDate(
                    DateEntered(day, MonthEntered(enteredMonth, expectedMonth), year),
                    LocalDate.of(year.toInt, expectedMonth, day.toInt)
                  )
                )
              )
            )
          }
        }
      }

      "convert valid date with numeric month to LocalDate" in new Setup {
        val form: Form[SomeFormWithDate] = testForm.bind(formData("1", "12", "2023"))
        form.errors should be(Nil)
        form.value  should be(
          Some(SomeFormWithDate(GovUkDate(DateEntered("1", MonthEntered("12", 12), "2023"), LocalDate.of(2023, 12, 1))))
        )
      }
    }

    "day is invalid or missing" should {
      "reject invalid date where day < 1" in new Setup {
        val form: Form[SomeFormWithDate] = testForm.bind(formData("0", "12", "2023"))
        form.errors should be(
          List(FormError("dateOfBirth.day", Seq("govuk.dateInput.error.date.invalid"), Seq("Date")))
        )
      }

      "reject invalid date where day > 31" in new Setup {
        val form: Form[SomeFormWithDate] = testForm.bind(formData("32", "12", "2023"))
        form.errors should be(
          List(FormError("dateOfBirth.day", Seq("govuk.dateInput.error.date.invalid"), Seq("Date")))
        )
      }

      "reject invalid date where day is not numeric" in new Setup {
        val form: Form[SomeFormWithDate] = testForm.bind(formData("foo", "12", "2023"))
        form.errors should be(
          List(FormError("dateOfBirth.day", Seq("govuk.dateInput.error.date.invalid"), Seq("Date")))
        )
      }

      "reject invalid date where day is empty" in new Setup {
        val form: Form[SomeFormWithDate] = testForm.bind(formData("  ", "12", "2023"))
        form.errors should be(List(FormError("dateOfBirth.day", Seq("govuk.dateInput.error.day.missing"), Seq("Date"))))
      }

      "support custom error message where day is missing" in new Setup {
        override val testForm: Form[SomeFormWithDate] = Form[SomeFormWithDate](
          mapping(
            "dateOfBirth" -> govUkDate(missingDayError = "my.custom.error")
          )(SomeFormWithDate.apply)(SomeFormWithDate.unapply)
        )
        val form: Form[SomeFormWithDate]              = testForm.bind(formData("  ", "12", "2023"))
        form.errors should be(List(FormError("dateOfBirth.day", Seq("my.custom.error"), Seq("Date"))))
      }
    }

    "month is invalid or missing" should {
      "reject invalid date where month < 1" in new Setup {
        val form: Form[SomeFormWithDate] = testForm.bind(formData("1", "0", "2023"))
        form.errors should be(
          List(FormError("dateOfBirth.month", Seq("govuk.dateInput.error.date.invalid"), Seq("Date")))
        )
      }

      "reject invalid date where month > 12" in new Setup {
        val form: Form[SomeFormWithDate] = testForm.bind(formData("1", "13", "2023"))
        form.errors should be(
          List(FormError("dateOfBirth.month", Seq("govuk.dateInput.error.date.invalid"), Seq("Date")))
        )
      }

      "reject invalid date where month is not a known English/Welsh month" in new Setup {
        val form: Form[SomeFormWithDate] = testForm.bind(formData("1", "Foo", "2023"))
        form.errors should be(
          List(FormError("dateOfBirth.month", Seq("govuk.dateInput.error.date.invalid"), Seq("Date")))
        )
      }

      "reject invalid date where month is missing" in new Setup {
        val form: Form[SomeFormWithDate] = testForm.bind(formData("1", "  ", "2023"))
        form.errors should be(
          List(FormError("dateOfBirth.month", Seq("govuk.dateInput.error.month.missing"), Seq("Date")))
        )
      }

      "support custom error message where month is missing" in new Setup {
        override val testForm: Form[SomeFormWithDate] = Form[SomeFormWithDate](
          mapping(
            "dateOfBirth" -> govUkDate(missingMonthError = "my.custom.error")
          )(SomeFormWithDate.apply)(SomeFormWithDate.unapply)
        )
        val form: Form[SomeFormWithDate]              = testForm.bind(formData("1", "  ", "2023"))
        form.errors should be(List(FormError("dateOfBirth.month", Seq("my.custom.error"), Seq("Date"))))
      }
    }

    "year is missing" should {
      "reject invalid date where year is missing" in new Setup {
        val form: Form[SomeFormWithDate] = testForm.bind(formData("1", "12", "  "))
        form.errors should be(
          List(FormError("dateOfBirth.year", Seq("govuk.dateInput.error.year.missing"), Seq("Date")))
        )
      }

      "support custom error message where year is missing" in new Setup {
        override val testForm: Form[SomeFormWithDate] = Form[SomeFormWithDate](
          mapping(
            "dateOfBirth" -> govUkDate(missingYearError = "my.custom.error")
          )(SomeFormWithDate.apply)(SomeFormWithDate.unapply)
        )
        val form: Form[SomeFormWithDate]              = testForm.bind(formData("1", "12", "  "))
        form.errors should be(List(FormError("dateOfBirth.year", Seq("my.custom.error"), Seq("Date"))))
      }
    }

    "multiple fields are empty" should {
      //If the date is incomplete
      //Highlight the day, month or year field where the information is missing or incomplete. If more than one field is missing information, highlight the fields the user needs to fill in.
      //
      //Say ‘[whatever it is] must include a [whatever is missing]’.
      //
      //For example, ‘Date of birth must include a month’, ‘Date of birth must include a day and month’ or ‘Year must include 4 numbers’.
      //
      "reject invalid date when day and month are missing" in {
        pending
      }
      "reject invalid date when day and year are missing" in {
        pending
      }
      "reject invalid date when month and year are missing" in {
        pending
      }
    }

    "nothing is entered" should {
      "prompt the user to enter something" in new Setup {
        val form: Form[SomeFormWithDate] = testForm.bind(formData("", "", ""))
        form.errors should be(List(FormError("dateOfBirth", Seq("govuk.dateInput.error.date.missing"), Nil)))
      }

      // If nothing is entered
      //Highlight the date input as a whole.
      //
      //Say ‘Enter [whatever it is]’. For example, ‘Enter your date of birth’.
      //
    }

    // Validation implemented by each service?  Or should we provide the prescribed error messages?
    //If the date is in the future when it needs to be in the past
    //If the date is in the future when it needs to be today or in the past
    //If the date is in the past when it needs to be in the future
    //If the date is in the past when it needs to be today or in the future
    //If the date must be the same as or after another date
    //If the date must be after another date
    //If the date must be the same as or before another date
    //If the date must be before another date
    //If the date must be between two dates

    "date is invalid/illogical" should {
      "reject invalid date" in new Setup {
        val form: Form[SomeFormWithDate] = testForm.bind(formData("30", "Feb", "2023"))
        form.errors should be(List(FormError("dateOfBirth", Seq("govuk.dateInput.error.date.invalid"), Seq("Date"))))
      }

      "support custom error message where date is invalid" in new Setup {
        override val testForm: Form[SomeFormWithDate] = Form[SomeFormWithDate](
          mapping(
            "dateOfBirth" -> govUkDate(invalidDateError = "my.invalid.date.error")
          )(SomeFormWithDate.apply)(SomeFormWithDate.unapply)
        )

        val form: Form[SomeFormWithDate] = testForm.bind(formData("30", "Feb", "2023"))
        form.errors should be(List(FormError("dateOfBirth", Seq("my.invalid.date.error"), Seq("Date"))))
      }
    }

    "date is bound back to the form" should {
      "bind entered month value back to the form data" in new Setup {
        val govukDate: GovUkDate         = GovUkDate("1", MonthEntered("dec", 12), "2023")
        val form: Form[SomeFormWithDate] = testForm.fill(SomeFormWithDate(govukDate))
        form.data should be(
          Map(
            "dateOfBirth.day"   -> "1",
            "dateOfBirth.month" -> "dec",
            "dateOfBirth.year"  -> "2023"
          )
        )
      }
    }
  }
}
