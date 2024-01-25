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
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.helpers.MessagesSupport

import scala.util.Try

class GovukWarningTextSpec extends TemplateUnitBaseSpec[WarningText]("govukWarningText") with MessagesSupport {

  private val component = app.injector.instanceOf[GovukWarningText]

  override def render(
    templateParams: WarningText
  ): Try[HtmlFormat.Appendable] = Try(component(templateParams))

  "GovukWarningText" when {
    "implicit messages language is welsh" should {
      val welshMessages: Messages = messagesApi.preferred(Seq(Lang("cy")))

      "display welsh translation of Warning" in {
        val content = component(WarningText())(welshMessages)

        val assistiveDescription = content.select(".govuk-visually-hidden")
        assistiveDescription.text() shouldBe "Rhybudd"
      }

      "still allow you to override the assistive description test for the icon" in {
        val content = component(WarningText(iconFallbackText = Some("asdfghjkl")))(welshMessages)

        val assistiveDescription = content.select(".govuk-visually-hidden")
        assistiveDescription.text() shouldBe "asdfghjkl"
      }

    }
  }
}
