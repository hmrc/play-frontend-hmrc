/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.newtablink

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.Generators._

object Generators {

  implicit val arbNewTabLink: Arbitrary[NewTabLink] = Arbitrary {
    for {
      text      <- genAlphaStr()
      href      <- Gen.option(genAlphaStr())
      language  <- Gen.option(genAlphaStr())
      classList <- Gen.option(genClasses())
    } yield
      NewTabLink(
        text      = text,
        href      = href,
        language  = language,
        classList = classList
      )
  }
}
