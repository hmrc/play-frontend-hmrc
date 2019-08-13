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

package uk.gov.hmrc.govukfrontend
package views
package components

import com.googlecode.htmlcompressor.compressor.HtmlCompressor
import org.jsoup.parser.Parser
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json._
import scala.io.Source

abstract class RenderHtmlSpec(exampleNames: Seq[String]) extends WordSpec with Matchers with RenderHtmlSpecHelper {
  exampleNames foreach { exampleName =>
    s"$exampleName" should {
      "render the same html as the nunjucks renderer" in {
        twirlHtml(exampleName) match {
          case Left(error) => fail(error)
          case Right(html) =>
            parseAndCompressHtml(html) shouldBe parseAndCompressHtml(nunjucksHtml(exampleName))
        }
      }
    }
  }
}

trait HtmlStringTag

trait RenderHtmlSpecHelper extends ReadsHelpers {
  type HtmlString = String @@ HtmlStringTag

  implicit def reads: Reads[HtmlString]

  private lazy val compressor = new HtmlCompressor()

  def parseAndCompressHtml(html: String): String =
    compressor.compress(Parser.unescapeEntities(html, false))

  def twirlHtml(exampleName: String): Either[String, HtmlString] = {
    val inputJson = readInputJson(exampleName)

    Json.parse(inputJson).validate[HtmlString] match {
      case JsSuccess(htmlString, _) => Right(htmlString)
      case e: JsError               => Left(s"failed to validate params: $e")
    }
  }

  def nunjucksHtml(exampleName: String): String =
    readOutputFile(exampleName)

  private val govukFrontendVersion = "2.11.0"

  private def readOutputFile(exampleName: String): String =
    readFileAsString("output.html", exampleName)

  private def readInputJson(exampleName: String): String =
    readFileAsString("input.json", exampleName)

  private def readFileAsString(fileName: String, exampleName: String): String =
    Source
      .fromInputStream(
        getClass.getResourceAsStream(s"/fixtures/test-fixtures-$govukFrontendVersion/$exampleName/$fileName"))
      .getLines
      .mkString("\n")
}
