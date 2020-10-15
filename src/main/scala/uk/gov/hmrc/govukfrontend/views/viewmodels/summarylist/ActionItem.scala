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
package summarylist

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

final case class ActionItem(
  href: String                       = "",
  content: Content                   = Empty,
  visuallyHiddenText: Option[String] = None,
  classes: String                    = "",
  attributes: Map[String, String] = Map.empty)

object ActionItem extends JsonDefaultValueFormatter[ActionItem] {

  override def defaultObject: ActionItem = ActionItem()

  override def defaultReads: Reads[ActionItem] =
    (
      (__ \ "href").read[String] and
        Content.reads and
        (__ \ "visuallyHiddenText").readNullable[String] and
        (__ \ "classes").read[String] and
        (__ \ "attributes").read[Map[String, String]]
    )(ActionItem.apply _)

  override implicit def jsonWrites: OWrites[ActionItem] =
    (
      (__ \ "href").write[String] and
        Content.writes and
        (__ \ "visuallyHiddenText").writeNullable[String] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(unlift(ActionItem.unapply))

}
