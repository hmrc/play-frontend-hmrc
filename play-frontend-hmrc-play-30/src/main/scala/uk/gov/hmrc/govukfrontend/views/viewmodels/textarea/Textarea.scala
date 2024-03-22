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

package uk.gov.hmrc.govukfrontend.views.viewmodels.textarea

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.supportfrontend.views.IntString
import uk.gov.hmrc.govukfrontend.views.viewmodels.FormGroup

/** Parameters to `GovukTextarea` Twirl template
  *
  * @param id the id of the `textarea` element
  * @param name the name of the `textarea` element
  * @param rows height of the `textarea` in rows
  * @param value optional initial value of the `textarea`
  * @param describedBy optional `aria-describedby` attribute for the `textarea` element
  * @param label optional `Label` for the control
  * @param hint optional `Hint` for the control
  * @param errorMessage optional `ErrorMessage` to display
  * @param formGroup additional CSS classes/attributes/etc. to apply to the form group
  * @param classes optional additional CSS classes to apply to the `textarea`
  * @param autocomplete optional `autocomplete` attribute
  * @param attributes optional additional HTML attributes to apply to the `textarea`
  * @param spellcheck optional `spellcheck` attribute
  * @param disabled optional `disabled` attribute
  */
case class Textarea(
  id: String = "",
  name: String = "",
  rows: Int = 5,
  value: Option[String] = None,
  describedBy: Option[String] = None,
  label: Label = Label(),
  hint: Option[Hint] = None,
  errorMessage: Option[ErrorMessage] = None,
  formGroup: FormGroup = FormGroup(),
  classes: String = "",
  autocomplete: Option[String] = None,
  attributes: Map[String, String] = Map.empty,
  spellcheck: Option[Boolean] = None,
  disabled: Option[Boolean] = None
)

object Textarea {

  def defaultObject: Textarea = Textarea()

  implicit def jsonReads: Reads[Textarea] =
    (
      (__ \ "id").readWithDefault[String](defaultObject.id) and
        (__ \ "name").readWithDefault[String](defaultObject.name) and
        (__ \ "rows").readWithDefault[IntString](IntString(defaultObject.rows)).int and
        (__ \ "value").readNullable[String] and
        (__ \ "describedBy").readNullable[String] and
        (__ \ "label").readWithDefault[Label](defaultObject.label) and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "errorMessage").readNullable[ErrorMessage] and
        (__ \ "formGroup").readWithDefault[FormGroup](defaultObject.formGroup) and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "autocomplete").readNullable[String] and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes) and
        (__ \ "spellcheck").readNullable[Boolean] and
        (__ \ "disabled").readNullable[Boolean]
    )(Textarea.apply _)

  implicit def jsonWrites: OWrites[Textarea] =
    (
      (__ \ "id").write[String] and
        (__ \ "name").write[String] and
        (__ \ "rows").write[Int] and
        (__ \ "value").writeNullable[String] and
        (__ \ "describedBy").writeNullable[String] and
        (__ \ "label").write[Label] and
        (__ \ "hint").writeNullable[Hint] and
        (__ \ "errorMessage").writeNullable[ErrorMessage] and
        (__ \ "formGroup").write[FormGroup] and
        (__ \ "classes").write[String] and
        (__ \ "autocomplete").writeNullable[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "spellcheck").writeNullable[Boolean] and
        (__ \ "disabled").writeNullable[Boolean]
    )(unlift(Textarea.unapply))

}
