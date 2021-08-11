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

package uk.gov.hmrc.govukfrontend.views
package components

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.html.components._
import scala.util.Try

class govukBackLinkSpec extends TemplateUnitSpec[BackLink, GovukBackLink]("govukBackLink") {

  "backLink" should {

    "render the default example with an anchor, href and text correctly" in {
      val params = BackLink(href = "#", content = Empty)
      val output = component(params).select(".govuk-back-link")

      output.first.tagName shouldBe "a"
      output.attr("href")  shouldBe "#"
      output.text          shouldBe "Back"
    }

    "render classes correctly" in {
      val params =
        BackLink(href = "#", classes = "app-back-link--custom-class", content = HtmlContent("<b>Back</b>"))
      val output = component(params).select(".govuk-back-link")

      assert(output.hasClass("app-back-link--custom-class"))
    }

    "render custom text correctly" in {
      val params = BackLink(href = "#", content = Text("Home"))
      val output = component(params).select(".govuk-back-link")

      output.html shouldBe "Home"
    }

    "render escaped html when passed as text" in {
      val params = BackLink(href = "#", content = Text("<b>Home</b>"))
      val output = component(params).select(".govuk-back-link")

      output.html shouldBe "&lt;b&gt;Home&lt;/b&gt;"
    }

    "render html correctly" in {
      val params = BackLink(href = "#", content = HtmlContent("<b>Back</b>"))
      val output = component(params).select(".govuk-back-link")

      output.html shouldBe "<b>Back</b>"
    }

    "render default text correctly" in {
      val params = BackLink(href = "#", content = Empty)
      val output = component(params).select(".govuk-back-link")

      output.html shouldBe "Back"
    }

    "render attributes correctly" in {
      val params = BackLink(
        href = "#",
        attributes = Map("data-test" -> "attribute", "aria-label" -> "Back to home"),
        content = HtmlContent("Back")
      )
      val output = component(params).select(".govuk-back-link")

      output.attr("data-test")  shouldBe "attribute"
      output.attr("aria-label") shouldBe "Back to home"
    }
  }

  /**
    * Calls the Twirl template with the given parameters and converts the resulting markup into a [[String]]
    *
    * @param templateParams
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  override def render(templateParams: BackLink): Try[HtmlFormat.Appendable] =
    Try(component(templateParams))

}
