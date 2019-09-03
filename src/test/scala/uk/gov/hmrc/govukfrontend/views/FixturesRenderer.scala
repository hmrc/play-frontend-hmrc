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

import play.api.libs.json.{JsError, JsSuccess, Json, Reads}
import play.twirl.api.Html
import scala.io.Source

trait FixturesRenderer extends ReadsHelpers with JsoupHelpers {

  implicit def reads: Reads[Html]

  def twirlHtml(exampleName: String): Either[String, String] = {
    val inputJson = readInputJson(exampleName)

    Json.parse(inputJson).validate[Html] match {
      case JsSuccess(html, _) => Right(html.body)
      case e: JsError         => Left(s"failed to validate params: $e")
    }
  }

  def nunjucksHtml(exampleName: String): String =
    readOutputFile(exampleName)

  // FIXME: Move this to config
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

  // TODO: Function to traverse the examples path to fetch all examples for a given component
  def exampleNames(component: String): Seq[String] = ???

}
