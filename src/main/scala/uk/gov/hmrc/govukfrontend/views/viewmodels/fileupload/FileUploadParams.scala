/*
 * Copyright 2019 HM Revenue & Customs
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
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessageParams
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.HintParams
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.LabelParams

case class FileUploadParams(
  name: String,
  id: String,
  value: Option[String]       = None,
  describedBy: Option[String] = None,
  labelParams: LabelParams,
  hintParams: Option[HintParams]                 = None,
  errorMessageParams: Option[ErrorMessageParams] = None,
  formGroupClasses: String                       = "",
  classes: String                                = "",
  attributes: Map[String, String]                = Map.empty
)

object FileUploadParams {

  implicit val reads = (
    (__ \ "name").read[String] and
      (__ \ "id").read[String] and
      (__ \ "value").readNullable[String] and
      (__ \ "describedBy").readNullable[String] and
      (__ \ "label").read[LabelParams] and
      (__ \ "hint").readNullable[HintParams] and
      (__ \ "errorMessage").readNullable[ErrorMessageParams] and
      readsFormGroupClasses and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(FileUploadParams.apply _)

  implicit val writes = (
    (__ \ "name").write[String] and
      (__ \ "id").write[String] and
      (__ \ "value").writeNullable[String] and
      (__ \ "describedBy").writeNullable[String] and
      (__ \ "label").write[LabelParams] and
      (__ \ "hint").writeNullable[HintParams] and
      (__ \ "errorMessage").writeNullable[ErrorMessageParams] and
      writesFormGroupClasses and
      (__ \ "classes").write[String] and
      (__ \ "attributes").write[Map[String, String]]
  )(unlift(FileUploadParams.unapply))
}
