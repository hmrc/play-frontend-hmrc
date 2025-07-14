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
import uk.gov.hmrc.govukfrontend.views.viewmodels.WritesUtils
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}
import uk.gov.hmrc.supportfrontend.views.IntString

final case class TableCell(
  content: Content = Empty,
  format: Option[String] = None,
  classes: String = "",
  colspan: Option[Int] = None,
  rowspan: Option[Int] = None,
  attributes: Map[String, String] = Map.empty
)

object TableCell {

  def defaultObject: TableCell = TableCell()

  import scala.language.implicitConversions

  implicit def tableRowToTableCell(row: TableRow): TableCell =
    TableCell(
      content = row.content,
      format = row.format,
      classes = row.classes,
      colspan = row.colspan,
      rowspan = row.rowspan,
      attributes = row.attributes
    )

  implicit def jsonReads: Reads[TableCell] =
    (
      Content.reads and
        (__ \ "format").readNullable[String] and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "colspan").readNullable[IntString].int and
        (__ \ "rowspan").readNullable[IntString].int and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)
    )(TableCell.apply _)

  implicit def jsonWrites: OWrites[TableCell] =
    (
      Content.writes and
        (__ \ "format").writeNullable[String] and
        (__ \ "classes").write[String] and
        (__ \ "colspan").writeNullable[Int] and
        (__ \ "rowspan").writeNullable[Int] and
        (__ \ "attributes").write[Map[String, String]]
    )(o => WritesUtils.unapplyCompat(unapply)(o))

}
