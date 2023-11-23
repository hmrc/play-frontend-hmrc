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

package uk.gov.hmrc.hmrcfrontend.views.config

import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.govukfrontend.views.Utils.toClasses
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty, HtmlContent}
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.Legend
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label

import scala.util.matching.Regex

trait HmrcPageHeading {
  private val validHeadingSizes: Seq[String] = Seq("m", "l", "xl")

  protected def pageHeadingCaption(content: Content, classes: String = "govuk-caption-xl"): Html =
    if (content.nonEmpty) {
      HtmlFormat.fill(
        List(
          Html(s""" <span class="hmrc-caption ${HtmlFormat.escape(classes)}">"""),
          content.asHtml,
          Html("</span>")
        )
      )
    } else content.asHtml

  protected def govukCaptionClassScaledToMatchHeadingSize(
    headingClasses: String,
    regexToExtractSizeFromHeadingClasses: Regex
  ): String =
    headingClasses
      .split("\\s+")
      .reverse
      .collectFirst({
        case regexToExtractSizeFromHeadingClasses(size) if validHeadingSizes.contains(size) => s"govuk-caption-$size"
      })
      .getOrElse("govuk-caption-xl")
}

object HmrcPageHeadingLabel extends HmrcPageHeading {
  val HmrcPageHeadingLabelSize: Regex = "govuk-label--([^\b]+)".r

  def apply(
    content: Content,
    caption: Content = Empty,
    classes: String = "govuk-label--xl",
    attributes: Map[String, String] = Map.empty
  ): Label = {
    require(content.nonEmpty, "HmrcPageHeadingLabel content must not be empty")
    Label(
      isPageHeading = true,
      attributes = attributes,
      content = HtmlContent(
        HtmlFormat.fill(
          List(
            content.asHtml,
            pageHeadingCaption(
              caption,
              govukCaptionClassScaledToMatchHeadingSize(
                headingClasses = classes,
                regexToExtractSizeFromHeadingClasses = HmrcPageHeadingLabelSize
              )
            )
          )
        )
      ),
      classes = toClasses("hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-2", classes)
    )
  }
}

object HmrcPageHeadingLegend extends HmrcPageHeading {
  val HmrcLegendSize: Regex = "govuk-fieldset__legend--([^\b]+)".r

  def apply(
    content: Content,
    caption: Content = Empty,
    classes: String = "govuk-fieldset__legend--xl"
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
            pageHeadingCaption(
              caption,
              govukCaptionClassScaledToMatchHeadingSize(
                headingClasses = classes,
                regexToExtractSizeFromHeadingClasses = HmrcLegendSize
              )
            ),
            Html("</h1>")
          )
        )
      ),
      classes = classes
    )
  }
}
