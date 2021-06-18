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

package uk.gov.hmrc.hmrcfrontend.views.helpers

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.i18n.{DefaultLangs, Lang}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.MessagesRequest
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, stubMessagesApi, _}
import play.twirl.api.Html
import uk.gov.hmrc.hmrcfrontend.config.AssetsConfig
import uk.gov.hmrc.hmrcfrontend.views.JsoupHelpers
import uk.gov.hmrc.hmrcfrontend.views.config.StandardBetaBanner
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcLayout

import scala.annotation.tailrec

class hmrcLayoutSpec extends AnyWordSpecLike with Matchers with JsoupHelpers with GuiceOneAppPerSuite {

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

  implicit val fakeRequest = FakeRequest("GET", "/foo")

  def getMessages(messages: Map[String, Map[String, String]] = Map.empty, lang: Lang = Lang("en")) = {
    val messagesApi = stubMessagesApi(messages, new DefaultLangs(Seq(lang)))
    new MessagesRequest(FakeRequest(), messagesApi).messages
  }

  val defaultHmrcLayout: Html = {
    val hmrcLayout = app.injector.instanceOf[HmrcLayout]
    val messages   = getMessages()
    hmrcLayout()(Html("Some main content"))(fakeRequest, messages)
  }

  "HmrcLayout" should {

    "render a standard template when passed only content HTML" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcLayout))

      val template = document.select(".govuk-template")
      template should have size 1
    }

    "not bind a service title by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcLayout))

      val serviceNameLink = document.select(".govuk-header__link--service-name")
      serviceNameLink should have size 0
    }

    "bind a service title when passed in" in {
      val hmrcLayout = app.injector.instanceOf[HmrcLayout]
      val messages   = getMessages()
      val content    =
        contentAsString(hmrcLayout(serviceName = Some("My Service Name"))(Html(""))(fakeRequest, messages))
      val document   = Jsoup.parse(content)

      val serviceNameLink = document.select(".govuk-header__link--service-name")
      serviceNameLink          should have size 1
      serviceNameLink.text() shouldBe "My Service Name"
    }

    "use a default title when none is passed" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcLayout))

      document.title() shouldBe "GOV.UK - The best place to find government services and information"
    }

    "bind a page title when passed" in {
      val hmrcLayout = app.injector.instanceOf[HmrcLayout]
      val messages   = getMessages()
      val layout     = hmrcLayout(pageTitle = Some("My Page Title"))(Html(""))(fakeRequest, messages)
      val document   = Jsoup.parse(contentAsString(layout))

      document.title() shouldBe "My Page Title"
    }

    "not include the language toggle by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcLayout))

      val languageSelect = document.select(".hmrc-language-select")
      languageSelect should have size 0
    }

    "include the language toggle if Welsh translations flagged as available" in {
      val hmrcLayout = app.injector.instanceOf[HmrcLayout]
      val messages   = getMessages()
      val layout     = hmrcLayout(isWelshTranslationAvailable = true)(Html(""))(fakeRequest, messages)
      val document   = Jsoup.parse(contentAsString(layout))

      val languageSelect = document.select(".hmrc-language-select")
      languageSelect should have size 1
    }

    "include a scripts block without a nonce by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcLayout))

      val scripts = document.select("script")
      scripts.last().hasAttr("src")   shouldBe true
      scripts.last().hasAttr("nonce") shouldBe false
    }

    "include a scripts block with a nonce if passed" in {
      val hmrcLayout = app.injector.instanceOf[HmrcLayout]
      val messages   = getMessages()
      val layout     = hmrcLayout(nonce = Some("my-nonce"))(Html(""))(fakeRequest, messages)
      val document   = Jsoup.parse(contentAsString(layout))

      val scripts = document.select("script")
      scripts.last().hasAttr("src") shouldBe true
      scripts.last().attr("nonce")  shouldBe "my-nonce"
    }

    "not include signOutUrl in the header by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcLayout))

      val signoutLink = document.select(".hmrc-sign-out-nav__link")
      signoutLink should have size 0
    }

    "include signOutUrl in the header if passed" in {
      val hmrcLayout = app.injector.instanceOf[HmrcLayout]
      val messages   = getMessages()
      val layout     =
        hmrcLayout(signOutUrl = Some("my-signOut-route"))(Html(""))(fakeRequest, messages)
      val document   = Jsoup.parse(contentAsString(layout))

      val signOutLink = document.select(".hmrc-sign-out-nav__link")
      signOutLink                should have size 1
      signOutLink.attr("href") shouldBe "my-signOut-route"
    }

    "not include a service url by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcLayout))

      val homepageLink = document.select(".govuk-header__link--service-name")
      homepageLink should have size 0
    }

    "include empty string for service url if passed a service name" in {
      val hmrcLayout = app.injector.instanceOf[HmrcLayout]
      val messages   = getMessages()
      val layout     = hmrcLayout(serviceName = Some("My Service"))(Html(""))(fakeRequest, messages)
      val document   = Jsoup.parse(contentAsString(layout))

      val homepageLink = document.select(".govuk-header__link--service-name")
      homepageLink                should have size 1
      homepageLink.attr("href") shouldBe ""
    }

    "include serviceUrl in the header if passed with a service name" in {
      val hmrcLayout = app.injector.instanceOf[HmrcLayout]
      val messages   = getMessages()
      val layout     = hmrcLayout(
        serviceName = Some("My Service"),
        serviceUrl = Some("my-homepage-route")
      )(Html(""))(fakeRequest, messages)
      val document   = Jsoup.parse(contentAsString(layout))

      val homepageLink = document.select(".govuk-header__link--service-name")
      homepageLink                should have size 1
      homepageLink.attr("href") shouldBe "my-homepage-route"
      homepageLink.text()       shouldBe "My Service"
    }

    "not include a user research banner by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcLayout))

      val hmrcBanner = document.select(".hmrc-user-research-banner")
      hmrcBanner should have size 0
    }

    "display a user research banner when passed  on" in {
      val hmrcLayout = app.injector.instanceOf[HmrcLayout]
      val messages   = getMessages()
      val document   = Jsoup.parse(
        contentAsString(
          hmrcLayout(userResearchBannerUrl = Some("my-research-route"))(Html(""))(
            fakeRequest,
            messages
          )
        )
      )

      val hmrcBanner = document.select(".hmrc-user-research-banner")
      hmrcBanner                                                         should have size 1
      document.select(".hmrc-user-research-banner__link").attr("href") shouldBe "my-research-route"
    }

    "not include a phase banner by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcLayout))

      val banner = document.select(".govuk-phase-banner")
      banner should have size 0
    }

    "include a phase banner when passed" in {
      val hmrcLayout         = app.injector.instanceOf[HmrcLayout]
      val standardBetaBanner = app.injector.instanceOf[StandardBetaBanner]
      val messages           = getMessages()
      val betaBanner         = standardBetaBanner(url = "my-beta-phase-url")(messages)
      val content            = hmrcLayout(phaseBanner = Some(betaBanner))(Html(""))(fakeRequest, messages)
      val document           = Jsoup.parse(contentAsString(content))

      val banner = document.select(".govuk-phase-banner")
      banner                                              should have size 1
      document.select(".govuk-phase-banner__text").html() should include("my-beta-phase-url")
    }

    "use default govukTemplate content layout by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcLayout))

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

      val hmrcLayout = app.injector.instanceOf[HmrcLayout]
      val messages   = getMessages()
      val content    = contentAsString(
        hmrcLayout(mainContentLayout = Some(customMainLayout))(Html("Some main content"))(fakeRequest, messages)
      )
      val document   = Jsoup.parse(content)

      val mainContent = document.getElementById("main-content")
      mainContent.childrenSize() shouldBe 1

      val customMainContent = mainContent.child(0)
      customMainContent.className()    shouldBe "my-custom-styling"
      customMainContent.text()         shouldBe "Some main content"
      customMainContent.childrenSize() shouldBe 0
    }

    "not display a HMRC banner by default" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcLayout))

      val hmrcBanner = document.select(".hmrc-banner")
      hmrcBanner should have size 0
    }

    "display a HMRC banner when flagged on" in {
      val hmrcLayout = app.injector.instanceOf[HmrcLayout]
      val messages   = getMessages()
      val content    = contentAsString(hmrcLayout(displayHmrcBanner = true)(Html(""))(fakeRequest, messages))
      val document   = Jsoup.parse(content)

      val hmrcBanner = document.select(".hmrc-banner")
      hmrcBanner should have size 1
    }

    "use default hmrcScripts if no scripts passed" in {
      val document = Jsoup.parse(contentAsString(defaultHmrcLayout))

      val scripts            = document.select("script")
      val scriptsWithSrcAttr = filterElements(scripts, elem => elem.hasAttr("src"), List.empty)
      scriptsWithSrcAttr should have size 1
    }

    "use custom additional scripts if passed" in {
      val hmrcLayout         = app.injector.instanceOf[HmrcLayout]
      val assetsConfig       = app.injector.instanceOf[AssetsConfig]
      val messages           = getMessages()
      val scriptsHtml        = Html("<script src=\"my-custom-script\"></script>")
      val content            =
        contentAsString(hmrcLayout(additionalScriptsBlock = Some(scriptsHtml))(Html(""))(fakeRequest, messages))
      val document: Document = Jsoup.parse(content)

      val scriptsWithSrcAttr = filterElements(document.select("script"), _.hasAttr("src"), List.empty)
      scriptsWithSrcAttr                  should have size 2
      scriptsWithSrcAttr(0).attr("src") shouldBe assetsConfig.hmrcFrontendJsUrl
      scriptsWithSrcAttr(1).attr("src") shouldBe "my-custom-script"
    }

    "use custom additional head content if passed" in {
      val hmrcLayout         = app.injector.instanceOf[HmrcLayout]
      val messages           = getMessages()
      val additionalHeadHtml = Html("<script src=\"my-additional-head-script\"></script>")
      val content            =
        contentAsString(hmrcLayout(additionalHeadBlock = Some(additionalHeadHtml))(Html(""))(fakeRequest, messages))
      val document: Document = Jsoup.parse(content)

      val head = document.select("head")
      head.toString should include(additionalHeadHtml.toString())
    }

  }
}
