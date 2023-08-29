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

import uk.gov.hmrc.govukfrontend.views.html.components._

class GovukSelectSpec extends TemplateUnitSpec[Select, GovukSelect]("govukSelect") {

  "anti-corruption layer" should {
    "throw if name is not provided" in {
      val invalidSelect = Select(id = "someId")

      the[IllegalArgumentException] thrownBy {
        component.render(invalidSelect)
      } should have message "requirement failed: parameter 'name' should not be empty"
    }

    "throw if id is not provided" in {
      val invalidSelect = Select(name = "someName")

      the[IllegalArgumentException] thrownBy {
        component.render(invalidSelect)
      } should have message "requirement failed: parameter 'id' should not be empty"
    }

    "throw if passed value conflicts with selected item" in {
      val invalidSelect = Select(
        name = "my select",
        id = "select1",
        items = Seq(
          SelectItem(value = Some("ketchup"), selected = true),
          SelectItem(value = Some("mayo"), selected = false)
        ),
        value = Some("mayo")
      )

      the[IllegalArgumentException] thrownBy {
        component.render(invalidSelect)
      } should have message "requirement failed: selected item(s) conflict with passed value"
    }
  }

}
