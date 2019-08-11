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
import play.api.libs.json.{Json, Reads}
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.components.skipLinkSpec.reads
import uk.gov.hmrc.govukfrontend.views.html.components._

class skipLinkSpec
    extends RenderHtmlSpec(
      Seq(
        "skip-link-default",
        "skip-link-with-focus"
      )
    ) {

  "skipLink" should {
    "render href" in {
      val skipLinkHtml = SkipLink.apply(href = "#custom")()

      val component = Jsoup.parse(skipLinkHtml.body).select(".govuk-skip-link")

      val attribute = component.first().attr("href")

      attribute shouldBe "#custom"
    }
  }
}

object skipLinkSpec {
  case class Params(
    text: Option[String] = None,
    html: Option[Html]   = None,
    href: String,
    classes: Option[String]         = None,
    attributes: Map[String, String] = Map.empty
  )

  import RenderHtmlSpec._

  implicit val reads: Reads[HtmlString] =
    Json
      .using[Json.WithDefaultValues]
      .reads[Params]
      .map {
        case Params(text, html, href, classes, attributes) =>
          tagger[HtmlStringTag][String](
            SkipLink
              .apply(text, href, classes, attributes)(html)
              .body)
      }
}
