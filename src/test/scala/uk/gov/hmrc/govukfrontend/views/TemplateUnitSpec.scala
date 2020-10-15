/*
 * Copyright 2020 HM Revenue & Customs
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
import org.scalatest.{Matchers, TryValues, WordSpecLike}
import play.api.libs.json._
import scala.util.{Failure, Success, Try}

/**
  * Base class for unit testing against test fixtures generated from govuk-frontend's yaml documentation files for
  * components
  *
  * @param govukComponentName
  * @param [[Reads[T]]]
  * @tparam T
  */
abstract class TemplateUnitSpec[T: Reads](govukComponentName: String)
    extends TwirlRenderer[T]
    with PreProcessor
    with JsoupHelpers
    with WordSpecLike
    with Matchers
    with TryValues {

  val skipBecauseOfJsonValidation = Seq(
    "date-input-with-values",
    "summary-list-value-with-html"
  )
  val skipBecauseOfAttributeOrdering = Seq(
    "details-attributes",
    "warning-text-attributes",
    "breadcrumbs-attributes"
  )
  val skipBecauseRequiredItemsSeemToBeMissing = Seq(
    "select-with-falsey-values",
    "date-input-items-with-classes",
    "date-input-with-id-on-items",
    "date-input-fieldset-html",
    "date-input-classes",
    "date-input-with-empty-items",
    "date-input-items-without-classes",
    "date-input-no-data",
    "date-input-attributes",
    "date-input-custom-pattern",
    "date-input-with-nested-name",
    "select-with-describedBy",
    "select-error",
    "select-error-and-describedBy",
    "select-hint",
    "select-hint-and-describedBy",
    "select-attributes",
    "select-attributes-on-items",
    "input-hint-with-describedBy",
    "input-classes",
    "input-with-error,-hint-and-describedBy",
    "input-with-error-and-hint",
    "input-attributes",
    "input-inputmode",
    "input-with-describedBy",
    "input-value",
    "input-error-with-describedBy",
    "input-custom-type",
    "textarea-with-error-message-and-described-by",
    "textarea-attributes",
    "textarea-with-describedBy",
    "textarea-with-hint-and-error-message",
    "textarea-with-hint,-error-message-and-described-by",
    "textarea-with-hint-and-described-by",
    "textarea-classes",
    "skip-link-default-values",
    "skip-link-html-as-text",
    "skip-link-html",
    "skip-link-attributes",
    "skip-link-classes",
    "skip-link-custom-text",
    "checkboxes-with-hints-on-items",
    "character-count-spellcheck-enabled",
    "character-count-custom-classes-with-error-message",
    "character-count-attributes",
    "character-count-formGroup-with-classes",
    "character-count-spellcheck-disabled",
    "character-count-custom-classes-on-countMessage",
    "character-count-classes",
    "file-upload-with-error-and-describedBy",
    "file-upload-attributes",
    "file-upload-with-describedBy",
    "file-upload-with-error,-describedBy-and-hint",
    "file-upload-classes",
    "file-upload-error",
    "file-upload-with-hint-and-describedBy",
    "button-input-type",
    "button-input-classes",
    "button-input-attributes"
  )
  val skipBecauseChangesNeededWithGDS = Seq(
    "checkboxes-with-falsey-values",
    "radios-with-falsey-items",
    "accordion-with-falsey-values"
  )
  val skip = skipBecauseOfJsonValidation ++
    skipBecauseOfAttributeOrdering ++ skipBecauseRequiredItemsSeemToBeMissing ++
    skipBecauseChangesNeededWithGDS

  exampleNames(fixturesDirs, govukComponentName)
    .foreach { fixtureDirExampleName =>
      val (fixtureDir, exampleName) = fixtureDirExampleName

      s"$exampleName" should {
        if (!skip.contains(exampleName)) {

          "render the same html as the nunjucks renderer" in {
            val tryTwirlHtml = renderExample(fixtureDir, exampleName)

            tryTwirlHtml match {
              case Success(twirlHtml) =>
                val preProcessedTwirlHtml    = preProcess(twirlHtml)
                val preProcessedNunjucksHtml = preProcess(nunjucksHtml(fixtureDir, exampleName).success.value)

                preProcessedTwirlHtml shouldBe preProcessedNunjucksHtml

            }
          }
        }
      }
    }

  private def renderExample(fixturesDir: File, exampleName: String): Try[String] =
    for {
      inputJson    <- Try(readInputJson(fixturesDir, exampleName))
      inputJsValue <- Try(Json.parse(inputJson))
      html <- inputJsValue.validate[T] match {
               case JsSuccess(templateParams, _) =>
                 render(templateParams)
                   .transform(html => Success(html.body), f => Failure(new TemplateValidationException(f.getMessage)))
               case e: JsError =>
                 throw new RuntimeException(s"Failed to validate Json params: [$inputJsValue]\nException: [$e]")
             }
    } yield html

  private def nunjucksHtml(fixtureDir: File, exampleName: String): Try[String] =
    Try(readOutputFile(fixtureDir, exampleName))

  /**
    * Traverse the test fixtures directory to fetch all examples for a given component
    *
    * @param govukComponentName govuk component name as found in the test fixtures file component.json
    * @return [[Seq[String]]] of folder names for each example in the test fixtures folder or
    *        fails if the fixtures folder is not defined
    */
  private def exampleNames(fixturesDirs: Seq[File], govukComponentName: String): Seq[(File, String)] = {
    val exampleFolders = fixturesDirs.flatMap(
      fixtureDir =>
        getExampleFolders(fixtureDir, govukComponentName).map(
          exampleDir => (fixtureDir, exampleDir)
      ))

    val examples = for ((fixtureDir, exampleDir) <- exampleFolders) yield (fixtureDir, exampleDir.name)

    if (examples.nonEmpty) examples
    else throw new RuntimeException(s"Couldn't find component named $govukComponentName. Spelling error?")
  }

  private def getExampleFolders(fixturesDir: File, govukComponentName: String): Seq[File] = {
    def parseComponentName(json: String): Option[String] = (Json.parse(json) \ "name").asOpt[String]

    val componentNameFiles = fixturesDir.listRecursively.filter(_.name == "component.json")

    val matchingFiles =
      componentNameFiles.filter(file => parseComponentName(file.contentAsString).contains(govukComponentName))

    val folders = matchingFiles.map(_.parent).toSeq.distinct

    folders
  }

  private lazy val fixturesDirs: Seq[File] = {
    val dir = s"/fixtures"
    val fixturesDir = Try(File(Resource.my.getUrl(dir)))
      .getOrElse(throw new RuntimeException(s"Test fixtures folder not found: $dir"))

    fixturesDir.children.toSeq
  }

  private def readOutputFile(fixturesDir: File, exampleName: String): String =
    (fixturesDir / exampleName / "output.txt").contentAsString

  private def readInputJson(fixturesDir: File, exampleName: String): String =
    (fixturesDir / exampleName / "input.json").contentAsString
}
