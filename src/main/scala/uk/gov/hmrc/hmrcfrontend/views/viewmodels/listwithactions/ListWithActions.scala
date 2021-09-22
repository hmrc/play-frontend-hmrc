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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.listwithactions

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._

case class ListWithActions(
  items: Seq[ListWithActionsItem] = Nil,
  classes: String = "",
  attributes: Map[String, String] = Map.empty
)

object ListWithActions {

  def defaultObject: ListWithActions = ListWithActions()

  implicit def jsonReads: Reads[ListWithActions] = (
    (__ \ "items").readWithDefault[Seq[ListWithActionsItem]](defaultObject.items)(
      forgivingSeqReads[ListWithActionsItem]
    ) and
      (__ \ "classes").readWithDefault[String](defaultObject.classes) and
      (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads)
  )(ListWithActions.apply _)

  implicit def jsonWrites: OWrites[ListWithActions] = Json.writes[ListWithActions]
}
