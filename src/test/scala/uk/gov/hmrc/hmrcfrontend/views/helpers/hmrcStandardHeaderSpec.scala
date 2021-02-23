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
import org.scalatest.{Matchers, WordSpecLike}
import play.api.Application
import play.api.i18n.{DefaultLangs, Lang}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.MessagesRequest
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, _}
import uk.gov.hmrc.hmrcfrontend.views.JsoupHelpers
import uk.gov.hmrc.hmrcfrontend.views.config.StandardPhaseBanner
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcStandardHeader
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.userresearchbanner.UserResearchBanner

import scala.collection.immutable.List

class hmrcStandardHeaderSpec extends WordSpecLike with Matchers with JsoupHelpers {
  implicit val fakeRequest = FakeRequest("GET", "/foo")

  def buildApp(properties: Map[String, String] = Map.empty): Application =
    new GuiceApplicationBuilder()
      .configure(Map("play.i18n.langs" -> List("en", "cy")) ++ properties)
      .build()

  def getMessages(messages: Map[String, Map[String, String]] = Map.empty, lang: Lang = Lang("en")) = {
    val messagesApi = stubMessagesApi(messages, new DefaultLangs(Seq(lang)))
    new MessagesRequest(FakeRequest(), messagesApi).messages
  }

  "HmrcStandardHeader" should {
    "render a header element" in {
      val hmrcStandardHeader = buildApp().injector.instanceOf[HmrcStandardHeader]

      val messages = getMessages()
      val content  = contentAsString(hmrcStandardHeader()(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val headers    = document.select("header")

      headers should have size 1
    }

    "not render the service name link by default" in {
      val hmrcStandardHeader = buildApp().injector.instanceOf[HmrcStandardHeader]

      val messages = getMessages()
      val content  = contentAsString(hmrcStandardHeader()(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val links    = document.select(".govuk-header__link--service-name")

      links should have size 0
    }

    "render the service name link if a messages key exists" in {
      val hmrcStandardHeader = buildApp().injector.instanceOf[HmrcStandardHeader]

      val messages = getMessages(Map("en" -> Map("service.name" -> "Foo Service")))
      val content  = contentAsString(hmrcStandardHeader(serviceUrl = Some("/foo"))(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val links    = document.select(".govuk-header__link--service-name")

      links should have size 1
      links.first.attr("href") should be("/foo")
      links.first.text should be ("Foo Service")
    }

    "render the service name specified as a parameter" in {
      val hmrcStandardHeader = buildApp().injector.instanceOf[HmrcStandardHeader]

      val messages = getMessages()
      val content  = contentAsString(hmrcStandardHeader(serviceUrl = Some("/foo"), serviceName = Some("Bam Service"))(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val links    = document.select(".govuk-header__link--service-name")

      links should have size 1
      links.first.text should be ("Bam Service")
    }

    "render the service name specified as a parameter even if a messages key exists" in {
      val hmrcStandardHeader = buildApp().injector.instanceOf[HmrcStandardHeader]

      val messages = getMessages(Map("en" -> Map("service.name" -> "Foo Service")))
      val content  = contentAsString(hmrcStandardHeader(serviceUrl = Some("/foo"), serviceName = Some("Bar Service"))(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val links    = document.select(".govuk-header__link--service-name")

      links should have size 1
      links.first.text should be ("Bar Service")
    }

    "render the service name in Welsh if a messages key exists" in {
      val hmrcStandardHeader = buildApp().injector.instanceOf[HmrcStandardHeader]

      val messages = getMessages(Map("cy" -> Map("service.name" -> "Welsh Service")), Lang("cy"))
      val content  = contentAsString(hmrcStandardHeader()(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val links    = document.select(".govuk-header__link--service-name")

      links should have size 1
      links.first.text should be ("Welsh Service")
    }

    "not render the sign out link by default" in {
      val hmrcStandardHeader = buildApp().injector.instanceOf[HmrcStandardHeader]

      val messages = getMessages()
      val content  = contentAsString(hmrcStandardHeader()(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val links    = document.select(".hmrc-sign-out-nav__link")

      links should have size 0
    }

    "render the sign out link if a signOutUrl is supplied" in {
      val hmrcStandardHeader = buildApp().injector.instanceOf[HmrcStandardHeader]

      val messages = getMessages()
      val content  = contentAsString(hmrcStandardHeader(signOutUrl = Some("/sign-out"))(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val links    = document.select(".hmrc-sign-out-nav__link")

      links should have size 1
      links.first.attr("href") should be ("/sign-out")
      links.first.text should be ("Sign out")
    }

    "render the sign out link in Welsh" in {
      val hmrcStandardHeader = buildApp().injector.instanceOf[HmrcStandardHeader]

      val welshMessages = getMessages(lang = Lang("cy"))

      val content  = contentAsString(hmrcStandardHeader(signOutUrl = Some("/sign-out"))(welshMessages, fakeRequest))
      val document = Jsoup.parse(content)
      val links    = document.select(".hmrc-sign-out-nav__link")

      links should have size 1
      links.first.text should be ("Allgofnodi")
    }

    "render the correct gov.uk homepage link" in {
      val hmrcStandardHeader = buildApp().injector.instanceOf[HmrcStandardHeader]

      val messages = getMessages(Map("en" -> Map("header.govuk.url" -> "/foo")))
      val content  = contentAsString(hmrcStandardHeader()(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val homepageLinks    = document.select(".govuk-header__link--homepage")

      homepageLinks should have size 1
      homepageLinks.first.attr("href") should be ("/foo")
    }

    "render the svg fallback image correctly" in {
      // simulate including the govuk routes in a frontend microservice via
      // ->         /govuk-frontend           govuk.Routes
      govuk.RoutesPrefix.setPrefix("/some-service/govuk-frontend")
      val hmrcStandardHeader = buildApp().injector.instanceOf[HmrcStandardHeader]

      val messages = getMessages()
      val content  = contentAsString(hmrcStandardHeader()(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val fallbackImages    = document.select(".govuk-header__logotype-crown-fallback-image")

      fallbackImages should have size 1
      fallbackImages.first.attr("src") should be ("/some-service/govuk-frontend/assets/images/govuk-logotype-crown.png")
    }

    "render the hmrc banner" in {
      val hmrcStandardHeader = buildApp().injector.instanceOf[HmrcStandardHeader]

      val messages = getMessages()
      val content  = contentAsString(hmrcStandardHeader(displayHmrcBanner = true)(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val banners    = document.select(".hmrc-banner")

      banners should have size 1
    }

    "render the phase banner" in {
      val hmrcStandardHeader = buildApp().injector.instanceOf[HmrcStandardHeader]
      val standardPhaseBanner = buildApp().injector.instanceOf[StandardPhaseBanner]

      implicit val messages = getMessages()
      val content  = contentAsString(hmrcStandardHeader(
        phaseBanner = Some(standardPhaseBanner(phase = "alpha", url = "/foo"))
      ))
      val document = Jsoup.parse(content)
      val banners    = document.select(".govuk-phase-banner")

      banners should have size 1
    }

    "render the user research banner" in {
      val hmrcStandardHeader = buildApp().injector.instanceOf[HmrcStandardHeader]

      implicit val messages = getMessages()
      val content  = contentAsString(hmrcStandardHeader(
        userResearchBanner = Some(UserResearchBanner(url = "/foo"))
      ))
      val document = Jsoup.parse(content)
      val banners    = document.select(".hmrc-user-research-banner")

      banners should have size 1
      banners.first.text should include("Help improve HMRC services")

    }

    "render the user research banner in Welsh" in {
      val hmrcStandardHeader = buildApp().injector.instanceOf[HmrcStandardHeader]

      implicit val messages = getMessages(lang = Lang("cy"))
      val content  = contentAsString(hmrcStandardHeader(
        userResearchBanner = Some(UserResearchBanner(url = "/foo"))
      ))
      val document = Jsoup.parse(content)
      val banners    = document.select(".hmrc-user-research-banner")

      banners should have size 1
      banners.first.text should include("Helpwch i wella gwasanaethau CThEM")
    }
  }
}
