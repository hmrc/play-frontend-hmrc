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

package uk.gov.hmrc.hmrcfrontend.views
package components

import play.api.i18n.{Lang, Messages}
import play.api.mvc.{AnyContentAsEmpty, RequestHeader}
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.hmrcfrontend.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.html.components._

import scala.util.Try

class hmrcAddToAList extends TemplateUnitSpec[AddToAList]("hmrcAddToAList") with MessagesSupport {

  /**
    * Calls the Twirl template with the given parameters and returns the resulting markup
    *
    * @param templateParams
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  override def render(templateParams: AddToAList): Try[HtmlFormat.Appendable] = {
    implicit val request: RequestHeader = FakeRequest("GET", "/foo")
    Try(HmrcAddToAList(templateParams))
  }

  "hmrcAddToAList" should {
    "display welsh translations if viewmodel language is welsh" in {
      val englishMessages: Messages = messagesApi.preferred(Seq(Lang("en")))
      val addToAList                = AddToAList(language = Cy)

      val content = HmrcAddToAList(addToAList)(englishMessages, FakeRequest())
      val heading = content.select(".govuk-heading-xl")

      heading.text() shouldBe "Nid ydych wedi ychwanegu unrhyw eitemau"
    }

    "display welsh translations if implicit messages language is welsh" in {
      val welshMessages: Messages = messagesApi.preferred(Seq(Lang("cy")))
      val addToAList              = AddToAList(language = En)

      val content = HmrcAddToAList(addToAList)(welshMessages, FakeRequest())
      val heading = content.select(".govuk-heading-xl")

      heading.text() shouldBe "Nid ydych wedi ychwanegu unrhyw eitemau"
    }

    "display english translations if viewmodel language and implicit messages language are english" in {
      val englishMessages: Messages = messagesApi.preferred(Seq(Lang("en")))
      val addToAList                = AddToAList(language = En)

      val content = HmrcAddToAList(addToAList)(englishMessages, FakeRequest())
      val heading = content.select(".govuk-heading-xl")

      heading.text() shouldBe "You have not added any items"
    }
  }
}
