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

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.i18n.{Lang, Messages}
import play.api.inject.guice.GuiceApplicationBuilder
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.helpers.views.JsoupHelpers
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcTrackingConsentSnippet

class TrackingConsentSnippetSpec
    extends AnyWordSpecLike
    with Matchers
    with GuiceOneAppPerSuite
    with JsoupHelpers
    with MessagesSupport {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure(
        Map(
          "optimizely.url"                          -> "https://cdn.optimizely.com/",
          "optimizely.projectId"                    -> "1234567",
          "tracking-consent-frontend.gtm.container" -> "d"
        )
      )
      .build()

  "TrackingConsentSnippet" should {

    "include the tracking consent script tag" in {
      val component = app.injector.instanceOf[HmrcTrackingConsentSnippet]
      val content   = component()
      val scripts   = content.select("script#tracking-consent-script-tag")
      scripts should have size 1
    }

    "include the tracking consent script tag with the correct container attribute" in {
      val component = app.injector.instanceOf[HmrcTrackingConsentSnippet]
      val content   = component()
      val scripts   = content.select("script#tracking-consent-script-tag")
      scripts.first.attr("data-gtm-container") should be("d")
    }

    "include the tracking consent script tag with the correct language attribute" in {
      val component = app.injector.instanceOf[HmrcTrackingConsentSnippet]
      val content   = component()
      val scripts   = content.select("script#tracking-consent-script-tag")
      scripts.first.attr("data-language") should be("en")
    }

    "include the tracking consent script tag with the correct language attribute when Welsh" in {
      val welshMessages: Messages = messagesApi.preferred(Seq(Lang("cy")))

      val component = app.injector.instanceOf[HmrcTrackingConsentSnippet]
      val content   = component()(welshMessages)
      val scripts   = content.select("script#tracking-consent-script-tag")
      scripts.first.attr("data-language") should be("cy")
    }

    "include the tracking script first" in {
      val component = app.injector.instanceOf[HmrcTrackingConsentSnippet]
      val content   = component()
      val scripts   = content.select("script")

      scripts.get(0).attr("id")  should be("tracking-consent-script-tag")
      scripts.get(0).attr("src") should be("http://localhost:12345/tracking-consent/tracking.js")
    }

    "include the optimizely script tag" in {
      val component = app.injector.instanceOf[HmrcTrackingConsentSnippet]
      val content   = component()
      val scripts   = content.select("script")

      scripts.get(1).attr("src") should be("https://cdn.optimizely.com/1234567.js")
    }

    "include the optimizely gtm script tag" in {
      val component = app.injector.instanceOf[HmrcTrackingConsentSnippet]
      val content   = component()
      val scripts   = content.select("script")

      scripts.get(2).attr("src") should be("http://localhost:12345/tracking-consent/tracking/optimizely.js")
    }

    "include nonce attribute for all scripts" in {
      val component = app.injector.instanceOf[HmrcTrackingConsentSnippet]
      val content   = component(Some("abcdefghij"))
      val scripts   = content.select("script")

      scripts.get(0).attr("nonce") should be("abcdefghij")
      scripts.get(1).attr("nonce") should be("abcdefghij")
      scripts.get(2).attr("nonce") should be("abcdefghij")
    }

    "not include script tags with any nonce attributes if nonce is not defined" in {
      val component = app.injector.instanceOf[HmrcTrackingConsentSnippet]
      val content   = component()
      val scripts   = content.select("script")

      scripts.get(0).attr("nonce") should be("")
      scripts.get(1).attr("nonce") should be("")
      scripts.get(2).attr("nonce") should be("")
    }
  }
}
