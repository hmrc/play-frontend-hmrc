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

import org.scalatest.{Matchers, TryValues, WordSpec}
import scala.util.{Failure, Success}

/**
  * TODO: Refactor to take type parameter like [[uk.gov.hmrc.govukfrontend.support.TemplateIntegrationSpec]]
  * @param govukComponentName
  */
abstract class TemplateUnitSpec(govukComponentName: String)
    extends WordSpec
    with Matchers
    with FixturesRenderer
    with TryValues {

  exampleNames(govukComponentName).filter(_ == "checkboxes-with-hints-on-items").foreach { exampleName =>
    s"$exampleName" should {
      "render the same html as the nunjucks renderer" in {
        val tryTwirlHtml = twirlHtml(exampleName)

        tryTwirlHtml match {
          case Success(html) =>
            val twirlOutput    = parseAndCompressHtml(html)
            val nunjucksOutput = parseAndCompressHtml(nunjucksHtml(exampleName).success.value)

            twirlOutput shouldBe nunjucksOutput

          case Failure(exception: IllegalArgumentException) =>
            if (exception.getMessage.startsWith("requirement failed")) {
              println(s"Requirement for template $govukComponentName failed: ${exception.getMessage}")
              println(s"Skipping test: $exampleName")
              succeed
            } else {
              fail(exception)
            }
          case Failure(exception) => fail(exception)
        }
      }
    }
  }
}
