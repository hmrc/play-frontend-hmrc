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
      * Deprecated method to allow a Play form Field to be used to populate parameters in a DateInput,
      * if they have not already been set to a non-default value. This method assumes that `dateInput.items`
      * will either equal `Seq.empty` or will have exactly three InputItems corresponding to the day, month and year.
      * Form errors will be bound as Text objects.
      *
      * @param field
      */
    @deprecated(
      "Use `withDayMonthYearFormField` instead",
      "10.1.0"
    )
    override def withFormField(field: Field): DateInput =
      dateInput
        .withId(field)
        .deprecatedWithInputItems(field)
        .withTextErrorMessage(field)

    /**
      * Extension method to allow a Play form Field to be used to populate parameters in a DateInput, as per
      * withFormField, with form errors bound as HtmlContent objects.
      *
      * @param field
      */
    @deprecated(
      "Use `withDayMonthYearWithErrorAsHtml` instead",
      "10.1.0"
    )
    override def withFormFieldWithErrorAsHtml(field: Field): DateInput =
      dateInput
        .withId(field)
        .deprecatedWithInputItems(field)
        .withHtmlErrorMessage(field)

    /**
     * Method to allow a Play form Field to be used to populate parameters in a DateInput. This method will populate
     * with three InputItems corresponding to the day, month and year. Form errors will be bound as Text objects.
     *
     * @param field
     */
    def withDayMonthYearFormField(field: Field): DateInput = {
      require(dateInput.items.isEmpty, "The DateInput `items` must be empty for withDayMonthYearFormField")
      dateInput
        .withId(field)
        .withDayMonthYearInputItems(field)
        .withTextErrorMessage(field)
   }

    /**
     * Method to allow a Play form Field to be used to populate parameters in a DateInput, with form errors bound as
     * HtmlContent objects.
     *
     * @param field
     */
    def withDayMonthYearWithErrorAsHtml(field: Field): DateInput = {
      require(dateInput.items.isEmpty, "The DateInput `items` must be empty for withDayMonthYearWithErrorAsHtml")
      dateInput
        .withId(field)
        .withDayMonthYearInputItems(field)
        .withHtmlErrorMessage(field)
    }

    def withHeading(heading: Content): DateInput =
      withHeadingLegend(dateInput, heading, None)((di, ul) => di.copy(fieldset = Some(ul.toFieldset)))

    def withHeadingAndSectionCaption(heading: Content, sectionCaption: Content): DateInput =
      withHeadingLegend(dateInput, heading, Some(sectionCaption))((di, ul) => di.copy(fieldset = Some(ul.toFieldset)))

    private[views] def withId(field: Field): DateInput =
      withStringProperty(field.name, dateInput.id, dateInput)((dateInput, id) => dateInput.copy(id = id))

    private[views] def deprecatedWithInputItems(field: Field): DateInput = {
      val dateInputItems: Seq[InputItem] =
        if (dateInput.items.size == 3) dateInput.items else Seq.fill(3)(InputItem.defaultObject)

      val items = Seq(
        deprecatedInputItem(field, dateInputItems(0), "day", className = "govuk-input--width-2"),
        deprecatedInputItem(field, dateInputItems(1), "month", className = "govuk-input--width-2"),
        deprecatedInputItem(field, dateInputItems(2), "year", className = "govuk-input--width-4")
      )

      dateInput.copy(items = items)
    }

    private def deprecatedInputItem(field: Field, inputItem: InputItem, key: String, className: String): InputItem = {
      def errorClass(itemField: Field) =
        if (field.errors.nonEmpty || itemField.errors.nonEmpty) "govuk-input--error" else ""

      val defaultInputItem = InputItem.defaultObject
      val classes          = if (inputItem.classes == defaultInputItem.classes) className else inputItem.classes

      inputItem.copy(
        id = if (inputItem.id == defaultInputItem.id) s"${field.name}.$key" else inputItem.id,
        name = if (inputItem.name == defaultInputItem.name) s"${field.name}.$key" else inputItem.name,
        value = if (inputItem.value == defaultInputItem.value) field(key).value else inputItem.value,
        label = if (inputItem.label == defaultInputItem.label) Some(messages(s"date.input.$key")) else inputItem.label,
        classes = s"$classes ${errorClass(field(key))}".trim
      )
    }

    private[views] def withDayMonthYearInputItems(field: Field): DateInput = {
      val items = defaultDateItems(field, Seq("day", "month", "year"))
      dateInput.copy(items = items)
    }

    private def defaultDateItems(field: Field, keyset: Seq[String]): Seq[InputItem] = {
      val allItems: Seq[InputItem] = Seq(
        defaultInputItem(field, "day", className = "govuk-input--width-2"),
        defaultInputItem(field, "month", className = "govuk-input--width-2"),
        defaultInputItem(field, "year", className = "govuk-input--width-4")
      )
      keyset.flatMap(key => allItems.find(_.name.endsWith(key)))
    }

    private def defaultInputItem(field: Field, key: String, className: String): InputItem = {
      def errorClass(itemField: Field) =
        if (field.errors.nonEmpty || itemField.errors.nonEmpty) "govuk-input--error" else ""

      InputItem(
        id = s"${field.name}.$key",
        name = s"${field.name}.$key",
        value = field(key).value,
        label = Some(messages(s"date.input.$key")),
        classes = s"$className ${errorClass(field(key))}".trim
      )
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
