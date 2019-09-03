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

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.html.components._

class labelSpec
    extends RenderHtmlSpec(
      Seq(
        "label-default",
        "label-as-page-heading",
        "label-with-bold-text"
      )
    ) {
  "label" should {
    "not output anything if no html or text is provided" in {
      val component = Label.apply()(Empty).select(".govuk-label")

      component.size() shouldBe 0
    }
  }

  override implicit val reads: Reads[HtmlString] = (
    (__ \ "forAttr").readNullable[String] and
      (__ \ "isPageHeading").readWithDefault[Boolean](false) and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      readsContents
  )(Label.apply(_, _, _, _)(_)).map(HtmlString(_))
}
