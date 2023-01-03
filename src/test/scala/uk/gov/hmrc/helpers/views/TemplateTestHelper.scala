/*
 * Copyright 2023 HM Revenue & Customs
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
import org.scalatest.TryValues
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}

import scala.util.{Failure, Success, Try}

abstract class TemplateTestHelper[T: Reads](componentName: String)
    extends TwirlRenderer[T]
    with JsoupHelpers
    with AnyWordSpecLike
    with Matchers
    with TryValues
    with GuiceOneAppPerSuite
    with PreProcessor {

  protected val baseFixturesDirectory: String

  protected val skippedExamples: Seq[String]
  protected val fullyCompressedExamples: Seq[String] = Seq(
    "summary-list-translated"
  )

  protected lazy val fixturesDirs: Seq[File] = {
    val dir         = baseFixturesDirectory
    val fixturesDir = Try(File(Resource.my.getUrl(dir)))
      .getOrElse(throw new RuntimeException(s"Test fixtures folder not found: $dir"))

    fixturesDir.children.toSeq
  }

  protected def matchTwirlAndNunjucksHtml(fixtureDirectories: Seq[File]): Unit =
    exampleNames(fixtureDirectories, componentName)
      .foreach { fixtureDirExampleName =>
        val (fixtureDir, exampleName) = fixtureDirExampleName

        s"$exampleName" should {
          if (!skippedExamples.contains(exampleName)) {

            "compare the rendered twirl against the the rendered nunjucks" in {
              val tryTwirlHtml    = renderExample(fixtureDir, exampleName)
              val tryNunjucksHtml = getNunjucksHtml(fixtureDir, exampleName)

              (tryTwirlHtml, tryNunjucksHtml) match {
                case (Success(twirlHtml), Success(nunjucksHtml))        =>
                  val preProcess: String => String =
                    if (fullyCompressedExamples.contains(exampleName)) compressHtml(_, maximumCompression = true)
                    else compressHtml(_)
                  val preProcessedTwirlHtml        = preProcess(twirlHtml)
                  val preProcessedNunjucksHtml     = preProcess(nunjucksHtml)

                  withClue(s"""
                       | Twirl output:
                       | $preProcessedTwirlHtml
                       | Nunjucks output:
                       | $preProcessedNunjucksHtml
                       |""".stripMargin) {
                    preProcessedTwirlHtml shouldBe preProcessedNunjucksHtml
                  }
                case (Failure(TemplateValidationException(message)), _) =>
                  println(s"Failed to validate the parameters for the $componentName twirl template")
                  println(s"Exception: $message")

                  fail
                case (Failure(twirlException), _)                       =>
                  println(s"Failed to render for the $componentName twirl template")
                  println(s"Exception: ${twirlException.getMessage}")
                  fail(twirlException)
                case (_, Failure(nunjucksException))                    =>
                  println(s"Failed to get the $componentName nunjucks template")
                  println(s"Exception: ${nunjucksException.getMessage}")
                  fail(nunjucksException)
              }
            }
          }
        }
      }

  /**
    * Traverse the test fixtures directory to fetch all examples for a given component
    *
    * @param componentName component name as found in the test fixtures file component.json
    * @return [[Seq[String]]] of folder names for each example in the test fixtures folder or
    *        fails if the fixtures folder is not defined
    */
  private def exampleNames(fixturesDirs: Seq[File], componentName: String): Seq[(File, String)] = {
    val exampleFolders = fixturesDirs.flatMap(fixtureDir =>
      getExampleFolders(fixtureDir, componentName).map(exampleDir => (fixtureDir, exampleDir))
    )

    val examples = for ((fixtureDir, exampleDir) <- exampleFolders) yield (fixtureDir, exampleDir.name)

    if (examples.nonEmpty) examples
    else throw new RuntimeException(s"Couldn't find component named $componentName. Spelling error?")
  }

  private def getExampleFolders(fixturesDir: File, govukComponentName: String): Seq[File] = {
    def parseComponentName(json: String): Option[String] = (Json.parse(json) \ "name").asOpt[String]

    val componentNameFiles = fixturesDir.listRecursively.filter(_.name == "component.json")

    val matchingFiles =
      componentNameFiles.filter(file => parseComponentName(file.contentAsString).contains(govukComponentName))

    val folders = matchingFiles.map(_.parent).toSeq.distinct

    folders
  }

  private def renderExample(fixturesDir: File, exampleName: String): Try[String] =
    for {
      inputJson    <- Try(readInputJson(fixturesDir, exampleName))
      inputJsValue <- Try(Json.parse(inputJson))
      html         <- inputJsValue.validate[T] match {
                        case JsSuccess(templateParams, _) =>
                          render(templateParams)
                            .transform(html => Success(html.body), f => Failure(new TemplateValidationException(f.getMessage)))
                        case e: JsError                   =>
                          throw new RuntimeException(s"Failed to validate Json params: [$inputJsValue]\nException: [$e]")
                      }
    } yield html

  private def readInputJson(fixturesDir: File, exampleName: String): String =
    (fixturesDir / exampleName / "input.json").contentAsString

  private def getNunjucksHtml(fixtureDir: File, exampleName: String): Try[String] =
    Try(readOutputFile(fixtureDir, exampleName))

  private def readOutputFile(fixturesDir: File, exampleName: String): String =
    (fixturesDir / exampleName / "output.txt").contentAsString

}
