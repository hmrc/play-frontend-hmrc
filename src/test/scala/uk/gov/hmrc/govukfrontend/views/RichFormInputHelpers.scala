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

import play.api.data.{Field, Form, FormError}
import play.api.data.Forms.{mapping, text}

trait RichFormInputHelpers {

  val field: Field = Field(
    form = TestFormBind.form.bind(Map(
      "user-name" -> "Test Name",
      "user-email" -> "test@example.com"
    )),
    name = "Form Name",
    constraints = Nil,
    format = None,
    errors = Seq(
      FormError(key = "user-name", "Not valid name"),
      FormError(key = "user-email", "Not valid email")
    ),
    value = Some("bad")
  )

  case class TestForm(name: String, email: String)

  object TestFormBind {
    def form: Form[TestForm] = Form[TestForm](
      mapping(
        "user-name" -> text,
        "user-email" -> text
      )(TestForm.apply)(TestForm.unapply))
  }

}
