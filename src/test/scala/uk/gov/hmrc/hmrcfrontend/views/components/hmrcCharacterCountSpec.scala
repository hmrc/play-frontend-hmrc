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

package uk.gov.hmrc.hmrcfrontend.views.components

import play.api.i18n.{Lang, Messages}
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.hmrcfrontend.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.{JsoupHelpers, TemplateUnitSpec}
import uk.gov.hmrc.hmrcfrontend.views.html.components.HmrcCharacterCount
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.charactercount.CharacterCount
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{Cy, En}

import scala.util.Try

class hmrcCharacterCountSpec
    extends TemplateUnitSpec[CharacterCount, HmrcCharacterCount]("hmrcCharacterCount")
    with MessagesSupport
    with JsoupHelpers {

  /**
    * Calls the Twirl template with the given parameters and returns the resulting markup
    *
    * @param templateParams
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  override def render(templateParams: CharacterCount): Try[HtmlFormat.Appendable] =
    Try(component(templateParams))

  "hmrcCharacterCount" should {
    "set data-language from the Messages when specified as cy" in {
      val welshMessages: Messages = messagesApi.preferred(Seq(Lang("cy")))
      val characterCount          = CharacterCount(id = "some-id", name = "some-name", maxWords = Some(25))

      val content           = component(characterCount)(welshMessages)
      val characterCountDiv = content.select(".hmrc-character-count")

      characterCountDiv.size()                shouldBe 1
      characterCountDiv.attr("data-language") shouldBe "cy"
    }

    "set data-language from the Messages when cy in Messages and en in viewmodel" in {
      val welshMessages: Messages = messagesApi.preferred(Seq(Lang("cy")))
      val characterCount          = CharacterCount(id = "some-id", name = "some-name", maxWords = Some(25), language = En)

      val content           = component(characterCount)(welshMessages)
      val characterCountDiv = content.select(".hmrc-character-count")

      characterCountDiv.size()                shouldBe 1
      characterCountDiv.attr("data-language") shouldBe "cy"
    }

    "set data-language from the viewmodel when cy in viewmodel and en in Messages" in {
      val welshMessages: Messages = messagesApi.preferred(Seq(Lang("en")))
      val characterCount          = CharacterCount(id = "some-id", name = "some-name", maxWords = Some(25), language = Cy)

      val content           = component(characterCount)(welshMessages)
      val characterCountDiv = content.select(".hmrc-character-count")

      characterCountDiv.size()                shouldBe 1
      characterCountDiv.attr("data-language") shouldBe "cy"
    }

    "set data-language from the viewmodel when none specified in messages" in {
      val welshMessages: Messages = messagesApi.preferred(Seq())
      val characterCount          = CharacterCount(id = "some-id", name = "some-name", maxWords = Some(25), language = En)

      val content           = component(characterCount)(welshMessages)
      val characterCountDiv = content.select(".hmrc-character-count")

      characterCountDiv.size()                shouldBe 1
      characterCountDiv.attr("data-language") shouldBe "en"
    }
  }
}
