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

package uk.gov.hmrc.govukfrontend.views.viewmodels
package pagination

import play.api.libs.functional.syntax.unlift
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsObject, Json, OWrites, Reads, __}
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats.attributesReads

case class Pagination(
  items: Option[Seq[PaginationItem]] = None,
  previous: Option[PaginationLink] = None,
  next: Option[PaginationLink] = None,
  landmarkLabel: Option[String] = None,
  classes: String = "",
  attributes: Map[String, String] = Map.empty
)

object Pagination {

  def defaultObject = Pagination()

  implicit def jsonReads: Reads[Pagination] =
    (
      (__ \ "items").readNullable[Seq[PaginationItem]] and
        (__ \ "previous").readNullable[PaginationLink] and
        (__ \ "next").readNullable[PaginationLink] and
        (__ \ "landmarkLabel").readNullable[String] and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads)
    )(Pagination.apply _)

  implicit def jsonWrites: OWrites[Pagination] =
    (
      (__ \ "items").writeNullable[Seq[PaginationItem]] and
        (__ \ "previous").writeNullable[PaginationLink] and
        (__ \ "next").writeNullable[PaginationLink] and
        (__ \ "landmarkLabel").writeNullable[String] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(o => WritesUtils.unapplyCompat(unapply)(o))
}
