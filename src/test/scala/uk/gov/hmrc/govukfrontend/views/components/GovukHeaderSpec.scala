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

package uk.gov.hmrc.govukfrontend.views
package components

import uk.gov.hmrc.govukfrontend.views.html.components._

class GovukHeaderSpec extends TemplateUnitSpec[Header, GovukHeader]("govukHeader") {

  // The following line is needed to ensure known state of the statically initialised reverse router
  // used to calculate asset paths
  hmrcfrontend.RoutesPrefix.setPrefix("")

  "header" should {
    "have a role of banner" in {
      val output = component(Header()).select(".govuk-header")
      output.attr("role") shouldBe "banner"
    }
  }

  "not render link when content are not passed" in {
    val params = Header(navigation = Some(Seq(HeaderNavigation(href = Some("#")))))
    val output = component(params).select(".govuk-header__navigation-item .govuk-header__link")
    output.size should be(0)
  }

  "not render link when href is empty" in {
    val params = Header(navigation = Some(Seq(HeaderNavigation(href = Some(""), content = HtmlContent("asdf")))))
    val output = component(params).select(".govuk-header__navigation-item .govuk-header__link")
    output.size should be(0)
  }

  "render text when passed in text field" in {
    val params = Header(navigation = Some(Seq(HeaderNavigation(text = Some("Regular text"), href = Some("#")))))
    val output = component(params).select(".govuk-header__navigation-item .govuk-header__link")

    output.html should include("Regular text")
  }

  "render text when passed in content field" in {
    val params = Header(navigation = Some(Seq(HeaderNavigation(href = Some("#"), content = Text("Some text")))))
    val output = component(params).select(".govuk-header__navigation-item .govuk-header__link")

    output.html should include("Some text")
  }

  "render html when passed" in {
    val params = Header(navigation =
      Some(Seq(HeaderNavigation(href = Some("#"), content = HtmlContent("<strong>Some HTML</strong>"))))
    )
    val output = component(params).select(".govuk-header__navigation-item .govuk-header__link")

    output.html should include("<strong>Some HTML</strong>")
  }

  "render html if text and html are passed" in {
    val params = Header(navigation =
      Some(
        Seq(
          HeaderNavigation(
            href = Some("#"),
            text = Some("Alternate text"),
            content = HtmlContent("<strong>Some text</strong>")
          )
        )
      )
    )
    val output = component(params).select(".govuk-header__navigation-item .govuk-header__link")

    output.html should include("<strong>Some text</strong>")
  }

  "use the provided assetsPath for the fallback SVG image" in {
    val output = component(Header(assetsPath = Some("/foo/bar")))

    output.body should include("""<img src="/foo/bar""")
  }
}
