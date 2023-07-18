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

package uk.gov.hmrc.govukfrontend.views.viewmodels.exitthispage

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators.{genAttributes, genClasses, genNonEmptyAlphaStr}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators.arbContent

object Generators {
  implicit val arbExitThisPage: Arbitrary[ExitThisPage] = Arbitrary {
    for {
      content               <- arbContent.arbitrary
      redirectUrl           <- Gen.option(genNonEmptyAlphaStr)
      id                    <- Gen.option(genNonEmptyAlphaStr)
      classes               <- genClasses()
      attributes            <- genAttributes()
      activatedText         <- Gen.option(genNonEmptyAlphaStr)
      timedOutText          <- Gen.option(genNonEmptyAlphaStr)
      pressTwoMoreTimesText <- Gen.option(genNonEmptyAlphaStr)
      pressOneMoreTimeText  <- Gen.option(genNonEmptyAlphaStr)

    } yield ExitThisPage(
      content = content,
      redirectUrl = redirectUrl,
      id = id,
      classes = classes,
      attributes = attributes,
      activatedText = activatedText,
      timedOutText = timedOutText,
      pressTwoMoreTimesText = pressTwoMoreTimesText,
      pressOneMoreTimeText = pressOneMoreTimeText
    )
  }
}
