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
import play.api.i18n.{DefaultLangs, Lang, Messages, MessagesApi}
import play.api.test.Helpers.stubMessagesApi
import uk.gov.hmrc.govukfrontend.views.Aliases.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.HtmlContent

class HmrcSectionCaptionSpec extends AnyWordSpec with Matchers {

  lazy val messagesApi: MessagesApi =
    stubMessagesApi(
      Map(
        "en" -> Map("this.section.is" -> "This section is"),
        "cy" -> Map("this.section.is" -> "This section is (welsh)")
      ),
      new DefaultLangs(Seq(Lang("en"), Lang("cy")))
    )

  implicit val englishMessages: Messages = messagesApi.preferred(Seq(Lang("en")))
  val welshMessages            = messagesApi.preferred(Seq(Lang("cy")))

  "HmrcSectionCaption" must {
    "construct caption with a visually hidden prefix" in {
      HmrcSectionCaption(Text("Personal details")) mustBe HtmlContent(
        """<span class="govuk-visually-hidden">This section is </span>Personal details"""
      )
    }

    "require section to be non empty" in {
      try {
        HmrcSectionCaption(Text(""))
        fail("construction succeeded with empty text")
      } catch {
        case e: IllegalArgumentException => // pass
      }
    }

    "allow default content to be translated via messages" in {
      HmrcSectionCaption(Text("Personal details"))(welshMessages) mustBe HtmlContent(
        """<span class="govuk-visually-hidden">This section is (welsh) </span>Personal details"""
      )
    }
  }
}
