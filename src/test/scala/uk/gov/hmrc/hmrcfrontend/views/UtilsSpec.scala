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

package uk.gov.hmrc.hmrcfrontend.views

import org.scalacheck._
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.hmrcfrontend.views.Utils._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.Generators._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class UtilsSpec extends AnyWordSpec with Matchers with ScalaCheckPropertyChecks with ShrinkLowPriority {

  implicit override val generatorDrivenConfig: PropertyCheckConfiguration =
    PropertyCheckConfiguration(minSuccessful = 50)

  "toClasses" should {
    "concatenate existing classes with classes with a single whitespace separator if classes are not empty" in {
      forAll(Gen.alphaStr.suchThat(_.trim.nonEmpty), Gen.alphaStr) { (existingClass, classes) =>
        (existingClass, classes) match {
          case (_, "") => toClasses(existingClass, classes) shouldBe existingClass
          case _       => toClasses(existingClass, classes) shouldBe s"$existingClass $classes"
        }
      }
    }
  }

  "toAttributes" should {
    "generate attributes HTML with the required padding" in {
      forAll(genAttributes(), Gen.chooseNum(0, 5)) { (attrsMap, padCount) =>
        (attrsMap.toSeq, padCount) match {
          case (Nil, _) => toAttributes(attrsMap, padCount)                 shouldBe HtmlFormat.empty
          case (_, 0)   => toAttributes(attrsToMap(toAttributes(attrsMap))) shouldBe toAttributes(attrsMap)
          case (_, n)   => toAttributes(attrsMap, n).body.drop(1)           shouldBe toAttributes(attrsMap, n - 1).body
        }
      }
    }

    def attrsToMap(html: Html): Map[String, String] = {
      val attrs: List[List[String]] =
        html.body.trim.split(" ").toList.map(_.split("=").toList)
      attrs.map {
        case attr :: value :: _ => attr -> value.replace("\"", "")
        case x                  => throw new IllegalArgumentException(s"expecting list of 2, instead got: $x")
      }.toMap
    }
  }

  "urlEncode" should {
    "should encode spaces with the old-style longhand" in {
      urlEncode("ABC DEF") shouldBe "ABC%20DEF"
    }
    "should encode URLs as expected" in {
      urlEncode(
        "https://www.tax.service.gov.uk/pay?abc=def&ghi=jkl"
      ) shouldBe "https%3A%2F%2Fwww.tax.service.gov.uk%2Fpay%3Fabc%3Ddef%26ghi%3Djkl"
    }
  }

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

  "calculateAssetPath" should {
    "use the path if provided" in {
      calculateAssetPath(Some("/foo"), "images/bar.png") shouldBe "/foo/images/bar.png"
    }

    "use the reverse router if path is not provided" in {
      hmrcfrontend.RoutesPrefix.setPrefix("/some-service/govuk-frontend")

      calculateAssetPath(None, "images/baz.png") shouldBe "/some-service/govuk-frontend/assets/images/baz.png"
    }
  }
}
