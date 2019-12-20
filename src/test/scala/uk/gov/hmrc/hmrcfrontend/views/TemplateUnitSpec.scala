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

package uk.gov.hmrc.hmrcfrontend.views

import better.files._
import org.scalatest.{Matchers, TryValues, WordSpecLike}
import uk.gov.hmrc.hmrcfrontend.views.HmrcFrontendDependency.hmrcFrontendVersion
import play.api.libs.json._

import scala.util.{Failure, Success, Try}

/**
  * Base class for unit testing against test fixtures generated from hmrc-frontend's yaml documentation files for
  * components
  *
  * @param hmrcComponentName
  * @param [[Reads[T]]]
  * @tparam T
  */
abstract class TemplateUnitSpec[T: Reads](hmrcComponentName: String)
    extends TwirlRenderer[T]
    with PreProcessor
    with JsoupHelpers
    with WordSpecLike
    with Matchers
    with TryValues {

  exampleNames(hmrcComponentName)
    .foreach { exampleName =>
    s"$exampleName" should {
      "render the same html as the nunjucks renderer" in {
        val tryTwirlHtml = renderExample(exampleName)

        tryTwirlHtml match {
          case Success(twirlHtml) =>
            val preProcessedTwirlHtml    = preProcess(twirlHtml)
            val preProcessedNunjucksHtml = preProcess(nunjucksHtml(exampleName).success.value)

            preProcessedTwirlHtml shouldBe preProcessedNunjucksHtml
          case Failure(TemplateValidationException(message)) =>
            println(s"Failed to validate the parameters for the $hmrcComponentName template")
            println(s"Exception: $message")
            println(s"Skipping test $exampleName")

            succeed
          case Failure(exception) => fail(exception)
        }
      }
    }
  }

  private def renderExample(exampleName: String): Try[String] =
    for {
      inputJson    <- Try(readInputJson(exampleName))
      inputJsValue <- Try(Json.parse(inputJson))
      html <- inputJsValue.validate[T] match {
               case JsSuccess(templateParams, _) =>
                 render(templateParams)
                   .transform(html => Success(html.body), f => Failure(new TemplateValidationException(f.getMessage)))
               case e: JsError =>
                 throw new RuntimeException(s"Failed to validate Json params: [$inputJsValue]\nException: [$e]")
             }
    } yield html

  private def nunjucksHtml(exampleName: String): Try[String] =
    Try(readOutputFile(exampleName))

  /**
    * Traverse the test fixtures directory to fetch all examples for a given component
    *
    * @param hmrcComponentName hmrc component name as found in the test fixtures file component.json
    * @return [[Seq[String]]] of folder names for each example in the test fixtures folder or
    *        fails if the fixtures folder is not defined
    */
  private def exampleNames(hmrcComponentName: String): Seq[String] = {
    val exampleFolders = getExampleFolders(hmrcComponentName)

    val examples = exampleFolders.map(_.name)

    if (examples.nonEmpty) examples
    else throw new RuntimeException(s"Couldn't find component named $hmrcComponentName. Spelling error?")
  }

  private def getExampleFolders(hmrcComponentName: String): Seq[File] = {
    def parseComponentName(json: String): Option[String] = (Json.parse(json) \ "name").asOpt[String]

    val componentNameFiles = fixturesDir.listRecursively.filter(_.name == "component.json")

    val matchingFiles =
      componentNameFiles.filter(file => parseComponentName(file.contentAsString).contains(hmrcComponentName))

    val folders = matchingFiles.map(_.parent).toSeq.distinct

    folders
  }

  private lazy val fixturesDir: File = {
    val dir = s"/fixtures/test-fixtures-$hmrcFrontendVersion"
    Try(File(Resource.my.getUrl(dir)))
      .getOrElse(throw new RuntimeException(s"Test fixtures folder not found: $dir"))
  }

  private def readOutputFile(exampleName: String): String =
    (fixturesDir / exampleName / "output.html").contentAsString

  private def readInputJson(exampleName: String): String =
    (fixturesDir / exampleName / "input.json").contentAsString
}
