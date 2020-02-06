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

package uk.gov.hmrc.govukfrontend.views.viewmodels.breadcrumbs

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators.{genAlphaStr, genAttributes}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators._

object Generators {

  implicit val arbBreadcrumbsItem: Arbitrary[BreadcrumbsItem] = Arbitrary {
    for {
      content    <- arbContent.arbitrary
      href       <- Gen.option(genAlphaStr())
      attributes <- genAttributes()
    } yield BreadcrumbsItem(content = content, href = href, attributes = attributes)
  }

  implicit val arbBreadcrumbs: Arbitrary[Breadcrumbs] = Arbitrary {
    for {
      n          <- Gen.chooseNum(0, 5)
      items      <- Gen.listOfN(n, arbBreadcrumbsItem.arbitrary)
      classes    <- genAlphaStr()
      attributes <- genAttributes()
    } yield Breadcrumbs(items = items, classes = classes, attributes = attributes)
  }
}
