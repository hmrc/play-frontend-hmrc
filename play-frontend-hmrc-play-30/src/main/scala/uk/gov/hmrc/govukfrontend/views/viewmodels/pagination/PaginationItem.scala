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
import play.api.libs.json.{OWrites, Reads, __}
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats.{attributesReads, forgivingOptStringReads}
import play.api.libs.functional.syntax._

case class PaginationItem(
  href: String = "",
  number: Option[String] = None,
  visuallyHiddenText: Option[String] = None,
  current: Option[Boolean] = None,
  ellipsis: Option[Boolean] = None,
  attributes: Map[String, String] = Map.empty
)

object PaginationItem {

  def defaultObject = PaginationItem()

  implicit def jsonReads: Reads[PaginationItem] =
    (
      (__ \ "href").readWithDefault[String](defaultObject.href) and
        (__ \ "number").readWithDefault[Option[String]](defaultObject.number)(forgivingOptStringReads) and
        (__ \ "visuallyHiddenText").readNullable[String] and
        (__ \ "current").readNullable[Boolean] and
        (__ \ "ellipsis").readNullable[Boolean] and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads)
    )(PaginationItem.apply _)

  implicit def jsonWrites: OWrites[PaginationItem] =
    (
      (__ \ "href").write[String] and
        (__ \ "number").writeNullable[String] and
        (__ \ "visuallyHiddenText").writeNullable[String] and
        (__ \ "current").writeNullable[Boolean] and
        (__ \ "ellipsis").writeNullable[Boolean] and
        (__ \ "attributes").write[Map[String, String]]
    )(o => WritesUtils.unapplyCompat(unapply)(o))
}
