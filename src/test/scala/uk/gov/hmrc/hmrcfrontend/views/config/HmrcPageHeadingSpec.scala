/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.config

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.govukfrontend.views.Aliases.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.HtmlContent
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.Legend
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label

class HmrcPageHeadingSpec extends AnyWordSpec with Matchers {

  "HmrcPageHeadingLabel" must {
    "construct expected label view model" in {
      HmrcPageHeadingLabel(content = Text("What is your name?"), caption = Text("Personal details")) mustBe Label(
        isPageHeading = true,
        content = HtmlContent(
          """What is your name? """
            + """<span class="hmrc-caption">"""
            + """Personal details"""
            + """</span>"""
        ),
        classes = "govuk-label--xl hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-2"
      )
    }

    "allow caption to be empty" in {
      HmrcPageHeadingLabel(content = Text("What is your name?")) mustBe Label(
        isPageHeading = true,
        content = HtmlContent(
          """What is your name?"""
        ),
        classes = "govuk-label--xl hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-2"
      )
    }

    "accept custom classes and attributes" in {
      HmrcPageHeadingLabel(
        content = Text("What is your name?"),
        caption = Text("Personal details"),
        classes = "foo bar",
        attributes = Map("foo" -> "bar")
      ) mustBe Label(
        isPageHeading = true,
        content = HtmlContent(
          """What is your name? """
            + """<span class="hmrc-caption">"""
            + """Personal details"""
            + """</span>"""
        ),
        classes = "govuk-label--xl hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-2 foo bar",
        attributes = Map("foo" -> "bar")
      )
    }

    "escape unsafe html output" in {
      HmrcPageHeadingLabel(
        content = Text("<page-title>"),
        caption = Text("<page-section>")
      ) mustBe Label(
        isPageHeading = true,
        content = HtmlContent(
          """&lt;page-title&gt; """
            + """<span class="hmrc-caption">"""
            + """&lt;page-section&gt;"""
            + """</span>"""
        ),
        classes = "govuk-label--xl hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-2"
      )
    }

    "not escape safe html output" in {
      HmrcPageHeadingLabel(
        content = HtmlContent("<page-title>"),
        caption = HtmlContent("<page-section>")
      ) mustBe Label(
        isPageHeading = true,
        content = HtmlContent(
          """<page-title> """
            + """<span class="hmrc-caption">"""
            + """<page-section>"""
            + """</span>"""
        ),
        classes = "govuk-label--xl hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-2"
      )
    }

    "require content to be non empty" in {
      try {
        HmrcPageHeadingLabel(
          content = Text("")
        )
        fail("construction succeeded with empty text")
      } catch {
        case e: IllegalArgumentException => // pass
      }
    }
  }

  "HmrcPageHeadingLegend" must {
    "construct expected legend view model" in {
      HmrcPageHeadingLegend(content = Text("Where do you live?"), caption = Text("Personal details")) mustBe Legend(
        isPageHeading = false,
        content = HtmlContent(
          """<h1 class="govuk-fieldset__heading hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-0">"""
            + """Where do you live? """
            + """<span class="hmrc-caption">"""
            + """Personal details"""
            + """</span>"""
            + """</h1>"""
        ),
        classes = "govuk-fieldset__legend--xl"
      )
    }

    "allow caption to be empty" in {
      HmrcPageHeadingLegend(content = Text("Where do you live?")) mustBe Legend(
        isPageHeading = false,
        content = HtmlContent(
          """<h1 class="govuk-fieldset__heading hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-0">"""
            + """Where do you live?"""
            + """</h1>"""
        ),
        classes = "govuk-fieldset__legend--xl"
      )
    }

    "accept custom classes" in {
      HmrcPageHeadingLegend(
        content = Text("Where do you live?"),
        caption = Text("Personal details"),
        classes = "foo bar"
      ) mustBe Legend(
        isPageHeading = false,
        classes = "govuk-fieldset__legend--xl foo bar",
        content = HtmlContent(
          """<h1 class="govuk-fieldset__heading hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-0">"""
            + """Where do you live? """
            + """<span class="hmrc-caption">"""
            + """Personal details"""
            + """</span>"""
            + """</h1>"""
        )
      )
    }

    "escape unsafe html output" in {
      HmrcPageHeadingLegend(
        content = Text("<page-title>"),
        caption = Text("<page-section>")
      ) mustBe Legend(
        isPageHeading = false,
        classes = "govuk-fieldset__legend--xl",
        content = HtmlContent(
          """<h1 class="govuk-fieldset__heading hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-0">"""
            + """&lt;page-title&gt; """
            + """<span class="hmrc-caption">"""
            + """&lt;page-section&gt;"""
            + """</span>"""
            + """</h1>"""
        )
      )
    }

    "not escape safe html output" in {
      HmrcPageHeadingLegend(
        content = HtmlContent("<page-title>"),
        caption = HtmlContent("<page-section>")
      ) mustBe Legend(
        isPageHeading = false,
        classes = "govuk-fieldset__legend--xl",
        content = HtmlContent(
          """<h1 class="govuk-fieldset__heading hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-0">"""
            + """<page-title> """
            + """<span class="hmrc-caption">"""
            + """<page-section>"""
            + """</span>"""
            + """</h1>"""
        )
      )
    }

    "require content to be non empty" in {
      try {
        HmrcPageHeadingLegend(
          content = Text("")
        )
        fail("construction succeeded with empty text")
      } catch {
        case e: IllegalArgumentException => // pass
      }
    }
  }

}
