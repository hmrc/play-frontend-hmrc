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
    navigationId: Option[String] = None,
    navigationLabel: Option[String] = None,
    classes: String = "",
    attributes: Map[String, String] = Map.empty,
    ariaLabel: String = "Service information",
    menuButtonText: Option[String] = None,
    menuButtonLabel: Option[String] = None
)

object ServiceNavigation {
    def defaultObject: ServiceNavigation = ServiceNavigation() // maybe don't need this
    implicit def jsonReads: Reads[ServiceNavigation] = 
    (
        (__ \ "serviceName").readsJsValueToString.map(Option[String]).orElse(Reads.pure(None)) and
            (__ \ "serviceUrl").readsJsValueToString.map(Option[String]).orElse(Reads.pure(None)) and
            (__ \ "navigation").readWithDefault[Seq[ServiceNavigationItem]](defaultObject.navigation)(forgivingSeqReads[ServiceNavigationItem]) and
            (__ \ "navigationClasses").readWithDefault[String](defaultObject.navigationClasses) and
            (__ \ "navigationId").readsJsValueToString.map(Option[String]).orElse(Reads.pure(None)) and
            (__ \ "navigationLabel").readsJsValueToString.map(Option[String]).orElse(Reads.pure(None)) and
            (__ \ "classes").readWithDefault[String](defaultObject.classes) and
            (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes) and
            (__ \ "ariaLabel").readWithDefault[String](defaultObject.ariaLabel) and
            (__ \ "menuButtonText").readsJsValueToString.map(Option[String]).orElse(Reads.pure(None)) and
            (__ \ "menuButtonLabel").readsJsValueToString.map(Option[String]).orElse(Reads.pure(None))
    )(ServiceNavigation.apply _)

    implicit def jsonWrites: OWrites[ServiceNavigation] = 
    (
        (__ \ "serviceName").writeNullable[String] and
            (__ \ "serviceUrl").writeNullable[String] and
            (__ \ "navigation").write[Seq[ServiceNavigationItem]] and
            (__ \ "navigationClasses").write[String] and
            (__ \ "navigationId").writeNullable[String] and
            (__ \ "navigationLabel").writeNullable[String] and
            (__ \ "classes").write[String] and
            (__ \ "attributes").write[Map[String, String]] and
            (__ \ "ariaLabel").write[String] and
            (__ \ "menuButtonText").writeNullable[String] and 
            (__ \ "menuButtonLabel").writeNullable[String]
    )(o => WritesUtils.unapplyCompat(unapply)(o))
}