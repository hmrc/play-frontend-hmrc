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

package uk.gov.hmrc.govukfrontend.views

import CharacterReferenceUtils.toDecimal
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CharacterReferenceUtilsSpec extends AnyWordSpec with Matchers {

  "toDecimal" should {
    "handle empty strings" in {
      val original = ""

      toDecimal(original) shouldBe original
    }

    "leave string unchanged if it does not contain a hex character reference" in {
      val original = "this is a normal string &#123; with a decimal character reference"

      toDecimal(original) shouldBe original
    }

    "convert a hex character reference to a decimal one" in {
      val original = "&#x27;"
      val expected = "&#39;"

      toDecimal(original) shouldBe expected
    }

    "convert multiple character references" in {
      val original = "reference one: &#x27;, and another reference &#xAE;"
      val expected = "reference one: &#39;, and another reference &#174;"

      toDecimal(original) shouldBe expected
    }

    "ignore malformed character references" in {
      val original = "malformed &#; one"

      toDecimal(original) shouldBe original
    }
  }
}
