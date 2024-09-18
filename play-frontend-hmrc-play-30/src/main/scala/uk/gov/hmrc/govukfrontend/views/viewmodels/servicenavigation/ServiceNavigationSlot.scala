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

case class ServiceNavigationSlot(
  start: String = "",
  end: String = "",
  navigationStart: String = "",
  navigationEnd: String = ""
)

object ServiceNavigationSlot {
  def defaultObject: ServiceNavigationSlot             = ServiceNavigationSlot()
  implicit def jsonReads: Reads[ServiceNavigationSlot] =
    (
      (__ \ "start").readWithDefault[String](defaultObject.start) and
        (__ \ "end").readWithDefault[String](defaultObject.end) and
        (__ \ "navigationStart").readWithDefault[String](defaultObject.navigationStart) and
        (__ \ "navigationEnd").readWithDefault[String](defaultObject.navigationEnd)
    )(ServiceNavigationSlot.apply _)

  implicit def jsonWrites: Writes[ServiceNavigationSlot] =
    (
      (__ \ "start").write[String] and
        (__ \ "end").write[String] and
        (__ \ "navigationStart").write[String] and
        (__ \ "navigationEnd").write[String]
    )(o => WritesUtils.unapplyCompat(unapply)(o))

}
