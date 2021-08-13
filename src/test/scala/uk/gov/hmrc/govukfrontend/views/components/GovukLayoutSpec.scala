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

package uk.gov.hmrc.govukfrontend.views.layouts

import org.scalatest.matchers.should.Matchers
import play.api.i18n.{Lang, Messages}
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.govukfrontend.views.{JsoupHelpers, MessagesHelpers}
import uk.gov.hmrc.govukfrontend.views.html.components._
import org.scalatest.wordspec.AnyWordSpecLike
import play.api.test.Helpers.{stubMessages, stubMessagesApi}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite

class govukLayoutSpec
    extends AnyWordSpecLike
    with Matchers
    with MessagesHelpers
    with JsoupHelpers
    with GuiceOneAppPerSuite {
  implicit lazy val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest()

  val component = app.injector.instanceOf[GovukLayout]

  "govukLayout" should {
    "render the default GOV.UK homepage link by default" in {
      val layoutHtml =
        component.apply()(HtmlFormat.empty)

      val homepageLink = layoutHtml.select(".govuk-header__link--homepage")
      homepageLink.first.attr("href") shouldBe "https://www.gov.uk/"
    }

    "render the default service link by default" in {
      implicit val messages: Messages = stubMessages(
        stubMessagesApi(
          Map(
            "en" -> Map(
              "service.homePageUrl" -> "/foo",
              "service.name"        -> "Foo service"
            )
          )
        )
      )

      val layoutHtml =
        component.apply()(HtmlFormat.empty)

      val serviceLink = layoutHtml.select(".govuk-header__link--service-name")
      serviceLink.first.attr("href") shouldBe "/foo"
      serviceLink.first.text         shouldBe "Foo service"
    }

    "render the correct number of footer links by default" in {
      val layoutHtml =
        component.apply()(HtmlFormat.empty)

      val footerLinks = layoutHtml.select(".govuk-footer__link")
      footerLinks should have size 2
    }

    "render the provided content" in {
      val layoutHtml =
        component.apply()(Html("<h1 class=\"govuk-heading-xl\">Customised page template</h1>"))

      val h1 = layoutHtml.select("h1")
      h1.first.text shouldBe "Customised page template"
    }

    "render the provided title" in {
      val layoutHtml =
        component.apply(
          pageTitle = Some("Custom title")
        )(HtmlFormat.empty)

      val title = layoutHtml.select("title")
      title.first.text shouldBe "Custom title"
    }

    "render the provided head block" in {
      val layoutHtml =
        component.apply(
          headBlock = Some(Html("<link href=\"custom-stylesheet.css\" rel=\"stylesheet\">"))
        )(HtmlFormat.empty)

      val head = layoutHtml.select("head")
      head.first.html should include("<link href=\"custom-stylesheet.css\" rel=\"stylesheet\">")
    }

    "render the provided before content block" in {
      val layoutHtml =
        component.apply(
          beforeContentBlock =
            Some(Html("<p>Customised before content, <a class=\"govuk-link\" href=\"#\">this is a link</a>.</p>"))
        )(HtmlFormat.empty)

      val container = layoutHtml.select("header + .govuk-width-container > p")
      container.first.html shouldBe
        "Customised before content, <a class=\"govuk-link\" href=\"#\">this is a link</a>."
    }

    "render the provided header block" in {
      val layoutHtml =
        component.apply(
          headerBlock = Some(Html("<header role=\"banner\">Custom header</header>"))
        )(HtmlFormat.empty)

      val header = layoutHtml.select("header")
      header.first.text shouldBe "Custom header"
    }

    "render the provided footer block" in {
      val layoutHtml =
        component.apply(
          footerBlock = Some(Html("<footer role=\"contentinfo\">Custom footer</footer>"))
        )(HtmlFormat.empty)

      val footer = layoutHtml.select("footer")
      footer.first.text shouldBe "Custom footer"
    }

    "render the provided scripts block" in {
      val layoutHtml =
        component.apply(
          scriptsBlock = Some(Html("<script src=\"custom-script.js\"></script>"))
        )(HtmlFormat.empty)

      val scripts = layoutHtml.select("script[src=custom-script.js]")
      scripts should have size 1
    }

    "render the provided body end block" in {
      val layoutHtml =
        component.apply(
          bodyEndBlock = Some(Html("<script src=\"custom-script-2.js\"></script>"))
        )(HtmlFormat.empty)

      val scripts = layoutHtml.select("script[src=custom-script-2.js]")
      scripts should have size 1
    }

    "render the layout with customised footer items" in {
      val layoutHtml =
        component.apply(footerItems =
          Seq(
            FooterItem(href = Some("/help"), text = Some("Help")),
            FooterItem(href = Some("/help/cookies"), text = Some("Cookies"))
          )
        )(HtmlFormat.empty)

      val footerLinks = layoutHtml.select(".govuk-footer__link")
      footerLinks should have size 4
    }

    "render the html lang as en by default" in {
      val layoutHtml =
        component.apply()(HtmlFormat.empty)

      val html = layoutHtml.select("html")
      html.attr("lang") shouldBe "en"
    }

    "render the html lang as cy if language toggle is set to Welsh" in {
      val messages = messagesApi.preferred(Seq(Lang("cy")))

      val layoutHtml =
        component.apply()(HtmlFormat.empty)(messages)

      val html = layoutHtml.select("html")
      html.attr("lang") shouldBe "cy"
    }

    "use the default layout of twoThirdsMainContent" in {
      val html = component.apply()(contentBlock = Html("<p>Here is my content</p>"))

      val gridRow = html.select(".govuk-main-wrapper .govuk-grid-row")
      gridRow.size() shouldBe 1

      val gridRowContent = gridRow.select(".govuk-grid-column-two-thirds")
      gridRowContent.size() shouldBe 1

      val twoThirdsContent = gridRowContent.select(".govuk-grid-column-two-thirds")
      twoThirdsContent.size()                      shouldBe 1
      twoThirdsContent.first().children().toString shouldBe "<p>Here is my content</p>"
    }

    "use the allow the passing in of a custom mainContentLayout" in {
      val html = component.apply(
        mainContentLayout = Some(_ => Html("<p>Custom function</p>"))
      )(contentBlock = Html("<p>Here is my content</p>"))

      val customMainContent = html.select(".govuk-main-wrapper")
      customMainContent.size()                      shouldBe 1
      customMainContent.first().children().toString shouldBe "<p>Custom function</p>"
    }

    "use the provided assetPath" in {
      val html = component.apply(
        assetPath = Some("/foo/bar")
      )(HtmlFormat.empty)

      val links = html.select("link")
      links.first.attr("href") should startWith("/foo/bar")
    }

    "use the provided nonce" in {
      val html = component.apply(
        cspNonce = Some("foo")
      )(HtmlFormat.empty)

      val scripts = html.select("script")
      scripts.first.attr("nonce") shouldBe "foo"
    }
  }
}
