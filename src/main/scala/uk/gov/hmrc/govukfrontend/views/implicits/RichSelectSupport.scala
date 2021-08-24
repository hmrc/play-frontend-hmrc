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
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content
import uk.gov.hmrc.govukfrontend.views.viewmodels.select.{Select, SelectItem}

trait RichSelectSupport {

  implicit class RichSelect(select: Select)(implicit val messages: Messages) extends ImplicitsSupport[Select] {

    /**
      * Extension method to allow a Play form Field to be used to add certain parameters in a Select, specifically
      * errorMessage, id, name, and selected (for a specific SelectItem). Note these values will only be added from the
      * Field if they are not specifically defined in the Select object.
      * Form errors will be bound as Text objects.
      *
      * @param field
      * @param messages
      */
    override def withFormField(field: Field): Select =
      select
        .withName(field)
        .withId(field)
        .withTextErrorMessage(field)
        .withItemSelected(field)

    /**
      * Extension method to allow a Play form Field to be used to add certain parameters in a Select, as per
      * withFormField, with form errors bound as HtmlContent objects.
      *
      * @param field
      * @param messages
      */
    override def withFormFieldWithErrorAsHtml(field: Field): Select =
      select
        .withName(field)
        .withId(field)
        .withHtmlErrorMessage(field)
        .withItemSelected(field)

    def withHeading(heading: Content): Select =
      withHeadingLabel(select, heading, None)((sl, ul) => sl.copy(label = ul))

    def withHeadingAndSectionCaption(heading: Content, sectionCaption: Content): Select =
      withHeadingLabel(select, heading, Some(sectionCaption))((sl, ul) => sl.copy(label = ul))

    private[views] def withName(field: Field): Select =
      withStringProperty(field.name, select.name, select)((sct, nm) => sct.copy(name = nm))

    private[views] def withId(field: Field): Select =
      withStringProperty(field.name, select.id, select)((sct, id) => sct.copy(id = id))

    private[views] def withTextErrorMessage(field: Field): Select =
      withOptTextErrorMessageProperty(field.error, select.errorMessage, select)((sct, errorMsg) =>
        sct.copy(errorMessage = errorMsg)
      )

    private[views] def withHtmlErrorMessage(field: Field): Select =
      withOptHtmlErrorMessageProperty(field.error, select.errorMessage, select)((sct, errorMsg) =>
        sct.copy(errorMessage = errorMsg)
      )

    private[views] def withItemSelected(field: Field): Select =
      select.copy(
        items = select.items.map { selectItem =>
          if (selectItem.selected == SelectItem.defaultObject.selected) {
            val isSelected = selectItem.value == field.value
            selectItem.copy(selected = isSelected)
          } else selectItem
        }
      )
  }
}
