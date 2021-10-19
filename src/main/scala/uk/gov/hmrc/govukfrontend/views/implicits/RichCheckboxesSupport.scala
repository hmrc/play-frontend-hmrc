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

package uk.gov.hmrc.govukfrontend.views.implicits

import play.api.data.Field
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.Implicits.RichLegend
import uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes.{CheckboxItem, Checkboxes}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.Fieldset
import uk.gov.hmrc.hmrcfrontend.views.config.{HmrcPageHeadingLegend, HmrcSectionCaption}

trait RichCheckboxesSupport {

  implicit class RichCheckboxes(checkboxes: Checkboxes)(implicit val messages: Messages)
      extends ImplicitsSupport[Checkboxes] {

    /**
      * Extension method to allow a Play form Field to be used to add certain parameters in a Checkboxes,
      * specifically errorMessage, idPrefix, name, and checked (for a specific CheckboxItem). Note these
      * values will only be added from the Field if they are not specifically defined in the Checkboxes object.
      * Form errors will be bound as Text objects.
      *
      * @param field
      * @param messages
      */
    override def withFormField(field: Field): Checkboxes =
      checkboxes
        .withName(field)
        .withIdPrefix(field)
        .withTextErrorMessage(field)
        .withItemsChecked(field)

    /**
      * Extension method to allow a Play form Field to be used to add certain parameters in a Checkboxes, as per
      * withFormField, with form errors bound as HtmlContent objects.
      *
      * @param field
      * @param messages
      */

    override def withFormFieldWithErrorAsHtml(field: Field): Checkboxes =
      checkboxes
        .withName(field)
        .withIdPrefix(field)
        .withHtmlErrorMessage(field)
        .withItemsChecked(field)

    def withHeading(heading: Content): Checkboxes =
      withHeadingLegend(checkboxes, heading, None)((cb, ul) => cb.copy(fieldset = Some(ul.toFieldset)))

    def withHeadingAndSectionCaption(heading: Content, sectionCaption: Content): Checkboxes =
      withHeadingLegend(checkboxes, heading, Some(sectionCaption))((cb, ul) => cb.copy(fieldset = Some(ul.toFieldset)))

    private[views] def withName(field: Field): Checkboxes =
      withStringProperty(field.name, checkboxes.name, checkboxes)((cb, nm) => cb.copy(name = s"$nm[]"))

    private[views] def withIdPrefix(field: Field): Checkboxes =
      withOptStringProperty(Some(field.name), checkboxes.idPrefix, checkboxes)((cb, ip) => cb.copy(idPrefix = ip))

    private[views] def withTextErrorMessage(field: Field): Checkboxes =
      withOptTextErrorMessageProperty(field.error, checkboxes.errorMessage, checkboxes)((cb, errorMsg) =>
        cb.copy(errorMessage = errorMsg)
      )

    private[views] def withHtmlErrorMessage(field: Field): Checkboxes =
      withOptHtmlErrorMessageProperty(field.error, checkboxes.errorMessage, checkboxes)((cb, errorMsg) =>
        cb.copy(errorMessage = errorMsg)
      )

    private[views] def withItemsChecked(field: Field): Checkboxes =
      checkboxes.copy(
        items = checkboxes.items.map { checkboxItem =>
          if (checkboxItem.checked == CheckboxItem.defaultObject.checked) {
            val checkedValues = field.indexes.flatMap((i: Int) => field(s"[$i]").value)

            val isChecked = checkedValues.contains(checkboxItem.value)
            checkboxItem.copy(checked = isChecked)
          } else checkboxItem
        }
      )
  }
}
