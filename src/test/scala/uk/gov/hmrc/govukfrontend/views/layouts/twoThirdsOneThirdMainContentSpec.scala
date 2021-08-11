/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.layouts

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.html.components.TwoThirdsOneThirdMainContent
import org.scalatestplus.play.guice.GuiceOneAppPerSuite

class twoThirdsOneThirdMainContentSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {

  val component = app.injector.instanceOf[TwoThirdsOneThirdMainContent]

  "Given a contentBlock of HTML, rendering the twoThirdsOneThirdMainContent" should {
    "render as expected" in {
      val contentBlock: Html =
        Html("<h1 class=\"govuk-heading-xl\">Page heading</h1><p class=\"govuk-body\">Some page content</p>")

      val sidebarBlock: Html =
        Html("<h2 class=\"govuk-heading-l\">Sidebar heading</h2><p class=\"govuk-body\">Some sidebar content</p>")

      component(sidebarBlock)(contentBlock) shouldBe Html(
        "\n<div class=\"govuk-grid-row\">\n" +
          "    <div class=\"govuk-grid-column-two-thirds\">\n" +
          "        <h1 class=\"govuk-heading-xl\">Page heading</h1><p class=\"govuk-body\">Some page content</p>\n" +
          "    </div>\n\n" +
          "    <div class=\"govuk-grid-column-one-third\">\n" +
          "        <h2 class=\"govuk-heading-l\">Sidebar heading</h2><p class=\"govuk-body\">Some sidebar content</p>\n" +
          "    </div>\n" +
          "</div>\n"
      )
    }
  }
}
