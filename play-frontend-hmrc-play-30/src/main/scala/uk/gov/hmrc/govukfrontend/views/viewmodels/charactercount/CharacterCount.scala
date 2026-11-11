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

package uk.gov.hmrc.govukfrontend.views.viewmodels
package charactercount

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
  * @param maxWords optional maximum number of words in the `textarea`. Deprecated, should use `maxLength` and `countType`
  * @param threshold optional threshold, in characters, for the `textarea`
  * @param countType optional count type used to count the text – `characters`, `length` or `words`
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
  countType: Option[CountType] = Some(LengthCountType),
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

  implicit def jsonReads: Reads[CharacterCount] = new Reads[CharacterCount] {
    override def reads(json: JsValue): JsResult[CharacterCount] =
      for {
        id                       <- (json \ "id").validateOpt[String]
        name                     <- (json \ "name").validateOpt[String]
        rows                     <- (json \ "rows").validateOpt[Int]
        spellcheck               <- (json \ "spellcheck").validateOpt[Boolean]
        value                    <- (json \ "value").validateOpt[String]
        maxLength                <- (json \ "maxlength").validateOpt[IntString]
        maxWords                 <- (json \ "maxwords").validateOpt[IntString]
        threshold                <- (json \ "threshold").validateOpt[IntString]
        countType                <- (json \ "countType").validateOpt[CountType]
        label                    <- (json \ "label").validateOpt[Label]
        hint                     <- (json \ "hint").validateOpt[Hint]
        errorMessage             <- (json \ "errorMessage").validateOpt[ErrorMessage]
        formGroup                <- (json \ "formGroup").validateOpt[FormGroup]
        attributes               <- (json \ "attributes").validateOpt[Map[String, String]](attributesReads)
        classes                  <- (json \ "classes").validateOpt[String]
        countMessageClasses      <- (json \ "countMessage" \ "classes").validateOpt[String]
        charactersUnderLimitText <- (json \ "charactersUnderLimitText").validateOpt[Map[String, String]]
        charactersAtLimitText    <- (json \ "charactersAtLimitText").validateOpt[String]
        charactersOverLimitText  <- (json \ "charactersOverLimitText").validateOpt[Map[String, String]]
        wordsUnderLimitText      <- (json \ "wordsUnderLimitText").validateOpt[Map[String, String]]
        wordsAtLimitText         <- (json \ "wordsAtLimitText").validateOpt[String]
        wordsOverLimitText       <- (json \ "wordsOverLimitText").validateOpt[Map[String, String]]
        textareaDescriptionText  <- (json \ "textareaDescriptionText").validateOpt[String]
      } yield CharacterCount(
        id = id.getOrElse(defaultObject.id),
        name = name.getOrElse(defaultObject.name),
        rows = rows.getOrElse(defaultObject.rows),
        spellcheck = spellcheck,
        value = value,
        maxLength = maxLength.map(_.int),
        maxWords = maxWords.map(_.int),
        threshold = threshold.map(_.int),
        countType = countType,
        label = label.getOrElse(defaultObject.label),
        hint = hint,
        errorMessage = errorMessage,
        formGroup = formGroup.getOrElse(defaultObject.formGroup),
        classes = classes.getOrElse(defaultObject.classes),
        attributes = attributes.getOrElse(defaultObject.attributes),
        countMessageClasses = countMessageClasses.getOrElse(defaultObject.countMessageClasses),
        charactersUnderLimitText = charactersUnderLimitText,
        charactersAtLimitText = charactersAtLimitText,
        charactersOverLimitText = charactersOverLimitText,
        wordsUnderLimitText = wordsUnderLimitText,
        wordsAtLimitText = wordsAtLimitText,
        wordsOverLimitText = wordsOverLimitText,
        textareaDescriptionText = textareaDescriptionText
      )
  }

  implicit def jsonWrites: OWrites[CharacterCount] = new OWrites[CharacterCount] {
    override def writes(cc: CharacterCount): JsObject =
      Seq(
        Some(Json.obj("id" -> cc.id)),
        Some(Json.obj("name" -> cc.name)),
        Some(Json.obj("rows" -> cc.rows)),
        cc.spellcheck.map(s => Json.obj("spellcheck" -> s)),
        cc.value.map(v => Json.obj("value" -> v)),
        cc.maxLength.map(ml => Json.obj("maxlength" -> ml)),
        cc.maxWords.map(mw => Json.obj("maxwords" -> mw)),
        cc.threshold.map(t => Json.obj("threshold" -> t)),
        cc.countType.map(ct => Json.obj("countType" -> Json.toJson(ct))),
        Some(Json.obj("label" -> Json.toJson(cc.label))),
        cc.hint.map(h => Json.obj("hint" -> Json.toJson(h))),
        cc.errorMessage.map(em => Json.obj("errorMessage" -> Json.toJson(em))),
        Some(Json.obj("formGroup" -> Json.toJson(cc.formGroup))),
        Some(Json.obj("classes" -> cc.classes)),
        Some(Json.obj("attributes" -> Json.toJson(cc.attributes))),
        Some(Json.obj("countMessage" -> Json.obj("classes" -> cc.countMessageClasses))),
        cc.charactersUnderLimitText.map(c => Json.obj("charactersUnderLimitText" -> Json.toJson(c))),
        cc.charactersAtLimitText.map(c => Json.obj("charactersAtLimitText" -> Json.toJson(c))),
        cc.charactersOverLimitText.map(c => Json.obj("charactersOverLimitText" -> Json.toJson(c))),
        cc.wordsUnderLimitText.map(w => Json.obj("wordsUnderLimitText" -> Json.toJson(w))),
        cc.wordsAtLimitText.map(w => Json.obj("wordsAtLimitText" -> Json.toJson(w))),
        cc.wordsOverLimitText.map(w => Json.obj("wordsOverLimitText" -> Json.toJson(w))),
        cc.textareaDescriptionText.map(t => Json.obj("textareaDescriptionText" -> t))
      ).flatten
        .foldLeft(Json.obj())(_ ++ _)
  }
}
