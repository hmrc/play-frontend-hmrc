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

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.govukfrontend.views.html.components.implicits._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.charactercount.CharacterCount

class RichCharacterCountSpec extends WordSpec with Matchers with MessagesHelpers with RichFormInputHelpers {

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

    "convert the first Field form error to an CharacterCount error message if provided" in {
      val characterCount = CharacterCount().withFormField(field)
      characterCount.errorMessage shouldBe Some(ErrorMessage(content = Text("Not valid name")))
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
        errorMessage =  Some(ErrorMessage(content = Text("Not valid name"))),
        value = Some("bad")
      )
    }
  }
}
