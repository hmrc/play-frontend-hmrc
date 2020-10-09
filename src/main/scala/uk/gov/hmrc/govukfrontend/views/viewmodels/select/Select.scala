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

package uk.gov.hmrc.govukfrontend.views.viewmodels.select

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._
import uk.gov.hmrc.govukfrontend.views.viewmodels.{JsonDefaultValueFormatter}

case class Select(
  id: String                         = "",
  name: String                       = "",
  items: Seq[SelectItem]             = Nil,
  describedBy: Option[String]        = None,
  label: Label                       = Label(),
  hint: Option[Hint]                 = None,
  errorMessage: Option[ErrorMessage] = None,
  formGroupClasses: String           = "",
  classes: String                    = "",
  attributes: Map[String, String]    = Map.empty
)

object Select extends JsonDefaultValueFormatter[Select] {

  override def defaultObject: Select = Select()

  override def defaultReads: Reads[Select] =
    (
      (__ \ "id").read[String] and
        (__ \ "name").read[String] and
        (__ \ "items").read[Seq[SelectItem]](forgivingSeqReads[SelectItem]) and
        (__ \ "describedBy").readNullable[String] and
        (__ \ "label").read[Label] and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "errorMessage").readNullable[ErrorMessage] and
        readsFormGroupClasses and
        (__ \ "classes").read[String] and
        (__ \ "attributes").read[Map[String, String]](attributesReads)
    )(Select.apply _)

  override implicit def jsonWrites: OWrites[Select] =
    (
      (__ \ "id").write[String] and
        (__ \ "name").write[String] and
        (__ \ "items").write[Seq[SelectItem]] and
        (__ \ "describedBy").writeNullable[String] and
        (__ \ "label").write[Label] and
        (__ \ "hint").writeNullable[Hint] and
        (__ \ "errorMessage").writeNullable[ErrorMessage] and
        writesFormGroupClasses and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(unlift(Select.unapply))

}
