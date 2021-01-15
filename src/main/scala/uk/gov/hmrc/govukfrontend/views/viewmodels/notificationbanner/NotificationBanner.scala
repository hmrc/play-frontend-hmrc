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

package uk.gov.hmrc.govukfrontend.views.viewmodels.notificationbanner

import play.api.libs.functional.syntax._
import play.api.libs.json.{OWrites, Reads, __}
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats.attributesReads
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

case class NotificationBanner(
  content: Content                  = Empty,
  bannerType: Option[String]        = None,
  role: Option[String]              = None,
  title: Content                    = Empty,
  titleId: Option[String]           = None,
  disableAutoFocus: Option[Boolean] = None,
  classes: Option[String]           = None,
  titleHeadingLevel: Option[Int]    = None,
  attributes: Map[String, String]   = Map.empty
)

object NotificationBanner {

  def defaultObject: NotificationBanner = NotificationBanner()

  implicit def jsonReads: Reads[NotificationBanner] =
    (
      Content.reads and
        (__ \ "type").readNullable[String] and
        (__ \ "role").readNullable[String] and
        Content.readsHtmlOrText((__ \ "titleHtml"), (__ \ "titleText")) and
        (__ \ "titleId").readNullable[String] and
        (__ \ "disableAutoFocus").readNullable[Boolean] and
        (__ \ "classes").readNullable[String] and
        (__ \ "titleHeadingLevel").readNullable[Int] and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads)
    )(NotificationBanner.apply _)

  implicit def jsonWrites: OWrites[NotificationBanner] =
    (
      Content.writes and
        (__ \ "type").writeNullable[String] and
        (__ \ "role").writeNullable[String] and
        Content.writesContent("titleHtml", "titleText") and
        (__ \ "titleId").writeNullable[String] and
        (__ \ "disableAutoFocus").writeNullable[Boolean] and
        (__ \ "classes").writeNullable[String] and
        (__ \ "titleHeadingLevel").writeNullable[Int] and
        (__ \ "attributes").write[Map[String, String]]
    )(unlift(NotificationBanner.unapply))
}
