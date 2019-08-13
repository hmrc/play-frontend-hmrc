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

class detailsSpec
    extends RenderHtmlSpec(
      Seq(
        "details-default",
        "details-expanded",
        "details-with-html"
      )
    ) {
  override implicit val reads: Reads[HtmlString] = (
    readsHtmlOrText("summaryHtml", "summaryText") and
      readsContents and
      (__ \ "id").readNullable[String] and
      (__ \ "open").readWithDefault[Boolean](false) and
      readsClasses and
      readsAttributes
  )(
    (summary, contents, id, open, classes, attributes) =>
      tagger[HtmlStringTag][String](
        Details
          .apply(id, open, classes, attributes)(summary)(contents)
          .body))
}
