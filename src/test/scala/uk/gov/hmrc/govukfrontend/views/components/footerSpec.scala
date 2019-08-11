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
import uk.gov.hmrc.govukfrontend.views.components.footerSpec.reads
import uk.gov.hmrc.govukfrontend.views.html.components._

class footerSpec
    extends RenderHtmlSpec(
      Seq(
        "footer-default",
        "footer-GOV.UK",
        "footer-with-custom-meta",
        "footer-with-custom-meta2",
        "footer-with-meta",
        "footer-with-meta-links-and-meta-content",
        "footer-with-navigation"
      )
    ) {

  "footer" should {
    "have a role of contentinfo" in {
      val footerHtml = Footer(FooterParams())
      val component  = Jsoup.parse(footerHtml.body).select(".govuk-footer")
      component.attr("role") shouldBe "contentinfo"
    }
  }
}

object footerSpec {
  import RenderHtmlSpec._

  implicit val reads: Reads[HtmlString] =
    Json
      .using[Json.WithDefaultValues]
      .reads[FooterParams]
      .map { params =>
        tagger[HtmlStringTag][String](
          Footer
            .apply(params)
            .body)
      }
}
