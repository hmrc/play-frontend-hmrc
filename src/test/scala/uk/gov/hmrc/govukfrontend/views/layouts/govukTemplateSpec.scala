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

package uk.gov.hmrc.govukfrontend.views
package layouts

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.template.Template

import scala.util.Try

class govukTemplateSpec extends TemplateUnitSpec[Template, GovukTemplate]("govukTemplate") {

  /**
    * Calls the Twirl template with the given parameters and returns the resulting markup
    *
    * @param template
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  override def render(template: Template): Try[HtmlFormat.Appendable] = {
    govuk.RoutesPrefix.setPrefix("")
    Try(
      component.apply(
        htmlLang = template.variables.htmlLang,
        pageTitleLang = template.variables.pageTitleLang,
        mainLang = template.variables.mainLang,
        htmlClasses = template.variables.htmlClasses,
        themeColour = template.variables.themeColor,
        bodyClasses = template.variables.bodyClasses,
        pageTitle = template.blocks.pageTitle,
        headIcons = template.blocks.headIcons,
        headBlock = template.blocks.head,
        bodyStart = template.blocks.bodyStart,
        skipLinkBlock = template.blocks.skipLink,
        headerBlock = template.blocks.header.getOrElse(header()),
        footerBlock = template.blocks.footer.getOrElse(footer()),
        bodyEndBlock = template.blocks.bodyEnd,
        mainClasses = template.variables.mainClasses,
        beforeContentBlock = template.blocks.beforeContent,
        cspNonce = template.variables.cspNonce
      )(template.blocks.content.getOrElse(HtmlFormat.empty))
    )
  }

  "template rendered with default values" should {
    "not have whitespace before the doctype" in {
      val templateHtml =
        component
          .apply(htmlLang = None, htmlClasses = None, themeColour = None, bodyClasses = None)(HtmlFormat.empty)
      val output       = templateHtml.body
      output.charAt(0) shouldBe '<'
    }
  }

  "govukTemplate" should {
    "use the provided assetPath in all LINK elements" in {
      val templateHtml =
        component
          .apply(assetPath = Some("/foo/bar"))(HtmlFormat.empty)

      val links = templateHtml.select("link")
      links.forEach { link =>
        link.attr("href") should startWith("/foo/bar")
      }
    }

    "use the provided assetPath in the open graph image" in {
      val templateHtml =
        component
          .apply(assetPath = Some("/foo/bar"))(HtmlFormat.empty)

      val ogImage = templateHtml.select("""meta[property="og:image"]""")
      ogImage.first.attr("content") should startWith("/foo/bar")
    }
  }
}
