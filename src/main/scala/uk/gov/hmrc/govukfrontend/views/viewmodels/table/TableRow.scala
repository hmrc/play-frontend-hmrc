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

package uk.gov.hmrc.govukfrontend.views.viewmodels
package table

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.supportfrontend.views.IntString
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

final case class TableRow(
  content: Content = Empty,
  format: Option[String] = None,
  classes: String = "",
  colspan: Option[Int] = None,
  rowspan: Option[Int] = None,
  attributes: Map[String, String] = Map.empty
)

object TableRow {

  def defaultObject: TableRow = TableRow()

  implicit def jsonReads: Reads[TableRow] =
    (
      Content.reads and
        (__ \ "format").readNullable[String] and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "colspan").readNullable[IntString].int and
        (__ \ "rowspan").readNullable[IntString].int and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)
    )(TableRow.apply _)

  implicit def jsonWrites: OWrites[TableRow] =
    (
      Content.writes and
        (__ \ "format").writeNullable[String] and
        (__ \ "classes").write[String] and
        (__ \ "colspan").writeNullable[Int] and
        (__ \ "rowspan").writeNullable[Int] and
        (__ \ "attributes").write[Map[String, String]]
    )(unlift(TableRow.unapply))

}
