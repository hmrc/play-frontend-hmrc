/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels.header

import play.api.libs.json.Json
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonRoundtripSpec
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Empty, HtmlContent, Text}
import Generators._

class HeaderNavigationSpec extends JsonRoundtripSpec[HeaderNavigation] {
  "HeaderNavigation" should {
    "round trip html content successfully" in {
      val testData =
        HeaderNavigation(None, Some("link"), active = true, Map("abc" -> "def"), HtmlContent("<strong>Html</strong>"))

      val json     = Json.toJson[HeaderNavigation](testData)

      json.as[HeaderNavigation] should be(
        HeaderNavigation(None, Some("link"), active = true, Map("abc" -> "def"), HtmlContent("<strong>Html</strong>"))
      )
    }

    "round trip empty content successfully" in {
      val testData =
        HeaderNavigation(None, Some("link"), active = true, Map("abc" -> "def"), Empty)

      val json     = Json.toJson[HeaderNavigation](testData)

      json.as[HeaderNavigation] should be(
        HeaderNavigation(None, Some("link"), active = true, Map("abc" -> "def"), Empty)
      )
    }

    "round trip non-Empty content successfully" in {
      val testData =
        HeaderNavigation(None, Some("link"), active = true, Map("abc" -> "def"), HtmlContent(""))

      val json     = Json.toJson[HeaderNavigation](testData)

      json.as[HeaderNavigation] should be(
        HeaderNavigation(None, Some("link"), active = true, Map("abc" -> "def"), HtmlContent(""))
      )
    }

    "move any text content to the content field" in {
      val testData =
        HeaderNavigation(Some("text"), Some("link"), active = true, Map("abc" -> "def"), Empty)

      val json     = Json.toJson[HeaderNavigation](testData)

      json.as[HeaderNavigation] should be(
        HeaderNavigation(None, Some("link"), active = true, Map("abc" -> "def"), Text("text"))
      )
    }

    "ignore any text in the text field if the content field is non-empty" in {
      val testData = HeaderNavigation(Some("text:text"), Some("link"), active = true, Map(), Text("content:text"))

      val json = Json.toJson[HeaderNavigation](testData)

      json.as[HeaderNavigation] should be(
        HeaderNavigation(text = None, Some("link"), active = true, Map(), Text("content:text"))
      )
    }
  }
}
