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

class GovukButtonSpec extends TemplateUnitSpec[Button, GovukButton]("govukButton") {

  "button element" should {
    "render the default example" in {
      val output =
        component(Button(content = Text("Save and continue")))
          .select(".govuk-button")

      output.first.tagName shouldBe "button"
      output.text            should include("Save and continue")
    }
  }
}
