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

package uk.gov.hmrc.helpers.views

import better.files.{File, Resource}
import play.api.libs.json.Json

import scala.util.Try

trait ShareTemplateUnitSpec {

  protected val baseFixturesDirectory: String

  protected def nunjucksHtml(fixtureDir: File, exampleName: String): Try[String] =
    Try(readOutputFile(fixtureDir, exampleName))

  /**
    * Traverse the test fixtures directory to fetch all examples for a given component
    *
    * @param componentName govuk component name as found in the test fixtures file component.json
    * @return [[Seq[String]]] of folder names for each example in the test fixtures folder or
    *        fails if the fixtures folder is not defined
    */
  protected def exampleNames(fixturesDirs: Seq[File], componentName: String): Seq[(File, String)] = {
    val exampleFolders = fixturesDirs.flatMap(fixtureDir =>
      getExampleFolders(fixtureDir, componentName).map(exampleDir => (fixtureDir, exampleDir))
    )

    val examples = for ((fixtureDir, exampleDir) <- exampleFolders) yield (fixtureDir, exampleDir.name)

    if (examples.nonEmpty) examples
    else throw new RuntimeException(s"Couldn't find component named $componentName. Spelling error?")
  }

  protected def getExampleFolders(fixturesDir: File, govukComponentName: String): Seq[File] = {
    def parseComponentName(json: String): Option[String] = (Json.parse(json) \ "name").asOpt[String]

    val componentNameFiles = fixturesDir.listRecursively.filter(_.name == "component.json")

    val matchingFiles =
      componentNameFiles.filter(file => parseComponentName(file.contentAsString).contains(govukComponentName))

    val folders = matchingFiles.map(_.parent).toSeq.distinct

    folders
  }

  protected lazy val fixturesDirs: Seq[File] = {
    val dir         = baseFixturesDirectory
    //"/fixtures/govuk-frontend"
    val fixturesDir = Try(File(Resource.my.getUrl(dir)))
      .getOrElse(throw new RuntimeException(s"Test fixtures folder not found: $dir"))

    fixturesDir.children.toSeq
  }

  protected def readOutputFile(fixturesDir: File, exampleName: String): String =
    (fixturesDir / exampleName / "output.txt").contentAsString

  protected def readInputJson(fixturesDir: File, exampleName: String): String =
    (fixturesDir / exampleName / "input.json").contentAsString
}
