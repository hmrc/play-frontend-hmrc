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

package uk.gov.hmrc.govukfrontend.views.viewmodels.textarea

import play.api.libs.json._
import play.api.libs.functional.syntax._
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._

case class TextareaParams(
  id: String,
  name: String,
  rows: Int                   = 5,
  value: Option[String]       = None,
  describedBy: Option[String] = None,
  labelParams: LabelParams,
  hintParams: Option[HintParams]                 = None,
  errorMessageParams: Option[ErrorMessageParams] = None,
  formGroupClasses: String                       = "",
  classes: String                                = "",
  autocomplete: Option[String]                   = None,
  attributes: Map[String, String]                = Map.empty
)

object TextareaParams {

  implicit val reads: Reads[TextareaParams] = (
    (__ \ "id").read[String] and
      (__ \ "name").read[String] and
      (__ \ "rows").readWithDefault[Int](5) and
      (__ \ "value").readNullable[String] and
      (__ \ "describedBy").readNullable[String] and
      (__ \ "label").read[LabelParams] and
      (__ \ "hint").readNullable[HintParams] and
      (__ \ "errorMessage").readNullable[ErrorMessageParams] and
      readsFormGroupClasses and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "autocomplete").readNullable[String] and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(TextareaParams.apply _)

  implicit val writes: OWrites[TextareaParams] = (
    (__ \ "id").write[String] and
      (__ \ "name").write[String] and
      (__ \ "rows").write[Int] and
      (__ \ "value").writeNullable[String] and
      (__ \ "describedBy").writeNullable[String] and
      (__ \ "label").write[LabelParams] and
      (__ \ "hint").writeNullable[HintParams] and
      (__ \ "errorMessage").writeNullable[ErrorMessageParams] and
      writesFormGroupClasses and
      (__ \ "classes").write[String] and
      (__ \ "autocomplete").writeNullable[String] and
      (__ \ "attributes").write[Map[String, String]]
  )(unlift(TextareaParams.unapply))
}
