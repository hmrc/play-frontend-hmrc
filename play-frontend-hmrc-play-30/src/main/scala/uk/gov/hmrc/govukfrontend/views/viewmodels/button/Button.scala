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

package uk.gov.hmrc.govukfrontend.views.viewmodels.button

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._

case class Button(
  element: Option[String] = None,
  name: Option[String] = None,
  inputType: Option[String] = None,
  value: Option[String] = None,
  disabled: Boolean = false,
  href: Option[String] = None,
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  preventDoubleClick: Option[Boolean] = None,
  isStartButton: Boolean = false,
  content: Content = Empty,
  id: Option[String] = None
)

object Button {

  def defaultObject: Button = Button()

  implicit def jsonReads: Reads[Button] =
    (
      (__ \ "element").readNullable[String] and
        (__ \ "name").readNullable[String] and
        (__ \ "type").readNullable[String] and
        (__ \ "value").readNullable[String] and
        (__ \ "disabled").readWithDefault[Boolean](defaultObject.disabled) and
        (__ \ "href").readNullable[String] and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads) and
        (__ \ "preventDoubleClick").readNullable[Boolean] and
        (__ \ "isStartButton").readWithDefault[Boolean](defaultObject.isStartButton) and
        Content.reads and
        (__ \ "id").readNullable[String]
    )(Button.apply _)

  implicit def jsonWrites: OWrites[Button] =
    (
      (__ \ "element").writeNullable[String] and
        (__ \ "name").writeNullable[String] and
        (__ \ "type").writeNullable[String] and
        (__ \ "value").writeNullable[String] and
        (__ \ "disabled").write[Boolean] and
        (__ \ "href").writeNullable[String] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "preventDoubleClick").writeNullable[Boolean] and
        (__ \ "isStartButton").write[Boolean] and
        Content.writes and
        (__ \ "id").writeNullable[String]
    )(b => (b.element, b.name, b.inputType, b.value, b.disabled, b.href, b.classes, b.attributes, b.preventDoubleClick, b.isStartButton, b.content, b.id))

}
