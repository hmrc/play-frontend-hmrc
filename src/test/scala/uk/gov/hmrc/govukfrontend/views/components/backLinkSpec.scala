/*
 * Copyright 2019 HM Revenue & Customs
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

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.html.components._

class backLinkSpec extends RenderHtmlSpec("govukBackLink") {

  "backLink" should {

    "render the default example with an anchor, href and text correctly" in {
      val component = BackLink.apply(href = "#")(Empty).select(".govuk-back-link")

      component.first.tagName shouldBe "a"
      component.attr("href")  shouldBe "#"
      component.text          shouldBe "Back"
    }

    "render classes correctly" in {
      val component = BackLink
        .apply(href = "#", classes = "app-back-link--custom-class")(HtmlContent("<b>Back</b>"))
        .select(".govuk-back-link")

      assert(component.hasClass("app-back-link--custom-class"))
    }

    "render custom text correctly" in {
      val component = BackLink.apply(href = "#")(Text("Home")).select(".govuk-back-link")

      component.html shouldBe "Home"
    }

    "render escaped html when passed as text" in {
      val component = BackLink.apply(href = "#")(Text("<b>Home</b>")).select(".govuk-back-link")

      component.html shouldBe "&lt;b&gt;Home&lt;/b&gt;"
    }

    "render html correctly" in {
      val component = BackLink.apply(href = "#")(HtmlContent("<b>Back</b>")).select(".govuk-back-link")

      component.html shouldBe "<b>Back</b>"
    }

    "render default text correctly" in {
      val component = BackLink.apply(href = "#")(Empty).select(".govuk-back-link")

      component.html shouldBe "Back"
    }

    "render attributes correctly" in {
      val component =
        BackLink
          .apply(href = "#", attributes = Map("data-test" -> "attribute", "aria-label" -> "Back to home"))(
            HtmlContent("Back"))
          .select(".govuk-back-link")

      component.attr("data-test")  shouldBe "attribute"
      component.attr("aria-label") shouldBe "Back to home"
    }
  }

  override implicit val reads: Reads[Html] = (
    (__ \ "href").read[String] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      readsContent
  )(BackLink.apply(_, _, _)(_))
}


