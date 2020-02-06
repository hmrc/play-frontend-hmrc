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

package uk.gov.hmrc.govukfrontend.views.viewmodels

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{Json, OWrites, Reads}

class JsonDefaultValueFormatterSpec extends WordSpec with Matchers {

  "Deserialising json with missing paths into a nested case class instance" should {
    "deserialise without errors into an instance with default values" in {
      val fooJson = Json.obj(
        "n" -> 1
      )

      fooJson.as[Foo] shouldBe Foo(n = 1, bar = Bar())
    }
  }

  case class Foo(n: Int = 1, bar: Bar = Bar())

  object Foo extends JsonDefaultValueFormatter[Foo] {
    override def defaultObject: Foo = Foo()

    override def defaultReads: Reads[Foo] = Json.reads[Foo]

    override implicit def jsonWrites: OWrites[Foo] = Json.writes[Foo]
  }

  case class Bar(s: String = "", baz: Baz = Baz())

  object Bar extends JsonDefaultValueFormatter[Bar] {
    override def defaultObject: Bar = Bar()

    override def defaultReads: Reads[Bar] = Json.reads[Bar]

    override implicit def jsonWrites: OWrites[Bar] = Json.writes[Bar]
  }

  case class Baz(t: String = "default", b: Boolean = true)

  object Baz extends JsonDefaultValueFormatter[Baz] {
    override def defaultObject: Baz = Baz()

    override def defaultReads: Reads[Baz] = Json.reads[Baz]

    override implicit def jsonWrites: OWrites[Baz] = Json.writes[Baz]
  }

}
