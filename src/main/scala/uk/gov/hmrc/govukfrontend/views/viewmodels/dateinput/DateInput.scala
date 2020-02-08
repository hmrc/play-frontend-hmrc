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

package uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonDefaultValueFormatter

case class DateInput(
  id: Option[String]                 = None,
  namePrefix: Option[String]         = None,
  items: Seq[InputItem]              = Seq.empty,
  hint: Option[Hint]                 = None,
  errorMessage: Option[ErrorMessage] = None,
  formGroupClasses: String           = "",
  fieldset: Option[Fieldset]         = None,
  classes: String                    = "",
  attributes: Map[String, String]    = Map.empty
)

object DateInput extends JsonDefaultValueFormatter[DateInput] {

  override def defaultObject: DateInput = DateInput()

  override def defaultReads: Reads[DateInput] =
    (
      (__ \ "id").readNullable[String] and
        (__ \ "namePrefix").readNullable[String] and
        (__ \ "items").read[Seq[InputItem]] and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "errorMessage").readNullable[ErrorMessage] and
        readsFormGroupClasses and
        (__ \ "fieldset").readNullable[Fieldset] and
        (__ \ "classes").read[String] and
        (__ \ "attributes").read[Map[String, String]]
    )(DateInput.apply _)

  override implicit def jsonWrites: OWrites[DateInput] =
    (
      (__ \ "id").writeNullable[String] and
        (__ \ "namePrefix").writeNullable[String] and
        (__ \ "items").write[Seq[InputItem]] and
        (__ \ "hint").writeNullable[Hint] and
        (__ \ "errorMessage").writeNullable[ErrorMessage] and
        writesFormGroupClasses and
        (__ \ "fieldset").writeNullable[Fieldset] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(unlift(DateInput.unapply))

}
