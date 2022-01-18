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

package uk.gov.hmrc.govukfrontend.views.implicits

import org.scalacheck.{Gen, ShrinkLowPriority}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.govukfrontend.views.html.components.implicits._

class RichHtmlSpec extends AnyWordSpec with Matchers with ScalaCheckPropertyChecks with ShrinkLowPriority {

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
}
