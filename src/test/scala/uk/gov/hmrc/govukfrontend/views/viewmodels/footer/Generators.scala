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

package uk.gov.hmrc.govukfrontend.views.viewmodels.footer

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators._

object Generators {

  implicit val arbFooterItem: Arbitrary[FooterItem] = Arbitrary {
    for {
      text       <- Gen.option(genAlphaStr())
      href       <- Gen.option(genAlphaStr())
      attributes <- genAttributes()
    } yield FooterItem(text = text, href = href, attributes = attributes)
  }

  implicit val arbFooterNavigation: Arbitrary[FooterNavigation] = Arbitrary {
    for {
      title   <- Gen.option(genAlphaStr())
      columns <- Gen.option(Gen.chooseNum(0, 5))
      n       <- Gen.chooseNum(0, 5)
      items   <- Gen.option(Gen.listOfN(n, arbFooterItem.arbitrary))
    } yield FooterNavigation(title = title, columns = columns, items = items)
  }

  implicit val arbMeta: Arbitrary[Meta] = Arbitrary {
    for {
      visuallyHiddenText <- Gen.option(genAlphaStr())
      content            <- arbContent.arbitrary
      n                  <- Gen.chooseNum(0, 5)
      items              <- Gen.option(Gen.listOfN(n, arbFooterItem.arbitrary))
    } yield Meta(visuallyHiddenTitle = visuallyHiddenText, content = content, items = items)
  }

  implicit val arbFooter: Arbitrary[Footer] = Arbitrary {
    for {
      meta             <- Gen.option(arbMeta.arbitrary)
      n                <- Gen.chooseNum(0, 5)
      navigation       <- Gen.option(Gen.listOfN(n, arbFooterNavigation.arbitrary))
      containerClasses <- genClasses()
      classes          <- genClasses()
      attributes       <- genAttributes()
    } yield
      Footer(
        meta             = meta,
        navigation       = navigation,
        containerClasses = containerClasses,
        classes          = classes,
        attributes       = attributes)
  }
}
