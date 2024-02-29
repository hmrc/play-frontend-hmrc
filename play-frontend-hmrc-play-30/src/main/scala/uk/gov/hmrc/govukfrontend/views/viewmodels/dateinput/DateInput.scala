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

package uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput

import play.api.data.Field
import play.api.i18n.Messages
import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._

case class DateInput(
  id: String = "",
  namePrefix: Option[String] = None,
  items: Seq[InputItem] = Seq.empty,
  hint: Option[Hint] = None,
  errorMessage: Option[ErrorMessage] = None,
  formGroupClasses: String = "",
  fieldset: Option[Fieldset] = None,
  classes: String = "",
  attributes: Map[String, String] = Map.empty
)

//type DateInputItems = Seq[InputItem]

object DateInput {

  def defaultObject: DateInput = DateInput()

  def dayMonthYearInputItems(field: Field)(implicit messages: Messages) = Seq(InputItem(
    id = s"${field.name}.day",
    name = s"${field.name}.day",
    value = field("day").value,
    label = Some(messages(s"date.input.day")),
    classes = "govuk-input--width-2"
  ),
  InputItem(
    id = s"${field.name}.month",
    name = s"${field.name}.month",
    value = field("month").value,
    label = Some(messages(s"date.input.month")),
    classes = "govuk-input--width-2"
  ),
  InputItem(
    id = s"${field.name}.year",
    name = s"${field.name}.year",
    value = field("year").value,
    label = Some(messages(s"date.input.year")),
    classes = "govuk-input--width-4"
  ))

  implicit def jsonReads: Reads[DateInput] =
    (
      (__ \ "id").readWithDefault[String](defaultObject.id) and
        (__ \ "namePrefix").readNullable[String] and
        (__ \ "items").readWithDefault[Seq[InputItem]](defaultObject.items) and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "errorMessage").readNullable[ErrorMessage] and
        readsFormGroupClasses and
        (__ \ "fieldset").readNullable[Fieldset] and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)
    )(DateInput.apply _)

  implicit def jsonWrites: OWrites[DateInput] =
    (
      (__ \ "id").write[String] and
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


