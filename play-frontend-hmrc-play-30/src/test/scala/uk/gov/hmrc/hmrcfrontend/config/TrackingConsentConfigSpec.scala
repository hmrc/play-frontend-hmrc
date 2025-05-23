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

package uk.gov.hmrc.hmrcfrontend.config

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

class TrackingConsentConfigSpec extends AnyWordSpec with Matchers {

  def buildApp(properties: Map[String, String]): Application =
    new GuiceApplicationBuilder()
      .configure(properties)
      .build()

  "optimizelyUrl" should {
    "return the correct optimizely url if optimizely.url and optimizely.projectId are defined" in {
      implicit val application: Application = buildApp(
        Map(
          "optimizely.url"       -> "http://optimizely.com/",
          "optimizely.projectId" -> "1234567"
        )
      )
      val config                            = application.injector.instanceOf[TrackingConsentConfig]
      config.optimizelyUrl should equal(Some("http://optimizely.com/1234567.js"))
    }

    "return None if optimizely.projectId is not defined" in {
      implicit val application: Application = buildApp(
        Map(
          "optimizely.url" -> "http://optimizely.com/"
        )
      )
      val config                            = application.injector.instanceOf[TrackingConsentConfig]
      config.optimizelyUrl should equal(None)
    }

    "return None if optimizely.projectId is null" in {
      implicit val application: Application = buildApp(
        Map(
          "optimizely.url"       -> "http://optimizely.com/",
          "optimizely.projectId" -> null
        )
      )
      val config                            = application.injector.instanceOf[TrackingConsentConfig]
      config.optimizelyUrl should equal(None)
    }

    "return None if optimizely.url is not defined" in {
      implicit val application: Application = buildApp(
        Map(
          "optimizely.url"       -> null,
          "optimizely.projectId" -> "1234567"
        )
      )
      val config                            = application.injector.instanceOf[TrackingConsentConfig]
      config.optimizelyUrl should equal(None)
    }
  }

  "trackingUrl" should {
    "return the correct url to tracking consent when running locally i.e. without platform.frontend.host defined" in {
      implicit val application: Application = buildApp(
        Map(
          "tracking-consent-frontend.gtm.container" -> "a"
        )
      )
      val config                            = application.injector.instanceOf[TrackingConsentConfig]
      config.trackingUrl() should equal(Some("http://localhost:12345/tracking-consent/tracking.js"))
    }

    "return the correct url to tracking consent when running in an MDTP environment i.e. with platform.frontend.host defined" in {
      implicit val application: Application = buildApp(
        Map(
          "platform.frontend.host"                  -> "https://www.tax.service.gov.uk",
          "tracking-consent-frontend.gtm.container" -> "a"
        )
      )
      val config                            = application.injector.instanceOf[TrackingConsentConfig]
      config.trackingUrl() should equal(Some("/tracking-consent/tracking.js"))
    }

    "return None if an tracking-consent-frontend.gtm.container does not exist in application.conf" in {
      implicit val application: Application = buildApp(Map.empty)
      val config                            = application.injector.instanceOf[TrackingConsentConfig]
      config.trackingUrl() should equal(None)
    }
  }
}
