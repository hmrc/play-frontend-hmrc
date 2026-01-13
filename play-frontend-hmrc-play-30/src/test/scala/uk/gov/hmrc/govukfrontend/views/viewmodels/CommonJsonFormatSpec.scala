/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels

import org.scalacheck.{Gen, ShrinkLowPriority}
import org.scalatest.OptionValues
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.libs.json._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators.genNonEmptyAlphaStr

class CommonJsonFormatSpec
    extends AnyWordSpec
    with Matchers
    with OptionValues
    with ScalaCheckPropertyChecks
    with ShrinkLowPriority {

  "readsJsValueToString" should {
    "deserialize any value as a String" in {
      import Generators._

      val reads = (__ \ "field").read[String](readsJsValueToString)

      forAll(genJsValue) { jsValue =>
        val json             = Json.obj("field" -> jsValue)
        val asString: String = json.validate[String](reads).get

        jsValue match {
          case JsString(s) => s == asString            shouldBe true
          case x           => x.toString() == asString shouldBe true
        }
      }
    }
  }

  object Generators {
    val genJsString = for (s <- Gen.alphaStr) yield JsString(s)

    val genJsNumber = for (n <- Gen.choose(0, 1000)) yield JsNumber(n)

    val genJsValue =
      Gen.frequency(
        (50, genJsString),
        (50, genJsNumber)
      )
  }

}
