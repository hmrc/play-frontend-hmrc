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
import play.api.libs.json._
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.GovukFrontendDependency._
import scala.util.{Failure, Success, Try}

trait FixturesRenderer extends JsonHelpers with JsoupHelpers {
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
    val exampleFolders = getExampleFolders(govukComponentName)

    val examples = exampleFolders.map(_.name)

    if (examples.nonEmpty) examples
    else throw new RuntimeException(s"Couldn't find component named $govukComponentName. Spelling error?")
  }
}

object FixturesRenderer {

  def getExampleFolders(govukComponentName: String): Seq[File] = {
    def parseComponentName(json: String): Option[String] = (Json.parse(json) \ "name").asOpt[String]

    val componentNameFiles = fixturesDir.listRecursively.filter(_.name == "component.json")

    val matchingFiles =
      componentNameFiles.filter(file => parseComponentName(file.contentAsString).contains(govukComponentName))

    val folders = matchingFiles.map(_.parent).toSeq.distinct

    folders
  }

  def readInputJsonFiles(govukComponentName: String): Seq[(File, JsValue)] = {
    val exampleFolders = getExampleFolders(govukComponentName)

    val inputJsonFiles: Seq[(File, File)] =
      exampleFolders.map(
        folder =>
          folder -> folder.children
            .find(_.name == "input.json")
            .getOrElse(throw new RuntimeException(s"Could not find input json file in folder $folder")))

    inputJsonFiles.map {
      case (exampleFolder, inputJsonFile) => exampleFolder -> Json.parse(inputJsonFile.contentAsString)
    }
  }

  private lazy val fixturesDir: File = {
    val dir = s"/fixtures/test-fixtures-$govukFrontendVersion"
    Try(File(Resource.my.getUrl(dir)))
      .getOrElse(throw new RuntimeException(s"Test fixtures folder not found: $dir"))
  }

  private def readOutputFile(exampleName: String): String =
    (fixturesDir / exampleName / "output.html").contentAsString

  private def readInputJson(exampleName: String): String =
    (fixturesDir / exampleName / "input.json").contentAsString
}
