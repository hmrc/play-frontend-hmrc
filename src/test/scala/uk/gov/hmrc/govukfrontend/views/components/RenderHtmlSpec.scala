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
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.twirl.api.Html
import RenderHtmlSpec.HtmlString
import html.components._
import scala.io.Source

class RenderHtmlSpec(exampleNames: Seq[String])(implicit reads: Reads[HtmlString]) extends WordSpec with Matchers {
  import RenderHtmlSpec._

  exampleNames foreach { exampleName =>
    s"$exampleName" should {
      "render the same html as the nunjucks renderer" in {
        parseAndCompressHtml(twirlHtml(exampleName)) shouldBe parseAndCompressHtml(nunjucksHtml(exampleName))
      }
    }
  }

  lazy val compressor = new HtmlCompressor()

  def parseAndCompressHtml(html: String): String =
    compressor.compress(Parser.unescapeEntities(html, false))

  def twirlHtml(exampleName: String): String = {
    val inputJson = readInputJson(exampleName)

    Json.parse(inputJson).validate[HtmlString] match {
      case JsSuccess(htmlString, _) => htmlString
      case e: JsError               => fail(s"failed to validate params: $e")
    }
  }

  def nunjucksHtml(exampleName: String): String =
    readOutputFile(exampleName)
}

trait HtmlStringTag

object RenderHtmlSpec {

  type HtmlString = String @@ HtmlStringTag

  // TODO: coming soon in Play 2.7.x https://www.playframework.com/documentation/2.7.x/api/scala/play/api/libs/json/Format.html#widen[B%3E:A]:play.api.libs.json.Reads[B]
  implicit class RichReads[A](r: Reads[A]) {
    def widen[B >: A]: Reads[B] = Reads[B] { r.reads }
  }

  implicit val readsHtml: Reads[Html] = new Reads[Html] {
    override def reads(json: JsValue): JsResult[Html] = json match {
      case JsString(html) => JsSuccess(Html(html))
      case _              => JsError("error.expected.string")
    }
  }

  implicit val readsHtmlContent: Reads[HtmlContent] =
    (__ \ "html").read[String].map(HtmlContent(_))

  implicit val readsText: Reads[Text] =
    (__ \ "text").read[String].map(Text)

  implicit val readsContents: Reads[Contents] =
    readsHtmlContent
      .widen[Contents]
      .orElse(readsText.widen[Contents])
      .orElse(Reads.pure[Contents](Empty))

  implicit val readsErrorLink: Reads[ErrorLink] = (
    (__ \ "href").readNullable[String] and
      readsContents and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(ErrorLink.apply _)

  implicit val readsLegend: Reads[Legend] = (
    readsContents and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "isPageHeading").readWithDefault[Boolean](false)
  )(Legend.apply _)

  implicit val readsFooterItem: Reads[FooterItem] = (
    (__ \ "text").readNullable[String] and
      (__ \ "href").readNullable[String] and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(FooterItem.apply _)

  implicit val readsFooterMeta: Reads[FooterMeta] = (
    readsContents and
      (__ \ "items").readWithDefault[Seq[FooterItem]](Nil)
  )(FooterMeta.apply _)

  implicit val readsFooterNavigation: Reads[FooterNavigation] =
    Json.using[Json.WithDefaultValues].reads[FooterNavigation]

  implicit val readsHeaderNavigation: Reads[HeaderNavigation] =
    Json.using[Json.WithDefaultValues].reads[HeaderNavigation]

  val govukFrontendVersion = "2.11.0"

  def readOutputFile(exampleName: String): String =
    readFileAsString("output.html", exampleName)

  def readInputJson(exampleName: String): String =
    readFileAsString("input.json", exampleName)

  def readFileAsString(fileName: String, exampleName: String): String =
    Source
      .fromInputStream(
        getClass.getResourceAsStream(s"/fixtures/test-fixtures-$govukFrontendVersion/$exampleName/$fileName"))
      .getLines
      .mkString("\n")
}
