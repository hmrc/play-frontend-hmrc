/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.currencyinput

import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage

case class CurrencyInput(
  id: String = "",
  name: String = "",
  describedBy: Option[String] = None,
  value: Option[String] = None,
  label: Label = Label(),
  hint: Option[Hint] = None,
  errorMessage: Option[ErrorMessage] = None,
  classes: String = "",
  autocomplete: Option[String] = None,
  attributes: Map[String, String] = Map.empty
)

object CurrencyInput {

  implicit def jsonFormats: OFormat[CurrencyInput] = Json.using[Json.WithDefaultValues].format[CurrencyInput]
}
