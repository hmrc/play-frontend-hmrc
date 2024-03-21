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

package uk.gov.hmrc.govukfrontend.views.viewmodels.fileupload

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.FormGroup
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label

/** Parameters to `GovukFileUpload` Twirl template
  *
  * @param name `name` attribute for the `input`
  * @param id `id` attribute for the `input`
  * @param value optional `value` attribute for the `input`
  * @param describedBy optional `aria-describedby` attribute for the `input`
  * @param label optional `Label` for the control
  * @param hint optional `Hint` for the control
  * @param errorMessage optional `ErrorMessage` to display
  * @param formGroup additional CSS classes/attributes/etc. to apply to the form group
  * @param classes optional additional CSS classes to apply to the `input`
  * @param attributes optional additional HTML attributes to apply to the `input`
  * @param disabled optional `disabled` attribute for the `input`
  */
case class FileUpload(
  name: String = "",
  id: String = "",
  value: Option[String] = None,
  describedBy: Option[String] = None,
  label: Label = Label(),
  hint: Option[Hint] = None,
  errorMessage: Option[ErrorMessage] = None,
  formGroup: FormGroup = FormGroup(),
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  disabled: Option[Boolean] = None
)

object FileUpload {

  def defaultObject: FileUpload = FileUpload()

  implicit def jsonReads: Reads[FileUpload] =
    (
      (__ \ "name").readWithDefault[String](defaultObject.name) and
        (__ \ "id").readWithDefault[String](defaultObject.id) and
        (__ \ "value").readNullable[String] and
        (__ \ "describedBy").readNullable[String] and
        (__ \ "label").readWithDefault[Label](defaultObject.label) and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "errorMessage").readNullable[ErrorMessage] and
        (__ \ "formGroup").readWithDefault[FormGroup](defaultObject.formGroup) and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes) and
        (__ \ "disabled").readNullable[Boolean]
    )(FileUpload.apply _)

  implicit def jsonWrites: OWrites[FileUpload] =
    (
      (__ \ "name").write[String] and
        (__ \ "id").write[String] and
        (__ \ "value").writeNullable[String] and
        (__ \ "describedBy").writeNullable[String] and
        (__ \ "label").write[Label] and
        (__ \ "hint").writeNullable[Hint] and
        (__ \ "errorMessage").writeNullable[ErrorMessage] and
        (__ \ "formGroup").write[FormGroup] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "disabled").writeNullable[Boolean]
    )(unlift(FileUpload.unapply))

}
