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
    navigation: Seq[ServiceNavigationItem] = Seq()
)

object ServiceNavigation {
    def defaultObject: ServiceNavigation = ServiceNavigation() // maybe don't need this
    implicit def jsonReads: Reads[ServiceNavigation] = 
    (
        (__ \ "serviceName").readsJsValueToString.map(Option[String]).orElse(Reads.pure(None)) and
            (__ \ "serviceUrl").readsJsValueToString.map(Option[String]).orElse(Reads.pure(None)) and
            (__ \ "navigation").readWithDefault[Seq[ServiceNavigationItem]](defaultObject.navigation)(forgivingSeqReads[ServiceNavigationItem])
    )(ServiceNavigation.apply _)

    implicit def jsonWrites: OWrites[ServiceNavigation] = 
    (
        (__ \ "href").writeNullable[String] and
            (__ \ "text").writeNullable[String] and
            (__ \ "active").write[Seq[ServiceNavigationItem]]
    )(o => WritesUtils.unapplyCompat(unapply)(o))    
}