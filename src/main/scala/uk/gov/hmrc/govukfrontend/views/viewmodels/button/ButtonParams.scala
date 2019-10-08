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

package uk.gov.hmrc.govukfrontend.views.viewmodels.button

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.html.components._

case class ButtonParams(
  element: Option[String]         = None,
  name: Option[String]            = None,
  inputType: Option[String]       = None,
  value: Option[String]           = None,
  disabled: Boolean               = false,
  href: Option[String]            = None,
  classes: String                 = "",
  attributes: Map[String, String] = Map.empty,
  preventDoubleClick: Boolean     = false,
  isStartButton: Boolean          = false,
  content: Content
)

object ButtonParams {

  implicit val reads: Reads[ButtonParams] = (
    (__ \ "element").readNullable[String] and
      (__ \ "name").readNullable[String] and
      (__ \ "input").readNullable[String] and
      (__ \ "value").readNullable[String] and
      (__ \ "disabled").readWithDefault[Boolean](false) and
      (__ \ "href").readNullable[String] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      (__ \ "preventDoubleClick").readWithDefault[Boolean](false) and
      (__ \ "isStartButton").readWithDefault[Boolean](false) and
      Content.reads
  )(ButtonParams.apply _)

  implicit val writes: OWrites[ButtonParams] = (
    (__ \ "element").writeNullable[String] and
      (__ \ "name").writeNullable[String] and
      (__ \ "input").writeNullable[String] and
      (__ \ "value").writeNullable[String] and
      (__ \ "disabled").write[Boolean] and
      (__ \ "href").writeNullable[String] and
      (__ \ "classes").write[String] and
      (__ \ "attributes").write[Map[String, String]] and
      (__ \ "preventDoubleClick").write[Boolean] and
      (__ \ "isStartButton").write[Boolean] and
      Content.writes
  )(unlift(ButtonParams.unapply))
}
