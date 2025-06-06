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

import play.api.libs.json.Json
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonRoundtripSpec
import uk.gov.hmrc.govukfrontend.views.viewmodels.servicenavigation.Generators._

class ServiceNavigationItemSpec extends JsonRoundtripSpec[ServiceNavigationItem] {

  "ServiceNavigationItem JSON reader" should {

    "return an default ServiceNavigationItem" when {
      "passed empty JSON object" in {
        val emptyJsonObject = Json.parse("{}")
        val validated       = emptyJsonObject.validate[ServiceNavigationItem]
        validated.isSuccess shouldBe true
        validated.get       shouldBe ServiceNavigationItem()
      }
    }

    "return a validation error" when {
      "passed a raw JSON value" in {
        val rawJsonValue = Json.parse("false")
        val validated    = rawJsonValue.validate[ServiceNavigationItem]
        validated.isError shouldBe true
      }
    }
  }
}
