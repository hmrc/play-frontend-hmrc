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

package uk.gov.hmrc.govukfrontend.views

import org.scalacheck._
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.govukfrontend.views.Utils._
import viewmodels.Generators._

class UtilsSpec extends WordSpec with Matchers with ScalaCheckPropertyChecks with ShrinkLowPriority {

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
    "return attributes provided" in {
      val html = toAttributes(Map(
        "id" -> "my-navigation",
        "role" -> "navigation"
      ))
      html.toString() shouldBe " id=\"my-navigation\" role=\"navigation\""
    }
    "return attributes in order (a)" in {
      val html = toAttributes(Map(
        "abcd" -> "first",
        "efgh" -> "second"
      ))
      html.toString() shouldBe " abcd=\"first\" efgh=\"second\""
    }
    "return attributes in order (b)" in {
      val html = toAttributes(Map(
        "efgh" -> "first",
        "abcd" -> "second"
      ))
      html.toString() shouldBe " efgh=\"first\" abcd=\"second\""
    }
    "return true for a non-empty string" in {
      val html = toAttributes(Map(
        "id" -> "my-navigation",
        "role" -> "navigation"
      ))
      html.toString() shouldBe " id=\"my-navigation\" role=\"navigation\""
    }

    "isNonEmptyOptionString" should {
      "return true for a non-empty string" in {
        isNonEmptyOptionString(Some("abc")) should be (true)
      }

      "return true for a non-empty string containing only whitespace" in {
        isNonEmptyOptionString(Some(" ")) should be (true)
      }

      "return false for an empty string" in {
        isNonEmptyOptionString(Some("")) should be (false)
      }

      "return false for None" in {
        isNonEmptyOptionString(None) should be (false)
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
}
