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

package uk.gov.hmrc.govukfrontend.views.viewmodels.fileupload

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Generators._
import org.scalacheck.Arbitrary.arbBool

object Generators {
  implicit val arbMultipleFilesChosenText: Arbitrary[FileUploadMultipleFilesMessages] = Arbitrary {
    for {
      one   <- genNonEmptyAlphaStr
      other <- genNonEmptyAlphaStr
    } yield FileUploadMultipleFilesMessages(
      one = one,
      other = other
    )
  }

  implicit val arbFileUpload: Arbitrary[FileUpload] = Arbitrary {
    for {
      name                    <- genNonEmptyAlphaStr
      id                      <- genNonEmptyAlphaStr
      value                   <- Gen.option(genAlphaStr())
      describedBy             <- Gen.option(genAlphaStr())
      label                   <- arbLabel.arbitrary
      hint                    <- Gen.option(arbHint.arbitrary)
      errorMessage            <- Gen.option(arbErrorMessage.arbitrary)
      formGroup               <- arbFormGroup.arbitrary
      classes                 <- genClasses()
      attributes              <- genAttributes()
      disabled                <- Gen.option(arbBool.arbitrary)
      multiple                <- Gen.option(arbBool.arbitrary)
      javascript              <- Gen.option(arbBool.arbitrary)
      chooseFilesButtonText   <- Gen.option(genAlphaStr())
      dropInstructionText     <- Gen.option(genAlphaStr())
      multipleFilesChosenText <- Gen.option(arbMultipleFilesChosenText.arbitrary)
      noFileChosenText        <- Gen.option(genAlphaStr())
      enteredDropZoneText     <- Gen.option(genAlphaStr())
      leftDropZoneText        <- Gen.option(genAlphaStr())
    } yield FileUpload(
      name = name,
      id = id,
      value = value,
      describedBy = describedBy,
      label = label,
      hint = hint,
      errorMessage = errorMessage,
      formGroup = formGroup,
      classes = classes,
      attributes = attributes,
      disabled = disabled,
      multiple = multiple,
      javascript = javascript,
      chooseFilesButtonText = chooseFilesButtonText,
      dropInstructionText = dropInstructionText,
      multipleFilesChosenText = multipleFilesChosenText,
      noFileChosenText = noFileChosenText,
      enteredDropZoneText = enteredDropZoneText,
      leftDropZoneText = leftDropZoneText
    )
  }

}
