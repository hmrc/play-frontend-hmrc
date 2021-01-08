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

package uk.gov.hmrc.govukfrontend.views
package viewmodels

import org.scalacheck.{Arbitrary, Gen}
import play.twirl.api.{Html, HtmlFormat}

object Generators {

  val genNonEmptyAlphaStr: Gen[String] = Gen.alphaStr.suchThat(_.nonEmpty)

  /**
    * Generates an alphabetic String with adjustable empty frequency String.
    * @param emptyFreq as a percentage (0 - 100)
    * @return
    */
  def genAlphaStr(emptyFreq: Int = 25): Gen[String] =
    Gen.frequency((100 - emptyFreq, Gen.alphaStr), (emptyFreq, Gen.const("")))

  val genAttrVal: Gen[(String, String)] = for {
    attr  <- genNonEmptyAlphaStr
    value <- Gen.alphaStr
  } yield (attr, value)

  def genAttributes(nAttributes: Int = 5) =
    for {
      sz         <- Gen.chooseNum(0, nAttributes)
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

  def genClasses(nClasses: Int = 3): Gen[String] =
    for {
      sz      <- Gen.chooseNum(0, nClasses)
      classes <- Gen.listOfN(sz, Gen.alphaStr.suchThat(_.trim.nonEmpty)).map(_.mkString(" "))
    } yield classes
}
