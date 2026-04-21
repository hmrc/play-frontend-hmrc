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

package uk.gov.hmrc.hmrcfrontend.views.config

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Configuration
import play.api.i18n.{Lang, Messages}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import uk.gov.hmrc.govukfrontend.views.Aliases.{HtmlContent, PhaseBanner, Tag, Text}
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.config.ContactFrontendConfig

class StandardPhaseBannerSpec extends AnyWordSpec with Matchers with MessagesSupport with GuiceOneAppPerSuite {
  "StandardBetaBanner" must {
    val standardBetaBanner = app.injector.instanceOf[StandardBetaBanner]

    "return the correct PhaseBanner object for the given phase and url" in {
      val phaseBanner = standardBetaBanner(url = "/feedback")

      phaseBanner mustBe PhaseBanner(
        tag = Some(Tag(content = Text("Beta"))),
        content = HtmlContent(
          "This is a new service. Help us improve it and <a class=\"govuk-link\" href=\"/feedback\" target=\"_blank\">give your feedback (opens in new tab)</a>."
        ),
        classes = "govuk-!-display-none-print"
      )
    }

    "return the correct PhaseBanner object for a different phase and url" in {
      val phaseBanner = standardBetaBanner(url = "/other-feedback")

      phaseBanner mustBe PhaseBanner(
        tag = Some(Tag(content = Text("Beta"))),
        content = HtmlContent(
          "This is a new service. Help us improve it and <a class=\"govuk-link\" href=\"/other-feedback\" target=\"_blank\">give your feedback (opens in new tab)</a>."
        ),
        classes = "govuk-!-display-none-print"
      )
    }

    "properly escape the url" in {
      val phaseBanner = standardBetaBanner(url = "\"><script>console.log('evil');</script><a href=\"")

      phaseBanner mustBe PhaseBanner(
        tag = Some(Tag(content = Text("Beta"))),
        content = HtmlContent(
          "This is a new service. Help us improve it and <a class=\"govuk-link\" href=\"&quot;&gt;&lt;script&gt;console.log(&#x27;evil&#x27;);&lt;/script&gt;&lt;a href=&quot;\" target=\"_blank\">give your feedback (opens in new tab)</a>."
        ),
        classes = "govuk-!-display-none-print"
      )
    }

    "return the correct Welsh content" in {
      implicit val messages: Messages = messagesApi.preferred(Seq(Lang("cy")))

      val phaseBanner = standardBetaBanner(url = "/feedback")

      phaseBanner mustBe PhaseBanner(
        tag = Some(Tag(content = Text("Beta"))),
        content = HtmlContent(
          "Gwasanaeth newydd yw hwn. Helpwch ni i’w wella a <a class=\"govuk-link\" href=\"/feedback\" target=\"_blank\">rhoi eich adborth (yn agor tab newydd)</a>."
        ),
        classes = "govuk-!-display-none-print"
      )
    }

    "return formatted URL with config values for beta-feedback in contact-frontend when no URL provided" in {
      implicit val request: RequestHeader          = FakeRequest("GET", "/help/terms-and-conditions")
      implicit val cfConfig: ContactFrontendConfig = new GuiceApplicationBuilder()
        .configure(
          "platform.frontend.host"     -> "tax.service.gov.uk",
          "contact-frontend.serviceId" -> "my-service"
        )
        .build()
        .injector
        .instanceOf[ContactFrontendConfig]

      val phaseBanner: PhaseBanner = standardBetaBanner()
      phaseBanner.content mustBe HtmlContent(
        "This is a new service. Help us improve it and <a class=\"govuk-link\" href=" +
          "\"tax.service.gov.uk/contact/beta-feedback?service=my-service&amp;referrerUrl=tax.service.gov.uk%2Fhelp%2Fterms-and-conditions\" target=\"_blank\"" +
          ">give your feedback (opens in new tab)</a>."
      )
    }

    "return formatted URL with empty config values for beta-feedback in contact-frontend when no URL provided" in {
      implicit val request: RequestHeader          = FakeRequest()
      implicit val cfConfig: ContactFrontendConfig = new GuiceApplicationBuilder()
        // the following falls back to always set contact-frontend.host if not passed
        .configure("platform.frontend.host" -> "tax.service.gov.uk")
        .build()
        .injector
        .instanceOf[ContactFrontendConfig]

      val phaseBanner: PhaseBanner = standardBetaBanner()
      phaseBanner.content mustBe HtmlContent(
        "This is a new service. Help us improve it and <a class=\"govuk-link\" href=" +
          "\"tax.service.gov.uk/contact/beta-feedback?referrerUrl=tax.service.gov.uk%2F\" target=\"_blank\">give your feedback (opens in new tab)</a>."
      )
    }
  }

  "StandardAlphaBanner" must {
    val standardAlphaBanner = app.injector.instanceOf[StandardAlphaBanner]

    "return the correct PhaseBanner object for the given url" in {
      val phaseBanner = standardAlphaBanner(url = "/feedback")

      phaseBanner mustBe PhaseBanner(
        tag = Some(Tag(content = Text("Alpha"))),
        content = HtmlContent(
          "This is a new service. Help us improve it and <a class=\"govuk-link\" href=\"/feedback\" target=\"_blank\">give your feedback (opens in new tab)</a>."
        ),
        classes = "govuk-!-display-none-print"
      )
    }
  }

  "PhaseBanner default links to contact frontend forms" must {
    "propagate the use of service nav when enabled" in {
      implicit val request: RequestHeader                   = FakeRequest("GET", "/help/terms-and-conditions")
      implicit val configWhenEnabled: ContactFrontendConfig = new GuiceApplicationBuilder()
        .configure(
          "platform.frontend.host"                    -> "tax.service.gov.uk",
          "contact-frontend.serviceId"                -> "my-service",
          "play-frontend-hmrc.forceServiceNavigation" -> "true"
        )
        .build()
        .injector
        .instanceOf[ContactFrontendConfig]

      val betaBanner = new StandardBetaBanner()

      betaBanner().content mustBe HtmlContent(
        "This is a new service. Help us improve it and <a class=\"govuk-link\" href=" +
          "\"tax.service.gov.uk/contact/beta-feedback?service=my-service&amp;referrerUrl=tax.service.gov.uk%2Fhelp%2Fterms-and-conditions&amp;useServiceNavigation\" target=\"_blank\">give your feedback (opens in new tab)</a>."
      )
    }
  }
}
