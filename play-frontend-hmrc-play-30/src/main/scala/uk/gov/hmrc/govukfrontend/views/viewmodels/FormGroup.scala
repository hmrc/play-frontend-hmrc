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
  attributes: Map[String, String] = Map.empty
)

object FormGroup {

  implicit def jsonReads: Reads[FormGroup] =
    (
      (__ \ "classes").readNullable[String] and
        (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
    )(FormGroup.apply _)

  implicit def jsonWrites: OWrites[FormGroup] =
    (
      (__ \ "classes").writeNullable[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(unlift(FormGroup.unapply))

}
