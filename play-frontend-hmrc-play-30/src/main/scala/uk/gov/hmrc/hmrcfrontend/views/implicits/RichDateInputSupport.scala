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

import play.api.data.Field
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.Implicits.RichLegend
import uk.gov.hmrc.govukfrontend.views.implicits.ImplicitsSupport
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content
import uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput.{DateInput, InputItem}
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.Fieldset
import uk.gov.hmrc.hmrcfrontend.views.config.{HmrcPageHeadingLegend, HmrcSectionCaption}

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

    def withHeading(heading: Content): DateInput =
      withHeadingLegend(dateInput, heading, None)((di, ul) => di.copy(fieldset = Some(ul.toFieldset)))

    def withHeadingAndSectionCaption(heading: Content, sectionCaption: Content): DateInput =
      withHeadingLegend(dateInput, heading, Some(sectionCaption))((di, ul) => di.copy(fieldset = Some(ul.toFieldset)))

    def withMonthAndYearOnly(field: Field): DateInput =
      dateInput.copy(items =
        Seq(
          InputItem(
            id = s"${field.name}.month",
            name = s"${field.name}.month",
            value = field("month").value,
            label = Some(messages(s"date.input.month")),
            classes = "govuk-input--width-2"
          ),
          InputItem(
            id = s"${field.name}.year",
            name = s"${field.name}.year",
            value = field("year").value,
            label = Some(messages(s"date.input.year")),
            classes = "govuk-input--width-4"
          )
        )
      )

    def withDayAndMonth(field: Field): DateInput =
      dateInput.copy(items =
        Seq(
          InputItem(
            id = s"${field.name}.day",
            name = s"${field.name}.day",
            value = field("day").value,
            label = Some(messages(s"date.input.day")),
            classes = "govuk-input--width-2"
          ),
          InputItem(
            id = s"${field.name}.month",
            name = s"${field.name}.month",
            value = field("month").value,
            label = Some(messages(s"date.input.month")),
            classes = "govuk-input--width-2"
          )
        )
      )

    def withDayMonthYear(field: Field): DateInput =
      dateInput.copy(items =
        Seq(
          InputItem(
            id = s"${field.name}.day",
            name = s"${field.name}.day",
            value = field("day").value,
            label = Some(messages(s"date.input.day")),
            classes = "govuk-input--width-2"
          ),
          InputItem(
            id = s"${field.name}.month",
            name = s"${field.name}.month",
            value = field("month").value,
            label = Some(messages(s"date.input.month")),
            classes = "govuk-input--width-2"
          ),
          InputItem(
            id = s"${field.name}.year",
            name = s"${field.name}.year",
            value = field("year").value,
            label = Some(messages(s"date.input.year")),
            classes = "govuk-input--width-4"
          )
        )
      )

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

      dateInput

//      val items: Seq[InputItem] =
//        if (dateInput.items.isEmpty) Seq(
//                inputItem(InputItem.defaultObject, "day", className = "govuk-input--width-2"),
//                inputItem(InputItem.defaultObject, "month", className = "govuk-input--width-2"),
//                inputItem(InputItem.defaultObject, "year", className = "govuk-input--width-4")
//              )
//        else dateInput.items
//
//      dateInput.copy(items = items)
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
