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

import org.jsoup.nodes.Document
import org.jsoup.{Jsoup, nodes}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.html.components._

class detailsSpec
    extends RenderHtmlSpec(
      Seq(
        "details-default",
        "details-expanded",
        "details-with-html"
      )
    ) {

  "details" should {
    "allow text to be passed whilst escaping HTML entities" in {
      val rendered = Details.apply()(Empty)(Text("More about the greater than symbol (>)")).body
      val details  = Jsoup.parse(rendered).select(".govuk-details__text").html.trim
      details shouldBe "More about the greater than symbol (&gt;)"
    }

    "allow HTML to be passed un-escaped" ignore { // TODO fix failing test
      val rendered = Details.apply()(Empty)(HtmlContent("More about <b>bold text</b>")).body
      val details = Jsoup.parse(rendered).select(".govuk-details__text").html.trim
      details shouldBe "More about <b>bold text</b>"
    }
  }

  override implicit val reads: Reads[HtmlString] = (
    readsHtmlOrText("summaryHtml", "summaryText") and
      readsContents and
      (__ \ "id").readNullable[String] and
      (__ \ "open").readWithDefault[Boolean](false) and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )((summary, contents, id, open, classes, attributes) =>
    HtmlString(Details.apply(id, open, classes, attributes)(summary)(contents)))
}
