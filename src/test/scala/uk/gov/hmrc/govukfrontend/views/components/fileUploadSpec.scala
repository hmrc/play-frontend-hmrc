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

package uk.gov.hmrc.govukfrontend.views.components

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.html.components._

class fileUploadSpec
    extends RenderHtmlSpec(
      Seq(
        "file-upload-default",
        "file-upload-with-error-message",
        "file-upload-with-hint-text",
        "file-upload-with-label-as-page-heading",
        "file-upload-with-optional-form-group-classes",
        "file-upload-with-value-and-attributes"
      )
    ) {
  case class FormGroup(classes: String)
  object FormGroup {
    implicit val readsFormGroup = Json.reads[FormGroup]
  }

  override implicit val reads: Reads[Html] = (
    (__ \ "name").read[String] and
      (__ \ "id").read[String] and
      (__ \ "value").readNullable[String] and
      (__ \ "label").read[LabelParams] and
      (__ \ "hint").readNullable[HintParams] and
      (__ \ "errorMessage").readNullable[ErrorMessageParams] and
      (__ \ "formGroup").readNullable[FormGroup].map(_.map(formGroup => formGroup.classes).getOrElse("")) and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(FileUpload.apply _)
}
