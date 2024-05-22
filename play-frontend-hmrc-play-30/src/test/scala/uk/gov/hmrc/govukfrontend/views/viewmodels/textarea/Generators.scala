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

package uk.gov.hmrc.govukfrontend.views.viewmodels.textarea

import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Arbitrary.arbBool
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Generators._

object Generators {

  implicit val arbTextarea: Arbitrary[Textarea] = Arbitrary {
    for {
      id           <- genNonEmptyAlphaStr
      name         <- genNonEmptyAlphaStr
      rows         <- Gen.chooseNum(0, 5)
      value        <- Gen.option(genAlphaStr())
      describedBy  <- Gen.option(genAlphaStr())
      label        <- arbLabel.arbitrary
      hint         <- Gen.option(arbHint.arbitrary)
      errorMessage <- Gen.option(arbErrorMessage.arbitrary)
      formGroup    <- arbFormGroup.arbitrary
      classes      <- genClasses()
      autocomplete <- Gen.option(genAlphaStr())
      attributes   <- genAttributes()
      spellcheck   <- Gen.option(arbBool.arbitrary)
      disabled     <- Gen.option(arbBool.arbitrary)
    } yield Textarea(
      id = id,
      name = name,
      rows = rows,
      spellcheck = spellcheck,
      value = value,
      describedBy = describedBy,
      label = label,
      hint = hint,
      errorMessage = errorMessage,
      formGroup = formGroup,
      classes = classes,
      autocomplete = autocomplete,
      attributes = attributes,
      disabled = disabled
    )
  }
}
