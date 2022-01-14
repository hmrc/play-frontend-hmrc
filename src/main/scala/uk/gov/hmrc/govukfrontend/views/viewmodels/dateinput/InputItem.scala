/*
 * Copyright 2022 HM Revenue & Customs
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
  id: String = "",
  name: String = "",
  label: Option[String] = None,
  value: Option[String] = None,
  autocomplete: Option[String] = None,
  pattern: Option[String] = None,
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  inputmode: Option[String] = None
)

object InputItem {

  def defaultObject: InputItem = InputItem()

  implicit def jsonReads: Reads[InputItem] =
    (
      (__ \ "id").readWithDefault[String](defaultObject.id) and
        (__ \ "name").readWithDefault[String](defaultObject.name) and
        (__ \ "label").readNullable[String] and
        (__ \ "value").readNullable[String] and
        (__ \ "autocomplete").readNullable[String] and
        (__ \ "pattern").readNullable[String] and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes) and
        (__ \ "inputmode").readNullable[String]
    )(InputItem.apply _)

  implicit def jsonWrites: OWrites[InputItem] =
    (
      (__ \ "id").write[String] and
        (__ \ "name").write[String] and
        (__ \ "label").writeNullable[String] and
        (__ \ "value").writeNullable[String] and
        (__ \ "autocomplete").writeNullable[String] and
        (__ \ "pattern").writeNullable[String] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "inputmode").writeNullable[String]
    )(unlift(InputItem.unapply))

}
