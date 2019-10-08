/*
 * Copyright 2019 HM Revenue & Customs
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
  href: String,
  content: Content                   = Empty,
  visuallyHiddenText: Option[String] = None,
  classes: String                    = "")

object ActionItem {

  implicit val reads: Reads[ActionItem] = (
    (__ \ "href").readWithDefault[String]("") and
      Content.reads and
      (__ \ "visuallyHiddenText").readNullable[String] and
      (__ \ "classes").readWithDefault[String]("")
  )(ActionItem.apply _)

  implicit val writes = (
    (__ \ "href").write[String] and
      Content.writes and
      (__ \ "visuallyHiddenText").writeNullable[String] and
      (__ \ "classes").write[String]
  )(unlift(ActionItem.unapply))
}
