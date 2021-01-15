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
package breadcrumbs

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

final case class BreadcrumbsItem(
  content: Content                = Empty,
  href: Option[String]            = None,
  attributes: Map[String, String] = Map.empty
)

object BreadcrumbsItem {

  def defaultObject: BreadcrumbsItem = BreadcrumbsItem()

  implicit def jsonReads: Reads[BreadcrumbsItem] = (
    Content.reads and
      (__ \ "href").readNullable[String] and
      (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)
    )(BreadcrumbsItem.apply _)

  implicit def jsonWrites: OWrites[BreadcrumbsItem] = (
    Content.writes and
      (__ \ "href").writeNullable[String] and
      (__ \ "attributes").write[Map[String, String]]
    )(unlift(BreadcrumbsItem.unapply))
}
