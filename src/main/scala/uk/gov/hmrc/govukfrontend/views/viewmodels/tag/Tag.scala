/*
 * Copyright 2020 HM Revenue & Customs
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
package tag

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

final case class Tag(
  content: Content                = Empty,
  classes: String                 = "",
  attributes: Map[String, String] = Map.empty
)

object Tag extends JsonDefaultValueFormatter[Tag] {

  override def defaultObject: Tag = Tag()

  override def defaultReads: Reads[Tag] =
    (
      Content.reads and
        (__ \ "classes").read[String] and
        (__ \ "attributes").read[Map[String, String]]
    )(Tag.apply _)

  override implicit def jsonWrites: OWrites[Tag] =
    (
      Content.writes and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(unlift(Tag.unapply))

}
