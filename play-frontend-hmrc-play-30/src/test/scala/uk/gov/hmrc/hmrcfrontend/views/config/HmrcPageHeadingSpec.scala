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

package uk.gov.hmrc.hmrcfrontend.views.config

import org.scalatest.matchers.must.Matchers
import org.scalatest.prop.TableFor2
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
            + """<span class="hmrc-caption govuk-caption-xl">"""
            + """Personal details"""
            + """</span>"""
        ),
        classes = "hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-2 govuk-label--xl"
      )
    }

    "allow caption to be empty" in {
      HmrcPageHeadingLabel(content = Text("What is your name?")) mustBe Label(
        isPageHeading = true,
        content = HtmlContent(
          """What is your name?"""
        ),
        classes = "hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-2 govuk-label--xl"
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
            + """<span class="hmrc-caption govuk-caption-xl">"""
            + """Personal details"""
            + """</span>"""
        ),
        classes = "hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-2 foo bar",
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
            + """<span class="hmrc-caption govuk-caption-xl">"""
            + """&lt;page-section&gt;"""
            + """</span>"""
        ),
        classes = "hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-2 govuk-label--xl"
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
            + """<span class="hmrc-caption govuk-caption-xl">"""
            + """<page-section>"""
            + """</span>"""
        ),
        classes = "hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-2 govuk-label--xl"
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

    "always use last matched label css for caption size" in {
      import org.scalatest.prop.TableDrivenPropertyChecks._

      val cssLabelCombinations: TableFor2[String, String] = Table(
        ("labelCss", "expectedCaptionCss"),
        ("govuk-label--xl govuk-label--m   govuk-label--l", "govuk-caption-l"),
        ("govuk-label--xl  govuk-label--m       govuk-label-l", "govuk-caption-m"),
        ("govuk-label--xl  foo   bar", "govuk-caption-xl"),
        ("govuk-label--l  govuk-label--xx", "govuk-caption-l"),
        (" foo   bar", "govuk-caption-xl"),
        ("", "govuk-caption-xl")
      )

      forAll(cssLabelCombinations) { (labelCss: String, expectedCaptionCss: String) =>
        HmrcPageHeadingLabel(
          content = Text("What is your name?"),
          caption = Text("Personal details"),
          classes = labelCss
        ) mustBe Label(
          isPageHeading = true,
          content = HtmlContent(
            """What is your name? """
              + s"""<span class="hmrc-caption $expectedCaptionCss">"""
              + """Personal details"""
              + """</span>"""
          ),
          classes =
            s"hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-2${if (labelCss.nonEmpty) s" $labelCss"
              else ""}"
        )
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
            + """<span class="hmrc-caption govuk-caption-xl">"""
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
        classes = "foo bar",
        content = HtmlContent(
          """<h1 class="govuk-fieldset__heading hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-0">"""
            + """Where do you live? """
            + """<span class="hmrc-caption govuk-caption-xl">"""
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
            + """<span class="hmrc-caption govuk-caption-xl">"""
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
            + """<span class="hmrc-caption govuk-caption-xl">"""
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

    "always use last matched legend css for caption size" in {
      import org.scalatest.prop.TableDrivenPropertyChecks._

      val cssLegendCombinations: TableFor2[String, String] = Table(
        ("legendCss", "expectedCaptionCss"),
        ("govuk-fieldset__legend--xl govuk-fieldset__legend--m   govuk-fieldset__legend--l", "govuk-caption-l"),
        ("govuk-fieldset__legend--xl  govuk-fieldset__legend--m       govuk-fieldset__legend-l", "govuk-caption-m"),
        ("govuk-fieldset__legend--xl  foo   bar", "govuk-caption-xl"),
        ("govuk-fieldset__legend--m  govuk-fieldset__legend--xx govuk-fieldset__legend--yy", "govuk-caption-m"),
        (" foo   bar", "govuk-caption-xl"),
        ("", "govuk-caption-xl")
      )

      forAll(cssLegendCombinations) { (legendCss: String, expectedCaptionCss: String) =>
        HmrcPageHeadingLegend(
          content = Text("Where do you live?"),
          caption = Text("Personal details"),
          classes = legendCss
        ) mustBe Legend(
          isPageHeading = false,
          content = HtmlContent(
            """<h1 class="govuk-fieldset__heading hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-0">"""
              + """Where do you live? """
              + s"""<span class="hmrc-caption $expectedCaptionCss">"""
              + """Personal details"""
              + """</span>"""
              + """</h1>"""
          ),
          classes = legendCss
        )
      }
    }
  }
}
