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

package uk.gov.hmrc.hmrcfrontend.views.helpers

import org.jsoup.Jsoup
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.i18n.Lang
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.html.helpers._

class hmrcReportTechnicalIssueHelperSpec
    extends AnyWordSpecLike
    with Matchers
    with MessagesSupport
    with GuiceOneAppPerSuite {

  def buildApp(properties: Map[String, String] = Map.empty): Application =
    new GuiceApplicationBuilder()
      .configure(properties)
      .build()

  "HmrcReportTechnicalIssueHelper" should {
    "render link with the configured serviceId" in {
      implicit val fakeRequest = FakeRequest("GET", "/foo")
      implicit val app         = buildApp(
        Map(
          "contact-frontend.serviceId" -> "online-payments",
          "platform.frontend.host"     -> "https://www.tax.service.gov.uk"
        )
      )

      val hmrcReportTechnicalIssueHelper = app.injector.instanceOf[HmrcReportTechnicalIssueHelper]
      val content                        = contentAsString(hmrcReportTechnicalIssueHelper()(messages, fakeRequest))
      val links                          = Jsoup.parse(content).select("a")

      links should have size 1
      links.first.attr(
        "href"
      )   shouldBe "https://www.tax.service.gov.uk/contact/report-technical-problem?newTab=true&service=online-payments&referrerUrl=https%3A%2F%2Fwww.tax.service.gov.uk%2Ffoo"
    }

    "use the platform host when both platform and contact-frontend hosts are set" in {
      implicit val fakeRequest = FakeRequest("GET", "/foo")
      implicit val app         = buildApp(
        Map(
          "contact-frontend.serviceId" -> "online-payments",
          "platform.frontend.host"     -> "https://www.tax.service.gov.uk",
          "contact-frontend.host"      -> "http://localhost:9999"
        )
      )

      val hmrcReportTechnicalIssueHelper = app.injector.instanceOf[HmrcReportTechnicalIssueHelper]
      val content                        = contentAsString(hmrcReportTechnicalIssueHelper()(messages, fakeRequest))
      val links                          = Jsoup.parse(content).select("a")

      links should have size 1
      links.first.attr(
        "href"
      )   shouldBe "https://www.tax.service.gov.uk/contact/report-technical-problem?newTab=true&service=online-payments&referrerUrl=https%3A%2F%2Fwww.tax.service.gov.uk%2Ffoo"
    }

    "use contact-frontend host if platform host is not set" in {
      implicit val fakeRequest = FakeRequest("GET", "/foo")
      implicit val app         = buildApp(
        Map(
          "contact-frontend.serviceId" -> "online-payments",
          "contact-frontend.host"      -> "http://localhost:9999"
        )
      )

      val hmrcReportTechnicalIssueHelper = app.injector.instanceOf[HmrcReportTechnicalIssueHelper]
      val content                        = contentAsString(hmrcReportTechnicalIssueHelper()(messages, fakeRequest))
      val links                          = Jsoup.parse(content).select("a")

      links should have size 1
      links.first.attr(
        "href"
      )   shouldBe "http://localhost:9999/contact/report-technical-problem?newTab=true&service=online-payments&referrerUrl=%2Ffoo"
    }

    "display link in English by default" in {
      implicit val fakeRequest = FakeRequest("GET", "/foo")
      implicit val app         = buildApp(
        Map(
          "contact-frontend.serviceId" -> "online-payments",
          "contact-frontend.host"      -> "http://localhost:9999"
        )
      )

      val englishMessages                = messagesApi.preferred(Seq(Lang("en")))
      val hmrcReportTechnicalIssueHelper = app.injector.instanceOf[HmrcReportTechnicalIssueHelper]
      val content                        = contentAsString(hmrcReportTechnicalIssueHelper()(englishMessages, fakeRequest))
      val links                          = Jsoup.parse(content).select("a")

      links              should have size 1
      links.first.text shouldBe "Is this page not working properly? (opens in new tab)"
    }

    "display link in Welsh" in {
      implicit val fakeRequest = FakeRequest("GET", "/foo")
      implicit val app         = buildApp(
        Map(
          "contact-frontend.serviceId" -> "online-payments",
          "contact-frontend.host"      -> "http://localhost:9999"
        )
      )

      val welshMessages                  = messagesApi.preferred(Seq(Lang("cy")))
      val hmrcReportTechnicalIssueHelper = app.injector.instanceOf[HmrcReportTechnicalIssueHelper]
      val content                        = contentAsString(hmrcReportTechnicalIssueHelper()(welshMessages, fakeRequest))
      val links                          = Jsoup.parse(content).select("a")

      links              should have size 1
      links.first.text shouldBe "A yw’r dudalen hon yn gweithio’n iawn? (yn agor tab newydd)"
    }

    "render no link when serviceId is not set" in {
      implicit val fakeRequest = FakeRequest("GET", "/foo")
      implicit val app         = buildApp()

      val hmrcReportTechnicalIssueHelper = app.injector.instanceOf[HmrcReportTechnicalIssueHelper]
      val content                        = contentAsString(hmrcReportTechnicalIssueHelper()(messages, fakeRequest))
      val links                          = Jsoup.parse(content).select("a")

      links should have size 0
    }
  }
}
