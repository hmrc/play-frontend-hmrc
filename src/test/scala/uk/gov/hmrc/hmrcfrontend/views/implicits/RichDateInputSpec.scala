/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.implicits

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.data.Forms.{mapping, text}
import play.api.data.{Field, Form, FormError}
import play.api.i18n.{Lang, Messages}
import play.api.inject.guice.GuiceApplicationBuilder
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput.{DateInput, InputItem}
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage.errorMessageWithDefaultStringsTranslated
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._

class RichDateInputSpec extends AnyWordSpec with Matchers with MessagesSupport with GuiceOneAppPerSuite {
  override def fakeApplication(): Application =
    new GuiceApplicationBuilder().configure(Map("play.i18n.langs" -> List("en", "cy"))).build()

  case class DateData(day: String, month: String, year: String)

  case class PageData(date: DateData)

  val form: Form[PageData] = Form[PageData](
    mapping = mapping(
      "date" -> mapping(
        "day"   -> text,
        "month" -> text,
        "year"  -> text
      )(DateData.apply)(DateData.unapply)
    )(PageData.apply)(PageData.unapply),
    data = Map(
      "date.day"   -> "1",
      "date.month" -> "2",
      "date.year"  -> "2020"
    ),
    errors = Seq.empty[FormError],
    value = None
  )

  val dateField: Field = Field(
    form = form,
    name = "date",
    constraints = Nil,
    format = None,
    errors = Seq.empty,
    value = None
  )

  val dateErrorField: Field = dateField.copy(
    errors = Seq(
      FormError(key = "date", "Not valid date")
    )
  )

  val dateMonthErrorField: Field = dateField.copy(
    form = form.copy(
      errors = Seq(
        FormError(key = "date", "Not valid date"),
        FormError(key = "date.month", "The date must include a month")
      )
    )
  )

  "Given a DateInput object, calling withFormField" should {
    "use the Field name as the id if no DateInput id is provided" in {
      val dateInput = DateInput().withFormField(dateField)
      dateInput.id shouldBe "date"
    }

    "use the DateInput id over the Field name if both exist" in {
      val dateInput = DateInput(id = "date-input-id").withFormField(dateField)
      dateInput.id shouldBe "date-input-id"
    }

    "create three InputItems" in {
      val dateInput = DateInput().withFormField(dateField)

      dateInput.items should have length 3
    }

    "populate the labels for the three InputItems" in {
      val dateInput = DateInput().withFormField(dateField)

      dateInput.items.head.label.get shouldBe "Day"
      dateInput.items(1).label.get   shouldBe "Month"
      dateInput.items(2).label.get   shouldBe "Year"
    }

    "populate the labels for the three InputItems in Welsh" in {
      implicit val messages: Messages = messagesApi.preferred(Seq(Lang("cy")))

      val dateInput = DateInput().withFormField(dateField)

      dateInput.items.head.label.get shouldBe "Diwrnod"
      dateInput.items(1).label.get   shouldBe "Mis"
      dateInput.items(2).label.get   shouldBe "Blwyddyn"
    }

    "populate the id for each of the three InputItems" in {
      val dateInput = DateInput().withFormField(dateField)

      dateInput.items.head.id shouldBe "date.day"
      dateInput.items(1).id   shouldBe "date.month"
      dateInput.items(2).id   shouldBe "date.year"
    }

    "not overwrite any previously populated ids for each of the three InputItems" in {
      val dateInput = DateInput(items =
        Seq(
          InputItem(id = "day-different"),
          InputItem(id = "month-different"),
          InputItem(id = "year-different")
        )
      ).withFormField(dateField)

      dateInput.items.head.id shouldBe "day-different"
      dateInput.items(1).id   shouldBe "month-different"
      dateInput.items(2).id   shouldBe "year-different"
    }

    "populate the name for each of the three InputItems" in {
      val dateInput = DateInput().withFormField(dateField)

      dateInput.items.head.name shouldBe "date.day"
      dateInput.items(1).name   shouldBe "date.month"
      dateInput.items(2).name   shouldBe "date.year"
    }

    "not overwrite any previously populated names for each of the three InputItems" in {
      val dateInput = DateInput(items =
        Seq(
          InputItem(name = "day-different"),
          InputItem(name = "month-different"),
          InputItem(name = "year-different")
        )
      ).withFormField(dateField)

      dateInput.items.head.name shouldBe "day-different"
      dateInput.items(1).name   shouldBe "month-different"
      dateInput.items(2).name   shouldBe "year-different"
    }

    "overwrite any previously populated InputItems where less than three are passed" in {
      val dateInput = DateInput(items =
        Seq(
          InputItem(name = "day-different"),
          InputItem(name = "month-different")
        )
      ).withFormField(dateField)

      dateInput.items.head.name shouldBe "date.day"
      dateInput.items(1).name   shouldBe "date.month"
      dateInput.items(2).name   shouldBe "date.year"
    }

    "populate the value for each of the three InputItems" in {
      val dateInput = DateInput().withFormField(dateField)

      dateInput.items.head.value.get shouldBe "1"
      dateInput.items(1).value.get   shouldBe "2"
      dateInput.items(2).value.get   shouldBe "2020"
    }

    "not overwrite any previously populated values for each of the three InputItems" in {
      val dateInput = DateInput(items =
        Seq(
          InputItem(value = Some("2")),
          InputItem(value = Some("3")),
          InputItem(value = Some("2021"))
        )
      ).withFormField(dateField)

      dateInput.items.head.value.get shouldBe "2"
      dateInput.items(1).value.get   shouldBe "3"
      dateInput.items(2).value.get   shouldBe "2021"
    }

    "populate the classes for each of the three InputItems" in {
      val dateInput = DateInput().withFormField(dateField)

      dateInput.items(0).classes should startWith("govuk-input--width-2")
      dateInput.items(1).classes should startWith("govuk-input--width-2")
      dateInput.items(2).classes should startWith("govuk-input--width-4")
    }

    "not overwrite any previously populated classes for each of the three InputItems" in {
      val dateInput = DateInput(items =
        Seq(
          InputItem(classes = "day-class"),
          InputItem(classes = "month-class"),
          InputItem(classes = "year-class")
        )
      ).withFormField(dateField)

      dateInput.items.head.classes shouldBe "day-class"
      dateInput.items(1).classes   shouldBe "month-class"
      dateInput.items(2).classes   shouldBe "year-class"
    }

    "preserve any other populated InputItem properties" in {
      val dateInput = DateInput(items =
        Seq(
          InputItem(autocomplete = Some("bday-day")),
          InputItem(autocomplete = Some("bday-month")),
          InputItem(autocomplete = Some("bday-year"))
        )
      ).withFormField(dateField)

      dateInput.items.head.autocomplete.get shouldBe "bday-day"
      dateInput.items(1).autocomplete.get   shouldBe "bday-month"
      dateInput.items(2).autocomplete.get   shouldBe "bday-year"
    }

    "populate an error class for an InputItem" in {
      val dateInput = DateInput().withFormField(dateMonthErrorField)

      dateInput.items(0).classes should not endWith "govuk-input--error"
      dateInput.items(1).classes should endWith("govuk-input--error")
      dateInput.items(2).classes should not endWith "govuk-input--error"
    }

    "populate an error class for InputItem with custom classes" in {
      val dateInput = DateInput(items =
        Seq(
          InputItem(classes = "day-class"),
          InputItem(classes = "month-class"),
          InputItem(classes = "year-class")
        )
      ).withFormField(dateMonthErrorField)

      dateInput.items(0).classes shouldBe "day-class"
      dateInput.items(1).classes shouldBe "month-class govuk-input--error"
      dateInput.items(2).classes shouldBe "year-class"
    }

    "populate the error message from a nested field first" in {
      val dateInput = DateInput().withFormField(dateMonthErrorField)

      dateInput.errorMessage shouldBe Some(
        errorMessageWithDefaultStringsTranslated(content = Text("The date must include a month"))
      )
    }

    "convert the first Field form error to a DateInput error message if provided" in {
      val dateInput = DateInput().withFormField(dateErrorField)
      dateInput.errorMessage shouldBe Some(errorMessageWithDefaultStringsTranslated(content = Text("Not valid date")))
    }

    "populate error css classes for all inputs in the case of a global date error" in {
      val dateInput = DateInput().withFormField(dateErrorField)
      dateInput.errorMessage shouldBe Some(errorMessageWithDefaultStringsTranslated(content = Text("Not valid date")))

      dateInput.items(0).classes should endWith("govuk-input--error")
      dateInput.items(1).classes should endWith("govuk-input--error")
      dateInput.items(2).classes should endWith("govuk-input--error")
    }

    "use the DateInput error message over the Field error if both provided" in {
      val dateInput = DateInput(
        errorMessage = Some(ErrorMessage(content = Text("DateInput Error")))
      ).withFormField(dateErrorField)

      dateInput.errorMessage shouldBe Some(ErrorMessage(content = Text("DateInput Error")))
    }
  }

  "Given a DateInput object, calling withFormFieldWithErrorAsHtml" should {
    "convert the first Field form error to a DateInput HTML error message if provided" in {
      val dateInput = DateInput().withFormFieldWithErrorAsHtml(dateErrorField)
      dateInput.errorMessage shouldBe Some(
        errorMessageWithDefaultStringsTranslated(content = HtmlContent("Not valid date"))
      )
    }

    "use the DateInput error message over the Field error if both provided" in {
      val dateInput = DateInput(
        errorMessage = Some(ErrorMessage(content = Text("DateInput Error")))
      ).withFormFieldWithErrorAsHtml(dateErrorField)
      dateInput.errorMessage shouldBe Some(ErrorMessage(content = Text("DateInput Error")))
    }
  }
}
