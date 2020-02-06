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

package uk.gov.hmrc.govukfrontend.views.viewmodels.header

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._

object Generators {

  implicit val arbHeaderNavigation: Arbitrary[HeaderNavigation] = Arbitrary {
    for {
      text       <- Gen.option(genAlphaStr())
      href       <- Gen.option(genAlphaStr())
      active     <- Arbitrary.arbBool.arbitrary
      attributes <- genAttributes()
    } yield HeaderNavigation(text = text, href = href, active = active, attributes = attributes)
  }

  implicit val arbHeader: Arbitrary[Header] = Arbitrary {
    for {
      homepageUrl       <- Gen.option(genAlphaStr())
      productName       <- Gen.option(genAlphaStr())
      serviceName       <- Gen.option(genAlphaStr())
      serviceUrl        <- Gen.option(genAlphaStr())
      n                 <- Gen.chooseNum(0, 5)
      navigation        <- Gen.option(Gen.listOfN(n, arbHeaderNavigation.arbitrary))
      navigationClasses <- genClasses()
      containerClasses  <- Gen.option(genClasses())
      classes           <- genClasses()
      attributes        <- genAttributes()
    } yield
      Header(
        homepageUrl       = homepageUrl,
        productName       = productName,
        serviceName       = serviceName,
        serviceUrl        = serviceUrl,
        navigation        = navigation,
        navigationClasses = navigationClasses,
        containerClasses  = containerClasses,
        classes           = classes,
        attributes        = attributes
      )
  }

}
