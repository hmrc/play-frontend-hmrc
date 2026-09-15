/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels.servicenavigation

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content._

case class ServiceNavigationEndSlot(
  end: Content = Empty,
  align: Option[String] = None
)

object ServiceNavigationEndSlot {
  def defaultObject: ServiceNavigationEndSlot = ServiceNavigationEndSlot()

  val readsWithAlign: Reads[ServiceNavigationEndSlot] =
    (
      Content.reads and
        (__ \ "align").readNullable[String]
    )(ServiceNavigationEndSlot.apply _)

  implicit val reads: Reads[ServiceNavigationEndSlot] = new Reads[ServiceNavigationEndSlot] {
    override def reads(json: JsValue): JsResult[ServiceNavigationEndSlot] =
      json.validate[JsString] match {
        case JsSuccess(contentAsString, _) =>
          JsSuccess(ServiceNavigationEndSlot(HtmlContent(contentAsString.as[String])))
        case _                             =>
          json.validate[JsObject] match {
            case JsSuccess(jsObject, _) =>
              jsObject.validate[ServiceNavigationEndSlot](readsWithAlign)
          }
      }
  }

  implicit val writes: Writes[ServiceNavigationEndSlot] = Json.writes[ServiceNavigationEndSlot]

}
