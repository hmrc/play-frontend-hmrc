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

package uk.gov.hmrc.hmrcfrontend.views.implicits

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.Configuration
import uk.gov.hmrc.govukfrontend.views.Aliases.ServiceNavigation
import uk.gov.hmrc.govukfrontend.views.Implicits._
import uk.gov.hmrc.helpers.MessagesSupport

class RichServiceNavigationSpec extends AnyWordSpec with Matchers with MessagesSupport {

  val configuration = Configuration.apply("language.fallback.url" -> "url")

  "Given ServiceNavigation object, calling withLanguageToggle" should {
    "populate the language toggle without any classes" in {
      val serviceNavigation = ServiceNavigation()

      val serviceNav = serviceNavigation.withLanguageToggle()(configuration)

      serviceNav.classes                    shouldBe "hmrc-service-navigation--with-language-select"
      serviceNav.slots.map(_.end).isDefined shouldBe true
    }

    "populate the language toggle and amend language toggle classes" in {
      val serviceNavigation = ServiceNavigation(classes = "custom-class")

      val serviceNav = serviceNavigation.withLanguageToggle()(configuration)

      serviceNav.classes                    shouldBe "custom-class hmrc-service-navigation--with-language-select"
      serviceNav.slots.map(_.end).isDefined shouldBe true
    }
  }
}
