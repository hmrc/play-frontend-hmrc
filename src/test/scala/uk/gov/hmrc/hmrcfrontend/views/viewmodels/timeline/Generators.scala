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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.timeline

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.timeline.Event
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.Generators.{genAlphaStr, genPositiveNumber}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.Generators.arbLanguage

object Generators {
  implicit val arbTimeline: Arbitrary[Timeline] = Arbitrary {
    for {
      headingLevel <- Gen.choose(1, 8)
      events       <- genEvents()
    } yield Timeline(
      headingLevel,
      events = events
    )
  }

  implicit val arbEvent: Arbitrary[Event]    = Arbitrary {
    for {
      title    <- genAlphaStr()
      time     <- genAlphaStr()
      content  <- genAlphaStr()
      datetime <- Gen.option(genAlphaStr())
    } yield Event(title = title, time = time, content = content, datetime = datetime)
  }
  def genEvents(n: Int = 5): Gen[Seq[Event]] = for {
    sz    <- Gen.chooseNum(0, n)
    items <- Gen.listOfN[Event](sz, arbEvent.arbitrary)
  } yield items
}
