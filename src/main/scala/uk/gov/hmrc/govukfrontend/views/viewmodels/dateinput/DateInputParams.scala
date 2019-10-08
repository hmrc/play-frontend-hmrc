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

package uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput

import uk.gov.hmrc.govukfrontend.views.html.components._
import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._

case class DateInputParams(
  id: Option[String]                             = None,
  namePrefix: Option[String]                     = None,
  items: Seq[InputItem]                          = Nil,
  hintParams: Option[HintParams]                 = None,
  errorMessageParams: Option[ErrorMessageParams] = None,
  formGroupClasses: String                       = "",
  fieldsetParams: Option[FieldsetParams]         = None,
  classes: String                                = "",
  attributes: Map[String, String]                = Map.empty
)

object DateInputParams {

  implicit val reads: Reads[DateInputParams] = (
    (__ \ "id").readNullable[String] and
      (__ \ "namePrefix").readNullable[String] and
      (__ \ "items").readWithDefault[Seq[InputItem]](Nil) and
      (__ \ "hint").readNullable[HintParams] and
      (__ \ "errorMessage").readNullable[ErrorMessageParams] and
      readsFormGroupClasses and
      (__ \ "fieldset").readNullable[FieldsetParams] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(DateInputParams.apply _)

  implicit val writes: OWrites[DateInputParams] = (
    (__ \ "id").writeNullable[String] and
      (__ \ "namePrefix").writeNullable[String] and
      (__ \ "items").write[Seq[InputItem]] and
      (__ \ "hint").writeNullable[HintParams] and
      (__ \ "errorMessage").writeNullable[ErrorMessageParams] and
      writesFormGroupClasses and
      (__ \ "fieldset").writeNullable[FieldsetParams] and
      (__ \ "classes").write[String] and
      (__ \ "attributes").write[Map[String, String]]
  )(unlift(DateInputParams.unapply))
}
