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

package uk.gov.hmrc.govukfrontend.views.implicits

import play.api.data.Field
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.Implicits.RichLegend
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.Fieldset
import uk.gov.hmrc.govukfrontend.views.viewmodels.radios.{RadioItem, Radios}

trait RichRadiosSupport {

  implicit class RichRadios(radios: Radios)(implicit val messages: Messages) extends ImplicitsSupport[Radios] {

    /**
      * Extension method to allow a Play form Field to be used to add certain parameters in a Radios,
      * specifically errorMessage, idPrefix, name, and checked (for a specific RadioItem). Note these
      * values will only be added from the Field if they are not specifically defined in the Radios object.
      * Form errors will be bound as Text objects.
      *
      * @param field
      * @param messages
      */
    override def withFormField(field: Field): Radios =
      radios
        .withName(field)
        .withIdPrefix(field)
        .withItemsChecked(field)
        .withTextErrorMessage(field)

    /**
      * Extension method to allow a Play form Field to be used to add certain parameters in a Radios, as per
      * withFormField, with form errors bound as HtmlContent objects.
      *
      * @param field
      * @param messages
      */
    override def withFormFieldWithErrorAsHtml(field: Field): Radios =
      radios
        .withName(field)
        .withIdPrefix(field)
        .withItemsChecked(field)
        .withHtmlErrorMessage(field)

    def withHeading(heading: Content): Radios =
      withHeadingLegend(radios, heading, None)((rd, ul) => rd.copy(fieldset = Some(ul.toFieldset)))

    def withHeadingAndSectionCaption(heading: Content, sectionCaption: Content): Radios =
      withHeadingLegend(radios, heading, Some(sectionCaption))((rd, ul) => rd.copy(fieldset = Some(ul.toFieldset)))

    private[views] def withName(field: Field): Radios =
      withStringProperty(field.name, radios.name, radios)((rds, nm) => rds.copy(name = nm))

    private[views] def withIdPrefix(field: Field): Radios =
      withOptStringProperty(Some(field.name), radios.idPrefix, radios)((rds, ip) => rds.copy(idPrefix = ip))

    private[views] def withTextErrorMessage(field: Field): Radios =
      withOptTextErrorMessageProperty(field.error, radios.errorMessage, radios)((rds, errorMsg) =>
        rds.copy(errorMessage = errorMsg)
      )

    private[views] def withHtmlErrorMessage(field: Field): Radios =
      withOptHtmlErrorMessageProperty(field.error, radios.errorMessage, radios)((rds, errorMsg) =>
        rds.copy(errorMessage = errorMsg)
      )

    private[views] def withItemsChecked(field: Field): Radios =
      radios.copy(
        items = radios.items.map { radioItem =>
          if (radioItem.checked == RadioItem.defaultObject.checked && radioItem.divider.isEmpty) {
            val isChecked = radioItem.value == field.value
            radioItem.copy(checked = isChecked)
          } else radioItem
        }
      )
  }
}
