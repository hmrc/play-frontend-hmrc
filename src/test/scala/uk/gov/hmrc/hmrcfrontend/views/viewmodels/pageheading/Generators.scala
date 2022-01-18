/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.pageheading

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.Generators._

object Generators {

  implicit val arbPageHeading: Arbitrary[PageHeading] = Arbitrary {
    for {
      text    <- genAlphaStr()
      section <- Gen.option(genAlphaStr())
    } yield PageHeading(text = text, section = section)
  }
}
