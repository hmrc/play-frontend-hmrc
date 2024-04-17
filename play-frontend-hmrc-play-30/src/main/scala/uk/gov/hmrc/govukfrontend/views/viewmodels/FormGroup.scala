/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class FormGroup(
  classes: Option[String] = None,
  attributes: Map[String, String] = Map.empty,
  afterInput: Option[AfterInput] = None
)

object FormGroup {

  val empty: FormGroup = FormGroup()

  implicit def jsonReads: Reads[FormGroup] =
    (
      (__ \ "classes").readNullable[String] and
        (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
        Reads.nullable[AfterInput](__)
    )(FormGroup.apply _)

  implicit def jsonWrites: OWrites[FormGroup] =
    (
      (__ \ "classes").writeNullable[String] and
        (__ \ "attributes").write[Map[String, String]] and
        Writes.nullable[AfterInput]
    )(unlift(FormGroup.unapply))

}

case class AfterInput(value: String)

object AfterInput {

  implicit def jsonReads: Reads[AfterInput] = new Reads[AfterInput] {
    override def reads(json: JsValue): JsResult[AfterInput] = {
      (json \ "afterInput").validate[String] match {
        case JsSuccess(before, _) => JsSuccess(AfterInput(before))
        case errorOne: JsError =>
          (json \ "afterInputs").validate[String] match {
            case JsSuccess(before, _) => JsSuccess(AfterInput(before))
            case _: JsError => errorOne
          }
      }
    }
  }

  implicit def writes: OWrites[AfterInput] = new OWrites[AfterInput] {
    override def writes(after: AfterInput): JsObject = Json.obj(
      "afterInput" ->  after.value,
      "afterInputs" -> after.value
    )
  }
}
