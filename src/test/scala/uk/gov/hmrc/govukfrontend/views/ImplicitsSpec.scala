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

import org.scalacheck.{Gen, ShrinkLowPriority}
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.data.FormError
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.govukfrontend.views.html.components.implicits._
import uk.gov.hmrc.govukfrontend.views.viewmodels.common.HtmlContent
import uk.gov.hmrc.govukfrontend.views.viewmodels.errorsummary.ErrorLink

class ImplicitsSpec
    extends WordSpec
    with Matchers
    with MessagesHelpers
    with ScalaCheckPropertyChecks
    with ShrinkLowPriority {

  "asErrorLinks" should {
    val errors = Seq(
      FormError(key = "k1", message = "m1"),
      FormError(key = "k2", message = "m2")
    )

    "convert FormErrors to ErrorLinks" in {
      errors.asErrorLinks should contain theSameElementsAs (
        Seq(
          ErrorLink(href = Some(errors(0).key), content = HtmlContent(messages(errors(0).message, errors(0).args: _*))),
          ErrorLink(href = Some(errors(1).key), content = HtmlContent(messages(errors(1).message, errors(1).args: _*)))
        )
      )
    }
  }

  "padLeft" should {
    "add left padding to non-empty HTML" in {

      val htmlGen: Gen[Html] = Gen.alphaStr.map(Html(_))

      forAll(htmlGen, Gen.chooseNum(0, 5)) { (html, padCount) =>
        (html, padCount) match {
          case (HtmlExtractor(""), n)      => html.padLeft(n)              shouldBe HtmlFormat.empty
          case (HtmlExtractor(content), 0) => html.padLeft(0).body         shouldBe content
          case (HtmlExtractor(content), n) => html.padLeft(n).body.drop(1) shouldBe html.padLeft(n - 1).body
        }
      }

      object HtmlExtractor {
        def unapply(html: Html): Option[String] =
          Some(html.body)
      }

    }
  }

}
