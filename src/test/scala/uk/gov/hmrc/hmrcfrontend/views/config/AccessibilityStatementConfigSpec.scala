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

package uk.gov.hmrc.hmrcfrontend.views.config

import org.scalatest.{Matchers, WordSpec}
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import uk.gov.hmrc.hmrcfrontend.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.config.AccessibilityStatementConfig

class AccessibilityStatementConfigSpec extends WordSpec with Matchers with MessagesSupport {

  def buildApp(properties: Map[String, String]): Application =
    new GuiceApplicationBuilder()
      .configure(properties)
      .build()

  "url" should {
    implicit val request: FakeRequest[_] = FakeRequest("GET", "/foo?bar=baz")

    "return the correct url to the service's accessibility statement" in {
      implicit val application: Application = buildApp(
        Map(
          "accessibility-statement.path"         -> "/accessibility-statement",
          "accessibility-statement.service-path" -> "/bar"
        ))
      val config = application.injector.instanceOf[AccessibilityStatementConfig]
      config.url should equal(Some("http://localhost:12346/accessibility-statement/bar?referrerUrl=%2Ffoo%3Fbar%3Dbaz"))
    }

    "return None if an accessibility-statement.service-path key does not exist in application.conf" in {
      implicit val application: Application =
        buildApp(Map("accessibility-statement.path" -> "/accessibility-statement"))
      val config = application.injector.instanceOf[AccessibilityStatementConfig]
      config.url should equal(None)
    }

    "return a url including a full, absolute, properly encoded referrerUrl" in {
      implicit val application: Application =
        buildApp(
          Map(
            "accessibility-statement.path"         -> "/accessibility-statement",
            "accessibility-statement.service-path" -> "/bar",
            "platform.frontend.host"               -> "https://www.tax.service.gov.uk"
          ))
      val config = application.injector.instanceOf[AccessibilityStatementConfig]
      config.url should equal(Some(
        "https://www.tax.service.gov.uk/accessibility-statement/bar?referrerUrl=https%3A%2F%2Fwww.tax.service.gov.uk%2Ffoo%3Fbar%3Dbaz"))
    }

    "return a url including a relative referrerUrl if platform.frontend.host is not defined" in {
      implicit val application: Application =
        buildApp(
          Map(
            "accessibility-statement.path"         -> "/accessibility-statement",
            "accessibility-statement.service-path" -> "/bar"))
      val config = application.injector.instanceOf[AccessibilityStatementConfig]
      config.url should equal(Some("http://localhost:12346/accessibility-statement/bar?referrerUrl=%2Ffoo%3Fbar%3Dbaz"))
    }
  }
}
