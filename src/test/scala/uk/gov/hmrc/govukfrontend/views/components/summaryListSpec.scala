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
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.html.components._

class summaryListSpec
    extends RenderHtmlSpec(
      Seq(
        "summary-list-default",
        "summary-list-check-your-answers",
        "summary-list-extreme",
        "summary-list-no-border",
        "summary-list-no-border-on-last-row",
        "summary-list-overridden-widths",
        "summary-list-with-some-actions",
        "summary-list-without-actions"
      )
    ) {

  override implicit val reads: Reads[Html] = (
    (__ \ "rows").read[Seq[Row]] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(SummaryList.apply _)
}
