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

package uk.gov.hmrc.govukfrontend.views.viewmodels.table

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._

case class Table(
  rows: Seq[Seq[TableRow]] = Nil,
  head: Option[Seq[HeadCell]] = None,
  caption: Option[String] = None,
  captionClasses: String = "",
  firstCellIsHeader: Boolean = false,
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  headAttributes: Map[String, String] = Map.empty,
  headRowAttributes: Map[String, String] = Map.empty
)

object Table {

  def defaultObject: Table = Table()

  implicit def jsonReads: Reads[Table] = {
    def readsRows: Reads[Seq[Seq[TableRow]]] =
      forgivingSeqReads(forgivingSeqReads[TableRow])

    (
      (__ \ "rows").readWithDefault[Seq[Seq[TableRow]]](defaultObject.rows)(readsRows) and
        (__ \ "head").readNullable[Seq[HeadCell]] and
        (__ \ "caption").readNullable[String] and
        (__ \ "captionClasses").readWithDefault[String](defaultObject.captionClasses) and
        (__ \ "firstCellIsHeader").readWithDefault[Boolean](defaultObject.firstCellIsHeader) and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads) and
        (__ \ "headAttributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads) and
        (__ \ "headRowAttributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads)
    )(Table.apply _)
  }

  implicit def jsonWrites: OWrites[Table] =
    (
      (__ \ "rows").write[Seq[Seq[TableRow]]] and
        (__ \ "head").writeNullable[Seq[HeadCell]] and
        (__ \ "caption").writeNullable[String] and
        (__ \ "captionClasses").write[String] and
        (__ \ "firstCellIsHeader").write[Boolean] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "headAttributes").write[Map[String, String]] and
        (__ \ "headRowAttributes").write[Map[String, String]]
    )(unlift(Table.unapply))

}
