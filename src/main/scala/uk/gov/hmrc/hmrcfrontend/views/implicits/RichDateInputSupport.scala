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

import play.api.data.Field
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.implicits.ImplicitsSupport
import uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput.{DateInput, InputItem}

trait RichDateInputSupport {

  implicit class RichDateInput(dateInput: DateInput)(implicit val messages: Messages)
      extends ImplicitsSupport[DateInput] {

    /**
      * Extension method to allow a Play form Field to be used to populate parameters in a DateInput,
      * if they have not already been set to a non-default value. This method assumes that `dateInput.items`
      * will either equal `Seq.empty` or will have exactly three InputItems corresponding to the day, month and year.
      * Form errors will be bound as Text objects.
      *
      * @param field
      */
    override def withFormField(field: Field): DateInput =
      dateInput
        .withId(field)
        .withInputItems(field)
        .withTextErrorMessage(field)

    /**
      * Extension method to allow a Play form Field to be used to populate parameters in a DateInput, as per
      * withFormField, with form errors bound as HtmlContent objects.
      *
      * @param field
      */
    override def withFormFieldWithErrorAsHtml(field: Field): DateInput =
      dateInput
        .withId(field)
        .withInputItems(field)
        .withHtmlErrorMessage(field)

    private[views] def withId(field: Field): DateInput =
      withStringProperty(field.name, dateInput.id, dateInput)((dateInput, id) => dateInput.copy(id = id))

    private[views] def withInputItems(field: Field): DateInput = {
      def errorClass(itemField: Field) =
        if (field.errors.nonEmpty || itemField.errors.nonEmpty) "govuk-input--error" else ""

      def inputItem(inputItem: InputItem, key: String, className: String): InputItem = {
        val defaultInputItem = InputItem.defaultObject
        val classes          = if (inputItem.classes == defaultInputItem.classes) className else inputItem.classes

        inputItem.copy(
          id = if (inputItem.id == defaultInputItem.id) s"${field.name}.$key" else inputItem.id,
          name = if (inputItem.name == defaultInputItem.name) s"${field.name}.$key" else inputItem.name,
          value = if (inputItem.value == defaultInputItem.value) field(key).value else inputItem.value,
          label =
            if (inputItem.label == defaultInputItem.label) Some(messages(s"date.input.$key")) else inputItem.label,
          classes = s"$classes ${errorClass(field(key))}".trim
        )
      }

      val dateInputItems = if (dateInput.items.size == 3) dateInput.items else Seq.fill(3)(InputItem.defaultObject)

      val items = Seq(
        inputItem(dateInputItems(0), "day", className = "govuk-input--width-2"),
        inputItem(dateInputItems(1), "month", className = "govuk-input--width-2"),
        inputItem(dateInputItems(2), "year", className = "govuk-input--width-4")
      )

      dateInput.copy(items = items)
    }

    private[views] def withTextErrorMessage(field: Field): DateInput = {
      val error = Seq(
        field("day").error,
        field("month").error,
        field("year").error,
        field.error
      ).flatten.headOption

      withOptTextErrorMessageProperty(error, dateInput.errorMessage, dateInput)((dateInput, errorMsg) =>
        dateInput.copy(errorMessage = errorMsg)
      )
    }

    private[views] def withHtmlErrorMessage(field: Field): DateInput = {
      val error = Seq(
        field("day").error,
        field("month").error,
        field("year").error,
        field.error
      ).flatten.headOption

      withOptHtmlErrorMessageProperty(error, dateInput.errorMessage, dateInput)((dateInput, errorMsg) =>
        dateInput.copy(errorMessage = errorMsg)
      )
    }
  }
}
