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

package uk.gov.hmrc.hmrcfrontend.views

import better.files._
import play.api.libs.json._
import uk.gov.hmrc.helpers.views.{SharedTemplateUnitSpec, TemplateValidationException}

import scala.util.{Failure, Success, Try}

/**
  * Base class for unit testing against test fixtures generated from hmrc-frontend's yaml documentation files for
  * components
  */
abstract class TemplateUnitSpec[T: Reads](
  hmrcComponentName: String
) extends SharedTemplateUnitSpec[T] {

  override protected val baseFixturesDirectory: String = "/fixtures/hmrc-frontend"

  val skipBecauseOfSpellcheckOrdering = Seq(
    "character-count-spellcheck-disabled",
    "character-count-spellcheck-enabled"
  )

  val skip = skipBecauseOfSpellcheckOrdering

  exampleNames(fixturesDirs, hmrcComponentName)
    .foreach { fixtureDirExampleName =>
      val (fixtureDir, exampleName) = fixtureDirExampleName

      s"$exampleName" should {
        if (!skip.contains(exampleName)) {

          "render the same html as the nunjucks renderer" in {
            val tryTwirlHtml = renderExample(fixtureDir, exampleName)

            tryTwirlHtml match {
              case Success(twirlHtml)                            =>
                val preProcessedTwirlHtml    = preProcess(twirlHtml)
                val preProcessedNunjucksHtml =
                  preProcess(nunjucksHtml(fixtureDir, exampleName).success.value)

                preProcessedTwirlHtml shouldBe preProcessedNunjucksHtml
              case Failure(TemplateValidationException(message)) =>
                println(s"Failed to validate the parameters for the $hmrcComponentName template")
                println(s"Exception: $message")

                fail
              case Failure(exception)                            => fail(exception)
            }
          }
        }
      }
    }

}
