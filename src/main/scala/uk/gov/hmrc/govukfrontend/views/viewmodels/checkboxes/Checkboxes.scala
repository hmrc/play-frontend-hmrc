/*
 * Copyright 2022 HM Revenue & Customs
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

case class Checkboxes(
  describedBy: Option[String] = None,
  fieldset: Option[Fieldset] = None,
  hint: Option[Hint] = None,
  errorMessage: Option[ErrorMessage] = None,
  formGroupClasses: String = "",
  idPrefix: Option[String] = None,
  name: String = "",
  items: Seq[CheckboxItem] = Nil,
  classes: String = "",
  attributes: Map[String, String] = Map.empty
)

object Checkboxes {

  def defaultObject: Checkboxes = Checkboxes()

  implicit def jsonReads: Reads[Checkboxes] =
    (
      (__ \ "describedBy").readNullable[String] and
        (__ \ "fieldset").readNullable[Fieldset] and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "errorMessage").readNullable[ErrorMessage] and
        readsFormGroupClasses and
        (__ \ "idPrefix").readNullable[String] and
        (__ \ "name").readWithDefault[String](defaultObject.name) and
        (__ \ "items").readWithDefault[Seq[CheckboxItem]](defaultObject.items)(forgivingSeqReads[CheckboxItem]) and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads)
    )(Checkboxes.apply _)

  implicit def jsonWrites: OWrites[Checkboxes] =
    (
      (__ \ "describedBy").writeNullable[String] and
        (__ \ "fieldset").writeNullable[Fieldset] and
        (__ \ "hint").writeNullable[Hint] and
        (__ \ "errorMessage").writeNullable[ErrorMessage] and
        writesFormGroupClasses and
        (__ \ "idPrefix").writeNullable[String] and
        (__ \ "name").write[String] and
        (__ \ "items").write[Seq[CheckboxItem]] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(unlift(Checkboxes.unapply))

}
