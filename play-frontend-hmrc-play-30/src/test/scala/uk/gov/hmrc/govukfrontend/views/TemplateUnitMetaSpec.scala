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

package uk.gov.hmrc.govukfrontend.views

import better.files.{File, Resource}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.Json

class TemplateUnitMetaSpec extends AnyWordSpec with Matchers {

  val library             = "govuk-frontend"
  val projectPath: String = "play-frontend-hmrc-play-30"

  val libraryDir = library.replace("-", "")

  val projectRoot: File      = File(projectPath)
  val testRoot: File         = projectRoot / "src" / "test" / "scala"
  val componentTestDir: File = testRoot / "uk" / "gov" / "hmrc" / libraryDir / "views" / "components"

  val fixturesRootPath: String = s"/fixtures/$library"
  val fixturesDir: File        = File(Resource.my.getUrl(fixturesRootPath))

  "TemplateUnitSpec" when {

    val fixtureTypes = fixturesDir.children.filter(_.isDirectory).filterNot(_.name == "excluded-fixtures")
    fixtureTypes.foreach { fixtureType =>
      s"fixture type is ${fixtureType.name}" when {

        val fixtures = fixtureType.children.filter(_.isDirectory).toList.sortBy(_.name)
        fixtures.foreach { fixture =>
          s"fixture is ${fixture.name}" should {

            "be covered by a test" in {
              val componentName = (Json.parse((fixture / "component.json").contentAsString) \ "name")
                .as[String]

              withClue(s"""no test found for component "$componentName" under $componentTestDir""") {
                componentTestDir.children
                  .filter(_.isRegularFile)
                  .exists(_.contentAsString.contains(s""""$componentName"""")) should be(true)
              }
            }
          }
        }
      }
    }
  }
}
