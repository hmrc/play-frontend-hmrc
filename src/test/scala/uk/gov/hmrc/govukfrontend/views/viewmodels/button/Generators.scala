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

package uk.gov.hmrc.govukfrontend.views.viewmodels.button

import org.scalacheck.Arbitrary._
import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators.{genAlphaStrOftenEmpty, genAttributes}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators._

object Generators {

  implicit val arbButton: Arbitrary[Button] = Arbitrary {
    for {
      element            <- Gen.option(genAlphaStrOftenEmpty())
      name               <- Gen.option(genAlphaStrOftenEmpty())
      inputType          <- Gen.option(genAlphaStrOftenEmpty())
      value              <- Gen.option(genAlphaStrOftenEmpty())
      disabled           <- arbBool.arbitrary
      href               <- Gen.option(genAlphaStrOftenEmpty())
      classes            <- genAlphaStrOftenEmpty()
      attributes         <- genAttributes()
      preventDoubleClick <- arbBool.arbitrary
      isStartButton      <- arbBool.arbitrary
      content            <- arbContent.arbitrary
    } yield
      Button(
        element            = element,
        name               = name,
        inputType          = inputType,
        value              = value,
        disabled           = disabled,
        href               = href,
        classes            = classes,
        attributes         = attributes,
        preventDoubleClick = preventDoubleClick,
        isStartButton      = isStartButton,
        content            = content
      )
  }
}
