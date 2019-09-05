/*
 * Copyright 2019 HM Revenue & Customs
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
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, WordSpec}
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.govukfrontend.views.Utils._

class UtilsSpec extends WordSpec with Matchers with PropertyChecks with NoShrink {

  import Generators._

  implicit override val generatorDrivenConfig: UtilsSpec.this.PropertyCheckConfiguration =
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
      forAll(attrsMapGen, Gen.chooseNum(0, 5)) { (attrsMap, padCount) =>
        (attrsMap.toSeq, padCount) match {
          case (Nil, _) => toAttributes(attrsMap, padCount)                 shouldBe HtmlFormat.empty
          case (_, 0)   => toAttributes(attrsToMap(toAttributes(attrsMap))) shouldBe toAttributes(attrsMap)
          case (_, n)   => toAttributes(attrsMap, n).body.drop(1)           shouldBe toAttributes(attrsMap, n - 1).body
        }
      }
    }

    def attrsToMap(html: Html): Map[String, String] = {
      val attrs: List[List[String]] = html.body.trim.split(" ").toList.map(_.split("=").toList)
      attrs.map {
        case attr :: value :: _ => attr -> value.replace("\"", "")
        case x                  => throw new IllegalArgumentException(s"expecting list of 2, instead got: $x")
      }.toMap
    }
  }

  "padLeft" should {
    "add left padding to non-empty HTML" in {
      forAll(htmlGen, Gen.chooseNum(0, 5)) { (html, padCount) =>
        (html, padCount) match {
          case (HtmlExtractor(""), n)      => html.padLeft(n)              shouldBe HtmlFormat.empty
          case (HtmlExtractor(content), 0) => html.padLeft(0).body         shouldBe content
          case (HtmlExtractor(content), n) => html.padLeft(n).body.drop(1) shouldBe html.padLeft(n - 1).body
        }
      }
    }
  }

  object Generators {
    val attrValGen: Gen[(String, String)] = for {
      attr  <- Gen.alphaStr.suchThat(_.trim.nonEmpty)
      value <- Gen.alphaStr.suchThat(_.trim.nonEmpty)
    } yield (attr, value)

    val attrsMapGen: Gen[Map[String, String]] = for {
      sz         <- Gen.choose(0, 10)
      attributes <- Gen.mapOfN[String, String](sz, attrValGen)
    } yield attributes

    val htmlGen: Gen[Html] = Gen.alphaStr.map(Html(_))
  }

  object HtmlExtractor {
    def unapply(html: Html): Option[String] =
      Some(html.body)
  }
}
