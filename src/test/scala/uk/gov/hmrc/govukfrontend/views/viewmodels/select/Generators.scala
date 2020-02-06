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

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Generators._

object Generators {

  implicit val arbSelectItem: Arbitrary[SelectItem] = Arbitrary {
    for {
      value      <- Gen.option(genAlphaStr())
      text       <- genAlphaStr()
      selected   <- Arbitrary.arbBool.arbitrary
      disabled   <- Arbitrary.arbBool.arbitrary
      attributes <- genAttributes()
    } yield SelectItem(value = value, text = text, selected = selected, disabled = disabled, attributes = attributes)
  }

  implicit val arbSelect: Arbitrary[Select] = Arbitrary {
    for {
      id               <- genNonEmptyAlphaStr
      name             <- genNonEmptyAlphaStr
      n                <- Gen.chooseNum(0, 5)
      items            <- Gen.listOfN(n, arbSelectItem.arbitrary)
      describedBy      <- Gen.option(genAlphaStr())
      label            <- arbLabel.arbitrary
      hint             <- Gen.option(arbHint.arbitrary)
      errorMessage     <- Gen.option(arbErrorMessage.arbitrary)
      formGroupClasses <- genClasses()
      classes          <- genClasses()
      attributes       <- genAttributes()
    } yield
      Select(
        id               = id,
        name             = name,
        items            = items,
        describedBy      = describedBy,
        label            = label,
        hint             = hint,
        errorMessage     = errorMessage,
        formGroupClasses = formGroupClasses,
        classes          = classes,
        attributes       = attributes
      )
  }
}
