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

package uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist

import play.api.libs.json._
import play.api.libs.functional.syntax._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._

case class SummaryList(
  rows: Seq[SummaryListRow] = Nil,
  classes: String = "",
  attributes: Map[String, String] = Map.empty
)

object SummaryList {

  def defaultObject: SummaryList = SummaryList()

  implicit def jsonReads: Reads[SummaryList] = (
    (__ \ "rows").readWithDefault[Seq[SummaryListRow]](defaultObject.rows)(forgivingSeqReads[SummaryListRow]) and
      (__ \ "classes").readWithDefault[String](defaultObject.classes) and
      (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads)
  )(SummaryList.apply _)

  implicit def jsonWrites: OWrites[SummaryList] = Json.writes[SummaryList]
}
