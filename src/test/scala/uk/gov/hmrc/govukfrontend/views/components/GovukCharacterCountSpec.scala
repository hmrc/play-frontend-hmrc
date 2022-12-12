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

package uk.gov.hmrc.govukfrontend.views
package components

import uk.gov.hmrc.govukfrontend.views.html.components._

class GovukCharacterCountSpec extends TemplateUnitSpec[CharacterCount, GovukCharacterCount]("govukCharacterCount") {

  "characterCount" should {
    "render with all additional language localisation attributes" in {
      val charactersUnderLimitText: Option[Map[String, String]] = Some(
        Map(
          "one"   -> "%{count} character to go",
          "other" -> "%{count} characters to go"
        )
      )

      val charactersAtLimitText = Some("Character limit reached")

      val charactersOverLimitText: Option[Map[String, String]] = Some(
        Map(
          "one"   -> "%{count} character to many",
          "other" -> "%{count} characters to many",
          "many"  -> "Way over limit"
        )
      )

      val wordsUnderLimitText: Option[Map[String, String]] = Some(
        Map(
          "one" -> "%{count} word to go",
          "two" -> "Two words to go"
        )
      )

      val wordsAtLimitText = Some("Word limit reached")

      val wordsOverLimitText: Option[Map[String, String]] = Some(
        Map(
          "xxx" -> "Just an additional attribute"
        )
      )

      val params = CharacterCount(
        id = "characterCount",
        name = "testCharacterCount",
        charactersUnderLimitText = charactersUnderLimitText,
        charactersAtLimitText = charactersAtLimitText,
        charactersOverLimitText = charactersOverLimitText,
        wordsAtLimitText = wordsAtLimitText,
        wordsUnderLimitText = wordsUnderLimitText,
        wordsOverLimitText = wordsOverLimitText
      )

      val output = component(params).select(".govuk-character-count")

      output.attr("data-i18n.characters-under-limit.one")   shouldBe "%{count} character to go"
      output.attr("data-i18n.characters-under-limit.other") shouldBe "%{count} characters to go"
      output.attr("data-i18n.characters-at-limit")          shouldBe "Character limit reached"
      output.attr("data-i18n.characters-over-limit.one")    shouldBe "%{count} character to many"
      output.attr("data-i18n.characters-over-limit.other")  shouldBe "%{count} characters to many"
      output.attr("data-i18n.characters-over-limit.many")   shouldBe "Way over limit"
      output.attr("data-i18n.words-at-limit")               shouldBe "Word limit reached"
      output.attr("data-i18n.words-under-limit.one")        shouldBe "%{count} word to go"
      output.attr("data-i18n.words-under-limit.two")        shouldBe "Two words to go"
      output.attr("data-i18n.words-over-limit.xxx")         shouldBe "Just an additional attribute"
    }

    "render custom text area hint attribute when maxLength and maxWords are not provided" in {
      val params = CharacterCount(
        id = "characterCount",
        name = "testCharacterCount",
        textareaDescriptionText = Some("custom text area text")
      )

      val output = component(params).select(".govuk-character-count")
      output.attr("data-i18n.textarea-description.other") shouldBe "custom text area text"

      output.select(".govuk-hint").text() shouldBe ""
    }

    "not render custom text area hint attribute when maxLength is provided" in {
      val params = CharacterCount(
        id = "characterCount",
        name = "testCharacterCount",
        maxLength = Some(100),
        textareaDescriptionText = Some("%{count} characters remaining")
      )

      val output = component(params).select(".govuk-character-count")
      output.attr("data-i18n.textarea-description.other") shouldBe ""

      output.select(".govuk-hint").text() shouldBe "100 characters remaining"
    }

    "not render custom text area hint attribute when maxWords is provided" in {
      val params = CharacterCount(
        id = "characterCount",
        name = "testCharacterCount",
        maxWords = Some(10),
        textareaDescriptionText = Some("%{count} words remaining")
      )

      val output = component(params).select(".govuk-character-count")
      output.attr("data-i18n.textarea-description.other") shouldBe ""

      output.select(".govuk-hint").text() shouldBe "10 words remaining"
    }
  }
}
