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

package uk.gov.hmrc.govukfrontend.views.viewmodels.select

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._
import uk.gov.hmrc.govukfrontend.views.viewmodels.FormGroup
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonImplicits.RichJsPath

/** Parameters to `GovukSelect` Twirl template
  *
  * @param id the id of the `select` element
  * @param name the name of the `select` element
  * @param items sequence of `SelectItem`s
  * @param describedBy optional `aria-describedby` attribute for the `select` element
  * @param label optional `Label` for the control
  * @param hint optional `Hint` for the control
  * @param errorMessage optional `ErrorMessage` to display
  * @param formGroup additional CSS classes/attributes/etc. to apply to the form group
  * @param classes optional additional CSS classes to apply to the `select`
  * @param attributes optional additional HTML attributes to apply to the `select`
  * @param value optional value of the item that should be `selected`
  * @note `value` overrides any `selected` `SelectItem`
  */
case class Select(
  id: String = "",
  name: String = "",
  items: Seq[SelectItem] = Nil,
  describedBy: Option[String] = None,
  label: Label = Label(),
  hint: Option[Hint] = None,
  errorMessage: Option[ErrorMessage] = None,
  formGroup: FormGroup = FormGroup(),
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  value: Option[String] = None,
  disabled: Option[Boolean] = None
)

object Select {

  def defaultObject: Select = Select()

  implicit def jsonReads: Reads[Select] =
    (
      (__ \ "id").readWithDefault[String](defaultObject.id) and
        (__ \ "name").readWithDefault[String](defaultObject.name) and
        (__ \ "items").readWithDefault[Seq[SelectItem]](defaultObject.items)(forgivingSeqReads[SelectItem]) and
        (__ \ "describedBy").readNullable[String] and
        (__ \ "label").readWithDefault[Label](defaultObject.label) and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "errorMessage").readNullable[ErrorMessage] and
        (__ \ "formGroup").readWithDefault[FormGroup](defaultObject.formGroup) and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads) and
        (__ \ "value").readsJsValueToString.map(Option[String]).orElse(Reads.pure(None)) and
        (__ \ "disabled").readNullable[Boolean]
    )(Select.apply _)

  implicit def jsonWrites: OWrites[Select] =
    (
      (__ \ "id").write[String] and
        (__ \ "name").write[String] and
        (__ \ "items").write[Seq[SelectItem]] and
        (__ \ "describedBy").writeNullable[String] and
        (__ \ "label").write[Label] and
        (__ \ "hint").writeNullable[Hint] and
        (__ \ "errorMessage").writeNullable[ErrorMessage] and
        (__ \ "formGroup").write[FormGroup] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "value").writeNullable[String] and
        (__ \ "disabled").writeNullable[Boolean]
    )(unlift(Select.unapply))

}
