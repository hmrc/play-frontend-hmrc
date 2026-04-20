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
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.typedmap.TypedMap
import play.api.test.FakeRequest
import uk.gov.hmrc.hmrcfrontend.config.ServiceNavCanBeControlledByRequestAttr.UseServiceNav

class ServiceNavCanBeControlledByRequestAttrSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {

  "forceServiceNavigation" should {
    val config = app.injector.instanceOf[ServiceNavCanBeControlledByRequestAttr]

    "return true" when {
      "UseServiceNav request attr is true" in {
        config.forceServiceNavigation(
          FakeRequest().withAttrs(
            TypedMap(
              UseServiceNav -> true
            )
          )
        ) shouldBe true
      }

      "UseServiceNav request attr is not set, but the default has been overridden to true" in {
        val configEnabledByDefault = new GuiceApplicationBuilder()
          .configure("play-frontend-hmrc.forceServiceNavigation" -> "true")
          .build()
          .injector
          .instanceOf[ServiceNavCanBeControlledByRequestAttr]

        configEnabledByDefault.forceServiceNavigation(FakeRequest()) shouldBe true
      }
    }

    "return false" when {
      "UseServiceNav request attr is not set, and the default has not been overridden" in {
        config.forceServiceNavigation(FakeRequest()) shouldBe false
      }

      "the default has been overridden to true, but UseServiceNav request attr is explicitly false" in {
        val configEnabledByDefault = new GuiceApplicationBuilder()
          .configure("play-frontend-hmrc.forceServiceNavigation" -> "true")
          .build()
          .injector
          .instanceOf[ServiceNavCanBeControlledByRequestAttr]

        configEnabledByDefault.forceServiceNavigation(
          FakeRequest().withAttrs(
            TypedMap(
              UseServiceNav -> false
            )
          )
        ) shouldBe false
      }
    }
  }
}
