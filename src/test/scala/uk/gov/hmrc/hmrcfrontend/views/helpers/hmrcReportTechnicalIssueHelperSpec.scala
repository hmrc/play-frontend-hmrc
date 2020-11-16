/*
 * Copyright 2020 HM Revenue & Customs
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
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.hmrcfrontend.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.html.helpers._

class hmrcReportTechnicalIssueHelperSpec
    extends WordSpecLike
    with Matchers
    with MessagesSupport
    with GuiceOneAppPerSuite {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure(
        Map(
          "play.allowGlobalApplication"  -> "true",
          "contact-frontend.serviceId" -> "my-service",
          "platform.frontend.host"       -> "https://www.tax.service.gov.uk"
        ))
      .build()

  def buildApp(properties: Map[String, String] = Map.empty): Application =
    new GuiceApplicationBuilder()
      .configure(Map("play.allowGlobalApplication" -> "true") ++ properties)
      .build()

  "HmrcReportTechnicalIssueHelper" should {
    "render link when serviceId is set" in {
      implicit val fakeRequest = FakeRequest("GET", "/foo")

      val content = contentAsString(HmrcReportTechnicalIssueHelper()(messages, fakeRequest))
      val links   = Jsoup.parse(content).select("a")

      links                    should have size 1
      links.first.attr("href") shouldBe "https://www.tax.service.gov.uk/contact/problem_reports_nonjs?newTab=true&service=my-service&referrerUrl=https%3A%2F%2Fwww.tax.service.gov.uk%2Ffoo"
    }
    "render link with correct referrer URL" in {
      implicit val fakeRequest = FakeRequest("GET", "/")

      val content = contentAsString(HmrcReportTechnicalIssueHelper()(messages, fakeRequest))
      val links   = Jsoup.parse(content).select("a")

      links                    should have size 1
      links.first.attr("href") shouldBe "https://www.tax.service.gov.uk/contact/problem_reports_nonjs?newTab=true&service=my-service&referrerUrl=https%3A%2F%2Fwww.tax.service.gov.uk%2F"
    }
    "render link with query string in referrer URL" in {
      implicit val fakeRequest = FakeRequest("GET", "/abc/def?ghi=jkl&mno=pqr")

      val content = contentAsString(HmrcReportTechnicalIssueHelper()(messages, fakeRequest))
      val links   = Jsoup.parse(content).select("a")

      links                    should have size 1
      links.first.attr("href") shouldBe "https://www.tax.service.gov.uk/contact/problem_reports_nonjs?newTab=true&service=my-service&referrerUrl=https%3A%2F%2Fwww.tax.service.gov.uk%2Fabc%2Fdef%3Fghi%3Djkl%26mno%3Dpqr"
    }
  }
}
