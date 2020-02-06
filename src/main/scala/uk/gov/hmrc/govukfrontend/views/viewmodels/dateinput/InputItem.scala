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
package dateinput

import play.api.libs.functional.syntax._
import play.api.libs.json._

final case class InputItem(
  id: Option[String]              = None,
  name: String                    = "",
  label: Option[String]           = None,
  value: Option[String]           = None,
  autocomplete: Option[String]    = None,
  pattern: Option[String]         = None,
  classes: String                 = "",
  inputType: String               = "",
  inputmode: String               = "",
  attributes: Map[String, String] = Map.empty
)

object InputItem extends JsonDefaultValueFormatter[InputItem] {

  override def defaultObject: InputItem = InputItem()

  override def defaultReads: Reads[InputItem] =
    (
      (__ \ "id").readNullable[String] and
        (__ \ "name").read[String] and
        (__ \ "label").readNullable[String] and
        (__ \ "value").readNullable[String] and
        (__ \ "autocomplete").readNullable[String] and
        (__ \ "pattern").readNullable[String] and
        (__ \ "classes").read[String] and
        (__ \ "inputType").read[String] and
        (__ \ "inputmode").read[String] and
        (__ \ "attributes").read[Map[String, String]]
    )(InputItem.apply _)

  override implicit def jsonWrites: OWrites[InputItem] =
    (
      (__ \ "id").writeNullable[String] and
        (__ \ "name").write[String] and
        (__ \ "label").writeNullable[String] and
        (__ \ "value").writeNullable[String] and
        (__ \ "autocomplete").writeNullable[String] and
        (__ \ "pattern").writeNullable[String] and
        (__ \ "classes").write[String] and
        (__ \ "inputType").write[String] and
        (__ \ "inputmode").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(unlift(InputItem.unapply))

}
