/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels.servicenavigation

import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonRoundtripSpec
import uk.gov.hmrc.govukfrontend.views.viewmodels.servicenavigation.Generators._

class ServiceNavigationSpec extends JsonRoundtripSpec[ServiceNavigation] {

  "ServiceNavigation.shouldDisplayNavigation" should {

    "return true" when {
      "navigation is nonEmpty" in {
        val serviceNavigation = ServiceNavigation(
          navigation = Seq(ServiceNavigationItem())
        )

        serviceNavigation.shouldDisplayNavigation shouldBe true
      }

      "navigationStart is nonEmpty" in {
        val serviceNavigation = ServiceNavigation(
          slots = Some(ServiceNavigationSlot(navigationStart = Some("navigationStart")))
        )

        serviceNavigation.shouldDisplayNavigation shouldBe true
      }

      "navigationEnd is nonEmpty" in {
        val serviceNavigation = ServiceNavigation(
          slots = Some(ServiceNavigationSlot(navigationEnd = Some("navigationEnd")))
        )

        serviceNavigation.shouldDisplayNavigation shouldBe true
      }

      "navigation and navigationStart are nonEmpty" in {
        val serviceNavigation = ServiceNavigation(
          navigation = Seq(ServiceNavigationItem()),
          slots = Some(ServiceNavigationSlot(navigationStart = Some("navigationStart")))
        )

        serviceNavigation.shouldDisplayNavigation shouldBe true
      }

      "navigation and navigationEnd are nonEmpty" in {
        val serviceNavigation = ServiceNavigation(
          navigation = Seq(ServiceNavigationItem()),
          slots = Some(ServiceNavigationSlot(navigationEnd = Some("navigationEnd")))
        )

        serviceNavigation.shouldDisplayNavigation shouldBe true
      }

      "navigationStart and navigationEnd are nonEmpty" in {
        val serviceNavigation = ServiceNavigation(
          slots = Some(
            ServiceNavigationSlot(
              navigationStart = Some("navigationStart"),
              navigationEnd = Some("navigationEnd")
            )
          )
        )

        serviceNavigation.shouldDisplayNavigation shouldBe true
      }

      "navigation, navigationStart and navigationEnd are nonEmpty" in {
        val serviceNavigation = ServiceNavigation(
          navigation = Seq(ServiceNavigationItem()),
          slots = Some(
            ServiceNavigationSlot(
              navigationStart = Some("navigationStart"),
              navigationEnd = Some("navigationEnd")
            )
          )
        )

        serviceNavigation.shouldDisplayNavigation shouldBe true
      }
    }

    "return false" when {
      "navigation, navigationStart and navigationEnd are empty" in {
        val serviceNavigation = ServiceNavigation()

        serviceNavigation.shouldDisplayNavigation shouldBe false
      }
    }
  }
}
