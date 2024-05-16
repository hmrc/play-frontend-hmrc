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
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats.attributesReads
import play.api.libs.functional.syntax._

case class PaginationLink(
  href: String = "",
  text: Option[String] = None,
  labelText: Option[String] = None,
  attributes: Map[String, String] = Map.empty
)

object PaginationLink {

  def defaultObject = PaginationLink()

  implicit def jsonReads: Reads[PaginationLink] =
    (
      (__ \ "href").readWithDefault[String](defaultObject.href) and
        (__ \ "text").readNullable[String] and
        (__ \ "labelText").readNullable[String] and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads)
    )(PaginationLink.apply _)

  implicit def jsonWrites: OWrites[PaginationLink] =
    (
      (__ \ "href").write[String] and
        (__ \ "text").writeNullable[String] and
        (__ \ "labelText").writeNullable[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(o => WritesUtils.unapplyCompat(unapply)(o))

}
