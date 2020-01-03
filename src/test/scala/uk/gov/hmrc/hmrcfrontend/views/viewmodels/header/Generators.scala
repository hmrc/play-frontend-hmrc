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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.header

import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Arbitrary.arbBool
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.Generators._

object Generators {

  implicit val arbNavigationItem: Arbitrary[NavigationItem] = Arbitrary {
    for {
      text       <- genAlphaStr()
      href       <- genAlphaStr()
      active     <- arbBool.arbitrary
      attributes <- genAttributes()
    } yield NavigationItem(text = text, href = href, active = active, attributes = attributes)
  }

  def genNavigationItems(n: Int = 5): Gen[List[NavigationItem]] = for {
    sz    <- Gen.chooseNum(0, n)
    items <- Gen.listOfN[NavigationItem](sz, arbNavigationItem.arbitrary)
  } yield items

  implicit val arbHeader: Arbitrary[Header] = Arbitrary {
    for {
      homepageUrl       <- genAlphaStr()
      assetsPath        <- genAlphaStr()
      productName       <- Gen.option(genAlphaStr())
      serviceName       <- Gen.option(genAlphaStr())
      serviceUrl        <- genAlphaStr()
      navigation        <- genNavigationItems()
      navigationClasses <- genAlphaStr()
      containerClasses  <- genAlphaStr()
      classes           <- genAlphaStr()
      attributes        <- genAttributes()
      language          <- arbLanguage.arbitrary
      displayHmrcBanner <- arbBool.arbitrary
      signOutHref       <- Gen.option(genAlphaStr())
      languageToggle    <- Gen.option(arbLanguageToggle.arbitrary)
    } yield Header(homepageUrl = homepageUrl, assetsPath = assetsPath, productName = productName, serviceName = serviceName, serviceUrl = serviceUrl, navigation = navigation, navigationClasses = navigationClasses, containerClasses = containerClasses, classes = classes, attributes = attributes, language = language, displayHmrcBanner = displayHmrcBanner , signOutHref = signOutHref, inputLanguageToggle = languageToggle)
  }

}
