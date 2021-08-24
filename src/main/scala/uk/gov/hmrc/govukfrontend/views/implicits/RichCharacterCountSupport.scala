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
import uk.gov.hmrc.govukfrontend.views.viewmodels.charactercount.CharacterCount
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content
import uk.gov.hmrc.hmrcfrontend.views.config.{HmrcPageHeadingLabel, HmrcSectionCaption}

trait RichCharacterCountSupport {

  implicit class RichCharacterCount(characterCount: CharacterCount)(implicit val messages: Messages)
      extends ImplicitsSupport[CharacterCount] {

    /**
      * Extension method to allow a Play form Field to be used to add certain parameters in an CharacterCount,
      * specifically errorMessage, id, name, and value. Note these values will only be added from the Field if they are
      * not specifically defined in the CharacterCount object.
      * Form errors will be bound as Text objects.
      *
      * @param field
      * @param messages
      */
    override def withFormField(field: Field): CharacterCount =
      characterCount
        .withName(field)
        .withId(field)
        .withValue(field)
        .withTextErrorMessage(field)

    /**
      * Extension method to allow a Play form Field to be used to add certain parameters in an CharacterCount, as per
      * withFormField, with form errors bound as HtmlContent objects.
      *
      * @param field
      * @param messages
      */
    override def withFormFieldWithErrorAsHtml(field: Field): CharacterCount =
      characterCount
        .withName(field)
        .withId(field)
        .withValue(field)
        .withHtmlErrorMessage(field)

    // FIXME: PoC only - needs tests and functionality to not-override any existing label
    def withHeadingAndSectionCaption(heading: Content, sectionCaption: Content): CharacterCount =
      characterCount.copy(
        label = HmrcPageHeadingLabel(
          content = heading,
          caption = HmrcSectionCaption(sectionCaption)
        )
      )

    def withHeading(heading: Content): CharacterCount =
      characterCount.copy(
        label = HmrcPageHeadingLabel(
          content = heading
        )
      )

    private[views] def withName(field: Field): CharacterCount =
      withStringProperty(field.name, characterCount.name, characterCount)((cc, nm) => cc.copy(name = nm))

    private[views] def withId(field: Field): CharacterCount =
      withStringProperty(field.name, characterCount.id, characterCount)((cc, id) => cc.copy(id = id))

    private[views] def withValue(field: Field): CharacterCount =
      withOptStringProperty(field.value, characterCount.value, characterCount)((cc, vl) => cc.copy(value = vl))

    private[views] def withTextErrorMessage(field: Field): CharacterCount =
      withOptTextErrorMessageProperty(field.error, characterCount.errorMessage, characterCount)((cc, errorMsg) =>
        cc.copy(errorMessage = errorMsg)
      )

    private[views] def withHtmlErrorMessage(field: Field): CharacterCount =
      withOptHtmlErrorMessageProperty(field.error, characterCount.errorMessage, characterCount)((cc, errorMsg) =>
        cc.copy(errorMessage = errorMsg)
      )
  }
}
