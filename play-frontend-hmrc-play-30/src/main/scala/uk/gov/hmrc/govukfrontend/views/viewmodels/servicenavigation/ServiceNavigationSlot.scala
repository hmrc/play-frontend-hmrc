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
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty, HtmlContent}

case class ServiceNavigationSlot(
  start: Content = Empty,
  end: Content = Empty,
  navigationStart: Content = Empty,
  navigationEnd: Content = Empty
)

object ServiceNavigationSlot {
  def defaultObject: ServiceNavigationSlot = ServiceNavigationSlot()

  private def contentFromOptionalString(optString: Option[String]): Content =
    optString match {
      case Some(str) => HtmlContent(str)
      case None      => Empty
    }

  private def contentToOptionalString(content: Content): Option[String] =
    content match {
      case Empty => None
      case value => Some(value.asHtml.body)
    }

  private def applyReads(
    start: Option[String],
    end: Option[String],
    navigationStart: Option[String],
    navigationEnd: Option[String]
  ): ServiceNavigationSlot =
    ServiceNavigationSlot(
      start = contentFromOptionalString(start),
      end = contentFromOptionalString(end),
      navigationStart = contentFromOptionalString(navigationStart),
      navigationEnd = contentFromOptionalString(navigationEnd)
    )

  private def unapplyWrites(
    slot: ServiceNavigationSlot
  ): (Option[String], Option[String], Option[String], Option[String]) =
    (
      contentToOptionalString(slot.start),
      contentToOptionalString(slot.end),
      contentToOptionalString(slot.navigationStart),
      contentToOptionalString(slot.navigationEnd)
    )

  implicit def jsonReads: Reads[ServiceNavigationSlot] =
    (
      (__ \ "start").readNullable[String] and
        (__ \ "end").readNullable[String] and
        (__ \ "navigationStart").readNullable[String] and
        (__ \ "navigationEnd").readNullable[String]
    )(applyReads _)

  implicit def jsonWrites: Writes[ServiceNavigationSlot] =
    (
      (__ \ "start").writeNullable[String] and
        (__ \ "end").writeNullable[String] and
        (__ \ "navigationStart").writeNullable[String] and
        (__ \ "navigationEnd").writeNullable[String]
    )(unapplyWrites(_))
}
