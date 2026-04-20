/*
 * Copyright 2025 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.config

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.RequestHeader
import play.api.test.FakeRequest

class ServiceNavCanBeControlledByQueryParamSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {

  "forceServiceNavigation" should {
    val config = app.injector.instanceOf[ServiceNavCanBeControlledByQueryParam]

    "return true" when {
      "url contains useServiceNav" in {
        config.forceServiceNavigation(FakeRequest("GET", "/test?useServiceNav")) shouldBe true
      }

      "url contains useServiceNav=true" in {
        config.forceServiceNavigation(FakeRequest("GET", "/test?useServiceNav=true")) shouldBe true
      }

      "url contains useServiceNav=false - because it's not a boolean, it's based on presence" in {
        config.forceServiceNavigation(FakeRequest("GET", "/test?useServiceNav=false")) shouldBe true
      }
    }

    "return false" when {
      "url does not contain useServiceNav" in {
        config.forceServiceNavigation(FakeRequest("GET", "/test")) shouldBe false
      }

      "url contains urlServiceNavigation - because it should match exactly only" in {
        config.forceServiceNavigation(FakeRequest("GET", "/test?useServiceNavigation")) shouldBe false
      }
    }
  }
}
