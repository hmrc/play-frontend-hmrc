/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels.servicenavigation

import org.scalacheck.Arbitrary.arbBool
import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.Generators.arbErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.Generators.arbFieldset
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Generators.arbHint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Generators.arbLabel
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators._

object Generators {
  implicit val arbServiceNavigationSlot: Arbitrary[ServiceNavigationSlot] = Arbitrary {
    for {
      start           <- genNonEmptyAlphaStr
      end             <- genNonEmptyAlphaStr
      navigationStart <- genNonEmptyAlphaStr
      navigationEnd   <- genNonEmptyAlphaStr
    } yield ServiceNavigationSlot.apply(
      start = start,
      end = end,
      navigationStart = navigationStart,
      navigationEnd = navigationEnd
    )
  }

  implicit val arbServiceNavigationItem: Arbitrary[ServiceNavigationItem] = Arbitrary {
    for {
      href       <- genNonEmptyAlphaStr
      text       <- genNonEmptyAlphaStr
      html       <- Gen.option(genNonEmptyAlphaStr)
      active     <- arbBool.arbitrary
      current    <- arbBool.arbitrary
      classes    <- genClasses()
      attributes <- genAttributes()
    } yield ServiceNavigationItem.apply(
      href = href,
      text = text,
      active = active,
      current = current,
      classes = classes,
      attributes = attributes
    )
  }

  implicit val arbServiceNavigation: Arbitrary[ServiceNavigation] = Arbitrary {
    for {
      serviceName       <- Gen.option(genNonEmptyAlphaStr)
      serviceUrl        <- Gen.option(genNonEmptyAlphaStr)
      n                 <- Gen.chooseNum(0, 5)
      navigation        <- Gen.listOfN(n, arbServiceNavigationItem.arbitrary)
      navigationClasses <- genClasses()
      navigationId      <- genNonEmptyAlphaStr
      navigationLabel   <- Gen.option(genNonEmptyAlphaStr)
      classes           <- genClasses()
      attributes        <- genAttributes()
      ariaLabel         <- genNonEmptyAlphaStr
      menuButtonText    <- genNonEmptyAlphaStr
      menuButtonLabel   <- Gen.option(genNonEmptyAlphaStr)
      slots             <- Gen.option(arbServiceNavigationSlot.arbitrary)
    } yield ServiceNavigation(
      serviceName = serviceName,
      serviceUrl = serviceUrl,
      navigation = navigation,
      navigationClasses = navigationClasses,
      navigationId = navigationId,
      navigationLabel = navigationLabel,
      classes = classes,
      attributes = attributes,
      ariaLabel = ariaLabel,
      menuButtonText = menuButtonText,
      menuButtonLabel = menuButtonLabel,
      slots = slots
    )
  }
}
