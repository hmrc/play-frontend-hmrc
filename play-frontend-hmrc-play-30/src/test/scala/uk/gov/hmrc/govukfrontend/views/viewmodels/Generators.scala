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

package uk.gov.hmrc.govukfrontend.views
package viewmodels

import org.scalacheck.{Arbitrary, Gen}
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators.arbContent
import uk.gov.hmrc.govukfrontend.views.viewmodels.input.InputWrapper

object Generators {

  val arbFormGroup: Arbitrary[FormGroup] = Arbitrary {
    for {
      classes     <- Gen.option(genClasses())
      attributes  <- genAttributes()
      beforeInput <- Gen.option(arbContent.arbitrary)
      afterInput  <- Gen.option(arbContent.arbitrary)
    } yield FormGroup(
      classes = classes,
      attributes = attributes,
      beforeInput = beforeInput,
      afterInput = afterInput
    )
  }

  val arbInputWrapper: Arbitrary[InputWrapper] = Arbitrary {
    for {
      classes    <- Gen.option(genClasses())
      attributes <- genAttributes()
    } yield InputWrapper(
      classes = classes,
      attributes = attributes
    )
  }

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
    value <- genNonEmptyAlphaStr
  } yield (attr, value)

  def genAttributes(nAttributes: Int = 5): Gen[Map[String, String]] = genMapValues(nAttributes)

  def genMapValues(nLength: Int = 5): Gen[Map[String, String]] =
    for {
      sz  <- Gen.chooseNum(0, nLength)
      map <- Gen.mapOfN[String, String](sz, genAttrVal)
    } yield map

  val genHtmlString: Gen[String] =
    Gen.oneOf(Gen.const("""<p>some paragraph</p>"""), Gen.const("""<b>Back</b>"""))

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
