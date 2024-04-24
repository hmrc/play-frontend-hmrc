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

import org.jsoup.Jsoup
import org.jsoup.helper.W3CDom
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import java.io.StringWriter

class HtmlComparisonTest extends AnyFlatSpec with Matchers {

  def normalizeHtml(html: String): String = {
    val w3cDom = new W3CDom().fromJsoup(Jsoup.parse(html))
    val writer = new StringWriter()
    val transformer = TransformerFactory.newInstance().newTransformer()
    transformer.transform(new DOMSource(w3cDom), new StreamResult(writer))
    writer.toString
  }

  "HTML outputs" should "match after normalization" in {
    val html1 = """<div class="test" id="div1"><p>Hello World</p></div>""".replaceAll("\\s+", " ")
    val html2 = """<div id="div1" class="test"><p>Hello World</p></div>""".replaceAll("\\s+", " ")

    val normalizedHtml1 = normalizeHtml(html1)
    val normalizedHtml2 = normalizeHtml(html2)

    normalizedHtml1 shouldEqual normalizedHtml2
  }
}
