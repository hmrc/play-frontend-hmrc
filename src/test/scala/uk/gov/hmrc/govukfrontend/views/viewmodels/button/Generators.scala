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

package uk.gov.hmrc.govukfrontend.views.viewmodels.button

import org.scalacheck.Arbitrary._
import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators.{genAlphaStr, genAttributes}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators._

object Generators {

  implicit val arbButton: Arbitrary[Button] = Arbitrary {
    for {
      element            <- Gen.option(Gen.oneOf("a", "button", "input", "foo", ""))
      name               <- Gen.option(genAlphaStr())
      inputType          <- Gen.option(genAlphaStr())
      value              <- Gen.option(genAlphaStr())
      disabled           <- arbBool.arbitrary
      href               <- Gen.option(genAlphaStr())
      classes            <- genAlphaStr()
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
