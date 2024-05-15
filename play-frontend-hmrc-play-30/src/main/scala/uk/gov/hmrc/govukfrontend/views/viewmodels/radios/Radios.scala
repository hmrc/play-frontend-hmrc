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

package uk.gov.hmrc.govukfrontend.views.viewmodels.radios

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._
import uk.gov.hmrc.govukfrontend.views.viewmodels.FormGroup
import uk.gov.hmrc.govukfrontend.views.viewmodels.FormGroup.{jsonReadsForMultipleInputs, jsonWritesForMultipleInputs}

/** Parameters to `GovukRadios` Twirl template
  *
  * @param fieldset optional `Fieldset` used to wrap the radio button control
  * @param hint optional `Hint` for the control
  * @param errorMessage optional `ErrorMessage` to display
  * @param formGroup additional CSS classes/attributes/etc. to apply to the form group
  * @param idPrefix optional id prefix to use for hint & error elements (defaults to `name`)
  * @param name the name of the `input` element
  * @param items sequence of `RadioItem`s
  * @param classes optional additional CSS classes to apply to the `govuk-radios` `div`
  * @param attributes optional additional HTML attributes to apply to the `govuk-radios` `div`
  * @param value optional value of the item that should be `checked`
  * @note `value` overrides any `checked` `RadioItem`
  */
case class Radios(
  fieldset: Option[Fieldset] = None,
  hint: Option[Hint] = None,
  errorMessage: Option[ErrorMessage] = None,
  formGroup: FormGroup = FormGroup.empty,
  idPrefix: Option[String] = None,
  name: String = "",
  items: Seq[RadioItem] = Nil,
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  value: Option[String] = None
)

object Radios {

  def defaultObject: Radios = Radios()

  implicit def jsonReads: Reads[Radios] =
    (
      (__ \ "fieldset").readNullable[Fieldset] and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "errorMessage").readNullable[ErrorMessage] and
        (__ \ "formGroup").readWithDefault[FormGroup](defaultObject.formGroup)(jsonReadsForMultipleInputs) and
        (__ \ "idPrefix").readNullable[String] and
        (__ \ "name").readWithDefault[String](defaultObject.name) and
        (__ \ "items").readWithDefault[Seq[RadioItem]](defaultObject.items)(forgivingSeqReads[RadioItem]) and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads) and
        (__ \ "value").readNullable[String]
    )(Radios.apply _)

  implicit def jsonWrites: OWrites[Radios] =
    (
      (__ \ "fieldset").writeNullable[Fieldset] and
        (__ \ "hint").writeNullable[Hint] and
        (__ \ "errorMessage").writeNullable[ErrorMessage] and
        (__ \ "formGroup").write[FormGroup](jsonWritesForMultipleInputs) and
        (__ \ "idPrefix").writeNullable[String] and
        (__ \ "name").write[String] and
        (__ \ "items").write[Seq[RadioItem]] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "value").writeNullable[String]
    )(r => (r.fieldset, r.hint, r.errorMessage, r.formGroup, r.idPrefix, r.name, r.items, r.classes, r.attributes, r.value))

}
