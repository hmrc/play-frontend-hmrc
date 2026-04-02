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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.caseworkerbanner

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators.{genAlphaStr, genAttributes, genClasses}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators.arbContent

object Generators {
  implicit val arbCaseworkerBanner: Arbitrary[CaseworkerBanner] = Arbitrary {
    for {
      content    <- arbContent.arbitrary
      titleId    <- Gen.option(genAlphaStr())
      classes    <- genClasses()
      attributes <- genAttributes()
    } yield CaseworkerBanner(
      content = content,
      titleId = titleId,
      classes = classes,
      attributes = attributes
    )
  }
}
