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

package uk.gov.hmrc.govukfrontend.views.viewmodels.charactercount

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.IntString
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonDefaultValueFormatter
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label

case class CharacterCount(
  id: String                         = "",
  name: String                       = "",
  rows: Int                          = 5,
  value: Option[String]              = None,
  maxLength: Option[Int]             = None,
  maxWords: Option[Int]              = None,
  threshold: Option[Int]             = None,
  label: Label                       = Label(),
  hint: Option[Hint]                 = None,
  errorMessage: Option[ErrorMessage] = None,
  formGroupClasses: String           = "",
  classes: String                    = "",
  attributes: Map[String, String]    = Map.empty)

object CharacterCount extends JsonDefaultValueFormatter[CharacterCount] {

  override def defaultObject: CharacterCount = CharacterCount()

  override def defaultReads: Reads[CharacterCount] =
    (
      (__ \ "id").read[String] and
        (__ \ "name").read[String] and
        (__ \ "rows").read[IntString].int and
        (__ \ "value").readNullable[String] and
        (__ \ "maxlength").readNullable[IntString].int and
        (__ \ "maxwords").readNullable[IntString].int and
        (__ \ "threshold").readNullable[IntString].int and
        (__ \ "label").read[Label] and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "errorMessage").readNullable[ErrorMessage] and
        readsFormGroupClasses and
        (__ \ "classes").read[String] and
        (__ \ "attributes").read[Map[String, String]]
    )(CharacterCount.apply _)

  override implicit def jsonWrites: OWrites[CharacterCount] =
    (
      (__ \ "id").write[String] and
        (__ \ "name").write[String] and
        (__ \ "rows").write[Int] and
        (__ \ "value").writeNullable[String] and
        (__ \ "maxlength").writeNullable[Int] and
        (__ \ "maxwords").writeNullable[Int] and
        (__ \ "threshold").writeNullable[Int] and
        (__ \ "label").write[Label] and
        (__ \ "hint").writeNullable[Hint] and
        (__ \ "errorMessage").writeNullable[ErrorMessage] and
        writesFormGroupClasses and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(unlift(CharacterCount.unapply))

}
