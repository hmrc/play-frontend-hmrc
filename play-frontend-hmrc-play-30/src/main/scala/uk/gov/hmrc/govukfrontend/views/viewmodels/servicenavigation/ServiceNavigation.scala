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

import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonImplicits._
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class ServiceNavigation(
  serviceName: Option[String] = None,
  serviceUrl: Option[String] = None,
  navigation: Seq[ServiceNavigationItem] = Seq(),
  navigationClasses: String = "",
  navigationId: String = "navigation",
  navigationLabel: Option[String] = None,
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  ariaLabel: Option[String] = None,
  menuButtonText: Option[String] = None,
  menuButtonLabel: Option[String] = None,
  slots: Option[ServiceNavigationSlot] = None
) {
  // serviceNavUrl is defaulted to empty string in the view, so it's not optional here
  // Check if we would like to handle it and if empty put None
  def addServiceNameAndUrl(serviceNavName: Option[String], serviceNavUrl: String): ServiceNavigation = {
    (serviceName, serviceUrl) match {
      case (Some(_), Some(_)) => this
      case (None, Some(_)) => this.copy(serviceName = serviceNavName)
      case (Some(_), None) => this.copy(serviceUrl = Some(serviceNavUrl))
      case (None, None) => this.copy(serviceName = serviceNavName, serviceUrl = Some(serviceNavUrl))
    }
  }

  def addLanguageToggle(displayLanguage: Boolean, languageToggle: String): ServiceNavigation = {
    if(displayLanguage) {
      val slotsWithToggle = if(slots.isEmpty)
        Some(ServiceNavigationSlot(end = Some(languageToggle)))
      else slots.map(slot => slot.copy(end = Some(languageToggle)))

      this.copy(slots = slotsWithToggle)
    } else this
  }
}

object ServiceNavigation {
  def defaultObject: ServiceNavigation             = ServiceNavigation()

  implicit def jsonReads: Reads[ServiceNavigation] =
    (
      (__ \ "serviceName").readNullable[String] and
        (__ \ "serviceUrl").readNullable[String] and
        (__ \ "navigation").readWithDefault[Seq[ServiceNavigationItem]](defaultObject.navigation)(
          forgivingSeqReads[ServiceNavigationItem]
        ) and
        (__ \ "navigationClasses").readWithDefault[String](defaultObject.navigationClasses) and
        (__ \ "navigationId").readWithDefault[String](defaultObject.navigationId) and
        (__ \ "navigationLabel").readNullable[String] and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes) and
        (__ \ "ariaLabel").readNullable[String] and
        (__ \ "menuButtonText").readNullable[String] and
        (__ \ "menuButtonLabel").readNullable[String] and
        (__ \ "slots").readNullable[ServiceNavigationSlot]
    )(ServiceNavigation.apply _)

  implicit def jsonWrites: OWrites[ServiceNavigation] =
    (
      (__ \ "serviceName").writeNullable[String] and
        (__ \ "serviceUrl").writeNullable[String] and
        (__ \ "navigation").write[Seq[ServiceNavigationItem]] and
        (__ \ "navigationClasses").write[String] and
        (__ \ "navigationId").write[String] and
        (__ \ "navigationLabel").writeNullable[String] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "ariaLabel").writeNullable[String] and
        (__ \ "menuButtonText").writeNullable[String] and
        (__ \ "menuButtonLabel").writeNullable[String] and
        (__ \ "slots").writeNullable[ServiceNavigationSlot]
    )(o => WritesUtils.unapplyCompat(unapply)(o))
}
