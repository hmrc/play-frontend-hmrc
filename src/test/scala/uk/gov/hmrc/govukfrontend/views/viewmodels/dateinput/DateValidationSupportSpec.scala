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
        "dateOfBirth" -> govukDate
      )(SomeFormWithDate.apply)(SomeFormWithDate.unapply)
    )

    def formData(day: String, month: String, year: String): Map[String, String] = Map(
      "dateOfBirth.day"   -> day,
      "dateOfBirth.month" -> month,
      "dateOfBirth.year"  -> year
    )
  }

  "DateValidationSupport.govukDate" should {
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
                  DateEntered(day.toInt, MonthEntered(enteredMonth, expectedMonth), year.toInt),
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
        Some(SomeFormWithDate(GovUkDate(DateEntered(1, MonthEntered("12", 12), 2023), LocalDate.of(2023, 12, 1))))
      )
    }

    "reject invalid date where day < 1" in new Setup {
      val form: Form[SomeFormWithDate] = testForm.bind(formData("0", "12", "2023"))
      form.errors should be(List(FormError("dateOfBirth.day", Seq("error.min"), Seq(1))))
    }

    "reject invalid date where day > 31" in new Setup {
      val form: Form[SomeFormWithDate] = testForm.bind(formData("32", "12", "2023"))
      form.errors should be(List(FormError("dateOfBirth.day", Seq("error.max"), Seq(31))))
    }

    "reject invalid date where month < 1" in new Setup {
      val form: Form[SomeFormWithDate] = testForm.bind(formData("1", "0", "2023"))
      form.errors should be(List(FormError("dateOfBirth.month", Seq("error.invalid"), Nil)))
    }

    "reject invalid date where month > 12" in new Setup {
      val form: Form[SomeFormWithDate] = testForm.bind(formData("1", "13", "2023"))
      form.errors should be(List(FormError("dateOfBirth.month", Seq("error.invalid"), Nil)))
    }

    "reject invalid date where month is not a known English/Welsh month" in new Setup {
      val form: Form[SomeFormWithDate] = testForm.bind(formData("1", "Foo", "2023"))
      form.errors should be(List(FormError("dateOfBirth.month", Seq("error.invalid"), Nil)))
    }

    "bind entered value back to the form data" in new Setup {
      val govukDate: GovUkDate = GovUkDate(1, MonthEntered("dec", 12), 2023)
      val form: Form[SomeFormWithDate] = testForm.fill(SomeFormWithDate(govukDate))
      form.data should be(Map(
        "dateOfBirth.day"   -> "1",
        "dateOfBirth.month" -> "dec",
        "dateOfBirth.year"  -> "2023"
      ))
    }
  }
}
