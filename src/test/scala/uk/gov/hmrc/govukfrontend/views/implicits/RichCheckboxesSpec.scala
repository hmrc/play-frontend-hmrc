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

package uk.gov.hmrc.govukfrontend.views.implicits

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.govukfrontend.views.html.components.implicits._
import uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes.{CheckboxItem, Checkboxes}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage.errorMessageWithDefaultStringsTranslated
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.{Fieldset, Legend}
import uk.gov.hmrc.helpers.MessagesHelpers
import uk.gov.hmrc.hmrcfrontend.views.config.{HmrcPageHeadingLegend, HmrcSectionCaption}

class RichCheckboxesSpec extends AnyWordSpec with Matchers with MessagesHelpers with RichFormInputHelpers {

  "Given a Checkboxes object, calling withFormField" should {
    "use the Field name, appending [], as the Checkboxes name if no Checkboxes name provided" in {
      val checkboxes = Checkboxes().withFormField(field)
      checkboxes.name shouldBe "user-name[]"
    }

    "use the Checkboxes name over the Field name if both exist" in {
      val checkboxes = Checkboxes(name = "checkboxesName").withFormField(field)
      checkboxes.name shouldBe "checkboxesName"
    }

    "use the Field name as the idPrefix if no Checkboxes idPrefix provided" in {
      val checkboxes = Checkboxes().withFormField(field)
      checkboxes.idPrefix shouldBe Some("user-name")
    }

    "use the Checkboxes idPrefix over the Field name if both exist" in {
      val checkboxes = Checkboxes(idPrefix = Some("Checkboxes Prefix")).withFormField(field)
      checkboxes.idPrefix shouldBe Some("Checkboxes Prefix")
    }

    "check items that are present in the Field's repeating values" in {
      val email = CheckboxItem(content = Text("Email"), value = "email")
      val post  = CheckboxItem(content = Text("Post"), value = "post")
      val phone = CheckboxItem(content = Text("Phone"), value = "phone")

      val checkboxes =
        Checkboxes(items = Seq(email, post, phone)).withFormField(repeatedField)
      checkboxes.items shouldBe Seq(
        email.copy(checked = true),
        post.copy(checked = true),
        phone
      )
    }

    "convert the first Field form error to a Checkboxes error message if provided" in {
      val checkboxes = Checkboxes().withFormField(field)
      checkboxes.errorMessage shouldBe Some(
        errorMessageWithDefaultStringsTranslated(content = Text("Error on: Firstname&nbsp;Lastname"))
      )
    }

    "use the Checkboxes error message over the Field error if both provided" in {
      val checkboxes = Checkboxes(
        errorMessage = Some(ErrorMessage(content = Text("Checkboxes Error")))
      ).withFormField(field)
      checkboxes.errorMessage shouldBe Some(ErrorMessage(content = Text("Checkboxes Error")))
    }

    "correctly chain multiple Field properties provided to update a Checkboxes" in {
      val email = CheckboxItem(content = Text("Email"), value = "email")
      val post  = CheckboxItem(content = Text("Post"), value = "post")
      val phone = CheckboxItem(content = Text("Phone"), value = "phone")

      val checkboxes =
        Checkboxes(items = Seq(email, post, phone)).withFormField(repeatedField)

      checkboxes.withFormField(repeatedField) shouldBe Checkboxes(
        name = "user-communication-preferences[]",
        idPrefix = Some("user-communication-preferences"),
        errorMessage = Some(errorMessageWithDefaultStringsTranslated(content = Text("Not valid preferences"))),
        items = Seq(
          email.copy(checked = true),
          post.copy(checked = true),
          phone
        )
      )
    }
  }

  "Given a Checkboxes object, calling withFormFieldWithErrorAsHtml" should {
    "convert the first Field form error to an Checkboxes HTML error message if provided" in {
      val checkboxes = Checkboxes().withFormFieldWithErrorAsHtml(field = field)
      checkboxes.errorMessage shouldBe Some(
        errorMessageWithDefaultStringsTranslated(content = HtmlContent("Error on: Firstname&nbsp;Lastname"))
      )
    }

    "use the Checkboxes error message over the Field error if both provided" in {
      val checkboxes = Checkboxes(
        errorMessage = Some(ErrorMessage(content = Text("Checkboxes Error")))
      ).withFormFieldWithErrorAsHtml(field)
      checkboxes.errorMessage shouldBe Some(ErrorMessage(content = Text("Checkboxes Error")))
    }
  }

  "Given a Checkboxes object, calling withHeading" should {
    "set the fieldset legend to the passed in content" in {
      val checkboxes = Checkboxes().withHeading(Text("This is a text heading"))
      checkboxes shouldBe Checkboxes(fieldset =
        Some(
          Fieldset(
            legend = Some(HmrcPageHeadingLegend(content = Text("This is a text heading")))
          )
        )
      )
    }

    "set the fieldset legend to the passed in content, overriding any previously set content" in {
      val checkboxesWithLegend =
        Checkboxes(fieldset =
          Some(Fieldset(legend = Some(Legend(content = Text("This is some original text heading")))))
        )
      val updatedCheckboxes    = checkboxesWithLegend.withHeading(Text("This is some updated text heading"))
      updatedCheckboxes shouldBe Checkboxes(fieldset =
        Some(
          Fieldset(
            legend = Some(HmrcPageHeadingLegend(content = Text("This is some updated text heading")))
          )
        )
      )
    }
  }

  "Given a Checkboxes object, calling withHeadingAndSectionCaption" should {
    "set the fieldset legend to the passed in content" in {
      val checkboxes =
        Checkboxes().withHeadingAndSectionCaption(Text("This is a text heading"), Text("This is a text caption"))
      checkboxes shouldBe Checkboxes(fieldset =
        Some(
          Fieldset(
            legend = Some(
              HmrcPageHeadingLegend(
                content = Text("This is a text heading"),
                caption = HmrcSectionCaption(Text("This is a text caption"))
              )
            )
          )
        )
      )
    }

    "set the fieldset legend to the passed in content, overriding any previously set content" in {
      val checkboxesWithLegend = Checkboxes(fieldset =
        Some(
          Fieldset(legend =
            Some(
              Legend(
                content = Text("This is some original text heading")
              )
            )
          )
        )
      )
      val updatedCheckboxes    = checkboxesWithLegend.withHeadingAndSectionCaption(
        Text("This is some updated text heading"),
        Text("This is some updated text caption")
      )
      updatedCheckboxes shouldBe Checkboxes(fieldset =
        Some(
          Fieldset(
            legend = Some(
              HmrcPageHeadingLegend(
                content = Text("This is some updated text heading"),
                caption = HmrcSectionCaption(Text("This is some updated text caption"))
              )
            )
          )
        )
      )
    }
  }
}
