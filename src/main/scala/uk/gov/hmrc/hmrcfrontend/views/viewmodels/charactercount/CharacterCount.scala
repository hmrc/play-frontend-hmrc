/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.charactercount

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.supportfrontend.views.IntString
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{En, Language}

case class CharacterCount(
  id: String = "",
  name: String = "",
  rows: Int = 5,
  value: Option[String] = None,
  maxLength: Option[Int] = None,
  maxWords: Option[Int] = None,
  threshold: Option[Int] = None,
  label: Label = Label(),
  hint: Option[Hint] = None,
  errorMessage: Option[ErrorMessage] = None,
  formGroupClasses: String = "",
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  spellcheck: Option[Boolean] = None,
  countMessageClasses: String = "",
  language: Language = En
)

object CharacterCount {

  def defaultObject: CharacterCount = CharacterCount()

  implicit def jsonReads: Reads[CharacterCount] =
    (
      (__ \ "id").readWithDefault[String](defaultObject.id) and
        (__ \ "name").readWithDefault[String](defaultObject.name) and
        (__ \ "rows").readWithDefault[IntString](IntString(defaultObject.rows)).int and
        (__ \ "value").readNullable[String] and
        (__ \ "maxlength").readNullable[IntString].int and
        (__ \ "maxwords").readNullable[IntString].int and
        (__ \ "threshold").readNullable[IntString].int and
        (__ \ "label").readWithDefault[Label](defaultObject.label) and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "errorMessage").readNullable[ErrorMessage] and
        readsFormGroupClasses and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes) and
        (__ \ "spellcheck").readNullable[Boolean] and
        readsCountMessageClasses and
        (__ \ "language").readWithDefault[Language](defaultObject.language)
    )(CharacterCount.apply _)

  implicit def jsonWrites: OWrites[CharacterCount] =
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
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "spellcheck").writeNullable[Boolean] and
        writesCountMessageClasses and
        (__ \ "language").write[Language]
    )(unlift(CharacterCount.unapply))

}
