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

package uk.gov.hmrc.govukfrontend.views.components

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.TemplateUnitSpec
import uk.gov.hmrc.govukfrontend.views.html.components._
import scala.util.Try

class govukErrorMessageSpec extends TemplateUnitSpec[ErrorMessage, GovukErrorMessage]("govukErrorMessage") {
  "errorMessage" should {
    "allow additional classes to be specified" in {
      val output = component(ErrorMessage(classes = "custom-class", content = Empty))
        .select(".govuk-error-message")
      assert(output.hasClass("custom-class"))
    }

    "allow text to be passed whilst escaping HTML entities" in {
      val content = component(ErrorMessage(content = Text("Unexpected > in body")))
        .select(".govuk-error-message")
        .html
        .trim
      content should include("Unexpected &gt; in body")
    }

    "allow summary HTML to be passed unescaped" in {
      val content = component(ErrorMessage(content = HtmlContent("Unexpected <b>bold text</b> in body copy")))
        .select(".govuk-error-message")
        .html
        .trim
      content should include("Unexpected <b>bold text</b> in body copy")
    }

    "allow additional attributes to be specified" in {
      val output = component(
        ErrorMessage(attributes = Map("data-test" -> "attribute", "id" -> "my-error-message"), content = Empty)
      )
        .select(".govuk-error-message")
      output.attr("data-test") shouldBe "attribute"
      output.attr("id") shouldBe "my-error-message"
    }

    "include a visually hidden 'Error' prefix by default" in {
      val output =
        component(ErrorMessage(content = Text("Enter your full name"))).select(".govuk-error-message")
      output.text.trim shouldBe "Error: Enter your full name"
    }

    "allow the visually hidden prefix to be customised" in {
      val output =
        component(ErrorMessage(visuallyHiddenText = Some("Gwall"), content = Text("Rhowch eich enw llawn")))
          .select(".govuk-error-message")
      output.text.trim shouldBe "Gwall: Rhowch eich enw llawn"
    }

    "allow the visually hidden prefix to be removed" in {
      val output =
        component(ErrorMessage(visuallyHiddenText = None, content = Text("There is an error on line 42")))
          .select(".govuk-error-message")
      output.text.trim shouldBe "There is an error on line 42"
    }
  }

  /**
    * Calls the Twirl template with the given parameters and converts the resulting markup into a [[String]]
    *
    * @param templateParams
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  override def render(templateParams: ErrorMessage): Try[HtmlFormat.Appendable] =
    Try(component(templateParams))
}
