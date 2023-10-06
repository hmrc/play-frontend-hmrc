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

import DateValidationSupport._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.data.{Form, FormError}
import play.api.data.Forms.mapping
import play.api.i18n.{Lang, Messages}
import uk.gov.hmrc.helpers.MessagesSupport

import java.time.LocalDate
import scala.List

class DateValidationSupportSpec extends AnyWordSpec with Matchers with MessagesSupport {

  trait Setup {
    case class SomeFormWithDate(dateOfBirth: LocalDate)

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
    "convert valid date with numeric month to LocalDate" in new Setup {
      val form: Form[SomeFormWithDate] = testForm.bind(formData("1", "12", "2023"))
      form.errors should be(Nil)
      form.value  should be(Some(SomeFormWithDate(LocalDate.of(2023, 12, 1))))
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
  }
}
