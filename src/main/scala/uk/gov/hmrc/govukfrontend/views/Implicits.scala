/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views

import play.api.data.FormError
import play.api.i18n.Messages
import play.twirl.api.{Html, HtmlFormat}
import viewmodels.common.HtmlContent
import viewmodels.errorsummary.ErrorLink

trait Implicits {

  implicit class RichHtml(html: Html) {
    def padLeft(padCount: Int = 1, padding: String = " "): Html = {
      val padStr = " " * (if (html.body.isEmpty) 0 else padCount)
      HtmlFormat.fill(collection.immutable.Seq(Html(padStr), html))
    }
  }

  implicit class RichString(s: String) {
    def toOption: Option[String] =
      if (s == null || s.isEmpty) None else Some(s)
  }

  implicit class RichFormErrors(errors: Seq[FormError])(implicit messages: Messages) {
    def asErrorLinks: Seq[ErrorLink] =
      errors.map { error =>
        ErrorLink(href = Some(error.key), content = HtmlContent(messages(error.message, error.args: _*)))
      }
  }
}
