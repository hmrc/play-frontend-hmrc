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

package uk.gov.hmrc.govukfrontend.views.viewmodels.tabs

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators._

object Generators {

  implicit val arbTabPanel: Arbitrary[TabPanel] = Arbitrary {
    for {
      content    <- arbContent.arbitrary
      attributes <- genAttributes()
    } yield TabPanel(content = content, attributes = attributes)
  }

  implicit val arbTabItem: Arbitrary[TabItem] = Arbitrary {
    for {
      id         <- Gen.option(genNonEmptyAlphaStr)
      label      <- genAlphaStr()
      attributes <- genAttributes()
      panel      <- arbTabPanel.arbitrary
    } yield TabItem(id = id, label = label, attributes = attributes, panel = panel)
  }

  implicit val arbTabs: Arbitrary[Tabs] = Arbitrary {
    for {
      id         <- Gen.option(genAlphaStr())
      idPrefix   <- Gen.option(genAlphaStr())
      title      <- genAlphaStr()
      n          <- Gen.chooseNum(0, 5)
      items      <- Gen.option(Gen.listOfN(n, arbTabItem.arbitrary))
      classes    <- genClasses()
      attributes <- genAttributes()
    } yield Tabs(id = id, idPrefix = idPrefix, title = title, items = items, classes = classes, attributes = attributes)
  }
}
