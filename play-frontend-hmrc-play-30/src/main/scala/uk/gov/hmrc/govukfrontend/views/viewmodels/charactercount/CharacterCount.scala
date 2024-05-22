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

package uk.gov.hmrc.govukfrontend.views.viewmodels.charactercount

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.FormGroup
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label
import uk.gov.hmrc.supportfrontend.views.IntString

/** Parameters to `GovukCharacterCount` Twirl template
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
  * @param countMessageClasses optional additional CSS classes to apply to the max length/word count message
  * @param charactersUnderLimitText optional text to display when the character count is under the limit
  *                                   (defaults to "You can enter up to {maxLength} characters")
  * @param charactersAtLimitText optional text to display when the character count is at the limit
  * @param charactersOverLimitText optional text to display when the character count is over the limit
  * @param wordsUnderLimitText optional text to display when the word count is under the limit
  *                               (defaults to "You can enter up to {maxWords} words")
  * @param wordsAtLimitText optional text to display when the word count is at the limit
  * @param wordsOverLimitText optional text to display when the word count is over the limit
  * @param textareaDescriptionText optional text to display under the textarea
  */
case class CharacterCount(
  id: String = "",
  name: String = "",
  rows: Int = 5,
  spellcheck: Option[Boolean] = None,
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
  countMessageClasses: String = "",
  charactersUnderLimitText: Option[Map[String, String]] = None,
  charactersAtLimitText: Option[String] = None,
  charactersOverLimitText: Option[Map[String, String]] = None,
  wordsUnderLimitText: Option[Map[String, String]] = None,
  wordsAtLimitText: Option[String] = None,
  wordsOverLimitText: Option[Map[String, String]] = None,
  textareaDescriptionText: Option[String] = None
)

object CharacterCount {

  def defaultObject: CharacterCount = CharacterCount()

  implicit def jsonReads: Reads[CharacterCount] =
    (
      (__ \ "id").readWithDefault[String](defaultObject.id) and
        (__ \ "name").readWithDefault[String](defaultObject.name) and
        (__ \ "rows").readWithDefault[IntString](IntString(defaultObject.rows)).int and
        (__ \ "spellcheck").readNullable[Boolean] and
        (__ \ "value").readNullable[String] and
        (__ \ "maxlength").readNullable[IntString].int and
        (__ \ "maxwords").readNullable[IntString].int and
        (__ \ "threshold").readNullable[IntString].int and
        (__ \ "label").readWithDefault[Label](defaultObject.label) and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "errorMessage").readNullable[ErrorMessage] and
        (__ \ "formGroup").readWithDefault[FormGroup](defaultObject.formGroup) and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes) and
        readsCountMessageClasses and
        (__ \ "charactersUnderLimitText").readNullable[Map[String, String]] and
        (__ \ "charactersAtLimitText").readNullable[String] and
        (__ \ "charactersOverLimitText").readNullable[Map[String, String]] and
        (__ \ "wordsUnderLimitText").readNullable[Map[String, String]] and
        (__ \ "wordsAtLimitText").readNullable[String] and
        (__ \ "wordsOverLimitText").readNullable[Map[String, String]] and
        (__ \ "textareaDescriptionText").readNullable[String]
    )(CharacterCount.apply _)

  implicit def jsonWrites: OWrites[CharacterCount] =
    (
      (__ \ "id").write[String] and
        (__ \ "name").write[String] and
        (__ \ "rows").write[Int] and
        (__ \ "spellcheck").writeNullable[Boolean] and
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
        writesCountMessageClasses and
        (__ \ "charactersUnderLimitText").writeNullable[Map[String, String]] and
        (__ \ "charactersAtLimitText").writeNullable[String] and
        (__ \ "charactersOverLimitText").writeNullable[Map[String, String]] and
        (__ \ "wordsUnderLimitText").writeNullable[Map[String, String]] and
        (__ \ "wordsAtLimitText").writeNullable[String] and
        (__ \ "wordsOverLimitText").writeNullable[Map[String, String]] and
        ((__ \ "textareaDescriptionText").writeNullable[String])
    )(unlift(CharacterCount.unapply))

  private implicit def readsStringOrNumber: Reads[String] = {
    case JsString(s) => JsSuccess(s)
    case JsNumber(n) => JsSuccess(n.toString)
    case _           => JsError("Unable to parse as String or Number")
  }
}
