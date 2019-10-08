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

package uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._

case class CheckboxesParams(
  describedBy: Option[String]                    = None,
  fieldsetParams: Option[FieldsetParams]         = None,
  hintParams: Option[HintParams]                 = None,
  errorMessageParams: Option[ErrorMessageParams] = None,
  formGroupClasses: String                       = "",
  idPrefix: Option[String]                       = None,
  name: String,
  items: Seq[CheckboxItem],
  classes: String                 = "",
  attributes: Map[String, String] = Map.empty
)

object CheckboxesParams {

  implicit val reads: Reads[CheckboxesParams] = (
    (__ \ "describedBy").readNullable[String] and
      (__ \ "fieldset").readNullable[FieldsetParams] and
      (__ \ "hint").readNullable[HintParams] and
      (__ \ "errorMessage").readNullable[ErrorMessageParams] and
      readsFormGroupClasses and
      (__ \ "idPrefix").readNullable[String] and
      (__ \ "name").readWithDefault[String]("") and
      (__ \ "items").read[Seq[CheckboxItem]] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(CheckboxesParams.apply _)

  implicit val writes: OWrites[CheckboxesParams] = (
    (__ \ "describedBy").writeNullable[String] and
      (__ \ "fieldset").writeNullable[FieldsetParams] and
      (__ \ "hint").writeNullable[HintParams] and
      (__ \ "errorMessage").writeNullable[ErrorMessageParams] and
      writesFormGroupClasses and
      (__ \ "idPrefix").writeNullable[String] and
      (__ \ "name").write[String] and
      (__ \ "items").write[Seq[CheckboxItem]] and
      (__ \ "classes").write[String] and
      (__ \ "attributes").write[Map[String, String]]
  )(unlift(CheckboxesParams.unapply))
}
