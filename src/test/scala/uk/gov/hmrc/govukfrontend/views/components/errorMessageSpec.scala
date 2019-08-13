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

package uk.gov.hmrc.govukfrontend.views.components

import org.jsoup.Jsoup
import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.html.components._

class errorMessageSpec extends RenderHtmlSpec(Seq("error-message-default")) {
  "errorMessage" should {
    "allow additional classes to be specified" in {
      val errorMessageHtml = ErrorMessage.apply(classes = "custom-class")(Empty).body
      val component        = Jsoup.parse(errorMessageHtml).select(".govuk-error-message")
      assert(component.hasClass("custom-class"))
    }

    "allow text to be passed whilst escaping HTML entities" in {
      val errorMessageHtml = ErrorMessage.apply()(Text("Unexpected > in body")).body
      val contents         = Jsoup.parse(errorMessageHtml).select(".govuk-error-message").html.trim
      contents should include("Unexpected &gt; in body")
    }

    "allow summary HTML to be passed unescaped" in {
      val errorMessageHtml = ErrorMessage.apply()(HtmlContent("Unexpected <b>bold text</b> in body copy")).body
      val contents         = Jsoup.parse(errorMessageHtml).select(".govuk-error-message").html.trim
      contents should include("Unexpected <b>bold text</b> in body copy")
    }

    "allow additional attributes to be specified" in {
      val errorMessageHtml =
        ErrorMessage.apply(attributes = Map("data-test" -> "attribute", "id" -> "my-error-message"))(Empty).body
      val component = Jsoup.parse(errorMessageHtml).select(".govuk-error-message")
      component.attr("data-test") shouldBe "attribute"
      component.attr("id")        shouldBe "my-error-message"
    }

    "include a visually hidden 'Error' prefix by default" in {
      val errorMessageHtml = ErrorMessage.apply()(Text("Enter your full name")).body
      val component        = Jsoup.parse(errorMessageHtml).select(".govuk-error-message")
      component.text.trim shouldBe "Error: Enter your full name"
    }

    "allow the visually hidden prefix to be customised" in {
      val errorMessageHtml = ErrorMessage.apply(visuallyHiddenText = "Gwall")(Text("Rhowch eich enw llawn")).body
      val component        = Jsoup.parse(errorMessageHtml).select(".govuk-error-message")
      component.text.trim shouldBe "Gwall: Rhowch eich enw llawn"
    }

    "allow the visually hidden prefix to be removed" in {
      val errorMessageHtml = ErrorMessage.apply(showHiddenText = false)(Text("There is an error on line 42")).body
      val component        = Jsoup.parse(errorMessageHtml).select(".govuk-error-message")
      component.text.trim shouldBe "There is an error on line 42"
    }
  }

  override implicit val reads: Reads[HtmlString] = (
    readsContents and
      (__ \ "id").readNullable[String] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      (__ \ "visuallyHiddenText").readWithDefault[String]("Error")
  )((contents, id, classes, attributes, visuallyHiddenText) =>
    HtmlString(ErrorMessage.apply(id, classes, attributes, visuallyHiddenText)(contents)))
}
