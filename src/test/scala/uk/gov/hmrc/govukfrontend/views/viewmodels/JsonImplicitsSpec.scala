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

package uk.gov.hmrc.govukfrontend.views.viewmodels

import org.scalacheck.{Gen, ShrinkLowPriority}
import org.scalatest.{Matchers, OptionValues, WordSpec}
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.libs.json._
import JsonImplicits._

class JsonImplicitsSpec
    extends WordSpec
    with Matchers
    with OptionValues
    with ScalaCheckPropertyChecks
    with ShrinkLowPriority {

  "readsJsValueToString" should {
    "deserialize any value as a String" in {
      import Generators._

      val reads = (__ \ "field").readsJsValueToString

      forAll(genJsValue) { jsValue =>
        val json = Json.obj("field" -> jsValue)

        json.validate[String](reads).asOpt.value shouldBe (jsValue match {
          case JsString(s) => s
          case x           => x.toString
        })
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
