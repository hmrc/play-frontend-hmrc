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

package uk.gov.hmrc.govukfrontend.views.viewmodels.cookiebanner

import org.scalacheck._
import org.scalacheck.Arbitrary._
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators.arbContent

object Generators {
  implicit val arbAction: Arbitrary[Action] = Arbitrary {
    for {
      text       <- genAlphaStr()
      inputType  <- Gen.option(genAlphaStr())
      href       <- Gen.option(genAlphaStr())
      name       <- Gen.option(genAlphaStr())
      value      <- Gen.option(genAlphaStr())
      classes    <- genClasses()
      attributes <- genAttributes()
    } yield Action(
      text = text,
      inputType = inputType,
      href = href,
      name = name,
      value = value,
      classes = classes,
      attributes = attributes
    )
  }

  implicit val arbMessage: Arbitrary[Message] = Arbitrary {
    for {
      heading    <- arbContent.arbitrary
      content    <- arbContent.arbitrary
      n          <- Gen.chooseNum(0, 3)
      actions    <- Gen.option(Gen.listOfN(n, arbAction.arbitrary))
      hidden     <- arbBool.arbitrary
      role       <- Gen.option(genAlphaStr())
      classes    <- genClasses()
      attributes <- genAttributes()
    } yield Message(
      heading = heading,
      content = content,
      actions = actions,
      hidden = hidden,
      role = role,
      classes = classes,
      attributes = attributes
    )
  }

  implicit val arbCookieBanner: Arbitrary[CookieBanner] = Arbitrary {
    for {
      id         <- genAlphaStr()
      classes    <- genClasses()
      attributes <- genAttributes()
      hidden     <- arbBool.arbitrary
      ariaLabel  <- Gen.option(genAlphaStr())
      n          <- Gen.chooseNum(0, 3)
      messages   <- Gen.listOfN(n, arbMessage.arbitrary)
    } yield CookieBanner(
      id = id,
      classes = classes,
      attributes = attributes,
      hidden = hidden,
      ariaLabel = ariaLabel,
      messages = messages
    )
  }
}
