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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels
package charactercount

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.supportfrontend.views.IntString
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._
import uk.gov.hmrc.govukfrontend.views.viewmodels.FormGroup
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{En, Language}

/** Parameters to `HmrcCharacterCount` Twirl template
  *
  * @param id the id of the `textarea` element
  * @param name the name of the `textarea` element
  * @param rows height of the `textarea` in rows
  * @param value optional initial value of the `textarea`
  * @param maxLength optional maximum length, in characters, of the text in the `textarea`
  * @param maxWords optional maximum number of words in the `textarea`
  * @param threshold optional threshold, in characters, for the `textarea`
  * @param label optional `Label` for the control
  * @param hint optional `Hint` for the control
  * @param errorMessage optional `ErrorMessage` to display
  * @param formGroup additional CSS classes/attributes/etc. to apply to the form group
  * @param classes optional additional CSS classes to apply to the `textarea`
  * @param attributes optional additional HTML attributes to apply to the `textarea`
  * @param spellcheck optional spellcheck attribute (NOT CURRENTLY WIRED UP)
  * @param language language for internationalisation of the character count messages
  */
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
  formGroup: FormGroup = FormGroup.empty,
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
        (__ \ "formGroup").readWithDefault[FormGroup](defaultObject.formGroup) and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads) and
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
        (__ \ "formGroup").write[FormGroup] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "spellcheck").writeNullable[Boolean] and
        writesCountMessageClasses and
        (__ \ "language").write[Language]
    )(o => WritesUtils.unapplyCompat(unapply)(o))

}
