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

package uk.gov.hmrc.hmrcfrontend.views.implicits

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.data.Forms.{mapping, text}
import play.api.data.{Field, Form, FormError}
import play.api.i18n.{DefaultLangs, Lang, Messages, MessagesApi}
import play.api.test.{Helpers => PlayHelpers}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage.errorMessageWithDefaultStringsTranslated
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label
import uk.gov.hmrc.hmrcfrontend.views.config.{HmrcPageHeadingLabel, HmrcSectionCaption}
import uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.charactercount.CharacterCount

class RichCharacterCountSpec extends AnyWordSpec with Matchers {

  implicit lazy val messagesApi: MessagesApi = {
    val langs = new DefaultLangs(Seq(Lang("en"), Lang("cy")))

    PlayHelpers.stubMessagesApi(messages = Map.empty, langs = langs)
  }

  implicit lazy val messages: Messages =
    PlayHelpers.stubMessages(messagesApi)

  val field: Field = Field(
    form = TestFormBind.form.bind(
      Map(
        "user-name"  -> "Test Name",
        "user-email" -> "test@example.com"
      )
    ),
    name = "Form Name",
    constraints = Nil,
    format = None,
    errors = Seq(
      FormError(key = "user-name", "Not valid name: Firstname&nbsp;Lastname"),
      FormError(key = "user-email", "Not valid email")
    ),
    value = Some("bad")
  )

  case class TestForm(name: String, email: String)

  object TestFormBind {
    def form: Form[TestForm] = Form[TestForm](
      mapping(
        "user-name"  -> text,
        "user-email" -> text
      )(TestForm.apply)(TestForm.unapply)
    )
  }

  "Given an CharacterCount object, calling withFormField" should {
    "use the Field name as the CharacterCount name if no CharacterCount name provided" in {
      val characterCount = CharacterCount().withFormField(field)
      characterCount.name shouldBe "Form Name"
    }

    "use the CharacterCount name over the Field name if both exist" in {
      val characterCount = CharacterCount(name = "CharacterCount Name").withFormField(field)
      characterCount.name shouldBe "CharacterCount Name"
    }

    "use the Field name as the id if no CharacterCount id provided" in {
      val characterCount = CharacterCount().withFormField(field)
      characterCount.id shouldBe "Form Name"
    }

    "use the CharacterCount id over the Field name if both exist" in {
      val characterCount = CharacterCount(id = "CharacterCount Id").withFormField(field)
      characterCount.id shouldBe "CharacterCount Id"
    }

    "convert the first Field form error to a CharacterCount Text error message if provided" in {
      val characterCount = CharacterCount().withFormField(field)
      characterCount.errorMessage shouldBe Some(
        errorMessageWithDefaultStringsTranslated(content = Text("Not valid name: Firstname&nbsp;Lastname"))
      )
    }

    "use the CharacterCount error message over the Field error if both provided" in {
      val characterCount = CharacterCount(
        errorMessage = Some(ErrorMessage(content = Text("Radios Error")))
      ).withFormField(field)
      characterCount.errorMessage shouldBe Some(ErrorMessage(content = Text("Radios Error")))
    }

    "use the Field value as the value if no CharacterCount id provided" in {
      val characterCount = CharacterCount().withFormField(field)
      characterCount.value shouldBe Some("bad")
    }

    "use the CharacterCount value over the Field value if both exist" in {
      val characterCount = CharacterCount(value = Some("CharacterCount Value")).withFormField(field)
      characterCount.value shouldBe Some("CharacterCount Value")
    }

    "correctly chain multiple Field properties provided to update an CharacterCount" in {
      val characterCount = CharacterCount()
      characterCount.withFormField(field) shouldBe CharacterCount(
        name = "Form Name",
        id = "Form Name",
        errorMessage =
          Some(errorMessageWithDefaultStringsTranslated(content = Text("Not valid name: Firstname&nbsp;Lastname"))),
        value = Some("bad")
      )
    }
  }

  "Given a CharacterCount object, calling withFormFieldWithErrorAsHtml" should {
    "convert the first Field form error to a CharacterCount HTML error message if provided" in {
      val characterCount = CharacterCount().withFormFieldWithErrorAsHtml(field)
      characterCount.errorMessage shouldBe Some(
        errorMessageWithDefaultStringsTranslated(content = HtmlContent("Not valid name: Firstname&nbsp;Lastname"))
      )
    }

    "use the CharacterCount error message over the Field error if both provided" in {
      val characterCount = CharacterCount(
        errorMessage = Some(ErrorMessage(content = Text("CharacterCount Error")))
      ).withFormFieldWithErrorAsHtml(field)
      characterCount.errorMessage shouldBe Some(ErrorMessage(content = Text("CharacterCount Error")))
    }
  }

  "Given a CharacterCount object, calling withHeadingAndSectionCaption" should {
    "set the label and caption to the passed in content" in {
      val characterCount = CharacterCount().withHeadingAndSectionCaption(
        heading = Text("This is a text heading"),
        sectionCaption = Text("This is a text caption")
      )
      characterCount shouldBe CharacterCount(
        label = HmrcPageHeadingLabel(
          content = Text("This is a text heading"),
          caption = HmrcSectionCaption(Text("This is a text caption"))
        )
      )
    }

    "set the label to the passed in content, overriding any previously set content" in {
      val characterCountWithLabel = CharacterCount(
        label = Label(content = Text("This is some original text heading"))
      )
      val updatedCharacterCount   = characterCountWithLabel.withHeadingAndSectionCaption(
        heading = Text("This is some updated text heading"),
        sectionCaption = Text("This is a text caption")
      )
      updatedCharacterCount shouldBe CharacterCount(
        label = HmrcPageHeadingLabel(
          content = Text("This is some updated text heading"),
          caption = HmrcSectionCaption(Text("This is a text caption"))
        )
      )
    }
  }
}
