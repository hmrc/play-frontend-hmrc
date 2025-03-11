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

package uk.gov.hmrc.govukfrontend.views.viewmodels.input

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Generators._
import org.scalacheck.Arbitrary._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators.{arbContent, arbHtmlContent}

object Generators {

  val arbPrefixOrSuffix: Arbitrary[PrefixOrSuffix] = Arbitrary {
    for {
      classes    <- genAlphaStr()
      attributes <- genAttributes()
      content    <- arbHtmlContent.arbitrary.suchThat(_.nonEmpty)
    } yield PrefixOrSuffix(
      classes = classes,
      attributes = attributes,
      content = content
    )
  }

  implicit val arbInput: Arbitrary[Input] = Arbitrary {
    for {
      id             <- Gen.option(genNonEmptyAlphaStr)
      name           <- genNonEmptyAlphaStr
      inputType      <- genNonEmptyAlphaStr
      inputMode      <- Gen.option(genNonEmptyAlphaStr)
      describedBy    <- Gen.option(genAlphaStr())
      value          <- Gen.option(genAlphaStr())
      label          <- arbLabel.arbitrary
      hint           <- Gen.option(arbHint.arbitrary)
      errorMessage   <- Gen.option(arbErrorMessage.arbitrary)
      formGroup      <- arbFormGroup.arbitrary
      classes        <- genClasses()
      autocomplete   <- Gen.option(genAlphaStr())
      pattern        <- Gen.option(genAlphaStr())
      attributes     <- genAttributes()
      spellcheck     <- Gen.option(arbBool.arbitrary)
      disabled       <- Gen.option(arbBool.arbitrary)
      autocapitalize <- Gen.option(genNonEmptyAlphaStr)
      inputWrapper   <- arbInputWrapper.arbitrary
      prefix         <- Gen.option(arbPrefixOrSuffix.arbitrary)
      suffix         <- Gen.option(arbPrefixOrSuffix.arbitrary)
    } yield Input(
      id = id,
      name = name,
      inputType = inputType,
      inputmode = inputMode,
      describedBy = describedBy,
      value = value,
      label = label,
      hint = hint,
      errorMessage = errorMessage,
      formGroup = formGroup,
      classes = classes,
      autocomplete = autocomplete,
      pattern = pattern,
      attributes = attributes,
      spellcheck = spellcheck,
      disabled = disabled,
      autocapitalize = autocapitalize,
      inputWrapper = inputWrapper,
      prefix = prefix,
      suffix = suffix
    )
  }
}
