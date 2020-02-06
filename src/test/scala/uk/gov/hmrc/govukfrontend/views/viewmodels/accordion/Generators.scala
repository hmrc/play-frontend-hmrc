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

package uk.gov.hmrc.govukfrontend.views.viewmodels.accordion

import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Arbitrary._
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators._

object Generators {

  implicit val arbSection: Arbitrary[Section] = Arbitrary {
    for {
      headingContent <- arbContent.arbitrary
      summaryContent <- arbContent.arbitrary
      content        <- arbContent.arbitrary
      expanded       <- arbBool.arbitrary
    } yield
      Section(headingContent = headingContent, summaryContent = summaryContent, content = content, expanded = expanded)
  }

  implicit val arbAccordion: Arbitrary[Accordion] = Arbitrary {
    for {
      id           <- genAlphaStr()
      headingLevel <- Gen.chooseNum(1, 6)
      classes      <- genClasses()
      attributes   <- genAttributes()
      n            <- Gen.chooseNum(0, 5)
      items        <- Gen.listOfN(n, arbSection.arbitrary)
    } yield Accordion(id = id, headingLevel = headingLevel, classes = classes, attributes = attributes, items = items)
  }
}
