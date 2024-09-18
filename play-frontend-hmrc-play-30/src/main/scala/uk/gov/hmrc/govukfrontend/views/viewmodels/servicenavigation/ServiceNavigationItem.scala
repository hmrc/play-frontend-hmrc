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

case class ServiceNavigationItem(
  href: String = "",
  text: String = "",
  html: Option[String] = None,
  active: Boolean = false,
  current: Boolean = false,
  classes: String = "",
  attributes: Map[String, String] = Map.empty
)

object ServiceNavigationItem {
  def defaultObject: ServiceNavigationItem = ServiceNavigationItem() // maybe don't need this

  implicit def jsonReads: Reads[ServiceNavigationItem] =
    (
      (__ \ "href").readWithDefault[String](defaultObject.href) and
        (__ \ "text").readWithDefault[String](defaultObject.text) and
        (__ \ "html").readNullable[String] and
        (__ \ "active").readWithDefault[Boolean](defaultObject.active) and
        (__ \ "current").readWithDefault[Boolean](defaultObject.current) and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)
    )(ServiceNavigationItem.apply _)

  implicit def jsonWrites: OWrites[ServiceNavigationItem] =
    (
      (__ \ "href").write[String] and
        (__ \ "text").write[String] and
        (__ \ "html").writeNullable[String] and
        (__ \ "active").write[Boolean] and
        (__ \ "current").write[Boolean] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(o => WritesUtils.unapplyCompat(unapply)(o))
}
