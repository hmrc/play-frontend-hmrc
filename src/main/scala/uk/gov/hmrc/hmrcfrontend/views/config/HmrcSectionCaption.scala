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

package uk.gov.hmrc.hmrcfrontend.views.config

import play.api.i18n.Messages
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, HtmlContent}

object HmrcSectionCaption {
  def apply(
    section: Content
  )(implicit messages: Messages): Content = {
    require(section.nonEmpty, "HmrcSectionCaption section must not be empty")
    HtmlContent(
      s"""<span class="govuk-visually-hidden">${HtmlFormat.escape(
        messages("this.section.is")
      )} </span>${section.asHtml}"""
    )
  }
}
