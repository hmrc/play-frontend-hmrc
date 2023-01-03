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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.notificationbadge

import play.api.libs.json.Json
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.JsonRoundtripSpec
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.notificationbadge.Generators._

class NotificationBadgeSpec extends JsonRoundtripSpec[NotificationBadge] {
  "NotificationBadge" should {
    "accept strings" in {
      val testData = NotificationBadge("3")

      val json = Json.toJson[NotificationBadge](testData)

      json.as[NotificationBadge] should be(NotificationBadge("3"))
    }
    "accept integers" in {
      val testData = NotificationBadge(1)

      val json = Json.toJson[NotificationBadge](testData)

      json.as[NotificationBadge] should be(NotificationBadge("1"))
    }
    "accept integers via JSON" in {
      val json = Json.parse("{\"text\": 3}")

      json.as[NotificationBadge] should be(NotificationBadge("3"))
    }
    "allow strings from JSON" in {
      val json = Json.parse("{\"text\": \"New\"}")

      json.as[NotificationBadge] should be(NotificationBadge("New"))
    }
  }
}
