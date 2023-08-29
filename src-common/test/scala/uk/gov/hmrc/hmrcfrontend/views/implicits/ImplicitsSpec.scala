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

package uk.gov.hmrc.hmrcfrontend.views.implicits

import org.scalacheck.Arbitrary._
import org.scalacheck.{Gen, ShrinkLowPriority}
import org.scalatest.OptionValues
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._

class ImplicitsSpec
    extends AnyWordSpec
    with Matchers
    with OptionValues
    with ScalaCheckPropertyChecks
    with ShrinkLowPriority {

  import Generators._

  implicit override val generatorDrivenConfig: PropertyCheckConfiguration =
    PropertyCheckConfiguration(minSuccessful = 50)

  "padLeft" should {
    "add left padding to non-empty HTML" in {

      forAll(htmlGen, Gen.chooseNum(0, 5)) { (html, padCount) =>
        (html, padCount) match {
          case (HtmlExtractor(""), n)      => html.padLeft(n)              shouldBe HtmlFormat.empty
          case (HtmlExtractor(content), 0) => html.padLeft(0).body         shouldBe content
          case (HtmlExtractor(content), n) => html.padLeft(n).body.drop(1) shouldBe html.padLeft(n - 1).body
        }
      }

      lazy val htmlGen: Gen[Html] = Gen.alphaStr.map(Html(_))

      object HtmlExtractor {
        def unapply(html: Html): Option[String] =
          Some(html.body)
      }

    }
  }

  // prove the property:
  // indent(n) = indent(n-1).indent(1) if n >=0
  // indent(n) = indent(n+1).indent(-1) if n < 0
  "indent(n, _)" should {
    "be the same as indent(signum(n) * (abs(n)-1), _).indent(signum(n) * 1, _)" in {
      forAll(genIndentArgs) { case (s, n, indentFirstLine) =>
        s.indent(n, indentFirstLine) shouldBe s
          .indent(math.signum(n) * (math.abs(n) - 1), indentFirstLine)
          .indent(math.signum(n) * 1, indentFirstLine)
      }
    }
  }

  object Generators {

    /**
      * Generate indentation arguments for [[uk.gov.hmrc.hmrcfrontend.views.Implicits.RichString.indent(int, boolean)]]
      */
    val genIndentArgs: Gen[(String, Int, Boolean)] =
      for {
        // generate text to indent with a maximum of 8 lines
        nLines               <- Gen.choose(0, 8)
        maxInitialIndentation = 10
        // Initial indentation for each line in the generated string: generate small indentations less often so we can test unindent more often
        initialIndentations  <- Gen.listOfN(nLines, Gen.frequency((10, Gen.oneOf(0 to 3)), (90, Gen.oneOf(4 to 10))))
        // indentation for each line
        paddings              = initialIndentations.map(" " * _)
        // maxIndentation > initialIndentation so we cover cases where we try to unindent (negative n) beyond the left margin
        maxIndentation        = maxInitialIndentation + 1
        n                    <- Gen.chooseNum(-maxIndentation, maxIndentation)
        // Generate lines interspersed with blank space lines
        lineGen               = Gen.frequency((70, Gen.alphaStr), (30, Gen.const(" ")))
        padLines              = (lines: Seq[String]) => paddings.zip(lines).map { case (padding, s) => padding + s }
        str                  <- Gen.frequency(
                                  (
                                    90,
                                    Gen
                                      .listOfN(nLines, lineGen)
                                      .map(padLines)
                                      .map(_.mkString("\n"))
                                  ),
                                  (10, Gen.const(""))
                                )
        indentFirstLine      <- arbBool.arbitrary
      } yield (str, n, indentFirstLine)

  }
}
