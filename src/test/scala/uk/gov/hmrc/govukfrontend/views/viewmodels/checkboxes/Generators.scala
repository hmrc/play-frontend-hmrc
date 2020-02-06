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

package uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes

import org.scalacheck.Arbitrary.arbBool
import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.Generators.arbErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.Generators.arbFieldset
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Generators.arbHint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Generators.arbLabel
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators._

object Generators {

  implicit val arbCheckboxItem: Arbitrary[CheckboxItem] = Arbitrary {
    for {
      content         <- arbContent.arbitrary
      id              <- Gen.option(genAlphaStr())
      name            <- Gen.option(genAlphaStr())
      value           <- genAlphaStr()
      label           <- Gen.option(arbLabel.arbitrary)
      hint            <- Gen.option(arbHint.arbitrary)
      checked         <- arbBool.arbitrary
      conditionalHtml <- Gen.option(arbHtml.arbitrary)
      disabled        <- arbBool.arbitrary
      attributes      <- genAttributes()
    } yield
      CheckboxItem(
        content         = content,
        id              = id,
        name            = name,
        value           = value,
        label           = label,
        hint            = hint,
        checked         = checked,
        conditionalHtml = conditionalHtml,
        disabled        = disabled,
        attributes      = attributes
      )
  }

  implicit val arbCheckboxes: Arbitrary[Checkboxes] = Arbitrary {
    for {
      describedBy        <- Gen.option(genAlphaStr())
      fieldsetParams     <- Gen.option(arbFieldset.arbitrary)
      hintParams         <- Gen.option(arbHint.arbitrary)
      errorMessageParams <- Gen.option(arbErrorMessage.arbitrary)
      formGroupClasses   <- genClasses()
      idPrefix           <- Gen.option(genAlphaStr())
      name               <- genNonEmptyAlphaStr
      nItems             <- Gen.chooseNum(0, 5)
      items              <- Gen.listOfN(nItems, arbCheckboxItem.arbitrary)
      classes            <- genClasses()
      attributes         <- genAttributes()
    } yield
      Checkboxes(
        describedBy      = describedBy,
        fieldset         = fieldsetParams,
        hint             = hintParams,
        errorMessage     = errorMessageParams,
        formGroupClasses = formGroupClasses,
        idPrefix         = idPrefix,
        name             = name,
        items            = items,
        classes          = classes,
        attributes       = attributes
      )
  }

}
