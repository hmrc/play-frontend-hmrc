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

package uk.gov.hmrc.hmrcfrontend.views.config

import play.api.i18n.Messages
import play.api.mvc.RequestHeader
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.Aliases.{HtmlContent, PhaseBanner, Tag, Text}
import uk.gov.hmrc.hmrcfrontend.config.ContactFrontendConfig
import uk.gov.hmrc.hmrcfrontend.views.Utils.urlEncode

trait StandardPhaseBanner {

  protected def contactFrontendBetaFeedbackUrl()(implicit request: RequestHeader, cfConfig: ContactFrontendConfig) = {
    println("cfConfig.referrerUrl is: " + cfConfig.referrerUrl)

    val queryStringParams: Seq[String] = Seq(
      cfConfig.serviceId.map(id => "service=" + id),
      cfConfig.referrerUrl.map(url => "referrerUrl=" + urlEncode(url))
    ).flatten

    val queryString = if (queryStringParams.isEmpty) "" else "?" + queryStringParams.mkString("&")
    cfConfig.baseUrl.getOrElse("") + "/contact/beta-feedback" + queryString
  }

  def apply(phase: String, url: String)(implicit messages: Messages): PhaseBanner =
    PhaseBanner(
      tag = Some(Tag(content = Text(phase))),
      content = HtmlContent(
        s"""${messages("phase.banner.before")} <a class=\"govuk-link\" href=\"${HtmlFormat.escape(url)}\">${messages(
          "phase.banner.link"
        )}</a> ${messages("phase.banner.after")}"""
      )
    )
}

class StandardBetaBanner extends StandardPhaseBanner {
  def apply(url: String)(implicit messages: Messages): PhaseBanner = apply(phase = "beta", url = url)

  def apply()(implicit
    request: RequestHeader,
    contactFrontendConfig: ContactFrontendConfig,
    messages: Messages
  ): PhaseBanner =
    apply(url = contactFrontendBetaFeedbackUrl())
}

class StandardAlphaBanner extends StandardPhaseBanner {
  def apply(url: String)(implicit messages: Messages): PhaseBanner = apply(phase = "alpha", url = url)
}
