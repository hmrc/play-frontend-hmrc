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

package uk.gov.hmrc.govukfrontend.views.viewmodels
package dateinput

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.FormGroup
import uk.gov.hmrc.govukfrontend.views.viewmodels.FormGroup.{jsonReadsForMultipleInputs, jsonWritesForMultipleInputs}

/** Parameters to `GovukDateInput` Twirl template
  *
  * @param id `id` attribute for the wrapper `div`
  * @param namePrefix optional name prefix for each of the inputs in the date control
  * @param items sequence of `InputItem`s
  * @param hint optional `Hint` for the control
  * @param errorMessage optional `ErrorMessage` to display
  * @param formGroup additional CSS classes/attributes/etc. to apply to the form group
  * @param fieldset optional `Fieldset` used to wrap the date input control
  * @param classes optional additional CSS classes to apply to the wrapper `div`
  * @param attributes optional additional HTML attributes to apply to the wrapper `div`
  */
case class DateInput(
  id: String = "",
  namePrefix: Option[String] = None,
  items: Seq[InputItem] = Seq.empty,
  hint: Option[Hint] = None,
  errorMessage: Option[ErrorMessage] = None,
  formGroup: FormGroup = FormGroup.empty,
  fieldset: Option[Fieldset] = None,
  classes: String = "",
  attributes: Map[String, String] = Map.empty
)

object DateInput {

  def defaultObject: DateInput = DateInput()

  implicit def jsonReads: Reads[DateInput] =
    (
      (__ \ "id").readWithDefault[String](defaultObject.id) and
        (__ \ "namePrefix").readNullable[String] and
        (__ \ "items").readWithDefault[Seq[InputItem]](defaultObject.items) and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "errorMessage").readNullable[ErrorMessage] and
        (__ \ "formGroup").readWithDefault[FormGroup](defaultObject.formGroup)(jsonReadsForMultipleInputs) and
        (__ \ "fieldset").readNullable[Fieldset] and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)
    )(DateInput.apply _)

  implicit def jsonWrites: OWrites[DateInput] =
    (
      (__ \ "id").write[String] and
        (__ \ "namePrefix").writeNullable[String] and
        (__ \ "items").write[Seq[InputItem]] and
        (__ \ "hint").writeNullable[Hint] and
        (__ \ "errorMessage").writeNullable[ErrorMessage] and
        (__ \ "formGroup").write[FormGroup](jsonWritesForMultipleInputs) and
        (__ \ "fieldset").writeNullable[Fieldset] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(o => WritesUtils.unapplyCompat(unapply)(o))

}
