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

package uk.gov.hmrc.hmrcfrontend.views.implicits

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.data.Forms.{mapping, text}
import play.api.data.{Field, Form, FormError}
import play.api.i18n.{Lang, Messages}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput.{DateInput, InputItem}
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage.errorMessageWithDefaultStringsTranslated
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.{Fieldset, Legend}
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.config.{HmrcPageHeadingLegend, HmrcSectionCaption}
import uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._

class RichDateInputSpec extends AnyWordSpec with Matchers with MessagesSupport {

  case class DateData(day: String, month: String, year: String)

  case class PageData(date: DateData)

  val form: Form[PageData] = Form[PageData](
    mapping = mapping(
      "date" -> mapping(
        "day"   -> text,
        "month" -> text,
        "year"  -> text
      )(DateData.apply)(dd => Some((dd.day, dd.month, dd.year)))
    )(PageData.apply)(pd => Some(pd.date)),
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

  "Given a DateInput object, calling the deprecated withFormField method" should {
    "use the Field name as the id if no DateInput id is provided" in {
      val dateInput = DateInput().withFormField(dateField)
      dateInput.id shouldBe "date"
    }

    "use the DateInput id over the Field name if both exist" in {
      val dateInput = DateInput(id = "custom-id").withFormField(dateField)
      dateInput.id shouldBe "custom-id"
    }

    "create three InputItems" in {
      val dateInput = DateInput().withFormField(dateField)
      dateInput.items should have length 3
    }

    "populate the labels for the three InputItems" in {
      val dateInput = DateInput().withFormField(dateField)
      dateInput.items.flatMap(_.label) shouldBe Seq("Day", "Month", "Year")
    }

    "populate the labels for the three InputItems in Welsh" in {
      implicit val messages: Messages = messagesApi.preferred(Seq(Lang("cy")))
      val dateInput                   = DateInput().withFormField(dateField)
      dateInput.items.flatMap(_.label) shouldBe Seq("Diwrnod", "Mis", "Blwyddyn")
    }

    "populate the id for each of the three InputItems" in {
      val dateInput = DateInput().withFormField(dateField)
      dateInput.items.map(_.id) shouldBe Seq("date.day", "date.month", "date.year")
    }

    "not overwrite any previously populated ids for each of the three InputItems" in {
      val dateInput = DateInput(items =
        Seq(
          InputItem(id = "day-different"),
          InputItem(id = "month-different"),
          InputItem(id = "year-different")
        )
      ).withFormField(dateField)
      dateInput.items.map(_.id) shouldBe Seq("day-different", "month-different", "year-different")
    }

    "populate the name for each of the three InputItems" in {
      val dateInput = DateInput().withFormField(dateField)
      dateInput.items.map(_.name) shouldBe Seq("date.day", "date.month", "date.year")
    }

    "not overwrite any previously populated names for each of the three InputItems" in {
      val dateInput = DateInput(items =
        Seq(
          InputItem(name = "day-different"),
          InputItem(name = "month-different"),
          InputItem(name = "year-different")
        )
      ).withFormField(dateField)
      dateInput.items.map(_.name) shouldBe Seq("day-different", "month-different", "year-different")
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

  "Given a DateInput object, calling the withDayMonthYearFormField method" should {
    "use the Field name as the id if no DateInput id is provided" in {
      val dateInput = DateInput().withDayMonthYearFormField(dateField)
      dateInput.id shouldBe "date"
    }

    "use the DateInput id over the Field name if both exist" in {
      val dateInput = DateInput(id = "custom-id").withDayMonthYearFormField(dateField)
      dateInput.id shouldBe "custom-id"
    }

    "create three default InputItems (day, month, year)" in {
      val dateInput = DateInput().withDayMonthYearFormField(dateField)

      dateInput.items shouldBe Seq(
        InputItem(
          id = "date.day",
          name = "date.day",
          value = Some("1"),
          label = Some("Day"),
          classes = "govuk-input--width-2"
        ),
        InputItem(
          id = "date.month",
          name = "date.month",
          value = Some("2"),
          label = Some("Month"),
          classes = "govuk-input--width-2"
        ),
        InputItem(
          id = "date.year",
          name = "date.year",
          value = Some("2020"),
          label = Some("Year"),
          classes = "govuk-input--width-4"
        )
      )
    }

    "pass the Field `name` through to individual fields if set" in {
      val dateInput = DateInput().withDayMonthYearFormField(dateField.copy(name = "custom-name"))
      dateInput.items.map(_.id)   shouldBe Seq("custom-name.day", "custom-name.month", "custom-name.year")
      dateInput.items.map(_.name) shouldBe Seq("custom-name.day", "custom-name.month", "custom-name.year")
    }

    "throw an exception if passed previously populated items" in {
      val prePopulatedItems = Seq(
        InputItem(id = "day-different", name = "day-different", value = Some("2"), classes = "day-class"),
        InputItem(id = "month-different", name = "month-different", value = Some("3"), classes = "month-class"),
        InputItem(id = "year-different", name = "year-different", value = Some("2021"), classes = "year-class")
      )

      val exception =
        intercept[RuntimeException](DateInput(items = prePopulatedItems).withDayMonthYearFormField(dateField))
      exception            shouldBe a[IllegalArgumentException]
      exception.getMessage shouldBe "requirement failed: The DateInput `items` must be empty for withDayMonthYearFormField"
    }

    "populate the labels for the InputItems in Welsh" in {
      implicit val messages: Messages = messagesApi.preferred(Seq(Lang("cy")))
      val dateInput                   = DateInput().withDayMonthYearFormField(dateField)
      dateInput.items.flatMap(_.label) shouldBe Seq("Diwrnod", "Mis", "Blwyddyn")
    }

    "populate an error class for an InputItem" in {
      val dateInput = DateInput().withDayMonthYearFormField(dateMonthErrorField)

      dateInput.items(0).classes should not endWith "govuk-input--error"
      dateInput.items(1).classes should endWith("govuk-input--error")
      dateInput.items(2).classes should not endWith "govuk-input--error"
    }

    "populate the error message from a nested field first" in {
      val dateInput = DateInput().withDayMonthYearFormField(dateMonthErrorField)

      dateInput.errorMessage shouldBe Some(
        errorMessageWithDefaultStringsTranslated(content = Text("The date must include a month"))
      )
    }

    "convert the first Field form error to a DateInput error message if provided" in {
      val dateInput = DateInput().withDayMonthYearFormField(dateErrorField)
      dateInput.errorMessage shouldBe Some(errorMessageWithDefaultStringsTranslated(content = Text("Not valid date")))
    }

    "populate error css classes for all inputs in the case of a global date error" in {
      val dateInput = DateInput().withDayMonthYearFormField(dateErrorField)
      dateInput.errorMessage shouldBe Some(errorMessageWithDefaultStringsTranslated(content = Text("Not valid date")))

      dateInput.items(0).classes should endWith("govuk-input--error")
      dateInput.items(1).classes should endWith("govuk-input--error")
      dateInput.items(2).classes should endWith("govuk-input--error")
    }

    "use the DateInput error message over the Field error if both provided" in {
      val dateInput = DateInput(
        errorMessage = Some(ErrorMessage(content = Text("DateInput Error")))
      ).withDayMonthYearFormField(dateErrorField)

      dateInput.errorMessage shouldBe Some(ErrorMessage(content = Text("DateInput Error")))
    }
  }

  "Given a DateInput object, calling the withMonthYearFormField method" should {
    "use the Field name as the id if no DateInput id is provided" in {
      val dateInput = DateInput().withMonthYearFormField(dateField)
      dateInput.id shouldBe "date"
    }

    "use the DateInput id over the Field name if both exist" in {
      val dateInput = DateInput(id = "custom-id").withMonthYearFormField(dateField)
      dateInput.id shouldBe "custom-id"
    }

    "create two default InputItems (month, year)" in {
      val dateInput = DateInput().withMonthYearFormField(dateField)

      dateInput.items shouldBe Seq(
        InputItem(
          id = "date.month",
          name = "date.month",
          value = Some("2"),
          label = Some("Month"),
          classes = "govuk-input--width-2"
        ),
        InputItem(
          id = "date.year",
          name = "date.year",
          value = Some("2020"),
          label = Some("Year"),
          classes = "govuk-input--width-4"
        )
      )
    }

    "pass the Field `name` through to individual fields if set" in {
      val dateInput = DateInput().withMonthYearFormField(dateField.copy(name = "custom-name"))
      dateInput.items.map(_.id)   shouldBe Seq("custom-name.month", "custom-name.year")
      dateInput.items.map(_.name) shouldBe Seq("custom-name.month", "custom-name.year")
    }

    "throw an exception if passed previously populated items" in {
      val prePopulatedItems = Seq(
        InputItem(id = "day-different", name = "day-different", value = Some("2"), classes = "day-class"),
        InputItem(id = "month-different", name = "month-different", value = Some("3"), classes = "month-class"),
        InputItem(id = "year-different", name = "year-different", value = Some("2021"), classes = "year-class")
      )

      val exception =
        intercept[RuntimeException](DateInput(items = prePopulatedItems).withMonthYearFormField(dateField))
      exception            shouldBe a[IllegalArgumentException]
      exception.getMessage shouldBe "requirement failed: The DateInput `items` must be empty for withMonthYearFormField"
    }

    "populate the labels for the InputItems in Welsh" in {
      implicit val messages: Messages = messagesApi.preferred(Seq(Lang("cy")))
      val dateInput                   = DateInput().withMonthYearFormField(dateField)
      dateInput.items.flatMap(_.label) shouldBe Seq("Mis", "Blwyddyn")
    }

    "populate an error class for an InputItem" in {
      val dateInput = DateInput().withMonthYearFormField(dateMonthErrorField)
      dateInput.items(0).classes should endWith("govuk-input--error")
      dateInput.items(1).classes should not endWith "govuk-input--error"
    }

    "populate the error message from a nested field first" in {
      val dateInput = DateInput().withMonthYearFormField(dateMonthErrorField)
      dateInput.errorMessage shouldBe Some(
        errorMessageWithDefaultStringsTranslated(content = Text("The date must include a month"))
      )
    }

    "convert the first Field form error to a DateInput error message if provided" in {
      val dateInput = DateInput().withMonthYearFormField(dateErrorField)
      dateInput.errorMessage shouldBe Some(errorMessageWithDefaultStringsTranslated(content = Text("Not valid date")))
    }

    "populate error css classes for all inputs in the case of a global date error" in {
      val dateInput = DateInput().withMonthYearFormField(dateErrorField)
      dateInput.errorMessage shouldBe Some(errorMessageWithDefaultStringsTranslated(content = Text("Not valid date")))

      dateInput.items(0).classes should endWith("govuk-input--error")
      dateInput.items(1).classes should endWith("govuk-input--error")
    }

    "use the DateInput error message over the Field error if both provided" in {
      val dateInput = DateInput(
        errorMessage = Some(ErrorMessage(content = Text("DateInput Error")))
      ).withMonthYearFormField(dateErrorField)

      dateInput.errorMessage shouldBe Some(ErrorMessage(content = Text("DateInput Error")))
    }
  }

  "Given a DateInput object, calling the deprecated withFormFieldWithErrorAsHtml method" should {
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

  "Given a DateInput object, calling the withDayMonthYearWithErrorAsHtml method" should {
    "convert the first Field form error to a DateInput HTML error message if provided" in {
      val dateInput = DateInput().withDayMonthYearWithErrorAsHtml(dateErrorField)
      dateInput.errorMessage shouldBe Some(
        errorMessageWithDefaultStringsTranslated(content = HtmlContent("Not valid date"))
      )
    }

    "Given a DateInput object, calling the withMonthYearWithErrorAsHtml method" should {
      "convert the first Field form error to a DateInput HTML error message if provided" in {
        val dateInput = DateInput().withMonthYearWithErrorAsHtml(dateErrorField)
        dateInput.errorMessage shouldBe Some(
          errorMessageWithDefaultStringsTranslated(content = HtmlContent("Not valid date"))
        )
      }

      "use the DateInput error message over the Field error if both provided" in {
        val dateInput = DateInput(
          errorMessage = Some(ErrorMessage(content = Text("DateInput Error")))
        ).withMonthYearWithErrorAsHtml(dateErrorField)
        dateInput.errorMessage shouldBe Some(ErrorMessage(content = Text("DateInput Error")))
      }
    }

    "use the DateInput error message over the Field error if both provided" in {
      val dateInput = DateInput(
        errorMessage = Some(ErrorMessage(content = Text("DateInput Error")))
      ).withDayMonthYearWithErrorAsHtml(dateErrorField)
      dateInput.errorMessage shouldBe Some(ErrorMessage(content = Text("DateInput Error")))
    }
  }

  "Given a DateInput object, calling withHeading" should {
    "set the fieldset legend to the passed in content" in {
      val dateInput = DateInput().withHeading(Text("This is a text heading"))
      dateInput shouldBe DateInput(fieldset =
        Some(
          Fieldset(
            legend = Some(HmrcPageHeadingLegend(content = Text("This is a text heading")))
          )
        )
      )
    }

    "set the fieldset legend to the passed in content, overriding any previously set content" in {
      val dateInputWithLegend =
        DateInput(fieldset =
          Some(Fieldset(legend = Some(Legend(content = Text("This is some original text heading")))))
        )
      val updatedDateInput    = dateInputWithLegend.withHeading(Text("This is some updated text heading"))
      updatedDateInput shouldBe DateInput(fieldset =
        Some(
          Fieldset(
            legend = Some(HmrcPageHeadingLegend(content = Text("This is some updated text heading")))
          )
        )
      )
    }
  }

  "Given a DateInput object, calling withHeadingAndSectionCaption" should {
    "set the fieldset legend to the passed in content" in {
      val dateInput =
        DateInput().withHeadingAndSectionCaption(Text("This is a text heading"), Text("This is a text caption"))
      dateInput shouldBe DateInput(fieldset =
        Some(
          Fieldset(
            legend = Some(
              HmrcPageHeadingLegend(
                content = Text("This is a text heading"),
                caption = HmrcSectionCaption(Text("This is a text caption"))
              )
            )
          )
        )
      )
    }

    "set the fieldset legend to the passed in content, overriding any previously set content" in {
      val dateInputWithLegend = DateInput(fieldset =
        Some(
          Fieldset(legend =
            Some(
              Legend(
                content = Text("This is some original text heading")
              )
            )
          )
        )
      )
      val updatedDateInput    = dateInputWithLegend.withHeadingAndSectionCaption(
        Text("This is some updated text heading"),
        Text("This is some updated text caption")
      )
      updatedDateInput shouldBe DateInput(fieldset =
        Some(
          Fieldset(
            legend = Some(
              HmrcPageHeadingLegend(
                content = Text("This is some updated text heading"),
                caption = HmrcSectionCaption(Text("This is some updated text caption"))
              )
            )
          )
        )
      )
    }
  }
}
