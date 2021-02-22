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
import uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes.{CheckboxItem, Checkboxes}

class RichCheckboxesSpec extends WordSpec with Matchers with MessagesHelpers with RichFormInputHelpers {

  "Given a Checkboxes object, calling withFormField" should {
    "use the Field name as the Checkboxes name if no Checkboxes name provided" in {
      val checkboxes = Checkboxes().withFormField(field)
      checkboxes.name shouldBe "Form Name"
    }

    "use the Checkboxes name over the Field name if both exist" in {
      val checkboxes = Checkboxes(name = "Checkboxes Name").withFormField(field)
      checkboxes.name shouldBe "Checkboxes Name"
    }

    "use the Field name as the idPrefix if no Checkboxes idPrefix provided" in {
      val checkboxes = Checkboxes().withFormField(field)
      checkboxes.idPrefix shouldBe Some("Form Name")
    }

    "use the Checkboxes idPrefix over the Field name if both exist" in {
      val checkboxes = Checkboxes(idPrefix = Some("Checkboxes Prefix")).withFormField(field)
      checkboxes.idPrefix shouldBe Some("Checkboxes Prefix")
    }

    "check a RadioItem if the Field value matches the Radio value and no checked value in Radio" in {
      val checkboxItemGood = CheckboxItem(content = Text("This is good"), value = "good")
      val checkboxItemBad = CheckboxItem(content = Text("This is bad"), value = "bad")
      val checkboxItemWorst = CheckboxItem(content = Text("This is THE WORST"), value = "worst")

      val checkboxes = Checkboxes(items = Seq(checkboxItemGood, checkboxItemBad, checkboxItemWorst)).withFormField(field)
      checkboxes.items shouldBe Seq(
        checkboxItemGood,
        checkboxItemBad.copy(checked = true),
        checkboxItemWorst
      )
    }

    "convert the first Field form error to a Checkboxes error message if provided" in {
      val checkboxes = Checkboxes().withFormField(field)
      checkboxes.errorMessage shouldBe Some(ErrorMessage(content = Text("Not valid name")))
    }

    "use the Checkboxes error message over the Field error if both provided" in {
      val checkboxes = Checkboxes(
        errorMessage = Some(ErrorMessage(content = Text("Checkboxes Error")))
      ).withFormField(field)
      checkboxes.errorMessage shouldBe Some(ErrorMessage(content = Text("Checkboxes Error")))
    }

    "correctly chain multiple Field properties provided to update a Checkboxes" in {
      val checkboxItemGood = CheckboxItem(content = Text("This is good"), value = "good")
      val checkboxItemBad = CheckboxItem(content = Text("This is bad"), value = "bad")
      val checkboxItemWorst = CheckboxItem(content = Text("This is THE WORST"), value = "worst")

      val checkboxes = Checkboxes(
        items = Seq(checkboxItemGood, checkboxItemBad, checkboxItemWorst)
      )
      checkboxes.withFormField(field) shouldBe Checkboxes(
        name = "Form Name",
        idPrefix = Some("Form Name"),
        errorMessage =  Some(ErrorMessage(content = Text("Not valid name"))),
        items = Seq(
          checkboxItemGood,
          checkboxItemBad.copy(checked = true),
          checkboxItemWorst
        )
      )
    }
  }
}
