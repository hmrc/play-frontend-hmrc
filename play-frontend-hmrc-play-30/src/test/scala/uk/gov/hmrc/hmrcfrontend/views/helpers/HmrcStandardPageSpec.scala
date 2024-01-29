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

package uk.gov.hmrc.hmrcfrontend.views.helpers

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.i18n.Lang
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.typedmap.TypedMap
import play.api.mvc.request.RequestAttrKey
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.Aliases.Text
import uk.gov.hmrc.govukfrontend.views.html.components.{FullWidthPageLayout, GovukBackLink}
import uk.gov.hmrc.govukfrontend.views.viewmodels.backlink.BackLink
import uk.gov.hmrc.govukfrontend.views.viewmodels.exitthispage.ExitThisPage
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.helpers.views.JsoupHelpers
import uk.gov.hmrc.hmrcfrontend.config.AssetsConfig
import uk.gov.hmrc.hmrcfrontend.views.Aliases.UserResearchBanner
import uk.gov.hmrc.hmrcfrontend.views.config.StandardBetaBanner
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcStandardPage
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.hmrcstandardpage._

import scala.annotation.tailrec

class HmrcStandardPageSpec
    extends AnyWordSpecLike
    with Matchers
    with JsoupHelpers
    with GuiceOneAppPerSuite
    with MessagesSupport {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure("accessibility-statement.service-path" -> "/test-service-path")
      .build()

  @tailrec
  private def filterElements(
    elements: Elements,
    func: Element => Boolean,
    matchedElements: List[Element]
  ): List[Element] =
    if (elements.size() == 0) matchedElements
    else {
      val nextElement = elements.first()
      val updatedList = if (func(nextElement)) matchedElements :+ nextElement else matchedElements
      filterElements(elements.nextAll(), func, updatedList)
    }

  implicit val fakeRequest: FakeRequest[_] = FakeRequest("GET", "/foo")
  val requestWithNonce: FakeRequest[_]     = fakeRequest.withAttrs(TypedMap(RequestAttrKey.CSPNonce -> "a-nonce"))

  private val hmrcStandardPage = app.injector.instanceOf[HmrcStandardPage]

  private val defaultHmrcStandardPage: Html =
    hmrcStandardPage(HmrcStandardPageParams())(Html("Some main content"))

  "HmrcStandardPage" should {

    "render a standard template when passed only content HTML" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcStandardPage))

      val template = document.select(".govuk-template")
      template should have size 1
    }

    "not bind a service title by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcStandardPage))

      val serviceNameLink = document.select(".hmrc-header__service-name")
      serviceNameLink should have size 0
    }

    "bind a service name when passed in" in {
      val content  = contentAsString(
        hmrcStandardPage(HmrcStandardPageParams(serviceName = Some("My Service Name")))(
          Html("")
        )
      )
      val document = Jsoup.parse(content)

      val serviceNameLink = document.select(".hmrc-header__service-name")
      serviceNameLink          should have size 1
      serviceNameLink.text() shouldBe "My Service Name"
    }

    "use a default title when none is passed" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcStandardPage))

      document.title() shouldBe "GOV.UK - The best place to find government services and information"
    }

    "bind a page title when passed" in {
      val page     = hmrcStandardPage(HmrcStandardPageParams(pageTitle = Some("My Page Title")))(Html(""))
      val document = Jsoup.parse(contentAsString(page))

      document.title() shouldBe "My Page Title"
    }

    "not include the language toggle by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcStandardPage))

      val languageSelect = document.select(".hmrc-language-select")
      languageSelect should have size 0
    }

    "include the language toggle if Welsh translations flagged as available" in {
      val page     = hmrcStandardPage(HmrcStandardPageParams(isWelshTranslationAvailable = true))(Html(""))
      val document = Jsoup.parse(contentAsString(page))

      val languageSelect = document.select(".hmrc-language-select")
      languageSelect should have size 1
    }

    "include a scripts block without a nonce by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcStandardPage))

      val scripts = document.select("script")
      scripts.last().hasAttr("src")   shouldBe true
      scripts.last().hasAttr("nonce") shouldBe false
    }

    "include a scripts block with a nonce if passed" in {
      val page     = hmrcStandardPage(HmrcStandardPageParams())(Html(""))(requestWithNonce, implicitly)
      val document = Jsoup.parse(contentAsString(page))

      val scripts = document.select("script")
      scripts.last().hasAttr("src") shouldBe true
      scripts.last().attr("nonce")  shouldBe "a-nonce"
    }

    "pass the nonce to govukLayout if provided" in {
      val page     = hmrcStandardPage(HmrcStandardPageParams())(Html(""))(requestWithNonce, implicitly)
      val document = Jsoup.parse(contentAsString(page))

      val scripts = document.select("script")
      scripts.first.attr("nonce") shouldBe "a-nonce"
    }

    "not include signOutUrl in the header by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcStandardPage))

      val signOutLink = document.select(".hmrc-sign-out-nav__link")
      signOutLink should have size 0
    }

    "include signOutUrl in the header if passed" in {
      val page     =
        hmrcStandardPage(HmrcStandardPageParams(serviceURLs = ServiceURLs(signOutUrl = Some("my-signOut-route"))))(
          Html("")
        )
      val document = Jsoup.parse(contentAsString(page))

      val signOutLink = document.select(".hmrc-sign-out-nav__link")
      signOutLink                should have size 1
      signOutLink.attr("href") shouldBe "my-signOut-route"
    }

    "not include a service url by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcStandardPage))

      val homepageLink = document.select(".hmrc-header__service-name")
      homepageLink should have size 0
    }

    "include empty string for service url if passed a service name" in {
      val page     = hmrcStandardPage(HmrcStandardPageParams(serviceName = Some("My Service")))(Html(""))
      val document = Jsoup.parse(contentAsString(page))

      val homepageLink = document.select(".hmrc-header__service-name")
      homepageLink                should have size 1
      homepageLink.attr("href") shouldBe ""
    }

    "include serviceUrl in the header if passed with a service name" in {
      val page     = hmrcStandardPage(
        HmrcStandardPageParams(
          serviceURLs = ServiceURLs(
            serviceUrl = Some("my-homepage-route")
          ),
          serviceName = Some("My Service")
        )
      )(Html(""))
      val document = Jsoup.parse(contentAsString(page))

      val homepageLink = document.select(".hmrc-header__service-name")
      homepageLink                should have size 1
      homepageLink.attr("href") shouldBe "my-homepage-route"
      homepageLink.text()       shouldBe "My Service"
    }

    "not include a user research banner by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcStandardPage))

      val hmrcBanner = document.select(".hmrc-user-research-banner")
      hmrcBanner should have size 0
    }

    "display a user research banner when passed" in {
      val document = Jsoup.parse(
        contentAsString(
          hmrcStandardPage(
            HmrcStandardPageParams(banners =
              Banners(userResearchBanner = Some(UserResearchBanner(url = "my-research-route")))
            )
          )(Html(""))
        )
      )

      val hmrcBanner = document.select(".hmrc-user-research-banner")
      hmrcBanner                                                         should have size 1
      document.select(".hmrc-user-research-banner__link").attr("href") shouldBe "my-research-route"
    }

    "not include a phase banner by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcStandardPage))

      val banner = document.select(".govuk-phase-banner")
      banner should have size 0
    }

    "include a phase banner when passed a URL" in {
      val standardBetaBanner = app.injector.instanceOf[StandardBetaBanner]
      val betaBanner         = standardBetaBanner(url = "my-beta-phase-url")
      val page               =
        hmrcStandardPage(HmrcStandardPageParams(banners = Banners(phaseBanner = Some(betaBanner))))(Html(""))
      val document           = Jsoup.parse(contentAsString(page))

      val banner = document.select(".govuk-phase-banner")
      banner                                              should have size 1
      document.select(".govuk-phase-banner__text").html() should include("my-beta-phase-url")
    }

    "use accessibility statement URL in footer if passed" in {
      val page     = hmrcStandardPage(
        HmrcStandardPageParams(serviceURLs = ServiceURLs(accessibilityStatementUrl = Some("some-custom-url")))
      )(Html(""))
      val document = Jsoup.parse(contentAsString(page))

      val footerLinks: Elements = document.select(".govuk-footer__link")
      footerLinks.select("a[href=\"some-custom-url\"]").text() should be("Accessibility statement")
    }

    "construct accessibility statement URL using config path in footer if none passed explicitly" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcStandardPage))

      val footerLinks: Elements = document.select(".govuk-footer__link")
      footerLinks
        .select("a[href=\"http://localhost:12346/accessibility-statement/test-service-path?referrerUrl=%2Ffoo\"]")
        .text() should be("Accessibility statement")
    }

    "use default govukTemplate content layout by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcStandardPage))

      val mainContent = document.getElementById("main-content")
      mainContent.childrenSize() shouldBe 1

      val gridRow = mainContent.child(0)
      gridRow.className()    shouldBe "govuk-grid-row"
      gridRow.childrenSize() shouldBe 1

      val gridRowColumn = gridRow.child(0)
      gridRowColumn.className() shouldBe "govuk-grid-column-two-thirds"
      gridRowColumn.text()      shouldBe "Some main content"
    }

    "use custom main content layout if passed" in {
      val customMainLayout: Html => Html = { html =>
        Html("<div class=\"my-custom-styling\">" + html.toString() + "</div>")
      }

      val content  = contentAsString(
        hmrcStandardPage(
          HmrcStandardPageParams(templateOverrides = TemplateOverrides(mainContentLayout = Some(customMainLayout)))
        )(Html("Some main content"))
      )
      val document = Jsoup.parse(content)

      val mainContent = document.getElementById("main-content")
      mainContent.childrenSize() shouldBe 1

      val customMainContent = mainContent.child(0)
      customMainContent.className()    shouldBe "my-custom-styling"
      customMainContent.text()         shouldBe "Some main content"
      customMainContent.childrenSize() shouldBe 0
    }

    "not display a HMRC banner by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcStandardPage))

      val hmrcBanner = document.select(".hmrc-banner")
      hmrcBanner should have size 0
    }

    "display a HMRC banner when flagged on" in {
      val content  = contentAsString(
        hmrcStandardPage(HmrcStandardPageParams(banners = Banners(displayHmrcBanner = true)))(Html(""))
      )
      val document = Jsoup.parse(content)

      val hmrcBanner = document.select(".hmrc-banner")
      hmrcBanner should have size 1
    }

    "use default hmrcScripts if no scripts passed" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcStandardPage))

      val scripts            = document.select("script")
      val scriptsWithSrcAttr = filterElements(scripts, elem => elem.hasAttr("src"), List.empty)
      scriptsWithSrcAttr should have size 1
    }

    "use custom additional scripts if passed" in {
      val assetsConfig       = app.injector.instanceOf[AssetsConfig]
      val scriptsHtml        = Html("<script src=\"my-custom-script\"></script>")
      val content            = contentAsString(
        hmrcStandardPage(
          HmrcStandardPageParams(templateOverrides = TemplateOverrides(additionalScriptsBlock = Some(scriptsHtml)))
        )(Html(""))
      )
      val document: Document = Jsoup.parse(content)

      val scriptsWithSrcAttr = filterElements(document.select("script"), _.hasAttr("src"), List.empty)
      scriptsWithSrcAttr                  should have size 2
      scriptsWithSrcAttr(0).attr("src") shouldBe assetsConfig.hmrcFrontendJsUrl
      scriptsWithSrcAttr(1).attr("src") shouldBe "my-custom-script"
    }

    "use custom additional head content if passed" in {
      val additionalHeadHtml = Html("<script src=\"my-additional-head-script\"></script>")
      val content            = contentAsString(
        hmrcStandardPage(
          HmrcStandardPageParams(templateOverrides = TemplateOverrides(additionalHeadBlock = Some(additionalHeadHtml)))
        )(Html(""))
      )
      val document: Document = Jsoup.parse(content)

      val head = document.select("head")
      head.toString should include(additionalHeadHtml.toString())
    }

    "use custom beforeContent if passed" in {
      val backLink           = app.injector.instanceOf[GovukBackLink]
      val backLinkHtml       = backLink(BackLink(href = "#"))
      val content            =
        contentAsString(
          hmrcStandardPage(
            HmrcStandardPageParams(templateOverrides = TemplateOverrides(beforeContentBlock = Some(backLinkHtml)))
          )(Html(""))
        )
      val document: Document = Jsoup.parse(content)

      val afterHeader = document.select("header ~ div")
      afterHeader.select("a").toString shouldBe "<a href=\"#\" class=\"govuk-back-link\">Back</a>"

      val mainContent = afterHeader.select("a ~ main")
      mainContent.hasClass("govuk-main-wrapper") shouldBe true
    }

    "not use backLink or language toggle if custom beforeContent is passed" in {
      val content            =
        contentAsString(
          hmrcStandardPage(
            HmrcStandardPageParams(
              templateOverrides = TemplateOverrides(
                beforeContentBlock = Some(Html("<div class=\"some-before-class\">Some content</div>"))
              ),
              isWelshTranslationAvailable = true,
              backLink = Some(BackLink(href = "#"))
            )
          )(Html(""))
        )
      val document: Document = Jsoup.parse(content)

      document.select(".govuk-back-link")      should have size 0
      document.select(".hmrc-language-select") should have size 0

      val afterHeader = document.select("header ~ div")
      afterHeader.select(".some-before-class") should have size 1

      val mainContent = afterHeader.select("div ~ main")
      mainContent.hasClass("govuk-main-wrapper") shouldBe true
    }

    "not include a back link by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcStandardPage))

      val backLink = document.select(".govuk-back-link")
      backLink should have size 0
    }

    "include the back link if passed a BackLink" in {
      val customBackLink = BackLink("/some/url", "custom-class", Map("some" -> "attribute"), Text("Custom Back"))
      val page           = hmrcStandardPage(HmrcStandardPageParams(backLink = Some(customBackLink)))(
        Html("")
      )
      val document       = Jsoup.parse(contentAsString(page))

      val actualBackLink = document.select(".govuk-back-link")
      actualBackLink                            should have size 1
      actualBackLink.attr("href")             shouldBe "/some/url"
      actualBackLink.hasClass("custom-class") shouldBe true
      actualBackLink.attr("some")             shouldBe "attribute"
      actualBackLink.html()                   shouldBe "Custom Back"
    }

    "correctly translate the back link based on language in messages" in {
      val messages           = messagesApi.preferred(Seq(Lang("cy")))
      val page               =
        hmrcStandardPage(
          HmrcStandardPageParams(backLink = Some(BackLink.withDefaultText(href = "my-back-link")(messages)))
        )(
          Html("")
        )(fakeRequest, messages)
      val document: Document = Jsoup.parse(contentAsString(page))

      val backLink = document.select(".govuk-back-link")
      backLink                should have size 1
      backLink.attr("href") shouldBe "my-back-link"
      backLink.html()       shouldBe "Yn ôl"
    }

    "include exit this page if passed an ExitThisPage" in {
      val page     = hmrcStandardPage(HmrcStandardPageParams(exitThisPage = Some(ExitThisPage())))(Html(""))
      val document = Jsoup.parse(contentAsString(page))

      val exitThisPageButton = document.select(".govuk-exit-this-page__button")
      exitThisPageButton                should have size 1
      exitThisPageButton.text()       shouldBe "EmergencyExit this page"
      exitThisPageButton.attr("href") shouldBe "https://www.bbc.co.uk/weather"
    }

    "correctly translate exit this page based on language in messages" in {
      val messages = messagesApi.preferred(Seq(Lang("cy")))
      val page     =
        hmrcStandardPage(HmrcStandardPageParams(exitThisPage = Some(ExitThisPage())))(Html(""))(fakeRequest, messages)
      val document = Jsoup.parse(contentAsString(page))

      val exitThisPageButton = document.select(".govuk-exit-this-page__button")
      exitThisPageButton                should have size 1
      exitThisPageButton.text()       shouldBe "EmergencyGadael y dudalen hon"
      exitThisPageButton.attr("href") shouldBe "https://www.bbc.co.uk/weather"
    }

    "add attributes to activate the JavaScript browser back in hmrc-frontend" in {
      val page     =
        hmrcStandardPage(HmrcStandardPageParams(backLink = Some(BackLink.mimicsBrowserBackButtonViaJavaScript)))(
          Html("")
        )
      val document = Jsoup.parse(contentAsString(page))

      val actualBackLink = document.select(".govuk-back-link")
      actualBackLink                       should have size 1
      actualBackLink.attr("href")        shouldBe "#"
      actualBackLink.attr("data-module") shouldBe "hmrc-back-link"
      actualBackLink.html()              shouldBe "Back"
    }

    "allow overriding of the default page layout" in {
      val fullWidthPageLayout = app.injector.instanceOf[FullWidthPageLayout]
      val page                = hmrcStandardPage(
        HmrcStandardPageParams(templateOverrides =
          TemplateOverrides(
            pageLayout = Some(fullWidthPageLayout(_)),
            beforeContentBlock = Some(Html("beforeContentBlock")),
            mainContentLayout = Some(TemplateOverrides.noMainContentLayout)
          )
        )
      )(Html("contentBlock"))

      val document = Jsoup.parse(contentAsString(page))
      document.select("body > .govuk-width-container > main") should have size 0
      document.html()                                         should include("beforeContentBlock")
      val mainElement = document.select("body > main")
      mainElement          should have size 1
      mainElement.html() shouldBe "contentBlock"
    }
  }
}
