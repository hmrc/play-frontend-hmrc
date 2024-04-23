/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.support

import better.files.File
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

abstract class TemplateIntegrationBaseMetaSpec(val libraryName: String, val ignoredHelpers: Seq[String] = Seq.empty)
    extends AnyWordSpec
    with Matchers {

  private val libraryDir    = libraryName.replace("-", "")
  private val libraryPrefix = libraryName.split('-').head

  private val itPath           = "it-play-30"
  private val itRoot           = File(itPath)
  private val testRoot         = itRoot / "src" / "test" / "scala"
  private val componentTestDir = testRoot / "uk" / "gov" / "hmrc" / libraryDir / "views" / "components"

  private val mainPath      = "play-frontend-hmrc-play-30"
  private val mainRoot      = File(mainPath)
  private val templatesRoot = mainRoot / "src" / "main" / "twirl"
  private val templatesDir  = templatesRoot / "uk" / "gov" / "hmrc" / libraryDir / "views" / "components"

  this.getClass.getCanonicalName when {

    val templates = templatesDir.children
      .filter(_.isRegularFile)
      .map(_.name)
      .map(filename => filename.split('.').head)
      .map(name => s"${name.head.toLower}${name.tail}")
      .filter(_.startsWith(libraryPrefix))
      .filterNot(ignoredHelpers.contains(_))
      .toList
      .sorted

    templates.foreach { template =>
      s"component is $template" should {

        "be covered by an integration test" in {
          withClue(s"""no test found for component "$template" under $componentTestDir""") {
            componentTestDir.children
              .filter(_.isRegularFile)
              .exists(_.contentAsString.contains(s""""$template"""")) should be(true)
          }
        }
      }
    }
  }

}
