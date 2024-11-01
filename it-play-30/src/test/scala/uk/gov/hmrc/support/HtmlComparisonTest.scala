/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.support

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.helpers.views.PreProcessor

class HtmlComparisonTest extends AnyFlatSpec with Matchers with PreProcessor {

  "HTML outputs" should "match after normalization" in {
    val html1 = """<div class="test" id="div1"><p>Hello World</p></div>""".replaceAll("\\s+", " ")
    val html2 = """<div id="div1" class="test"><p>Hello World</p></div>""".replaceAll("\\s+", " ")

    val normalizedHtml1 = normaliseHtml(html1)
    val normalizedHtml2 = normaliseHtml(html2)

    normalizedHtml1 shouldEqual normalizedHtml2
  }

// Example showing how normaliseHtml modifies the html:
//
//  "HTML inputs that have unclosed tags" should "not match after normalization" in {
//    val html1 = """<div class="test" id="div1"><p>Hello World</p></div>""".replaceAll("\\s+", " ")
//    val html2 = """<div id="div1" class="test"><p>Hello World""".replaceAll("\\s+", " ")
//
//    val normalizedHtml1 = normaliseHtml(html1)
//    val normalizedHtml2 = normaliseHtml(html2)
//
//    normalizedHtml1 shouldNot equal(normalizedHtml2)
//  }
}
