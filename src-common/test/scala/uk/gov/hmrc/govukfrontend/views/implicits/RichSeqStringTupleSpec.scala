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

package uk.gov.hmrc.govukfrontend.views.implicits

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text
import uk.gov.hmrc.govukfrontend.views.html.components.implicits._
import uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes.CheckboxItem
import uk.gov.hmrc.govukfrontend.views.viewmodels.radios.RadioItem

class RichSeqStringTupleSpec extends AnyWordSpec with Matchers {

  "Given a sequence of String tuples, Seq[(String, String)], calling toRadioItems" should {
    "convert to a sequence of RadioItem" in {
      val valuesAsStrings: Seq[(String, String)] = Seq(
        ("key-1", "some-first-radio"),
        ("key-2", "some-second-radio"),
        ("key-3", "some-third-radio")
      )

      valuesAsStrings.toRadioItems shouldBe Seq(
        RadioItem(content = Text("some-first-radio"), value = Some("key-1")),
        RadioItem(content = Text("some-second-radio"), value = Some("key-2")),
        RadioItem(content = Text("some-third-radio"), value = Some("key-3"))
      )
    }
  }

  "Given a sequence of String tuples, Seq[(String, String)], calling toCheckboxItems" should {
    "convert to a CheckboxItems" in {
      val valuesAsStrings: Seq[(String, String)] = Seq(
        ("key-1", "some-first-checkbox"),
        ("key-2", "some-second-checkbox"),
        ("key-3", "some-third-checkbox")
      )

      valuesAsStrings.toCheckboxItems shouldBe Seq(
        CheckboxItem(content = Text("some-first-checkbox"), value = "key-1"),
        CheckboxItem(content = Text("some-second-checkbox"), value = "key-2"),
        CheckboxItem(content = Text("some-third-checkbox"), value = "key-3")
      )
    }
  }
}
