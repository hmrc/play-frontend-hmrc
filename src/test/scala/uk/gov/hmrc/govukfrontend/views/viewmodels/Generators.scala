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
package viewmodels

import play.twirl.api.{Html, HtmlFormat}
import html.components._
import org.scalacheck.{Arbitrary, Gen}

object Generators {

  val genNonEmptyAlphaStr: Gen[String] = Gen.alphaStr.suchThat(_.nonEmpty)

  val genAttrVal: Gen[(String, String)] = for {
    attr  <- genNonEmptyAlphaStr
    value <- genNonEmptyAlphaStr
  } yield (attr, value)

  val genAttrsMap: Gen[Map[String, String]] = for {
    sz         <- Gen.chooseNum(0, 10)
    attributes <- Gen.mapOfN[String, String](sz, genAttrVal)
  } yield attributes

  val genHtmlString: Gen[String] =
    Gen.oneOf(Gen.const("""<p>some paragraph</p>"""), Gen.const(""""<b>Back</b>""""))

  implicit val arbHtml: Arbitrary[Html] = Arbitrary {
    for {
      htmlString <- genHtmlString
      html       <- Gen.frequency((80, Html(htmlString)), (20, HtmlFormat.empty))
    } yield html
  }

  val genEmpty = Gen.const(Empty)

  val genHtmlContent =
    arbHtml.arbitrary.map(HtmlContent(_))

  val genText =
    Gen.frequency((80, Gen.asciiPrintableStr), (20, genHtmlString)).map(Text(_))

  val genContent: Gen[Content] = Gen.oneOf(genEmpty, genHtmlContent, genText)
}
