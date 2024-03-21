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
  formGroup: FormGroup = FormGroup(),
  classes: String = "",
  autocomplete: Option[String] = None,
  pattern: Option[String] = None,
  attributes: Map[String, String] = Map.empty,
  spellcheck: Option[Boolean] = None,
  prefix: Option[PrefixOrSuffix] = None,
  suffix: Option[PrefixOrSuffix] = None,
  disabled: Option[Boolean] = None
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
        (__ \ "disabled").readNullable[Boolean]
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
        (__ \ "disabled").writeNullable[Boolean]
    )(unlift(Input.unapply))

}
