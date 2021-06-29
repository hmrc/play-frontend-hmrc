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

package uk.gov.hmrc.govukfrontend.views.components

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.TemplateUnitSpec
import uk.gov.hmrc.govukfrontend.views.html.components._
import scala.util.Try

class govukHeaderSpec extends TemplateUnitSpec[Header]("govukHeader") {

  "header" should {
    "have a role of banner" in {
      val component = GovukHeader(Header()).select(".govuk-header")
      component.attr("role") shouldBe "banner"
    }
  }

  "not render link when content are not passed" in {
    val params    = Header(navigation = Some(Seq(HeaderNavigation(href = Some("#")))))
    val component = GovukHeader(params).select(".govuk-header__navigation-item .govuk-header__link")
    component.size should be(0)
  }

  "not render link when href is empty" in {
    val params    = Header(navigation = Some(Seq(HeaderNavigation(href = Some(""), content = HtmlContent("asdf")))))
    val component = GovukHeader(params).select(".govuk-header__navigation-item .govuk-header__link")
    component.size should be(0)
  }

  "render text when passed in text field" in {
    val params    = Header(navigation = Some(Seq(HeaderNavigation(text = Some("Regular text"), href = Some("#")))))
    val component = GovukHeader(params).select(".govuk-header__navigation-item .govuk-header__link")

    component.html should include("Regular text")
  }

  "render text when passed in content field" in {
    val params    = Header(navigation = Some(Seq(HeaderNavigation(href = Some("#"), content = Text("Some text")))))
    val component = GovukHeader(params).select(".govuk-header__navigation-item .govuk-header__link")

    component.html should include("Some text")
  }

  "render html when passed" in {
    val params    = Header(navigation =
      Some(Seq(HeaderNavigation(href = Some("#"), content = HtmlContent("<strong>Some HTML</strong>"))))
    )
    val component = GovukHeader(params).select(".govuk-header__navigation-item .govuk-header__link")

    component.html should include("<strong>Some HTML</strong>")
  }

  "render html if text and html are passed" in {
    val params    = Header(navigation =
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
    val component = GovukHeader(params).select(".govuk-header__navigation-item .govuk-header__link")

    component.html should include("<strong>Some text</strong>")
  }

  "use the provided assetsPath for the fallback SVG image" in {
    val component = GovukHeader(Header(assetsPath = Some("/foo/bar")))

    component.body should include("""<img src="/foo/bar""")
  }

  /**
    * Calls the Twirl template with the given parameters and returns the resulting markup
    *
    * @param templateParams
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  override def render(templateParams: Header): Try[HtmlFormat.Appendable] =
    Try(GovukHeader(templateParams))
}
