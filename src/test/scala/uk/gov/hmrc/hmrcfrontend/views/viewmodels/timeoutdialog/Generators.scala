/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.timeoutdialog

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.Generators._

object Generators {

  implicit val arbTimeoutDialog: Arbitrary[TimeoutDialog] = Arbitrary {
    for {
      name                <- genAlphaStr()
      timeout             <- Gen.option(Gen.nu)
      countdown           <- Gen.option(genAlphaStr())
      keepAliveUrl        <- Gen.option(genAlphaStr())
      signOutUrl          <- Gen.option(genAlphaStr())
      title               <- Gen.option(genAlphaStr())
      message             <- Gen.option(genAlphaStr())
      messageSuffix       <- Gen.option(genAlphaStr())
      keepAliveButtonText <- Gen.option(genAlphaStr())
      signOutButtonText   <- Gen.option(genAlphaStr())
    } yield
      TimeoutDialog(
        name                = name,
        timeout             = timeout,
        countdown           = countdown,
        keepAliveUrl        = keepAliveUrl,
        signOutUrl          = signOutUrl,
        title               = title,
        message             = message,
        messageSuffix       = messageSuffix,
        keepAliveButtonText = keepAliveButtonText,
        signOutButtonText   = signOutButtonText
      )
  }
}
