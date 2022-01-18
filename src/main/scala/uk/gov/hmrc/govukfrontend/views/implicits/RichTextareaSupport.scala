/*
 * Copyright 2022 HM Revenue & Customs
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
import uk.gov.hmrc.govukfrontend.views.viewmodels.textarea.Textarea
import uk.gov.hmrc.hmrcfrontend.views.config.{HmrcPageHeadingLabel, HmrcSectionCaption}

trait RichTextareaSupport {

  implicit class RichTextarea(textArea: Textarea)(implicit val messages: Messages) extends ImplicitsSupport[Textarea] {

    /**
      * Extension method to allow a Play form Field to be used to add certain parameters in an Textarea, specifically
      * errorMessage, id, name, and value. Note these values will only be added from the Field if they are not
      * specifically defined in the Textarea object.
      * Form errors will be bound as Text objects.
      *
      * @param field
      * @param messages
      */
    override def withFormField(field: Field): Textarea =
      textArea
        .withName(field)
        .withId(field)
        .withValue(field)
        .withTextErrorMessage(field)

    /**
      * Extension method to allow a Play form Field to be used to add certain parameters in an Textarea, as per
      * withFormField, with form errors bound as HtmlContent objects.
      *
      * @param field
      * @param messages
      */
    override def withFormFieldWithErrorAsHtml(field: Field): Textarea =
      textArea
        .withName(field)
        .withId(field)
        .withValue(field)
        .withHtmlErrorMessage(field)

    def withHeading(heading: Content): Textarea =
      withHeadingLabel(textArea, heading, None)((ta, ul) => ta.copy(label = ul))

    def withHeadingAndSectionCaption(heading: Content, sectionCaption: Content): Textarea =
      withHeadingLabel(textArea, heading, Some(sectionCaption))((ta, ul) => ta.copy(label = ul))

    private[views] def withName(field: Field): Textarea =
      withStringProperty(field.name, textArea.name, textArea)((ta, nm) => ta.copy(name = nm))

    private[views] def withId(field: Field): Textarea =
      withStringProperty(field.name, textArea.id, textArea)((ta, id) => ta.copy(id = id))

    private[views] def withValue(field: Field): Textarea =
      withOptStringProperty(field.value, textArea.value, textArea)((ta, vl) => ta.copy(value = vl))

    private[views] def withTextErrorMessage(field: Field): Textarea =
      withOptTextErrorMessageProperty(field.error, textArea.errorMessage, textArea)((ta, errorMsg) =>
        ta.copy(errorMessage = errorMsg)
      )

    private[views] def withHtmlErrorMessage(field: Field): Textarea =
      withOptHtmlErrorMessageProperty(field.error, textArea.errorMessage, textArea)((ta, errorMsg) =>
        ta.copy(errorMessage = errorMsg)
      )
  }
}
