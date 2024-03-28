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

package uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._
import uk.gov.hmrc.govukfrontend.views.viewmodels.FormGroup

/** Parameters to `GovukCheckboxes` Twirl template
  *
  * @param describedBy optional `aria-describedby` attribute for the `input` element
  * @param fieldset optional `Fieldset` used to wrap the checkboxes control
  * @param hint optional `Hint` for the control
  * @param errorMessage optional `ErrorMessage` to display
  * @param formGroup additional CSS classes/attributes/etc. to apply to the form group
  * @param idPrefix optional id prefix to use for hint & error elements (defaults to `name`)
  * @param name the name of the `input` element
  * @param items sequence of `CheckboxItem`s
  * @param classes optional additional CSS classes to apply to the `govuk-checkboxes` `div`
  * @param attributes optional additional HTML attributes to apply to the `govuk-checkboxes` `div`
  * @param values sequence of values of any `items` that should be `checked`
  * @note `values` override any `checked` `CheckboxItem`(s)
  */
case class Checkboxes(
  describedBy: Option[String] = None,
  fieldset: Option[Fieldset] = None,
  hint: Option[Hint] = None,
  errorMessage: Option[ErrorMessage] = None,
  formGroup: FormGroup = FormGroup.empty,
  idPrefix: Option[String] = None,
  name: String = "",
  items: Seq[CheckboxItem] = Nil,
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  values: Set[String] = Set.empty
)

object Checkboxes {

  def defaultObject: Checkboxes = Checkboxes()

  implicit def jsonReads: Reads[Checkboxes] =
    (
      (__ \ "describedBy").readNullable[String] and
        (__ \ "fieldset").readNullable[Fieldset] and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "errorMessage").readNullable[ErrorMessage] and
        (__ \ "formGroup").readWithDefault[FormGroup](defaultObject.formGroup) and
        (__ \ "idPrefix").readNullable[String] and
        (__ \ "name").readWithDefault[String](defaultObject.name) and
        (__ \ "items").readWithDefault[Seq[CheckboxItem]](defaultObject.items)(forgivingSeqReads[CheckboxItem]) and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads) and
        (__ \ "values").readWithDefault[Set[String]](defaultObject.values)
    )(Checkboxes.apply _)

  implicit def jsonWrites: OWrites[Checkboxes] =
    (
      (__ \ "describedBy").writeNullable[String] and
        (__ \ "fieldset").writeNullable[Fieldset] and
        (__ \ "hint").writeNullable[Hint] and
        (__ \ "errorMessage").writeNullable[ErrorMessage] and
        (__ \ "formGroup").write[FormGroup] and
        (__ \ "idPrefix").writeNullable[String] and
        (__ \ "name").write[String] and
        (__ \ "items").write[Seq[CheckboxItem]] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "values").write[Set[String]]
    )(unlift(Checkboxes.unapply))

}
