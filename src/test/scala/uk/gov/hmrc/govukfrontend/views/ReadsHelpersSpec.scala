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

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{JsSuccess, Json}
import uk.gov.hmrc.govukfrontend.views.viewmodels.common.HtmlContent
import uk.gov.hmrc.govukfrontend.views.viewmodels.label
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.LabelParams

class ReadsHelpersSpec extends WordSpec with Matchers with ReadsHelpers {
  "readsLabelParams" should {
    "deserialize LabelParams" in {
      val json = Json.parse(
        """
          |{
           |"for": "abc",
           |"isPageHeading": true,
           |"classes": "class1 class2",
           |"attributes": {
           |  "attr1": "val1",
           |  "attr2": "val2"
           |},
           |"html": "<p>a paragraph</p>"
          |}
          |""".stripMargin
      )

      json.validate[LabelParams] shouldBe JsSuccess(
        label.LabelParams(
          forAttr       = Some("abc"),
          isPageHeading = true,
          classes       = "class1 class2",
          attributes    = Map("attr1" -> "val1", "attr2" -> "val2"),
          contents      = HtmlContent("<p>a paragraph</p>")
        ))
    }
  }
}
