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

import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, OptionValues, WordSpec}
import play.api.libs.json.{JsNumber, JsString, JsSuccess, Json, _}
import uk.gov.hmrc.govukfrontend.views.viewmodels.common.{HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.label
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.LabelParams
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist._

class ReadsHelpersSpec
    extends WordSpec
    with Matchers
    with ReadsHelpers
    with OptionValues
    with PropertyChecks
    with NoShrink {

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

  "reads of Row" should {
    "deserialize a Row" in {
      val json = Json.parse(
        """{
          |      "key": {
          |        "text": "Previous application number"
          |      },
          |      "value": {
          |        "text": 502135326
          |      },
          |      "actions": {
          |        "items": [
          |          {
          |            "href": "#",
          |            "text": "Change",
          |            "visuallyHiddenText": "previous application number"
          |          }
          |        ]
          |      }
          |    }""".stripMargin
      )

      json.validate[Row] shouldBe JsSuccess(
        Row(
          key   = Key(contents   = Text("Previous application number")),
          value = Value(contents = Text("502135326")),
          actions = Some(Actions(items =
            Seq(Item(href = "#", contents = Text("Change"), visuallyHiddenText = Some("previous application number")))))
        )
      )
    }
  }

  "readsJsValueToString" should {
    "deserialize any value as a String" in {
      import Generators._

      val reads = (__ \ "field").readsJsValueToString

      forAll(genJsValue) { jsValue =>
        val json = Json.obj("field" -> jsValue)

        json.validate[String](reads).asOpt.value shouldBe (jsValue match {
          case JsString(s) => s
          case x           => x.toString
        })
      }
    }
  }

  object Generators {
    val genJsString = for {
      s <- Gen.alphaStr
    } yield JsString(s)

    val genJsNumber = for {
      n <- Gen.choose(0, 1000)
    } yield JsNumber(n)

    val genJsValue =
      Gen.frequency(
        (50, genJsString),
        (50, genJsNumber)
      )
  }
}
