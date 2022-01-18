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

import play.api.data.FormError
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage.errorMessageWithDefaultStringsTranslated
import uk.gov.hmrc.govukfrontend.views.viewmodels.errorsummary.ErrorLink

trait RichFormErrorsSupport {

  /**
    * Extension methods to convert from [[FormError]] to other types used in components to display errors
    *
    * @param formErrors
    * @param messages
    */
  implicit class RichFormErrors(formErrors: Seq[FormError])(implicit messages: Messages) {

    def asHtmlErrorLinks: Seq[ErrorLink] =
      asErrorLinks(HtmlContent.apply)

    def asTextErrorLinks: Seq[ErrorLink] =
      asErrorLinks(Text.apply)

    private[views] def asErrorLinks(contentConstructor: String => Content): Seq[ErrorLink] =
      formErrors.map { formError =>
        ErrorLink(href = Some(s"#${formError.key}"), content = contentConstructor(errorMessage(formError)))
      }

    def asHtmlErrorMessages: Seq[ErrorMessage] =
      asErrorMessages(HtmlContent.apply)

    def asTextErrorMessages: Seq[ErrorMessage] =
      asErrorMessages(Text.apply)

    private[views] def asErrorMessages(contentConstructor: String => Content): Seq[ErrorMessage] =
      formErrors
        .map { formError =>
          errorMessageWithDefaultStringsTranslated(content = contentConstructor(errorMessage(formError)))
        }

    def asHtmlErrorMessage(messageSelector: String): Option[ErrorMessage] =
      asErrorMessage(HtmlContent.apply, messageSelector)

    def asTextErrorMessage(messageSelector: String): Option[ErrorMessage] =
      asErrorMessage(Text.apply, messageSelector)

    private[views] def asErrorMessage(
      contentConstructor: String => Content,
      messageSelector: String
    ): Option[ErrorMessage] =
      formErrors
        .find(_.message == messageSelector)
        .map { formError =>
          errorMessageWithDefaultStringsTranslated(content = contentConstructor(errorMessage(formError)))
        }

    def asHtmlErrorMessageForField(fieldKey: String): Option[ErrorMessage] =
      asErrorMessageForField(HtmlContent.apply, fieldKey)

    def asTextErrorMessageForField(fieldKey: String): Option[ErrorMessage] =
      asErrorMessageForField(Text.apply, fieldKey)

    private[views] def asErrorMessageForField(
      contentConstructor: String => Content,
      fieldKey: String
    ): Option[ErrorMessage] =
      formErrors
        .find(_.key == fieldKey)
        .map { formError =>
          errorMessageWithDefaultStringsTranslated(content = contentConstructor(errorMessage(formError)))
        }

    private def errorMessage(formError: FormError) = messages(formError.message, formError.args: _*)
  }
}
