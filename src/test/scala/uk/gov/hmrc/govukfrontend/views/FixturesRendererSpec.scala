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

package uk.gov.hmrc.govukfrontend.views

import org.scalatest.{Matchers, OptionValues, WordSpec}
import GovukFrontendDependency._

class FixturesRendererSpec extends WordSpec with Matchers with OptionValues {

  "findFirstMatch" should {
    "find the first match of a regular expression in a Seq" in {
      val dependencies = Seq(
        "org.scala-lang:scala-library:2.11.12",
        "com.typesafe.play:twirl-api:1.3.15",
        "com.typesafe.play:play-server:2.6.23",
        "com.typesafe.play:play-test:2.6.23:test",
        "com.typesafe.play:play-omnidoc:2.6.23:docs",
        "com.typesafe.play:filters-helpers:2.6.23",
        "com.typesafe.play:play-logback:2.6.23",
        "com.typesafe.play:play-akka-http-server:2.6.23",
        "com.typesafe.play:play:2.6.23",
        "com.typesafe.play:filters-helpers:2.6.23",
        "org.joda:joda-convert:2.0.2",
        "org.webjars.npm:govuk-frontend:2.11.0",
        "org.scalatest:scalatest:3.0.5:test",
        "org.pegdown:pegdown:1.6.0:test",
        "org.jsoup:jsoup:1.11.3:test",
        "com.typesafe.play:play-test:2.6.23:test",
        "org.scalacheck:scalacheck:1.14.0:test",
        "com.googlecode.htmlcompressor:htmlcompressor:1.5.2:test",
        "com.github.pathikrit:better-files:3.8.0:test",
        "org.scalatestplus.play:scalatestplus-play:3.1.2"
      )

      findFirstMatch(govukFrontendVersionRegex, dependencies).value.group(1) shouldBe "2.11.0"
    }
  }
}
