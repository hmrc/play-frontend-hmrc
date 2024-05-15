/*
 * Copyright 2023 HM Revenue & Customs
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
  href: String = "",
  content: Content = Empty,
  visuallyHiddenText: Option[String] = None,
  classes: String = "",
  attributes: Map[String, String] = Map.empty
)

object ActionItem {

  def defaultObject: ActionItem = ActionItem()

  implicit def jsonReads: Reads[ActionItem] =
    (
      (__ \ "href").readWithDefault[String](defaultObject.href) and
        Content.reads and
        (__ \ "visuallyHiddenText").readNullable[String] and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)
    )(ActionItem.apply _)

  implicit def jsonWrites: OWrites[ActionItem] =
    (
      (__ \ "href").write[String] and
        Content.writes and
        (__ \ "visuallyHiddenText").writeNullable[String] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(ai => (ai.href, ai.content, ai.visuallyHiddenText, ai.classes, ai.attributes))

}
