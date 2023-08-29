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

package uk.gov.hmrc.govukfrontend.views
package components

import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.html.components._

class GovukCheckboxesSpec extends TemplateUnitSpec[Checkboxes, GovukCheckboxes]("govukCheckboxes") {

  "anti-corruption layer" should {
    "throw if name is not provided" in {
      val invalidCheckboxes = Checkboxes()

      the[IllegalArgumentException] thrownBy {
        component.render(invalidCheckboxes)
      } should have message "requirement failed: parameter 'name' should not be empty"
    }

    "throw if passed values conflict with checked items" in {
      val invalidCheckboxes = Checkboxes(
        name = "my checkbox",
        items = Seq(
          CheckboxItem(value = "ketchup", checked = true),
          CheckboxItem(value = "mayo", checked = false)
        ),
        values = Set("ketchup", "mayo")
      )

      the[IllegalArgumentException] thrownBy {
        component.render(invalidCheckboxes)
      } should have message "requirement failed: checked item(s) conflict with passed value(s)"
    }

    "not throw if passed values are ordered differently to the checked items" in {
      val validCheckboxes = Checkboxes(
        name = "my checkbox",
        items = Seq(
          CheckboxItem(value = "ketchup", checked = true),
          CheckboxItem(value = "mayo", checked = false),
          CheckboxItem(value = "mustard", checked = true)
        ),
        values = Set("mustard", "ketchup")
      )

      component.render(validCheckboxes) shouldBe an[Html]
    }
  }

}
