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

import better.files._
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}
import play.twirl.api.Html
import uk.gov.hmrc.BuildInfo
import scala.util.matching.Regex
import scala.util.{Failure, Success, Try}

trait FixturesRenderer extends ReadsHelpers with JsoupHelpers {

  import FixturesRenderer._

  /**
    * Reads for inputs to the example templates included in the test fixtures.
    * They should be able to parse the inputs as documented in the component's yaml.
    * ex: [[https://github.com/alphagov/govuk-frontend/blob/master/src/govuk/components/back-link/back-link.yaml]]
    *
    * @return [[Html]] of the rendered Twirl template given the parsed inputs
    */
  implicit def reads: Reads[Html]

  def twirlHtml(exampleName: String): Try[String] = {
    val inputJson = readInputJson(exampleName)
    val tryHtml = Json.parse(inputJson).validate[Html] match {
      case JsSuccess(html, _) => Success(html.body)
      case e: JsError         => Failure(new RuntimeException(s"failed to validate params: $e"))
    }
    tryHtml
  }

  def nunjucksHtml(exampleName: String): Try[String] =
    Try(readOutputFile(exampleName))

  /**
    * Traverse the test fixtures directory to fetch all examples for a given component
    *
    * @param govukComponentName govuk component name as found in the test fixtures file component.json
    * @return [[Seq[String]]] of folder names for each example in the test fixtures folder or
    *        fails if the fixtures folder is not defined
    */
  def exampleNames(govukComponentName: String): Seq[String] = {
    def parseComponentName(json: String): Option[String] = (Json.parse(json) \ "name").asOpt[String]

    val componentNameFiles = fixturesDir.listRecursively.filter(_.name == "component.json")

    val matchingFiles =
      componentNameFiles.filter(file => parseComponentName(file.contentAsString).contains(govukComponentName))

    val examples = matchingFiles.map(_.parent.name).toSeq.distinct

    if (examples.nonEmpty) examples
    else throw new RuntimeException(s"Couldn't find component named $govukComponentName. Spelling error?")
  }
}

object FixturesRenderer {

  val govukFrontendVersionRegex: Regex = """org\.webjars\.npm:govuk-frontend:(\d+\.\d+\.\d+)""".r

  val govukFrontendVersion: String =
    findFirstMatch(govukFrontendVersionRegex, BuildInfo.libraryDependencies)
      .getOrElse(throw new RuntimeException("Unable to find the govuk-frontend dependency"))
      .group(1)

  lazy val fixturesDir: File = {
    val dir = s"/fixtures/test-fixtures-$govukFrontendVersion"
    Try(File(Resource.my.getUrl(dir)))
      .getOrElse(throw new RuntimeException(s"test fixtures folder not found: $dir"))
  }

  def readOutputFile(exampleName: String): String =
    (fixturesDir / exampleName / "output.html").contentAsString

  def readInputJson(exampleName: String): String =
    (fixturesDir / exampleName / "input.json").contentAsString

  def findFirstMatch(regex: Regex, xs: Seq[String]): Option[Regex.Match] =
    for {
      x <- xs.find {
            case regex(_*) => true
            case _         => false
          }
      firstMatch <- regex.findFirstMatchIn(x)
    } yield firstMatch
}
