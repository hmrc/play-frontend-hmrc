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

package uk.gov.hmrc.govukfrontend.views.viewmodels.attributes

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import play.twirl.api.Html

class AttributesSpec extends AnyWordSpecLike with Matchers {

  // As described in https://github.com/alphagov/govuk-frontend/blob/main/packages/govuk-frontend/src/govuk/macros/attributes.njk

  "Given a single Attribute, calling toString" should {

    "render as expected when value is defined as `true`" in {
      //  Output attribute ` aria-hidden="true"` when `true` (boolean) or `"true"` (string)
      //  govukAttributes({
      //    "aria-hidden": true
      //  })
      val stringAttribute = StringAttribute(
        name = "aria-hidden",
        value = Some("true")
      )

      val booleanAttribute = BooleanAttribute(
        name = "aria-hidden",
        value = Some(true)
      )

      stringAttribute.toString  shouldBe "aria-hidden=\"true\""
      booleanAttribute.toString shouldBe "aria-hidden=\"true\""
    }

    "render as expected when value is defined as `false`" in {
      //  Output attribute ` aria-hidden="false"` when `false` (boolean) or `"false"` (string)
      //  govukAttributes({
      //    "aria-hidden": false
      //  })
      val stringAttribute = StringAttribute(
        name = "aria-hidden",
        value = Some("false")
      )

      val booleanAttribute = BooleanAttribute(
        name = "aria-hidden",
        value = Some(false)
      )

      stringAttribute.toString  shouldBe "aria-hidden=\"false\""
      booleanAttribute.toString shouldBe "aria-hidden=\"false\""
    }

    "render as expected when value is empty string" in {
      //  Output attribute ` hidden=""` when `null`, `undefined` or empty `""` (string)
      //  govukAttributes({
      //    "hidden": undefined
      //  })
      val attribute = StringAttribute(
        name = "hidden",
        value = Some("")
      )

      attribute.toString shouldBe "hidden=\"\""
    }

    "render as expected when value is undefined" in {
      //  Output attribute ` hidden=""` when `null`, `undefined` or empty `""` (string)
      //  govukAttributes({
      //    "hidden": undefined
      //  })
      val attribute = StringAttribute(
        name = "hidden",
        value = None
      )

      attribute.toString shouldBe "hidden=\"\""
    }

    "render as expected when attribute `optional` is true and value is `true`" in {
      // Output attribute ` hidden` as boolean attribute when optional and `true`
      // govukAttributes({
      //    hidden: {
      //      value: true,
      //      optional: true
      //    },
      //  })
      val stringAttribute  = StringAttribute(
        name = "hidden",
        value = Some("true"),
        optional = true
      )
      val booleanAttribute = BooleanAttribute(
        name = "hidden",
        value = Some(true),
        optional = true
      )

      stringAttribute.toString  shouldBe "hidden=\"true\""
      booleanAttribute.toString shouldBe "hidden"
    }

    "render as expected optional` is true and value is None" in {
      // Output empty string when optional and `null`, `undefined` or `false`
      //  govukAttributes({
      //    hidden: {
      //      value: undefined,
      //      optional: true
      //    },
      //  })
      val stringAttribute = StringAttribute(
        name = "hidden",
        value = None,
        optional = true
      )

      val booleanAttribute = BooleanAttribute(
        name = "hidden",
        value = None,
        optional = true
      )

      stringAttribute.toString  shouldBe ""
      booleanAttribute.toString shouldBe ""
    }

    "render as expected whe `optional` is true and value is `false`" in {
      // Output empty string when optional and `null`, `undefined` or `false`
      //  govukAttributes({
      //    hidden: {
      //      value: undefined,
      //      optional: true
      //    },
      //  })
      val stringAttribute = StringAttribute(
        name = "hidden",
        value = Some("false"),
        optional = true
      )

      val booleanAttribute = BooleanAttribute(
        name = "hidden",
        value = Some(false),
        optional = true
      )

      stringAttribute.toString  shouldBe "hidden=\"false\""
      booleanAttribute.toString shouldBe ""
    }
  }

  "Given a list of boolean and string attributes, converting to HTML" should {
    "return HTML of a space delineated list of attributes" in {
      val attributes = Attributes(
        Seq(
          StringAttribute("attr-one", Some("true")),
          StringAttribute("attr-two", Some("")),
          BooleanAttribute("attr-three", Some(false), optional = true),
          StringAttribute("attr-four", Some("false"), optional = true),
          StringAttribute("attr-five", None, optional = true)
        )
      )

      attributes.toString().trim shouldBe Html("attr-one=\"true\" attr-two=\"\" attr-four=\"false\"").toString()
    }
  }
}
