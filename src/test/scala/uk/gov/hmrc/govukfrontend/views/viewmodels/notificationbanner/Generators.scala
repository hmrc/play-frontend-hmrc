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

package uk.gov.hmrc.govukfrontend.views.viewmodels.notificationbanner

import org.scalacheck.Arbitrary.arbBool
import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators.{genAlphaStr, genAttributes, genClasses}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators.arbContent

object Generators {
  implicit val arbNotificationBanner: Arbitrary[NotificationBanner] = Arbitrary {
    for {
      content           <- arbContent.arbitrary
      bannerType        <- Gen.option(genAlphaStr())
      role              <- Gen.option(genAlphaStr())
      title             <- arbContent.arbitrary
      titleId           <- Gen.option(genAlphaStr())
      disableAutoFocus  <- Gen.option(arbBool.arbitrary)
      classes           <- genClasses()
      titleHeadingLevel <- Gen.option(Gen.chooseNum(1, 6))
      attributes        <- genAttributes()
    } yield NotificationBanner(
      content = content,
      bannerType = bannerType,
      role = role,
      title = title,
      titleId = titleId,
      disableAutoFocus = disableAutoFocus,
      titleHeadingLevel = titleHeadingLevel,
      classes = classes,
      attributes = attributes
    )
  }
}
