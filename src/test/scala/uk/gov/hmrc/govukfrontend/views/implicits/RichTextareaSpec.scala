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
import uk.gov.hmrc.govukfrontend.views.MessagesHelpers
import uk.gov.hmrc.govukfrontend.views.html.components.implicits._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage.errorMessageWithDefaultStringsTranslated
import uk.gov.hmrc.govukfrontend.views.viewmodels.textarea.Textarea

class RichTextareaSpec extends AnyWordSpec with Matchers with MessagesHelpers with RichFormInputHelpers {

  "Given an Textarea object, calling withFormField" should {
    "use the Field name as the Textarea name if no Textarea name provided" in {
      val textarea = Textarea().withFormField(field)
      textarea.name shouldBe "user-name"
    }

    "use the Textarea name over the Field name if both exist" in {
      val textarea = Textarea(name = "Textarea Name").withFormField(field)
      textarea.name shouldBe "Textarea Name"
    }

    "use the Field name as the id if no Textarea id provided" in {
      val textarea = Textarea().withFormField(field)
      textarea.id shouldBe "user-name"
    }

    "use the Textarea id over the Field name if both exist" in {
      val textarea = Textarea(id = "Textarea Id").withFormField(field)
      textarea.id shouldBe "Textarea Id"
    }

    "convert the first Field form error to a Textarea text error message if provided" in {
      val textarea = Textarea().withFormField(field)
      textarea.errorMessage shouldBe Some(
        errorMessageWithDefaultStringsTranslated(content = Text("Error on: Firstname&nbsp;Lastname"))
      )
    }

    "use the Textarea error message over the Field error if both provided" in {
      val textarea = Textarea(
        errorMessage = Some(ErrorMessage(content = Text("Radios Error")))
      ).withFormField(field)
      textarea.errorMessage shouldBe Some(ErrorMessage(content = Text("Radios Error")))
    }

    "use the Field value as the value if no Textarea id provided" in {
      val textarea = Textarea().withFormField(field)
      textarea.value shouldBe Some("bad")
    }

    "use the Textarea value over the Field value if both exist" in {
      val textarea = Textarea(value = Some("Textarea Value")).withFormField(field)
      textarea.value shouldBe Some("Textarea Value")
    }

    "correctly chain multiple Field properties provided to update an Textarea" in {
      val textarea = Textarea()
      textarea.withFormField(field) shouldBe Textarea(
        name = "user-name",
        id = "user-name",
        errorMessage =
          Some(errorMessageWithDefaultStringsTranslated(content = Text("Error on: Firstname&nbsp;Lastname"))),
        value = Some("bad")
      )
    }
  }

  "Given a Textarea object, calling withFormFieldWithErrorAsHtml" should {
    "convert the first Field form error to a Textarea HTML error message if provided" in {
      val textarea = Textarea().withFormFieldWithErrorAsHtml(field = field)
      textarea.errorMessage shouldBe Some(
        errorMessageWithDefaultStringsTranslated(content = HtmlContent("Error on: Firstname&nbsp;Lastname"))
      )
    }

    "use the Textarea error message over the Field error if both provided" in {
      val textarea = Textarea(
        errorMessage = Some(ErrorMessage(content = Text("Textarea Error")))
      ).withFormFieldWithErrorAsHtml(field)
      textarea.errorMessage shouldBe Some(ErrorMessage(content = Text("Textarea Error")))
    }
  }
}
