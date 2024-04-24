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

import org.scalacheck.Arbitrary.arbBool
import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Generators._

object Generators {

  implicit val arbCharacterCount: Arbitrary[CharacterCount] = Arbitrary {
    for {
      id                       <- genNonEmptyAlphaStr
      name                     <- genNonEmptyAlphaStr
      rows                     <- Gen.chooseNum(0, 5)
      spellcheck               <- Gen.option(arbBool.arbitrary)
      value                    <- Gen.option(genAlphaStr())
      maxLength                <- Gen.option(Gen.chooseNum(1, 10))
      maxWords                 <- Gen
                                    .option(Gen.chooseNum(1, 10))
                                    .retryUntil(optMaxWords => maxLength.nonEmpty || optMaxWords.nonEmpty)
      threshold                <- Gen.option(Gen.chooseNum(1, 5))
      label                    <- arbLabel.arbitrary
      hint                     <- Gen.option(arbHint.arbitrary)
      errorMessage             <- Gen.option(arbErrorMessage.arbitrary)
      formGroup                <- arbFormGroup.arbitrary
      classes                  <- genClasses()
      attributes               <- genAttributes()
      countMessageClasses      <- genNonEmptyAlphaStr
      charactersUnderLimitText <- Gen.option(genMapValues(2))
      charactersAtLimitText    <- Gen.option(genAlphaStr())
      charactersOverLimitText  <- Gen.option(genMapValues(2))
      wordsUnderLimitText      <- Gen.option(genMapValues())
      wordsAtLimitText         <- Gen.option(genAlphaStr())
      wordsOverLimitText       <- Gen.option(genMapValues(5))
      textareaDescriptionText  <- Gen.option(genNonEmptyAlphaStr)
    } yield CharacterCount(
      id = id,
      name = name,
      rows = rows,
      spellcheck = spellcheck,
      value = value,
      maxLength = maxLength,
      maxWords = maxWords,
      threshold = threshold,
      label = label,
      hint = hint,
      errorMessage = errorMessage,
      formGroup = formGroup,
      classes = classes,
      attributes = attributes,
      countMessageClasses = countMessageClasses,
      charactersUnderLimitText = charactersUnderLimitText,
      charactersAtLimitText = charactersAtLimitText,
      charactersOverLimitText = charactersOverLimitText,
      wordsUnderLimitText = wordsUnderLimitText,
      wordsAtLimitText = wordsAtLimitText,
      wordsOverLimitText = wordsOverLimitText,
      textareaDescriptionText = textareaDescriptionText
    )

  }
}
