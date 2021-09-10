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

package uk.gov.hmrc.govukfrontend.views

import org.scalacheck._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import uk.gov.hmrc.govukfrontend.views.Utils._

class UtilsSpec extends AnyWordSpec with Matchers with ScalaCheckPropertyChecks with ShrinkLowPriority {

  "isNonEmptyOptionString" should {
    "return true for a non-empty string" in {
      isNonEmptyOptionString(Some("abc")) should be(true)
    }

    "return true for a non-empty string containing only whitespace" in {
      isNonEmptyOptionString(Some(" ")) should be(true)
    }

    "return false for an empty string" in {
      isNonEmptyOptionString(Some("")) should be(false)
    }

    "return false for None" in {
      isNonEmptyOptionString(None) should be(false)
    }
  }

}
