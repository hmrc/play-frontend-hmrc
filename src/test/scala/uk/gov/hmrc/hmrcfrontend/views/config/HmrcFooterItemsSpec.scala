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
import play.api.Application
import play.api.i18n.{Lang, Messages}
import play.api.test.FakeRequest
import play.api.inject.guice.GuiceApplicationBuilder
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.footer.FooterItem

class HmrcFooterItemsSpec extends AnyWordSpec with Matchers with MessagesSupport {

  implicit val request: FakeRequest[_] = FakeRequest("GET", "/foo")

  def buildApp(properties: Map[String, String] = Map.empty): Application =
    new GuiceApplicationBuilder()
      .configure(properties)
      .build()

  "HmrcFooterItems" must {
    "Return the correct links for the item keys defined" in {
      val app             = buildApp()
      val hmrcFooterItems = app.injector.instanceOf[HmrcFooterItems]

      hmrcFooterItems.get mustBe Seq(
        FooterItem(
          Some("Cookies"),
          Some("/help/cookies")
        ),
        FooterItem(
          Some("Privacy policy"),
          Some("/help/privacy")
        ),
        FooterItem(
          Some("Terms and conditions"),
          Some("/help/terms-and-conditions")
        ),
        FooterItem(
          Some("Help using GOV.UK"),
          Some("https://www.gov.uk/help")
        ),
        FooterItem(
          Some("Contact"),
          Some("https://www.gov.uk/government/organisations/hm-revenue-customs/contact")
        ),
        FooterItem(
          Some("Rhestr o Wasanaethau Cymraeg"),
          Some("https://www.gov.uk/cymraeg"),
          attributes = Map(
            "lang"     -> "cy",
            "hreflang" -> "cy"
          )
        )
      )
    }

    "Return the correct links including a manually defined accessibility statement link" in {
      val app             = buildApp()
      val hmrcFooterItems = app.injector.instanceOf[HmrcFooterItems]

      hmrcFooterItems.getWithAccessibilityStatementUrl(accessibilityStatementUrl =
        Some("https://www.example.com/accessibility-statement")
      ) mustBe Seq(
        FooterItem(
          Some("Cookies"),
          Some("/help/cookies")
        ),
        FooterItem(
          Some("Accessibility statement"),
          Some(
            "https://www.example.com/accessibility-statement"
          )
        ),
        FooterItem(
          Some("Privacy policy"),
          Some("/help/privacy")
        ),
        FooterItem(
          Some("Terms and conditions"),
          Some("/help/terms-and-conditions")
        ),
        FooterItem(
          Some("Help using GOV.UK"),
          Some("https://www.gov.uk/help")
        ),
        FooterItem(
          Some("Contact"),
          Some("https://www.gov.uk/government/organisations/hm-revenue-customs/contact")
        ),
        FooterItem(
          Some("Rhestr o Wasanaethau Cymraeg"),
          Some("https://www.gov.uk/cymraeg"),
          attributes = Map(
            "lang"     -> "cy",
            "hreflang" -> "cy"
          )
        )
      )
    }

    "Return the accessibility link if defined" in {
      val app             = buildApp(
        Map(
          "platform.frontend.host"               -> "https://www.tax.service.gov.uk",
          "accessibility-statement.service-path" -> "/bar"
        )
      )
      val hmrcFooterItems = app.injector.instanceOf[HmrcFooterItems]

      hmrcFooterItems.get mustBe Seq(
        FooterItem(
          Some("Cookies"),
          Some("/help/cookies")
        ),
        FooterItem(
          Some("Accessibility statement"),
          Some(
            "https://www.tax.service.gov.uk/accessibility-statement/bar?referrerUrl=https%3A%2F%2Fwww.tax.service.gov.uk%2Ffoo"
          )
        ),
        FooterItem(
          Some("Privacy policy"),
          Some("/help/privacy")
        ),
        FooterItem(
          Some("Terms and conditions"),
          Some("/help/terms-and-conditions")
        ),
        FooterItem(
          Some("Help using GOV.UK"),
          Some("https://www.gov.uk/help")
        ),
        FooterItem(
          Some("Contact"),
          Some("https://www.gov.uk/government/organisations/hm-revenue-customs/contact")
        ),
        FooterItem(
          Some("Rhestr o Wasanaethau Cymraeg"),
          Some("https://www.gov.uk/cymraeg"),
          attributes = Map(
            "lang"     -> "cy",
            "hreflang" -> "cy"
          )
        )
      )
    }

    "Return the correct links in Welsh" in {
      val app                         = buildApp()
      implicit val messages: Messages = messagesApi.preferred(Seq(Lang("cy")))
      val hmrcFooterItems             = app.injector.instanceOf[HmrcFooterItems]

      hmrcFooterItems.get mustBe Seq(
        FooterItem(
          Some("Cwcis"),
          Some("/help/cookies")
        ),
        FooterItem(
          Some("Polisi preifatrwydd"),
          Some("/help/privacy")
        ),
        FooterItem(
          Some("Telerau ac Amodau"),
          Some("/help/terms-and-conditions")
        ),
        FooterItem(
          Some("Help wrth ddefnyddio GOV.UK"),
          Some("https://www.gov.uk/help")
        ),
        FooterItem(
          Some("Cysylltu"),
          Some("https://www.gov.uk/government/organisations/hm-revenue-customs/contact")
        ),
        FooterItem(
          Some("Rhestr o Wasanaethau Cymraeg"),
          Some("https://www.gov.uk/cymraeg"),
          attributes = Map(
            "lang"     -> "cy",
            "hreflang" -> "cy"
          )
        )
      )
    }

    "Return the correct links in Welsh including the accessibility statement link" in {
      val app                         = buildApp(
        Map(
          "platform.frontend.host"               -> "https://www.tax.service.gov.uk",
          "accessibility-statement.service-path" -> "/bar"
        )
      )
      implicit val messages: Messages = messagesApi.preferred(Seq(Lang("cy")))
      val hmrcFooterItems             = app.injector.instanceOf[HmrcFooterItems]

      hmrcFooterItems.get mustBe Seq(
        FooterItem(
          Some("Cwcis"),
          Some("/help/cookies")
        ),
        FooterItem(
          Some("Datganiad hygyrchedd"),
          Some(
            "https://www.tax.service.gov.uk/accessibility-statement/bar?referrerUrl=https%3A%2F%2Fwww.tax.service.gov.uk%2Ffoo"
          )
        ),
        FooterItem(
          Some("Polisi preifatrwydd"),
          Some("/help/privacy")
        ),
        FooterItem(
          Some("Telerau ac Amodau"),
          Some("/help/terms-and-conditions")
        ),
        FooterItem(
          Some("Help wrth ddefnyddio GOV.UK"),
          Some("https://www.gov.uk/help")
        ),
        FooterItem(
          Some("Cysylltu"),
          Some("https://www.gov.uk/government/organisations/hm-revenue-customs/contact")
        ),
        FooterItem(
          Some("Rhestr o Wasanaethau Cymraeg"),
          Some("https://www.gov.uk/cymraeg"),
          attributes = Map(
            "lang"     -> "cy",
            "hreflang" -> "cy"
          )
        )
      )
    }
  }
}
