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

import play.api.data.{Form, FormError}
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.Aliases.Empty
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.errorsummary.{ErrorLink, ErrorSummary}

trait RichErrorSummarySupport {

  /**
    * Extension methods to hydrate an [[ErrorSummary]] with the errors found in a Play form.
    *
    * Error messages are hyperlinked to the HTML input elements corresponding to the Play field.
    * For situations where the form field does not correspond to
    * an HTML input element, for example, a composite field such as a date, provide a mapping from the
    * composite field to Play field corresponding to the first HTML input element in the group.
    *
    * For example:
    * {{{
    *   @govukErrorSummary(ErrorSummary().withFormErrorsAsText(dateInputForm, mapping = Map("date" -> "date.day")))
    * }}}
    */
  implicit class RichErrorSummary[T](errorSummary: ErrorSummary)(implicit val messages: Messages) {
    import uk.gov.hmrc.govukfrontend.views.Implicits.RichFormErrors

    def withFormErrorsAsHtml(form: Form[T]): ErrorSummary =
      withFormErrorsAsHtml(form, Map.empty)

    def withFormErrorsAsHtml(form: Form[T], mapping: Map[String, String]): ErrorSummary =
      withForm(form, _.asHtmlErrorLinks, mapping)

    def withFormErrorsAsText(form: Form[T]): ErrorSummary =
      withFormErrorsAsText(form, Map.empty)

    def withFormErrorsAsText(form: Form[T], mapping: Map[String, String]): ErrorSummary =
      withForm(form, _.asTextErrorLinks, mapping)

    private[views] def withForm(
      form: Form[T],
      asErrorLinks: Seq[FormError] => Seq[ErrorLink],
      mapping: Map[String, String]
    ): ErrorSummary =
      errorSummary.withTitle().withErrorList(form, asErrorLinks, mapping)

    private[views] def withErrorList(
      form: Form[T],
      asErrorLinks: Seq[FormError] => Seq[ErrorLink],
      mapping: Map[String, String]
    ): ErrorSummary = {
      val remappedFormErrors = form.errors map { e =>
        e.copy(
          key = mapping.getOrElse(e.key, e.key)
        )
      }
      errorSummary.copy(errorList = asErrorLinks(remappedFormErrors))
    }

    private[views] def withTitle(): ErrorSummary = errorSummary.copy(
      title = if (errorSummary.title == Empty) Text(messages("error.summary.title")) else errorSummary.title
    )
  }
}
