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

package uk.gov.hmrc.govukfrontend.views.viewmodels.warningtext

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.html.components._

case class WarningText(
  iconFallbackText: String,
  classes: String                 = "",
  attributes: Map[String, String] = Map.empty,
  content: Content                = Empty
)

object WarningText {

  implicit val reads: Reads[WarningText] = (
    (__ \ "iconFallbackText").read[String] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      Content.reads
  )(WarningText.apply _)

  implicit val writes: OWrites[WarningText] = (
    (__ \ "iconFallbackText").write[String] and
      (__ \ "classes").write[String] and
      (__ \ "attributes").write[Map[String, String]] and
      Content.writes
  )(unlift(WarningText.unapply))
}
