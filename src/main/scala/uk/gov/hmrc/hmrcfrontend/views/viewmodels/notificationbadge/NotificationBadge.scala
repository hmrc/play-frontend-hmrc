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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.notificationbadge

import play.api.libs.json._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.JsonDefaultValueFormatter
import play.api.libs.functional.syntax._

case class NotificationBadge(
                              text: String = ""
                            )

object NotificationBadge extends JsonDefaultValueFormatter[NotificationBadge] {

  def apply(text: Int): NotificationBadge = new NotificationBadge(text.toString)

  override def defaultObject: NotificationBadge = NotificationBadge()

  override def defaultReads: Reads[NotificationBadge] = new Reads[NotificationBadge] {
    override def reads(json: JsValue): JsResult[NotificationBadge] = {
      (json \ "text").asOpt[String] orElse (json \ "text").asOpt[Int].map(_.toString) match {
        case Some(text) => JsSuccess(NotificationBadge(text))
        case None => JsError("Cannot parse value text as either String or Int")
      }
    }
  }

  override implicit def jsonWrites: OWrites[NotificationBadge] = Json.writes[NotificationBadge]
}
