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

class govukDetailsSpec extends TemplateUnitSpec[Details, GovukDetails]("govukDetails") {

  "details" should {
    "allow text to be passed whilst escaping HTML entities" in {
      val details = component(Details(summary = Empty, content = Text("More about the greater than symbol (>)")))
        .select(".govuk-details__text")
        .html
        .trim

      details shouldBe "More about the greater than symbol (&gt;)"
    }

    "allow HTML to be passed un-escaped" in {
      val details = component(Details(summary = Empty, content = HtmlContent("More about <b>bold text</b>")))
        .select(".govuk-details__text")
        .html
        .trim

      details shouldBe "More about <b>bold text</b>"
    }

    "allow summary text to be passed whilst escaping HTML entities" in {
      val details = component(Details(summary = Text("The greater than symbol (>) is the best"), content = Empty))
        .select(".govuk-details__summary-text")
        .html
        .trim

      details shouldBe "The greater than symbol (&gt;) is the best"
    }

    "allow summary HTML to be passed un-escaped" in {
      val details =
        component(Details(summary = HtmlContent("Use <b>bold text</b> sparingly"), content = Empty))
          .select(".govuk-details__summary-text")
          .html
          .trim

      details shouldBe "Use <b>bold text</b> sparingly"
    }

    "allow additional classes to be added to the details element" in {
      val details =
        component(Details(classes = "some-additional-class", summary = Empty, content = Empty))
          .select(".govuk-details")

      assert(details.hasClass("some-additional-class"))
    }

    "allow additional attributes to be added to the details element" in {
      val details =
        component(
          Details(
            attributes = Map("data-some-data-attribute" -> "i-love-data", "another-attribute" -> "true"),
            summary = Empty,
            content = Empty
          )
        )
          .select(".govuk-details")

      details.attr("data-some-data-attribute") shouldBe "i-love-data"
      details.attr("another-attribute")        shouldBe "true"
    }
  }

  /**
    * Calls the Twirl template with the given parameters and returns the resulting markup
    *
    * @param params
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  override def render(params: Details): Try[HtmlFormat.Appendable] =
    Try(component(params))
}
