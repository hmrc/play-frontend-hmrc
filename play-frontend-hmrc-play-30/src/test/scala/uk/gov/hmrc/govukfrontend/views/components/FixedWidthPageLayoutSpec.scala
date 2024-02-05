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

package uk.gov.hmrc.govukfrontend.views.components

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.Aliases.PageLayout
import uk.gov.hmrc.govukfrontend.views.html.components.FixedWidthPageLayout

class FixedWidthPageLayoutSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {

  private val component = app.injector.instanceOf[FixedWidthPageLayout]

  "Given a PageLayout render it at fixed width" should {
    "render as expected" in {
      component(
        PageLayout(
          Some(Html("beforeContentBlock")),
          contentBlock = Html("contentBlock"),
          mainLang = Some("en"),
          mainClasses = Some("custom-class")
        )
      ) shouldBe Html(
        s"""
           |
           |<div class="govuk-width-container">
           |    beforeContentBlock
           |    <main class="govuk-main-wrapper custom-class" id="main-content" role="main" lang="en">
           |        contentBlock
           |    </main>
           |</div>""".stripMargin
      )
    }
  }
}
