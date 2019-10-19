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
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label

case class Input(
  id: String,
  name: String,
  inputType: String           = "text",
  inputmode: Option[String]   = None,
  describedBy: Option[String] = None,
  value: Option[String]       = None,
  label: Label,
  hint: Option[Hint]                 = None,
  errorMessage: Option[ErrorMessage] = None,
  formGroupClasses: String           = "",
  classes: String                    = "",
  autocomplete: Option[String]       = None,
  pattern: Option[String]            = None,
  attributes: Map[String, String]    = Map.empty
)

object Input {

  implicit val reads: Reads[Input] = (
    (__ \ "id").read[String] and
      (__ \ "name").read[String] and
      (__ \ "type").readWithDefault[String]("text") and
      (__ \ "inputmode").readNullable[String] and
      (__ \ "describedBy").readNullable[String] and
      (__ \ "value").readNullable[String] and
      (__ \ "label").read[Label] and
      (__ \ "hint").readNullable[Hint] and
      (__ \ "errorMessage").readNullable[ErrorMessage] and
      readsFormGroupClasses and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "autocomplete").readNullable[String] and
      (__ \ "pattern").readNullable[String] and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(Input.apply _)

  implicit val writes: OWrites[Input] = (
    (__ \ "id").write[String] and
      (__ \ "name").write[String] and
      (__ \ "type").write[String] and
      (__ \ "inputmode").writeNullable[String] and
      (__ \ "describedBy").writeNullable[String] and
      (__ \ "value").writeNullable[String] and
      (__ \ "label").write[Label] and
      (__ \ "hint").writeNullable[Hint] and
      (__ \ "errorMessage").writeNullable[ErrorMessage] and
      writesFormGroupClasses and
      (__ \ "classes").write[String] and
      (__ \ "autocomplete").writeNullable[String] and
      (__ \ "pattern").writeNullable[String] and
      (__ \ "attributes").write[Map[String, String]]
  )(unlift(Input.unapply))
}
