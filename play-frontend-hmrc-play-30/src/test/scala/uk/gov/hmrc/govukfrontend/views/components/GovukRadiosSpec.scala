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

class GovukRadiosSpec extends TemplateUnitSpec[Radios, GovukRadios]("govukRadios") {

  "anti-corruption layer" should {
    "throw if more than one radio item is checked" in {
      val invalidRadios = Radios(
        items = Seq(
          RadioItem(value = Some("ketchup"), checked = true),
          RadioItem(value = Some("mayo"), checked = true)
        )
      )

      the[IllegalArgumentException] thrownBy {
        component.render(invalidRadios)
      } should have message "requirement failed: only one checked item allowed in a radio button"
    }

    "throw if passed value conflicts with checked item" in {
      val invalidRadios = Radios(
        items = Seq(
          RadioItem(value = Some("ketchup"), checked = true),
          RadioItem(value = Some("mayo"), checked = false)
        ),
        value = Some("mayo")
      )

      the[IllegalArgumentException] thrownBy {
        component.render(invalidRadios)
      } should have message "requirement failed: value conflicts with checked item"
    }
  }
}
