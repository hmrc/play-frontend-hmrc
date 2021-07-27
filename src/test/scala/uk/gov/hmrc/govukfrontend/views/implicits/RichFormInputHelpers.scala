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

import play.api.data.Forms.{mapping, set, text}
import play.api.data.{Field, Form, FormError}

trait RichFormInputHelpers {

  val form = TestFormBind.form.bindFromRequest(
    Map(
      "user-name"                        -> Seq("Test Name"),
      "user-email"                       -> Seq("test@example.com"),
      "user-communication-preferences[]" -> Seq("post", "email")
    )
  )

  val field: Field = Field(
    form = form,
    name = "user-name",
    constraints = Nil,
    format = None,
    errors = Seq(
      FormError(key = "user-name", "Error on: Firstname&nbsp;Lastname")
    ),
    value = Some("bad")
  )

  val repeatedField: Field = Field(
    form = form,
    name = "user-communication-preferences",
    constraints = Nil,
    format = None,
    errors = Seq(
      FormError(key = "user-communication-preferences", "Not valid preferences")
    ),
    value = None
  )

  case class TestForm(name: String, email: String, userMarketingPreferences: Set[String])

  object TestFormBind {
    def form: Form[TestForm] = Form[TestForm](
      mapping(
        "user-name"                      -> text,
        "user-email"                     -> text,
        "user-communication-preferences" -> set(text)
      )(TestForm.apply)(TestForm.unapply)
    )
  }

}
