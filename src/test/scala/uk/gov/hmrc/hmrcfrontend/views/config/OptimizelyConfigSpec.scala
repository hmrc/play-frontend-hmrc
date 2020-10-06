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

package uk.gov.hmrc.hmrcfrontend.views.config

import org.scalatest.{Matchers, WordSpec}
import play.api.{Application, Configuration, Environment}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest
import uk.gov.hmrc.hmrcfrontend.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.config.OptimizelyConfig

class OptimizelyConfigSpec extends WordSpec with Matchers with MessagesSupport {

  def buildApp(properties: Map[String, String]) =
    new GuiceApplicationBuilder()
      .configure(properties)
      .build()

  "url" should {
    implicit val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/foo")

    "return the correct optimizely url if optimizely.url and optimizely.projectId are defined" in {
      implicit val application: Application = buildApp(
        Map(
          "optimizely.url"       -> "http://optimizely.com/",
          "optimizely.projectId" -> "1234567"
        ))
      val config = application.injector.instanceOf[OptimizelyConfig]
      config.url should equal(Some("http://optimizely.com/1234567.js"))
    }

    "return None if optimizely.projectId is not defined" in {
      implicit val application: Application = buildApp(
        Map(
          "optimizely.url"               -> "http://optimizely.com/"
        ))
      val config = application.injector.instanceOf[OptimizelyConfig]
      config.url should equal(None)
    }

    "return None if optimizely.url is not defined" in {
      implicit val application: Application = buildApp(
        Map(
          "optimizely.projectId" -> "1234567"
        ))
      val config = application.injector.instanceOf[OptimizelyConfig]
      config.url should equal(None)
    }
  }
}
