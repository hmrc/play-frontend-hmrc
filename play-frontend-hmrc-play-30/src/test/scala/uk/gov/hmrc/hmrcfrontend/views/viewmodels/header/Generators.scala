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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.header

import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Arbitrary.arbBool
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.content.Generators._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.Generators._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.userresearchbanner.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.phasebanner.PhaseBanner
import uk.gov.hmrc.govukfrontend.views.viewmodels.servicenavigation.{ServiceNavigation, ServiceNavigationItem, ServiceNavigationSlot}
import uk.gov.hmrc.govukfrontend.views.viewmodels.tag.Tag

object Generators {

  implicit val arbTag: Arbitrary[Tag] = Arbitrary {
    for {
      content    <- arbContent.arbitrary
      classes    <- genClasses()
      attributes <- genAttributes()
    } yield Tag(content = content, classes = classes, attributes = attributes)
  }

  implicit val arbPhaseBanner: Arbitrary[PhaseBanner] = Arbitrary {
    for {
      tag        <- Gen.option(arbTag.arbitrary)
      classes    <- genClasses()
      attributes <- genAttributes()
      content    <- arbContent.arbitrary
    } yield PhaseBanner(tag = tag, classes = classes, attributes = attributes, content = content)
  }

  implicit val arbNavigationItem: Arbitrary[NavigationItem] = Arbitrary {
    for {
      href       <- Gen.option(genAlphaStr())
      active     <- arbBool.arbitrary
      attributes <- genAttributes()
      content    <- arbContent.arbitrary
    } yield NavigationItem(href = href, active = active, attributes = attributes, content = content)
  }

  implicit val arbServiceNavigationItem: Arbitrary[ServiceNavigationItem] = Arbitrary {
    for {
      content    <- arbContent.arbitrary
      href       <- genAlphaStr()
      active     <- arbBool.arbitrary
      current    <- arbBool.arbitrary
      classes    <- genAlphaStr()
      attributes <- genAttributes()
    } yield ServiceNavigationItem(
      content = content,
      href = href,
      active = active,
      current = current,
      classes = classes,
      attributes = attributes
    )
  }

  implicit val arbServiceNavigationSlot: Arbitrary[ServiceNavigationSlot] = Arbitrary {
    for {
      start           <- Gen.option(genAlphaStr())
      end             <- Gen.option(genAlphaStr())
      navigationStart <- Gen.option(genAlphaStr())
      navigationEnd   <- Gen.option(genAlphaStr())
    } yield ServiceNavigationSlot(
      start = start,
      end = end,
      navigationStart = navigationStart,
      navigationEnd = navigationEnd
    )
  }

  implicit val arbServiceNavigation: Arbitrary[ServiceNavigation] = Arbitrary {
    for {
      serviceName       <- Gen.option(genAlphaStr())
      serviceUrl        <- Gen.option(genAlphaStr())
      navigation        <- genElemSeq(arbServiceNavigationItem)
      navigationClasses <- genClasses()
      navigationId      <- genAlphaStr()
      navigationLabel   <- Gen.option(genAlphaStr())
      classes           <- genClasses()
      attributes        <- genAttributes()
      ariaLabel         <- Gen.option(genAlphaStr())
      menuButtonText    <- Gen.option(genAlphaStr())
      menuButtonLabel   <- Gen.option(genAlphaStr())
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

  def genNavigationItems(n: Int = 15): Gen[List[NavigationItem]] = for {
    sz    <- Gen.chooseNum(0, n)
    items <- Gen.listOfN[NavigationItem](sz, arbNavigationItem.arbitrary)
  } yield items

  implicit val arbHeader: Arbitrary[Header] = Arbitrary {
    for {
      homepageUrl            <- genAlphaStr()
      assetsPath             <- genAlphaStr()
      productName            <- Gen.option(genAlphaStr())
      serviceName            <- Gen.option(genAlphaStr())
      serviceUrl             <- genAlphaStr()
      navigation             <- Gen.option(genNavigationItems())
      navigationClasses      <- genAlphaStr()
      containerClasses       <- genAlphaStr()
      classes                <- genAlphaStr()
      attributes             <- genAttributes()
      language               <- arbLanguage.arbitrary
      displayHmrcBanner      <- arbBool.arbitrary
      useTudorCrown          <- Gen.option(arbBool.arbitrary)
      signOutHref            <- Gen.option(genAlphaStr())
      languageToggle         <- Gen.option(arbLanguageToggle.arbitrary)
      phaseBanner            <- Gen.option(arbPhaseBanner.arbitrary)
      userResearchBanner     <- Gen.option(arbUserResearchBanner.arbitrary)
      additionalBannersBlock <- Gen.option(arbHtml.arbitrary)
      menuButtonLabel        <- Gen.option(genNonEmptyAlphaStr)
      navigationLabel        <- Gen.option(genNonEmptyAlphaStr)
      menuButtonText         <- Gen.option(genNonEmptyAlphaStr)
      serviceNavigation      <- Gen.option(arbServiceNavigation.arbitrary)
    } yield Header(
      homepageUrl = homepageUrl,
      assetsPath = assetsPath,
      productName = productName,
      serviceName = serviceName,
      serviceUrl = serviceUrl,
      navigation = navigation,
      navigationClasses = navigationClasses,
      containerClasses = containerClasses,
      classes = classes,
      attributes = attributes,
      language = language,
      displayHmrcBanner = displayHmrcBanner,
      useTudorCrown = useTudorCrown,
      signOutHref = signOutHref,
      inputLanguageToggle = languageToggle,
      phaseBanner = phaseBanner,
      userResearchBanner = userResearchBanner,
      additionalBannersBlock = additionalBannersBlock,
      navigationLabel = navigationLabel,
      menuButtonText = menuButtonText,
      menuButtonLabel = menuButtonLabel,
      serviceNavigation = serviceNavigation
    )
  }
}
