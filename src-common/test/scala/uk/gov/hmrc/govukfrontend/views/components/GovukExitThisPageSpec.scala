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

package uk.gov.hmrc.govukfrontend.views
package components

import play.api.i18n.{Lang, Messages}
import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.helpers.MessagesSupport

import scala.util.Try

class GovukExitThisPageSpec
    extends TemplateUnitBaseSpec[ExitThisPage](
      govukComponentName = "govukExitThisPage",
      fullyCompressedExamples = Seq(
        "exit-this-page-default",
        "exit-this-page-testing",
        "exit-this-page-testing-html",
        "exit-this-page-translated"
      )
    )
    with MessagesSupport {

  private val component = app.injector.instanceOf[GovukExitThisPage]

  def render(templateParams: ExitThisPage): Try[HtmlFormat.Appendable] = {
    implicit val request: RequestHeader = FakeRequest("GET", "/foo")

    Try(component(templateParams))
  }

  "GovukExitThisPage" when {
    "implicit messages language is welsh" should {
      val welshMessages: Messages = messagesApi.preferred(Seq(Lang("cy")))

      "display welsh translation of Exit this page" in {
        val notificationBanner = ExitThisPage()

        val content = component(notificationBanner)(welshMessages)

        val button = content.select(".govuk-exit-this-page__button")
        button.text() shouldBe "Gadael y dudalen hon"
      }
    }
  }
}
