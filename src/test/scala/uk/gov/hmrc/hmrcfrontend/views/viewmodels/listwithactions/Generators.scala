/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.listwithactions

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators._

object Generators {

  implicit val arbAction: Arbitrary[ListWithActionsAction] = Arbitrary {
    for {
      href               <- genNonEmptyAlphaStr
      content            <- arbContent.arbitrary
      visuallyHiddenText <- Gen.option(genAlphaStr())
      classes            <- genClasses()
    } yield ListWithActionsAction(
      href = href,
      content = content,
      visuallyHiddenText = visuallyHiddenText,
      classes = classes
    )
  }

  implicit val arbListWithActionsItem: Arbitrary[ListWithActionsItem] = Arbitrary {
    for {
      name    <- arbContent.arbitrary
      n       <- Gen.chooseNum(0, 5)
      actions <- Gen.listOfN(n, arbAction.arbitrary)
    } yield ListWithActionsItem(name = name, actions = actions)
  }

  implicit val arbListWithActions: Arbitrary[ListWithActions] = Arbitrary {
    for {
      n          <- Gen.chooseNum(0, 5)
      items      <- Gen.listOfN(n, arbListWithActionsItem.arbitrary)
      classes    <- genClasses()
      attributes <- genAttributes()
    } yield ListWithActions(items = items, classes = classes, attributes = attributes)
  }
}
