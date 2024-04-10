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

package uk.gov.hmrc.govukfrontend.views.viewmodels.input

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.FormGroup
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label

/** Parameters to `GovukInput` Twirl template
  *
  * @param id `id` attribute for the `input`
  * @param name `name` attribute for the `input`
  * @param inputType `type` attribute for the `input`
  * @param inputmode optional `inputmode` attribute for the `input`
  * @param describedBy optional `aria-describedby` attribute for the `input`
  * @param value optional `value` attribute for the `input`
  * @param label optional `Label` for the control
  * @param hint optional `Hint` for the control
  * @param errorMessage optional `ErrorMessage` to display
  * @param formGroup additional CSS classes/attributes/etc. to apply to the form group
  * @param classes optional additional CSS classes to apply to the `input`
  * @param autocomplete optional `autocomplete` attribute for the `input`
  * @param pattern optional `pattern` attribute for the `input`
  * @param attributes optional additional HTML attributes to apply to the `input`
  * @param spellcheck optional `spellcheck` attribute for the `input`
  * @param prefix optional content to display immediately before the `input`
  * @param suffix optional content to display immediately after the `input`
  * @param disabled optional `disabled` attribute for the `input`
  * @param autocapitalize optional `autocapitalize` attribute for the `input`
  */
case class Input(
  id: String = "",
  name: String = "",
  inputType: String = "text",
  inputmode: Option[String] = None,
  describedBy: Option[String] = None,
  value: Option[String] = None,
  label: Label = Label(),
  hint: Option[Hint] = None,
  errorMessage: Option[ErrorMessage] = None,
  formGroup: FormGroup = FormGroup.empty,
  classes: String = "",
  autocomplete: Option[String] = None,
  pattern: Option[String] = None,
  attributes: Map[String, String] = Map.empty,
  spellcheck: Option[Boolean] = None,
  prefix: Option[PrefixOrSuffix] = None,
  suffix: Option[PrefixOrSuffix] = None,
  disabled: Option[Boolean] = None,
  autocapitalize: Option[String] = None,
  inputWrapper: InputWrapper = InputWrapper.empty
)

object Input {

  def defaultObject: Input = Input()

  implicit def jsonReads: Reads[Input] =
    (
      (__ \ "id").readWithDefault[String](defaultObject.id) and
        (__ \ "name").readWithDefault[String](defaultObject.name) and
        (__ \ "type").readWithDefault[String](defaultObject.inputType) and
        (__ \ "inputmode").readNullable[String] and
        (__ \ "describedBy").readNullable[String] and
        (__ \ "value").readNullable[String] and
        (__ \ "label").readWithDefault[Label](defaultObject.label) and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "errorMessage").readNullable[ErrorMessage] and
        (__ \ "formGroup").readWithDefault[FormGroup](defaultObject.formGroup) and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "autocomplete").readNullable[String] and
        (__ \ "pattern").readNullable[String] and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes) and
        (__ \ "spellcheck").readNullable[Boolean] and
        (__ \ "prefix").readNullable[PrefixOrSuffix] and
        (__ \ "suffix").readNullable[PrefixOrSuffix] and
        (__ \ "disabled").readNullable[Boolean] and
        (__ \ "autocapitalize").readNullable[String] and
        (__ \ "inputWrapper").readWithDefault[InputWrapper](defaultObject.inputWrapper)
    )(Input.apply _)

  implicit def jsonWrites: OWrites[Input] =
    (
      (__ \ "id").write[String] and
        (__ \ "name").write[String] and
        (__ \ "type").write[String] and
        (__ \ "inputmode").writeNullable[String] and
        (__ \ "describedBy").writeNullable[String] and
        (__ \ "value").writeNullable[String] and
        (__ \ "label").write[Label] and
        (__ \ "hint").writeNullable[Hint] and
        (__ \ "errorMessage").writeNullable[ErrorMessage] and
        (__ \ "formGroup").write[FormGroup] and
        (__ \ "classes").write[String] and
        (__ \ "autocomplete").writeNullable[String] and
        (__ \ "pattern").writeNullable[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "spellcheck").writeNullable[Boolean] and
        (__ \ "prefix").writeNullable[PrefixOrSuffix] and
        (__ \ "suffix").writeNullable[PrefixOrSuffix] and
        (__ \ "disabled").writeNullable[Boolean] and
        (__ \ "autocapitalize").writeNullable[String] and
        (__ \ "inputWrapper").write[InputWrapper]
    )(unlift(Input.unapply))

}
