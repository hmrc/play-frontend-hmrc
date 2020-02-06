/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.components

import org.scalatest.{Matchers, WordSpec}
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.html.components._

class govukTemplateSpec extends WordSpec with Matchers {
  "template rendered with default values" should {
    "not have whitespeace before the doctype" in {
      val templateHtml =
        GovukTemplate
          .apply(htmlLang = None, htmlClasses = None, themeColour = None, bodyClasses = None)(HtmlFormat.empty)
      val component = templateHtml.body
      component.charAt(0) shouldBe '<'
    }
  }
}
