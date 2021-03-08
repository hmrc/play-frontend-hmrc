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

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.govukfrontend.views.html.components.implicits._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.input.Input

class RichInputSpec
  extends AnyWordSpec
    with Matchers
    with MessagesHelpers
    with RichFormInputHelpers {

  "Given an Input object, calling withFormField" should {
    "use the Field name as the Input name if no Input name provided" in {
      val input = Input().withFormField(field)
      input.name shouldBe "Form Name"
    }

    "use the Input name over the Field name if both exist" in {
      val input = Input(name = "Input Name").withFormField(field)
      input.name shouldBe "Input Name"
    }

    "use the Field name as the id if no Input id provided" in {
      val input = Input().withFormField(field)
      input.id shouldBe "Form Name"
    }

    "use the Input id over the Field name if both exist" in {
      val input = Input(id = "Input Id").withFormField(field)
      input.id shouldBe "Input Id"
    }

    "convert the first Field form error to an Input error message if provided" in {
      val input = Input().withFormField(field)
      input.errorMessage shouldBe Some(ErrorMessage(content = Text("Not valid name")))
    }

    "use the Input error message over the Field error if both provided" in {
      val input = Input(
        errorMessage = Some(ErrorMessage(content = Text("Radios Error")))
      ).withFormField(field)
      input.errorMessage shouldBe Some(ErrorMessage(content = Text("Radios Error")))
    }

    "use the Field value as the value if no Input id provided" in {
      val input = Input().withFormField(field)
      input.value shouldBe Some("bad")
    }

    "use the Input value over the Field value if both exist" in {
      val input = Input(value = Some("Input Value")).withFormField(field)
      input.value shouldBe Some("Input Value")
    }

    "correctly chain multiple Field properties provided to update an Input" in {
      val input = Input()
      input.withFormField(field) shouldBe Input(
        name = "Form Name",
        id = "Form Name",
        errorMessage =  Some(ErrorMessage(content = Text("Not valid name"))),
        value = Some("bad")
      )
    }
  }
}
