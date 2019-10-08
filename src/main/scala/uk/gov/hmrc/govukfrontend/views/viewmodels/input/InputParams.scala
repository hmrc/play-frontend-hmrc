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

package uk.gov.hmrc.govukfrontend.views.viewmodels.input

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessageParams
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.HintParams
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.LabelParams
import scala.util.matching.Regex

case class InputParams(
  id: String,
  name: String,
  inputType: String           = "text",
  inputMode: Option[String]   = None,
  describedBy: Option[String] = None,
  value: Option[String]       = None,
  labelParams: LabelParams,
  hintParams: Option[HintParams]                 = None,
  errorMessageParams: Option[ErrorMessageParams] = None,
  formGroupClasses: String                       = "",
  classes: String                                = "",
  autoComplete: Option[String]                   = None,
  pattern: Option[Regex]                         = None,
  attributes: Map[String, String]                = Map.empty
)

object InputParams {

  implicit val reads: Reads[InputParams] = (
    (__ \ "id").read[String] and
      (__ \ "name").read[String] and
      (__ \ "type").readWithDefault[String]("text") and
      (__ \ "inputmode").readNullable[String] and
      (__ \ "describedBy").readNullable[String] and
      (__ \ "value").readNullable[String] and
      (__ \ "label").read[LabelParams] and
      (__ \ "hint").readNullable[HintParams] and
      (__ \ "errorMessage").readNullable[ErrorMessageParams] and
      readsFormGroupClasses and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "autocomplete").readNullable[String] and
      (__ \ "pattern").readNullable[Regex] and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(InputParams.apply _)

  implicit val writes: OWrites[InputParams] = (
    (__ \ "id").write[String] and
      (__ \ "name").write[String] and
      (__ \ "type").write[String] and
      (__ \ "inputmode").writeNullable[String] and
      (__ \ "describedBy").writeNullable[String] and
      (__ \ "value").writeNullable[String] and
      (__ \ "label").write[LabelParams] and
      (__ \ "hint").writeNullable[HintParams] and
      (__ \ "errorMessage").writeNullable[ErrorMessageParams] and
      writesFormGroupClasses and
      (__ \ "classes").write[String] and
      (__ \ "autocomplete").writeNullable[String] and
      (__ \ "pattern").writeNullable[Regex] and
      (__ \ "attributes").write[Map[String, String]]
  )(unlift(InputParams.unapply))
}
