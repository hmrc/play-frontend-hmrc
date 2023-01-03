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
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content
import uk.gov.hmrc.govukfrontend.views.viewmodels.input.Input

trait RichInputSupport {

  implicit class RichInput(input: Input)(implicit val messages: Messages) extends ImplicitsSupport[Input] {

    /**
      * Extension method to allow a Play form Field to be used to add certain parameters in an Input, specifically
      * errorMessage, id, name, and value. Note these values will only be added from the Field if they are not
      * specifically defined in the Input object.
      * Form errors will be bound as Text objects.
      *
      * @param field
      * @param messages
      */
    override def withFormField(field: Field): Input =
      input
        .withName(field)
        .withId(field)
        .withValue(field)
        .withTextErrorMessage(field)

    /**
      * Extension method to allow a Play form Field to be used to add certain parameters in an Input, as per
      * withFormField, with form errors bound as HtmlContent objects.
      *
      * @param field
      * @param messages
      */

    override def withFormFieldWithErrorAsHtml(field: Field): Input =
      input
        .withName(field)
        .withId(field)
        .withValue(field)
        .withHtmlErrorMessage(field)

    def withHeading(heading: Content): Input =
      withHeadingLabel(input, heading, None)((ip, ul) => ip.copy(label = ul))

    def withHeadingAndSectionCaption(heading: Content, sectionCaption: Content): Input =
      withHeadingLabel(input, heading, Some(sectionCaption))((ip, ul) => ip.copy(label = ul))

    private[views] def withName(field: Field): Input =
      withStringProperty(field.name, input.name, input)((ipt, nm) => ipt.copy(name = nm))

    private[views] def withId(field: Field): Input =
      withStringProperty(field.name, input.id, input)((ipt, id) => ipt.copy(id = id))

    private[views] def withValue(field: Field): Input =
      withOptStringProperty(field.value, input.value, input)((ipt, vl) => ipt.copy(value = vl))

    private[views] def withTextErrorMessage(field: Field): Input =
      withOptTextErrorMessageProperty(field.error, input.errorMessage, input)((ipt, errorMsg) =>
        ipt.copy(errorMessage = errorMsg)
      )

    private[views] def withHtmlErrorMessage(field: Field): Input =
      withOptHtmlErrorMessageProperty(field.error, input.errorMessage, input)((ipt, errorMsg) =>
        ipt.copy(errorMessage = errorMsg)
      )
  }
}
