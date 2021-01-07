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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.footer

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

final case class Meta(
  visuallyHiddenTitle: Option[String] = None,
  content: Content                    = Empty,
  items: Option[Seq[FooterItem]]      = None
)

object Meta {

  implicit val reads: Reads[Meta] = (
    (__ \ "visuallyHiddenTitle").readNullable[String] and
      Content.reads and
      (__ \ "items").readNullable[Seq[FooterItem]]
  )(Meta.apply _)

  implicit val writes: OWrites[Meta] = (
    (__ \ "visuallyHiddenTitle").writeNullable[String] and
      Content.writes and
      (__ \ "items").writeNullable[Seq[FooterItem]]
  )(unlift(Meta.unapply))
}
