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

import better.files.{File, Resource}
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{JsObject, JsSuccess, Json}

import scala.util.Try

class GenerateFixturesSpec extends WordSpec with Matchers {
  val dir         = s"/fixtures/test-fixtures"
  val fixturesDir = Try(File(Resource.my.getUrl(dir)))
  val knownEmptyOutput = Seq(
    "label-empty"
  )

  "generateFixtures" should {
    "create a VERSION.txt file" in {
      fixturesDir.isSuccess shouldBe true
      val version = fixturesDir.get / "VERSION.txt"
      version.exists                  shouldBe true
      version.contentAsString.isEmpty shouldBe false
    }

    "create example directories" in {
      fixturesDir.isSuccess                         shouldBe true
      fixturesDir.get.children.count(_.isDirectory) should be > 0
    }

    fixturesDir.get.children.filter(_.isDirectory).foreach { example =>
      if(!knownEmptyOutput.contains(example.name)) {
        s"create a non-empty output.txt in ${example.name}" in {
          (example / "output.txt").exists                  shouldBe true
          (example / "output.txt").contentAsString.isEmpty shouldBe false
        }
      }

      s"create a valid component.json in ${example.name}" in {
        (example / "component.json").exists shouldBe true

        val contentTry = Try(Json.parse((example / "component.json").contentAsString))
        contentTry.isSuccess shouldBe true

        val json = contentTry.get.validate[JsObject]
        json shouldBe a[JsSuccess[_]]

        val name = (json.get \ "name").validate[String]

        name               shouldBe a[JsSuccess[_]]
        (name.get.isEmpty) shouldBe false
      }

      s"create a valid input.json in ${example.name}" in {
        (example / "input.json").exists shouldBe true

        val jsonTry = Try(Json.parse((example / "input.json").contentAsString))

        jsonTry.isSuccess              shouldBe true
        jsonTry.get.validate[JsObject] shouldBe a[JsSuccess[_]]
      }
    }
  }
}
