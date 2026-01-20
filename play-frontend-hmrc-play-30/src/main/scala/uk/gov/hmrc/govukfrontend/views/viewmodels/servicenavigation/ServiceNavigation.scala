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
import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Empty

case class ServiceNavigation(
  serviceName: Option[String] = None,
  serviceUrl: Option[String] = None,
  navigation: Seq[ServiceNavigationItem] = Seq(),
  navigationClasses: String = "",
  navigationId: String = "",
  navigationLabel: Option[String] = None,
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  ariaLabel: Option[String] = None,
  menuButtonText: Option[String] = None,
  menuButtonLabel: Option[String] = None,
  slots: Option[ServiceNavigationSlot] = None,
  collapseNavigationOnMobile: Option[Boolean] = None
) {
  def shouldDisplayNavigation: Boolean =
    navigation.nonEmpty || slots.exists(slot => slot.navigationStart.nonEmpty || slot.navigationEnd.nonEmpty)
}

object ServiceNavigation {
  def defaultObject: ServiceNavigation = ServiceNavigation()

  implicit def jsonReads: Reads[ServiceNavigation] = Json.using[Json.WithDefaultValues].reads[ServiceNavigation]

  implicit def jsonWrites: OWrites[ServiceNavigation] = Json.writes[ServiceNavigation]

}
