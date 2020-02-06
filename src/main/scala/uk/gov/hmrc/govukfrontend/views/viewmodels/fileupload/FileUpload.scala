/*
 * Copyright 2020 HM Revenue & Customs
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
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonDefaultValueFormatter
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label

case class FileUpload(
  name: String                       = "",
  id: String                         = "",
  value: Option[String]              = None,
  describedBy: Option[String]        = None,
  label: Label                       = Label(),
  hint: Option[Hint]                 = None,
  errorMessage: Option[ErrorMessage] = None,
  formGroupClasses: String           = "",
  classes: String                    = "",
  attributes: Map[String, String]    = Map.empty
)

object FileUpload extends JsonDefaultValueFormatter[FileUpload] {

  override def defaultObject: FileUpload = FileUpload()

  override def defaultReads: Reads[FileUpload] =
    (
      (__ \ "name").read[String] and
        (__ \ "id").read[String] and
        (__ \ "value").readNullable[String] and
        (__ \ "describedBy").readNullable[String] and
        (__ \ "label").read[Label] and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "errorMessage").readNullable[ErrorMessage] and
        readsFormGroupClasses and
        (__ \ "classes").read[String] and
        (__ \ "attributes").read[Map[String, String]]
    )(FileUpload.apply _)

  override implicit def jsonWrites: OWrites[FileUpload] =
    (
      (__ \ "name").write[String] and
        (__ \ "id").write[String] and
        (__ \ "value").writeNullable[String] and
        (__ \ "describedBy").writeNullable[String] and
        (__ \ "label").write[Label] and
        (__ \ "hint").writeNullable[Hint] and
        (__ \ "errorMessage").writeNullable[ErrorMessage] and
        writesFormGroupClasses and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(unlift(FileUpload.unapply))

}
