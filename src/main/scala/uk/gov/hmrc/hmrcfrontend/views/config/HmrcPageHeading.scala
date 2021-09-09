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

import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.govukfrontend.views.Utils.toClasses
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty, HtmlContent}
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.Legend
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label

trait HmrcPageHeading {
  protected def pageHeadingCaption(content: Content): Html = if (content.nonEmpty)
    HtmlFormat.fill(
      List(
        Html(""" <span class="govuk-caption-xl hmrc-caption-xl">"""),
        content.asHtml,
        Html("</span>")
      )
    )
  else content.asHtml
}

object HmrcPageHeadingLabel extends HmrcPageHeading {
  def apply(
    content: Content,
    caption: Content = Empty,
    classes: String = "",
    attributes: Map[String, String] = Map.empty
  ): Label = {
    require(content.nonEmpty, "HmrcPageHeadingLabel content must not be empty")
    Label(
      isPageHeading = true,
      attributes = attributes,
      content = HtmlContent(HtmlFormat.fill(List(content.asHtml, pageHeadingCaption(caption)))),
      classes = toClasses("govuk-label--xl hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-2", classes)
    )
  }
}

object HmrcPageHeadingLegend extends HmrcPageHeading {
  def apply(
    content: Content,
    caption: Content = Empty,
    classes: String = ""
  ): Legend = {
    require(content.nonEmpty, "HmrcPageHeadingLegend content must not be empty")
    Legend(
      isPageHeading = false, // we build it ourselves because to include a section we need to add some extra classes
      content = HtmlContent(
        HtmlFormat.fill(
          List(
            Html(
              """<h1 class="govuk-fieldset__heading hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-0">"""
            ),
            content.asHtml,
            pageHeadingCaption(caption),
            Html("</h1>")
          )
        )
      ),
      classes = toClasses("govuk-fieldset__legend--xl", classes)
    )
  }
}
