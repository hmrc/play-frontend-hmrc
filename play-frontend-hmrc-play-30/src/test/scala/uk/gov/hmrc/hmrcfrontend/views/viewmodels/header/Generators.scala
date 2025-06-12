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
import uk.gov.hmrc.govukfrontend.views.viewmodels.tag.Tag
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.header.v2.{HeaderNames, HeaderNavigation, HeaderParams, HeaderTemplateOverrides, HeaderUrls, LogoOverrides, MenuButtonOverrides}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.hmrcstandardpage.Banners

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

  def genNavigationItems(n: Int = 15): Gen[List[NavigationItem]] = for {
    sz    <- Gen.chooseNum(0, n)
    items <- Gen.listOfN[NavigationItem](sz, arbNavigationItem.arbitrary)
  } yield items

  val arbHeaderUrls: Arbitrary[HeaderUrls] = Arbitrary {
    for {
      homepageUrl <- genAlphaStr()
      assetsPath  <- genAlphaStr()
      serviceUrl  <- genAlphaStr()
      signOutHref <- Gen.option(genAlphaStr())
    } yield HeaderUrls(
      homepageUrl = homepageUrl,
      serviceUrl = serviceUrl,
      assetsPath = assetsPath,
      signOutHref = signOutHref
    )
  }

  val arbHeaderNames: Arbitrary[HeaderNames] = Arbitrary {
    for {
      productName <- Gen.option(genAlphaStr())
      serviceName <- Gen.option(genAlphaStr())

    } yield HeaderNames(
      productName = productName,
      serviceName = serviceName
    )
  }

  val arbHeaderTemplateOverrides: Arbitrary[HeaderTemplateOverrides] = Arbitrary {
    for {
      containerClasses <- genAlphaStr()
      classes          <- genAlphaStr()
      attributes       <- genAttributes()
    } yield HeaderTemplateOverrides(
      containerClasses = containerClasses,
      classes = classes,
      attributes = attributes
    )
  }

  val arbHeaderNavigation: Arbitrary[HeaderNavigation] = Arbitrary {
    for {
      navigation        <- Gen.option(genNavigationItems())
      navigationClasses <- genAlphaStr()
      navigationLabel   <- Gen.option(genNonEmptyAlphaStr)
    } yield HeaderNavigation(
      navigation = navigation,
      navigationClasses = navigationClasses,
      navigationLabel = navigationLabel
    )
  }

  val arbBanners: Arbitrary[Banners] = Arbitrary {
    for {
      displayHmrcBanner      <- arbBool.arbitrary
      phaseBanner            <- Gen.option(arbPhaseBanner.arbitrary)
      userResearchBanner     <- Gen.option(arbUserResearchBanner.arbitrary)
      additionalBannersBlock <- Gen.option(arbHtml.arbitrary)

    } yield Banners(
      displayHmrcBanner = displayHmrcBanner,
      phaseBanner = phaseBanner,
      userResearchBanner = userResearchBanner,
      additionalBannersBlock = additionalBannersBlock
    )
  }

  val arbMenuButtonOverrides: Arbitrary[MenuButtonOverrides] = Arbitrary {
    for {
      menuButtonLabel <- Gen.option(genNonEmptyAlphaStr)
      menuButtonText  <- Gen.option(genNonEmptyAlphaStr)
    } yield MenuButtonOverrides(
      menuButtonLabel = menuButtonLabel,
      menuButtonText = menuButtonText
    )
  }

  val arbLogoOverrides: Arbitrary[LogoOverrides] = Arbitrary {
    for {
      useTudorCrown <- Gen.option(arbBool.arbitrary)
      rebrand       <- Gen.option(arbBool.arbitrary)
    } yield LogoOverrides(
      useTudorCrown = useTudorCrown,
      rebrand = rebrand
    )
  }

  implicit val arbHeaderParams: Arbitrary[HeaderParams] = Arbitrary {
    for {
      headerUrls              <- arbHeaderUrls.arbitrary
      headerNames             <- arbHeaderNames.arbitrary
      headerTemplateOverrides <- arbHeaderTemplateOverrides.arbitrary
      headerNavigation        <- arbHeaderNavigation.arbitrary
      banners                 <- arbBanners.arbitrary
      menuButtonOverrides     <- arbMenuButtonOverrides.arbitrary
      logoOverrides           <- arbLogoOverrides.arbitrary
      language                <- arbLanguage.arbitrary
      languageToggle          <- Gen.option(arbLanguageToggle.arbitrary)
    } yield HeaderParams(
      headerUrls = headerUrls,
      headerNames = headerNames,
      headerTemplateOverrides = headerTemplateOverrides,
      headerNavigation = headerNavigation,
      language = language,
      banners = banners,
      menuButtonOverrides = menuButtonOverrides,
      logoOverrides = logoOverrides,
      inputLanguageToggle = languageToggle
    )
  }

}
