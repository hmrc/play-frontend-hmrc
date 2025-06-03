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

package uk.gov.hmrc.govukfrontend.views
package components

import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.typedmap.TypedMap
import play.api.mvc.request.RequestAttrKey
import play.api.test.FakeRequest
import play.twirl.api.{HtmlFormat, Template1}
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.template.Template

import scala.util.Try

class GovukTemplateSpec extends TemplateUnitSpec[Template, GovukTemplateWrapper]("govukTemplate") {

  val request: FakeRequest[_]                   = FakeRequest()
  implicit val requestWithNonce: FakeRequest[_] = request.withAttrs(TypedMap(RequestAttrKey.CSPNonce -> "a-nonce"))

  "template rendered with default values" should {
    "not have whitespace before the doctype" in {
      val templateHtml =
        govukTemplate
          .apply(htmlLang = None, htmlClasses = None, themeColour = None, bodyClasses = None)(HtmlFormat.empty)
      val output       = templateHtml.body
      output.charAt(0) shouldBe '<'
    }
  }

  "govukTemplate" should {
    "use the provided assetPath in all LINK elements" in {
      val templateHtml =
        govukTemplate
          .apply(assetPath = Some("/foo/bar"))(HtmlFormat.empty)

      val links = templateHtml.select("link")
      links.forEach { link =>
        link.attr("href") should startWith("/foo/bar")
      }
    }

    "use the provided assetPath in the open graph image" in {
      val templateHtml =
        govukTemplate
          .apply(assetPath = Some("/foo/bar"))(HtmlFormat.empty)

      val ogImage = templateHtml.select("""meta[property="og:image"]""")
      ogImage.first.attr("content") should startWith("/foo/bar")
    }

    "use the nonce from the request" in {
      val html = govukTemplate.apply()(HtmlFormat.empty)

      val scripts = html.select("script")
      scripts.first.attr("nonce") shouldBe "a-nonce"
    }
  }

  "govukTemplate" should {
    def rebrandAppBuilder(useRebrand: Boolean): Application =
      new GuiceApplicationBuilder()
        .configure(Map("play-frontend-hmrc.useRebrand" -> useRebrand.toString))
        .build()

    "include additional rebrand class within html tag" when {
      "rebrand is enabled" in {
        val rebrandApp           = rebrandAppBuilder(true)
        val govukTemplateRebrand = rebrandApp.injector.instanceOf[GovukTemplate]
        val html                 = govukTemplateRebrand.apply()(HtmlFormat.empty)

        val htmlTag = html.select("html")
        htmlTag.first().className() should include("govuk-template--rebranded")
      }
    }

    "does not include additional rebrand class within html tag" when {
      "rebrand is disabled" in {
        val rebrandApp           = rebrandAppBuilder(false)
        val govukTemplateRebrand = rebrandApp.injector.instanceOf[GovukTemplate]
        val html                 = govukTemplateRebrand.apply()(HtmlFormat.empty)

        val htmlTag = html.select("html")
        htmlTag.first().className() shouldNot include("govuk-template--rebranded")
      }
    }

    "contains rebrand assets paths" when {
      "rebrand is enabled" in {
        val rebrandApp           = rebrandAppBuilder(true)
        val govukTemplateRebrand = rebrandApp.injector.instanceOf[GovukTemplate]
        val html                 = govukTemplateRebrand.apply()(HtmlFormat.empty)

        val linkTags = html.select("link")
        val metaTags = html.select("meta")

        linkTags.get(0).attr("href") should include("govuk/rebrand/")
        linkTags.get(1).attr("href") should include("govuk/rebrand/")
        linkTags.get(2).attr("href") should include("govuk/rebrand/")
        linkTags.get(3).attr("href") should include("govuk/rebrand/")
        linkTags.get(4).attr("href") should include("govuk/rebrand/")

        println(metaTags)
        metaTags.get(3).attr("content") should include("govuk/rebrand/")
      }
    }

    "does not contain rebrand assets paths" when {
      "rebrand is not enabled" in {
        val rebrandApp           = rebrandAppBuilder(false)
        val govukTemplateRebrand = rebrandApp.injector.instanceOf[GovukTemplate]
        val html                 = govukTemplateRebrand.apply()(HtmlFormat.empty)

        val linkTags = html.select("link")
        val metaTags = html.select("meta")

        linkTags.get(0).attr("href") shouldNot include("govuk/rebrand/")
        linkTags.get(1).attr("href") shouldNot include("govuk/rebrand/")
        linkTags.get(2).attr("href") shouldNot include("govuk/rebrand/")
        linkTags.get(3).attr("href") shouldNot include("govuk/rebrand/")
        linkTags.get(4).attr("href") shouldNot include("govuk/rebrand/")

        println(metaTags)
        metaTags.get(3).attr("content") shouldNot include("govuk/rebrand/")
      }
    }
  }

  private val govukTemplate: GovukTemplate = app.injector.instanceOf[GovukTemplate]

  override def render(templateParams: Template): Try[HtmlFormat.Appendable] = {
    // The following line is needed to ensure known state of the statically initialised reverse router
    // used to calculate asset paths.
    hmrcfrontend.RoutesPrefix.setPrefix("")

    super.render(templateParams)
  }
}

class GovukTemplateWrapper @javax.inject.Inject() (
  govukHeader: GovukHeader,
  govukFooter: GovukFooter,
  govukTemplate: GovukTemplate
) extends Template1[Template, HtmlFormat.Appendable] {
  override def render(template: Template): HtmlFormat.Appendable = {
    implicit val requestWithNonce: FakeRequest[_] =
      FakeRequest().withAttrs(TypedMap(RequestAttrKey.CSPNonce -> "secure-random-csp-nonce"))

    govukTemplate.apply(
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
      headerBlock = template.blocks.header.getOrElse(govukHeader()),
      footerBlock = template.blocks.footer.getOrElse(govukFooter()),
      bodyEndBlock = template.blocks.bodyEnd,
      mainClasses = template.variables.mainClasses,
      beforeContentBlock = template.blocks.beforeContent,
      bodyAttributes = template.variables.bodyAttributes
    )(template.blocks.content.getOrElse(HtmlFormat.empty))
  }

  def apply(template: Template): HtmlFormat.Appendable = render(template)
}
