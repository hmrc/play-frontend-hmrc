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

package uk.gov.hmrc.govukfrontend.views.viewmodels.radios

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Generators._

object Generators {

  implicit val arbRadioItem: Arbitrary[RadioItem] = Arbitrary {
    for {
      content         <- arbContent.arbitrary
      id              <- Gen.option(genAlphaStr())
      value           <- Gen.option(genAlphaStr())
      label           <- Gen.option(arbLabel.arbitrary)
      hint            <- Gen.option(arbHint.arbitrary)
      divider         <- Gen.option(genAlphaStr())
      checked         <- Arbitrary.arbBool.arbitrary
      conditionalHtml <- Gen.option(arbHtml.arbitrary)
      disabled        <- Arbitrary.arbBool.arbitrary
      attributes      <- genAttributes()
    } yield
      RadioItem(
        content         = content,
        id              = id,
        value           = value,
        label           = label,
        hint            = hint,
        divider         = divider,
        checked         = checked,
        conditionalHtml = conditionalHtml,
        disabled        = disabled,
        attributes      = attributes
      )
  }

  implicit val arbRadios: Arbitrary[Radios] = Arbitrary {
    for {
      fieldset         <- Gen.option(arbFieldset.arbitrary)
      hint             <- Gen.option(arbHint.arbitrary)
      errorMessage     <- Gen.option(arbErrorMessage.arbitrary)
      formGroupClasses <- genClasses()
      idPrefix         <- Gen.option(genAlphaStr())
      name             <- genAlphaStr()
      n                <- Gen.chooseNum(0, 5)
      items            <- Gen.listOfN(n, arbRadioItem.arbitrary)
      classes          <- genClasses()
      attributes       <- genAttributes()
    } yield
      Radios(
        fieldset         = fieldset,
        hint             = hint,
        errorMessage     = errorMessage,
        formGroupClasses = formGroupClasses,
        idPrefix         = idPrefix,
        name             = name,
        items            = items,
        classes          = classes,
        attributes       = attributes
      )
  }

}
