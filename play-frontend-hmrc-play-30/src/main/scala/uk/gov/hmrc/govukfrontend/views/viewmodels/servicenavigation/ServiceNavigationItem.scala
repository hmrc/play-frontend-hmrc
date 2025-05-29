/*
 * Copyright 2024 HM Revenue & Customs
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
package servicenavigation

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

case class ServiceNavigationItem(
  content: Content = Empty,
  href: String = "",
  active: Boolean = false,
  current: Boolean = false,
  classes: String = "",
  attributes: Map[String, String] = Map.empty
) {
  def nonEmptyContent(): Boolean = content != Empty
}

object ServiceNavigationItem {
  def defaultObject: ServiceNavigationItem = ServiceNavigationItem()

  implicit def jsonReads: Reads[ServiceNavigationItem] =
    (
      Content.reads and
        (__ \ "href").readWithDefault[String](defaultObject.href) and
        (__ \ "active").readWithDefault[Boolean](defaultObject.active) and
        (__ \ "current").readWithDefault[Boolean](defaultObject.current) and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)
    )(ServiceNavigationItem.apply _)

  implicit def jsonWrites: OWrites[ServiceNavigationItem] =
    (
      Content.writes and
        (__ \ "href").write[String] and
        (__ \ "active").write[Boolean] and
        (__ \ "current").write[Boolean] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(o => WritesUtils.unapplyCompat(unapply)(o))
}
