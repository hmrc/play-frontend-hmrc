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

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.i18n.{Lang, Messages}
import uk.gov.hmrc.govukfrontend.views.Aliases.{HtmlContent, PhaseBanner, Tag, Text}
import uk.gov.hmrc.hmrcfrontend.MessagesSupport

class StandardPhaseBannerSpec extends AnyWordSpec
  with Matchers with MessagesSupport with GuiceOneAppPerSuite {
  "StandardBetaBanner" must {
    val standardBetaBanner = app.injector.instanceOf[StandardBetaBanner]

    "Return the correct PhaseBanner object for the given phase and url" in {
      val phaseBanner = standardBetaBanner(url = "/feedback")

      phaseBanner mustBe PhaseBanner(
        tag = Some(Tag(content = Text("beta"))),
        content = HtmlContent("This is a new service – your <a class=\"govuk-link\" href=\"/feedback\">feedback</a> will help us to improve it.")
      )
    }

    "Return the correct PhaseBanner object for a different phase and url" in {
      val phaseBanner = standardBetaBanner(url = "/other-feedback")

      phaseBanner mustBe PhaseBanner(
        tag = Some(Tag(content = Text("beta"))),
        content = HtmlContent("This is a new service – your <a class=\"govuk-link\" href=\"/other-feedback\">feedback</a> will help us to improve it.")
      )
    }

    "properly escape the url" in {
      val phaseBanner = standardBetaBanner(url = "\"><script>console.log('evil');</script><a href=\"")

      phaseBanner mustBe PhaseBanner(
        tag = Some(Tag(content = Text("beta"))),
        content = HtmlContent("This is a new service – your <a class=\"govuk-link\" href=\"&quot;&gt;&lt;script&gt;console.log(&#x27;evil&#x27;);&lt;/script&gt;&lt;a href=&quot;\">feedback</a> will help us to improve it.")
      )
    }

    "Return the correct Welsh content" in {
      implicit val messages: Messages = messagesApi.preferred(Seq(Lang("cy")))

      val phaseBanner = standardBetaBanner(url = "/feedback")

      phaseBanner mustBe PhaseBanner(
        tag = Some(Tag(content = Text("beta"))),
        content = HtmlContent("Gwasanaeth newydd yw hwn – bydd eich <a class=\"govuk-link\" href=\"/feedback\">adborth</a> yn ein helpu i’w wella.")
      )
    }
  }

  "StandardAlphaBanner" must {
    val standardAlphaBanner = app.injector.instanceOf[StandardAlphaBanner]

    "Return the correct PhaseBanner object for the given url" in {
      val phaseBanner = standardAlphaBanner(url = "/feedback")

      phaseBanner mustBe PhaseBanner(
        tag = Some(Tag(content = Text("alpha"))),
        content = HtmlContent("This is a new service – your <a class=\"govuk-link\" href=\"/feedback\">feedback</a> will help us to improve it.")
      )
    }
  }
}
