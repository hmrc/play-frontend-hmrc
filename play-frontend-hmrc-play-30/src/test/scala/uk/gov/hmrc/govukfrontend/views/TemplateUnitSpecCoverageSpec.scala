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

import scala.util.Try

class TemplateUnitSpecCoverageSpec extends AnyWordSpec with Matchers {

  val library = "govuk-frontend"
  val libraryDir = library.replace("-", "")

  private val projectRoot: File = File("play-frontend-hmrc-play-30")
  val testRoot = projectRoot / "src" / "test" / "scala"
  val componentTestDir = testRoot / "uk" / "gov" / "hmrc" / libraryDir / "views" / "components"

  val fixturesRootPath = s"/fixtures/$library"
  val fixturesDir      = Try(File(Resource.my.getUrl(fixturesRootPath)))

  "TemplateUnitSpec" should {
    "have instances declared to cover all non-excluded test fixtures" in {

      val fixtureTypes = fixturesDir.get.children.filter(_.isDirectory).filterNot(_.name == "excluded-fixtures")

      fixtureTypes.foreach { fixtureType =>
        val fixtures = fixtureType.children.filter(_.isDirectory)
        fixtures.foreach { fixture =>
          val componentName = (Json.parse((fixture / "component.json").contentAsString) \ "name")
            .as[String]

          withClue(s"""no test found for component "$componentName"""") {
            componentTestDir
              .children
              .filter(_.isRegularFile)
              .exists(_.contentAsString.contains(s""""$componentName"""")) should be (true)
          }
        }
      }
    }
  }

}
