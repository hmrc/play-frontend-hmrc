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
import uk.gov.hmrc.govukfrontend.views.viewmodels.select.{Select, SelectItem}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage

class RichSelectSpec extends AnyWordSpec with Matchers with MessagesHelpers with RichFormInputHelpers {

  "Given a Select object, calling withFormField" should {
    "use the Field name as the Select name if no Select name provided" in {
      val select = Select().withFormField(field)
      select.name shouldBe "Form Name"
    }

    "use the Select name over the Field name if both exist" in {
      val select = Select(name = "Select Name").withFormField(field)
      select.name shouldBe "Select Name"
    }

    "use the Field name as the id if no Select id provided" in {
      val select = Select().withFormField(field)
      select.id shouldBe "Form Name"
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

    "convert the first Field form error to a Select error message if provided" in {
      val select = Select().withFormField(field)
      select.errorMessage shouldBe Some(ErrorMessage(content = Text("Not valid name")))
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
        name = "Form Name",
        id = "Form Name",
        errorMessage = Some(ErrorMessage(content = Text("Not valid name"))),
        items = Seq(
          selectItemGood,
          selectItemBad.copy(selected = true),
          selectItemWorst
        )
      )
    }
  }
}
