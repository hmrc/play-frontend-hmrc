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

package uk.gov.hmrc.hmrcfrontend.views.config

import org.scalatest.{Matchers, WordSpec}
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest
import uk.gov.hmrc.hmrcfrontend.config.TrackingConsentConfig

class TrackingConsentConfigSpec extends WordSpec with Matchers {

  def buildApp(properties: Map[String, String]) =
    new GuiceApplicationBuilder()
      .configure(properties)
      .build()

  "url" should {
    implicit val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/foo")

    "return the correct url to tracking consent when running locally" in {
      implicit val application: Application = buildApp(
        Map(
          "tracking-consent-frontend.gtm.container" -> "a"
        ))
      val config = application.injector.instanceOf[TrackingConsentConfig]
      config.url should equal(Some("http://localhost:12345/tracking-consent/tracking.js"))
    }

    "return the correct url to tracking consent when running in an MDTP environment" in {
      implicit val application: Application = buildApp(
        Map(
          "platform.frontend.host"                  -> "https://www.tax.service.gov.uk",
          "tracking-consent-frontend.gtm.container" -> "a"
        ))
      val config = application.injector.instanceOf[TrackingConsentConfig]
      config.url should equal(Some("https://www.tax.service.gov.uk/tracking-consent/tracking.js"))
    }

    "return None if an tracking-consent-frontend.gtm.container does not exist in application.conf" in {
      implicit val application: Application = buildApp(Map.empty)
      val config                            = application.injector.instanceOf[TrackingConsentConfig]
      config.url should equal(None)
    }
  }

  "optimizelyGtmUrl" should {
    implicit val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/foo")

    "return the correct url to the optimizely gtm snippet when running locally" in {
      implicit val application: Application = buildApp(
        Map(
          "tracking-consent-frontend.gtm.container" -> "a"
        ))
      val config = application.injector.instanceOf[TrackingConsentConfig]
      config.optimizelyGtmUrl should equal(Some("http://localhost:12345/tracking-consent/tracking/optimizely.js"))
    }

    "return the correct url to the optimizely gtm snippet when running in an MDTP environment" in {
      implicit val application: Application = buildApp(
        Map(
          "platform.frontend.host"                  -> "https://www.tax.service.gov.uk",
          "tracking-consent-frontend.gtm.container" -> "a"
        ))
      val config = application.injector.instanceOf[TrackingConsentConfig]
      config.optimizelyGtmUrl should equal(
        Some("https://www.tax.service.gov.uk/tracking-consent/tracking/optimizely.js"))
    }

    "return None if an tracking-consent-frontend.gtm.container does not exist in application.conf" in {
      implicit val application: Application = buildApp(Map.empty)
      val config                            = application.injector.instanceOf[TrackingConsentConfig]
      config.optimizelyGtmUrl should equal(None)
    }
  }
}
