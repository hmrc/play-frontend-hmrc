/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels.tabs

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._

case class Tabs(
  id: Option[String] = None,
  idPrefix: Option[String] = None,
  title: String = "",
  items: Seq[TabItem] = Seq.empty,
  classes: String = "",
  attributes: Map[String, String] = Map.empty
)

object Tabs {

  def defaultObject: Tabs = Tabs()

  implicit def jsonReads: Reads[Tabs] = (
    (__ \ "id").readNullable[String] and
      (__ \ "idPrefix").readNullable[String] and
      (__ \ "title").readWithDefault[String](defaultObject.title) and
      (__ \ "items").readWithDefault[Seq[TabItem]](defaultObject.items)(forgivingSeqReads[TabItem]) and
      (__ \ "classes").readWithDefault[String](defaultObject.classes) and
      (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads)
  )(Tabs.apply _)

  implicit def jsonWrites: OWrites[Tabs] = Json.writes[Tabs]
}
