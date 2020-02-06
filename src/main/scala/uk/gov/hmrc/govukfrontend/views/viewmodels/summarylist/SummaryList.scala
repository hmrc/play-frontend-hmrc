/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist

import play.api.libs.json.{Json, OWrites, Reads}
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonDefaultValueFormatter

case class SummaryList(
  rows: Seq[SummaryListRow]       = Nil,
  classes: String                 = "",
  attributes: Map[String, String] = Map.empty
)

object SummaryList extends JsonDefaultValueFormatter[SummaryList] {

  override def defaultObject: SummaryList = SummaryList()

  override def defaultReads: Reads[SummaryList] = Json.reads[SummaryList]

  override implicit def jsonWrites: OWrites[SummaryList] = Json.writes[SummaryList]
}
