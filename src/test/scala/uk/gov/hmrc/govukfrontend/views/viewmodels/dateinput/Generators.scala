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

package uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.Generators.arbErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.Generators.arbFieldset
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Generators.arbHint

object Generators {

  implicit val arbInputItem: Arbitrary[InputItem] = Arbitrary {
    for {
      id           <- Gen.option(genAlphaStr(5))
      name         <- genNonEmptyAlphaStr
      label        <- Gen.option(genAlphaStr())
      value        <- Gen.option(genAlphaStr())
      autocomplete <- Gen.option(genAlphaStr())
      pattern      <- Gen.option(genAlphaStr())
      classes      <- genClasses()
      attributes   <- genAttributes()
    } yield
      InputItem(
        id           = id,
        name         = name,
        label        = label,
        value        = value,
        autocomplete = autocomplete,
        pattern      = pattern,
        classes      = classes,
        attributes   = attributes)
  }

  implicit val arbDateInput: Arbitrary[DateInput] = Arbitrary {
    for {
      id               <- genNonEmptyAlphaStr
      namePrefix       <- genNonEmptyAlphaStr.map(Some(_))
      n                <- Gen.chooseNum(0, 5)
      items            <- Gen.option(Gen.listOfN(n, arbInputItem.arbitrary))
      hint             <- Gen.option(arbHint.arbitrary)
      errorMessage     <- Gen.option(arbErrorMessage.arbitrary)
      formGroupClasses <- genClasses()
      fieldset         <- Gen.option(arbFieldset.arbitrary)
      classes          <- genClasses()
      attributes       <- genAttributes()
    } yield
      DateInput(
        id               = id,
        namePrefix       = namePrefix,
        items            = items,
        hint             = hint,
        errorMessage     = errorMessage,
        formGroupClasses = formGroupClasses,
        fieldset         = fieldset,
        classes          = classes,
        attributes       = attributes
      )
  }
}
