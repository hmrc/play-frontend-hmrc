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
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import play.api.Application
import play.api.i18n.{DefaultLangs, Lang}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.MessagesRequest
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, _}
import uk.gov.hmrc.hmrcfrontend.views.JsoupHelpers
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcTimeoutDialogHelper

import scala.collection.immutable.List

class hmrcTimeoutDialogHelperSpec
  extends AnyWordSpecLike
    with Matchers
    with JsoupHelpers {

  implicit val fakeRequest = FakeRequest("GET", "/foo")

  def buildApp(properties: Map[String, String] = Map.empty): Application =
    new GuiceApplicationBuilder()
      .configure(Map("play.i18n.langs" -> List("en", "cy")) ++ properties)
      .build()

  def getMessages(messages: Map[String, Map[String, String]] = Map.empty, lang: Lang = Lang("en")) = {
    val messagesApi = stubMessagesApi(messages, new DefaultLangs(Seq(lang)))
    new MessagesRequest(FakeRequest(), messagesApi).messages
  }

  "HmrcTimeoutDialogHelper" should {
    "render a meta element" in {
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out")(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
    }

    "render the signOutUrl data attribute element" in {
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out")(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-sign-out-url") should be("/sign-out")
    }

    "render the timeout data attribute using the default" in {
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out")(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-timeout") should be("900")
    }

    "render the timeout data attribute using the bootstrap-play session.timeoutSeconds key" in {
      val hmrcTimeoutDialogHelper = buildApp(Map(
        "session.timeoutSeconds" -> "600"
      )).injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out")(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-timeout") should be("600")
    }

    "render the timeout data attribute using the bootstrap-play session.timeout key" in {
      val hmrcTimeoutDialogHelper = buildApp(Map(
        "session.timeout" -> "30.minutes"
      )).injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out")(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-timeout") should be("1800")
    }

    "render the timeout data attribute using the passed parameter" in {
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out", timeout = Some(1800))(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-timeout") should be("1800")
    }

    "render the countdown data attribute element" in {
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out")(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-countdown") should be("120")
    }

    "render the countdown data attribute element using the passed parameter" in {
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out", countdown = Some(240))(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-countdown") should be("240")
    }

    "render the keepAliveUrl data attribute element" in {
      hmrcfrontend.RoutesPrefix.setPrefix("/foo-service/hmrc-frontend")
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out")(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-keep-alive-url") should be("/foo-service/hmrc-frontend/keep-alive")
    }

    "render the keepAliveUrl data attribute element using the passed parameter" in {
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out", keepAliveUrl = Some("/keep-alive"))(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-keep-alive-url") should be("/keep-alive")
    }

    "render the timeoutUrl data attribute element" in {
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out", timeoutUrl = Some("/time-out"))(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-timeout-url") should be("/time-out")
    }

    "render empty content data attributes by default" in {
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out")(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-title") should be("")
      metas.first.attr("data-message") should be("")
      metas.first.attr("data-message-suffix") should be("")
      metas.first.attr("data-keep-alive-button-text") should be("")
      metas.first.attr("data-sign-out-button-text") should be("")
    }

    "render an alternative title attribute if passed" in {
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out", title = Some("Foo Title"))(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-title") should be("Foo Title")
    }

    "render the message data attribute" in {
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out", message = Some("Foo Message"))(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-message") should be("Foo Message")
    }

    "render the message suffix data attribute" in {
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out", messageSuffix = Some("suffix"))(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-message-suffix") should be("suffix")
    }

    "render the keep alive button text data attribute" in {
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out", keepAliveButtonText = Some("Keep Alive"))(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-keep-alive-button-text") should be("Keep Alive")
    }

    "render the sign out button text data attribute" in {
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages()
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out", signOutButtonText = Some("Logout"))(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-sign-out-button-text") should be("Logout")
    }

    "render the language attribute" in {
      val hmrcTimeoutDialogHelper = buildApp().injector.instanceOf[HmrcTimeoutDialogHelper]

      val messages = getMessages(Map.empty, Lang("cy"))
      val content  = contentAsString(hmrcTimeoutDialogHelper(signOutUrl = "/sign-out")(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val metas    = document.select("meta")

      metas should have size 1
      metas.first.attr("data-language") should be("cy")
    }
  }
}
