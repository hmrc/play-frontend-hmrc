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
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.Json

abstract class TemplateUnitBaseMetaSpec(val libraryName: String) extends AnyWordSpec with Matchers {

  private val libraryDir       = libraryName.replace("-", "")
  private val projectPath      = "play-frontend-hmrc-play-30"
  private val projectRoot      = File(projectPath)
  private val testRoot         = projectRoot / "src" / "test" / "scala"
  private val componentTestDir = testRoot / "uk" / "gov" / "hmrc" / libraryDir / "views" / "components"

  private val fixturesResourcePath = s"/fixtures/$libraryName"
  private val fixturesDir          = File(Resource.my.getUrl(fixturesResourcePath))

  this.getClass.getCanonicalName when {

    val fixtureTypes = fixturesDir.children.filter(_.isDirectory).filterNot(_.name == "excluded-fixtures")
    fixtureTypes.foreach { fixtureType =>
      s"fixture type is ${fixtureType.name}" when {

        val fixtures = fixtureType.children.filter(_.isDirectory).toList.sortBy(_.name)
        fixtures
          .map { fixture =>
            val componentName = (Json.parse((fixture / "component.json").contentAsString) \ "name")
              .as[String]
            (componentName, fixture.name)
          }
          .groupBy { case (componentName, _) => componentName }
          .foreach { case (componentName, fixtures) =>
            s"component is $componentName" should {

              "be covered by a unit test" in {

                withClue(
                  s"""no test found under $componentTestDir for component "$componentName"""" +
                    s""" (fixtures ${fixtures.map { case (_, name) => name }.mkString(", ")})"""
                ) {
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
