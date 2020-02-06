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

package uk.gov.hmrc.govukfrontend.views.viewmodels.table

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonDefaultValueFormatter

case class Table(
  rows: Seq[Seq[TableRow]]        = Nil,
  head: Option[Seq[HeadCell]]     = None,
  caption: Option[String]         = None,
  captionClasses: String          = "",
  firstCellIsHeader: Boolean      = false,
  classes: String                 = "",
  attributes: Map[String, String] = Map.empty
)

object Table extends JsonDefaultValueFormatter[Table] {

  override def defaultObject: Table = Table()

  override def defaultReads: Reads[Table] =
    (
      (__ \ "rows").read[Seq[Seq[TableRow]]] and
        (__ \ "head").readNullable[Seq[HeadCell]] and
        (__ \ "caption").readNullable[String] and
        (__ \ "captionClasses").read[String] and
        (__ \ "firstCellIsHeader").read[Boolean] and
        (__ \ "classes").read[String] and
        (__ \ "attributes").read[Map[String, String]]
    )(Table.apply _)

  override implicit def jsonWrites: OWrites[Table] =
    (
      (__ \ "rows").write[Seq[Seq[TableRow]]] and
        (__ \ "head").writeNullable[Seq[HeadCell]] and
        (__ \ "caption").writeNullable[String] and
        (__ \ "captionClasses").write[String] and
        (__ \ "firstCellIsHeader").write[Boolean] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(unlift(Table.unapply))

}
