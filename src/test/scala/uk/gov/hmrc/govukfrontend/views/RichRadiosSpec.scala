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
import uk.gov.hmrc.govukfrontend.views.viewmodels.radios.{RadioItem, Radios}

class RichRadiosSpec extends AnyWordSpec with Matchers with MessagesHelpers with RichFormInputHelpers {

  "Given a Radios object, calling withFormField" should {
    "use the Field name as the Radios name if no Radios name provided" in {
      val radios = Radios().withFormField(field)
      radios.name shouldBe "Form Name"
    }

    "use the Radios name over the Field name if both exist" in {
      val radios = Radios(name = "Radios Name").withFormField(field)
      radios.name shouldBe "Radios Name"
    }

    "use the Field name as the idPrefix if no Radios idPrefix provided" in {
      val radios = Radios().withFormField(field)
      radios.idPrefix shouldBe Some("Form Name")
    }

    "use the Radios idPrefix over the Field name if both exist" in {
      val radios = Radios(idPrefix = Some("Radios Prefix")).withFormField(field)
      radios.idPrefix shouldBe Some("Radios Prefix")
    }

    "check a RadioItem if the Field value matches the Radio value and no checked value in Radio" in {
      val radioItemGood  = RadioItem(content = Text("This is good"), value = Some("good"))
      val radioItemBad   = RadioItem(content = Text("This is bad"), value = Some("bad"))
      val radioItemWorst = RadioItem(content = Text("This is THE WORST"), value = Some("worst"))

      val radios = Radios(items = Seq(radioItemGood, radioItemBad, radioItemWorst)).withFormField(field)
      radios.items shouldBe Seq(
        radioItemGood,
        radioItemBad.copy(checked = true),
        radioItemWorst
      )
    }

    "convert the first Field form error to a Radios error message if provided" in {
      val radios = Radios().withFormField(field)
      radios.errorMessage shouldBe Some(ErrorMessage(content = Text("Not valid name")))
    }

    "use the Radios error message over the Field error if both provided" in {
      val radios = Radios(
        errorMessage = Some(ErrorMessage(content = Text("Radios Error")))
      ).withFormField(field)
      radios.errorMessage shouldBe Some(ErrorMessage(content = Text("Radios Error")))
    }

    "correctly chain multiple Field properties provided to update a Radios" in {
      val radioItemGood  = RadioItem(content = Text("This is good"), value = Some("good"))
      val radioItemBad   = RadioItem(content = Text("This is bad"), value = Some("bad"))
      val radioItemWorst = RadioItem(content = Text("This is THE WORST"), value = Some("worst"))

      val radios = Radios(
        items = Seq(radioItemGood, radioItemBad, radioItemWorst)
      )
      radios.withFormField(field) shouldBe Radios(
        name = "Form Name",
        idPrefix = Some("Form Name"),
        errorMessage = Some(ErrorMessage(content = Text("Not valid name"))),
        items = Seq(
          radioItemGood,
          radioItemBad.copy(checked = true),
          radioItemWorst
        )
      )
    }
  }
}
