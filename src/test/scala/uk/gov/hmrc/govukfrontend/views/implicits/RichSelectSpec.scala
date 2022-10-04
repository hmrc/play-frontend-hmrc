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

package uk.gov.hmrc.govukfrontend.views.implicits

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.govukfrontend.views.html.components.implicits._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage.errorMessageWithDefaultStringsTranslated
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label
import uk.gov.hmrc.govukfrontend.views.viewmodels.select.{Select, SelectItem}
import uk.gov.hmrc.helpers.MessagesHelpers
import uk.gov.hmrc.hmrcfrontend.views.config.{HmrcPageHeadingLabel, HmrcSectionCaption}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.accessibleautocomplete.AccessibleAutocomplete

class RichSelectSpec extends AnyWordSpec with Matchers with MessagesHelpers with RichFormInputHelpers {

  "Given a Select object, calling withFormField" should {
    "use the Field name as the Select name if no Select name provided" in {
      val select = Select().withFormField(field)
      select.name shouldBe "user-name"
    }

    "use the Select name over the Field name if both exist" in {
      val select = Select(name = "Select Name").withFormField(field)
      select.name shouldBe "Select Name"
    }

    "use the Field name as the id if no Select id provided" in {
      val select = Select().withFormField(field)
      select.id shouldBe "user-name"
    }

    "use the Select id over the Field name if both exist" in {
      val select = Select(id = "Select Id").withFormField(field)
      select.id shouldBe "Select Id"
    }

    "select a SelectItem if the Field value matches the Select value and no selected value in Select" in {
      val selectItemGood  = SelectItem(text = "This is good", value = Some("good"))
      val selectItemBad   = SelectItem(text = "This is bad", value = Some("bad"))
      val selectItemWorst = SelectItem(text = "This is THE WORST", value = Some("worst"))

      val select = Select(items = Seq(selectItemGood, selectItemBad, selectItemWorst)).withFormField(field)
      select.items shouldBe Seq(
        selectItemGood,
        selectItemBad.copy(selected = true),
        selectItemWorst
      )
    }

    "convert the first Field form error to a Select text error message if provided" in {
      val select = Select().withFormField(field)
      select.errorMessage shouldBe Some(
        errorMessageWithDefaultStringsTranslated(content = Text("Error on: Firstname&nbsp;Lastname"))
      )
    }

    "use the Select error message over the Field error if both provided" in {
      val select = Select(
        errorMessage = Some(ErrorMessage(content = Text("Select Error")))
      ).withFormField(field)
      select.errorMessage shouldBe Some(ErrorMessage(content = Text("Select Error")))
    }

    "correctly chain multiple Field properties provided to update a Select" in {
      val selectItemGood  = SelectItem(text = "This is good", value = Some("good"))
      val selectItemBad   = SelectItem(text = "This is bad", value = Some("bad"))
      val selectItemWorst = SelectItem(text = "This is THE WORST", value = Some("worst"))

      val select = Select(
        items = Seq(selectItemGood, selectItemBad, selectItemWorst)
      )
      select.withFormField(field) shouldBe Select(
        name = "user-name",
        id = "user-name",
        errorMessage =
          Some(errorMessageWithDefaultStringsTranslated(content = Text("Error on: Firstname&nbsp;Lastname"))),
        items = Seq(
          selectItemGood,
          selectItemBad.copy(selected = true),
          selectItemWorst
        )
      )
    }
  }

  "Given a Select object, calling withFormFieldWithErrorAsHtml" should {
    "convert the first Field form error to a Select HTML error message if provided" in {
      val select = Select().withFormFieldWithErrorAsHtml(field = field)
      select.errorMessage shouldBe Some(
        errorMessageWithDefaultStringsTranslated(content = HtmlContent("Error on: Firstname&nbsp;Lastname"))
      )
    }

    "use the Select error message over the Field error if both provided" in {
      val select = Select(
        errorMessage = Some(ErrorMessage(content = Text("Select Error")))
      ).withFormFieldWithErrorAsHtml(field)
      select.errorMessage shouldBe Some(ErrorMessage(content = Text("Select Error")))
    }
  }

  "Given a Select object, calling withHeading" should {
    "set the label to the passed in content" in {
      val select = Select().withHeading(Text("This is a text heading"))
      select shouldBe Select(label =
        HmrcPageHeadingLabel(
          content = Text("This is a text heading")
        )
      )
    }

    "set the label to the passed in content, overriding any previously set content" in {
      val selectWithLabel =
        Select(label = Label(content = Text("This is some original text heading")))
      val updatedSelect   = selectWithLabel.withHeading(Text("This is some updated text heading"))
      updatedSelect shouldBe Select(label =
        HmrcPageHeadingLabel(
          content = Text("This is some updated text heading")
        )
      )
    }
  }

  "Given a Select object, calling withHeadingAndSectionCaption" should {
    "set the label and caption to the passed in content" in {
      val select =
        Select().withHeadingAndSectionCaption(Text("This is a text heading"), Text("This is a text caption"))
      select shouldBe Select(label =
        HmrcPageHeadingLabel(
          content = Text("This is a text heading"),
          caption = HmrcSectionCaption(Text("This is a text caption"))
        )
      )
    }

    "set the label to the passed in content, overriding any previously set content" in {
      val selectWithLabel =
        Select(label = Label(content = Text("This is some original text heading")))
      val updatedSelect   =
        selectWithLabel.withHeadingAndSectionCaption(
          Text("This is some updated text heading"),
          Text("This is some updated text caption")
        )
      updatedSelect shouldBe Select(label =
        HmrcPageHeadingLabel(
          content = Text("This is some updated text heading"),
          caption = HmrcSectionCaption(Text("This is some updated text caption"))
        )
      )
    }
  }

  "Given a Select object, calling asAccessibleAutocomplete" should {
    "set the accessible autocomplete data attributes" in {
      val select        = Select()
      val updatedSelect = select.asAccessibleAutocomplete(
        Some(AccessibleAutocomplete(Some("Test"), showAllValues = true, autoSelect = true))
      )

      updatedSelect shouldBe Select(attributes =
        Map(
          "data-default-value"   -> "Test",
          "data-show-all-values" -> "true",
          "data-auto-select"     -> "true",
          "data-module"          -> "hmrc-accessible-autocomplete"
        )
      )
    }

    "set the default accessible autocomplete data attributes when AccessibleAutocomplete is not provided" in {
      val select        = Select()
      val updatedSelect = select.asAccessibleAutocomplete()

      updatedSelect shouldBe Select(attributes =
        Map(
          "data-default-value"   -> "",
          "data-show-all-values" -> "false",
          "data-auto-select"     -> "false",
          "data-module"          -> "hmrc-accessible-autocomplete"
        )
      )
    }

    "append the accessible autocomplete data attributes to existing select attributes" in {
      val select        = Select(attributes = Map("some-attr1" -> "1", "some-attr2" -> "2"))
      val updatedSelect = select.asAccessibleAutocomplete(Some(AccessibleAutocomplete(Some("Test"))))

      updatedSelect shouldBe Select(attributes =
        Map(
          "data-default-value"   -> "Test",
          "data-show-all-values" -> "false",
          "data-auto-select"     -> "false",
          "data-module"          -> "hmrc-accessible-autocomplete",
          "some-attr1"           -> "1",
          "some-attr2"           -> "2"
        )
      )
    }
  }
}
