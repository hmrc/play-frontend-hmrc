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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.content

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty, HtmlContent, Text}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.Generators._

object Generators {

  implicit val arbEmpty: Arbitrary[Empty.type] = Arbitrary { Gen.const(Empty) }

  implicit val arbHtmlContent: Arbitrary[HtmlContent] = Arbitrary {
    arbHtml.arbitrary.map(HtmlContent(_))
  }

  implicit val arbText: Arbitrary[Text] = Arbitrary {
    Gen.frequency((80, Gen.asciiPrintableStr), (20, genHtmlString)).map(Text)
  }

  implicit val arbContent: Arbitrary[Content] = Arbitrary {
    Gen.oneOf(arbEmpty.arbitrary, arbHtmlContent.arbitrary, arbText.arbitrary)
  }
}
