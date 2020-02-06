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

package uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators._

object Generators {

  implicit val arbActionItem: Arbitrary[ActionItem] = Arbitrary {
    for {
      href               <- genNonEmptyAlphaStr
      content            <- arbContent.arbitrary
      visuallyHiddenText <- Gen.option(genAlphaStr())
      classes            <- genClasses()
    } yield ActionItem(href = href, content = content, visuallyHiddenText = visuallyHiddenText, classes = classes)
  }

  implicit val arbActions: Arbitrary[Actions] = Arbitrary {
    for {
      classes <- genClasses()
      n       <- Gen.chooseNum(0, 5)
      items   <- Gen.listOfN(n, arbActionItem.arbitrary)
    } yield Actions(classes = classes, items = items)
  }

  implicit val arbKey: Arbitrary[Key] = Arbitrary {
    for {
      content <- arbContent.arbitrary
      classes <- genClasses()
    } yield Key(content = content, classes = classes)
  }

  implicit val arbValue: Arbitrary[Value] = Arbitrary {
    for {
      content <- arbContent.arbitrary
      classes <- genClasses()
    } yield Value(content = content, classes = classes)
  }

  implicit val arbSummaryListRow: Arbitrary[SummaryListRow] = Arbitrary {
    for {
      key     <- arbKey.arbitrary
      value   <- arbValue.arbitrary
      classes <- genClasses()
      actions <- Gen.option(arbActions.arbitrary)
    } yield SummaryListRow(key = key, value = value, classes = classes, actions = actions)
  }

  implicit val arbSummaryList: Arbitrary[SummaryList] = Arbitrary {
    for {
      n          <- Gen.chooseNum(0, 5)
      rows       <- Gen.listOfN(n, arbSummaryListRow.arbitrary)
      classes    <- genClasses()
      attributes <- genAttributes()
    } yield SummaryList(rows = rows, classes = classes, attributes = attributes)
  }

}
