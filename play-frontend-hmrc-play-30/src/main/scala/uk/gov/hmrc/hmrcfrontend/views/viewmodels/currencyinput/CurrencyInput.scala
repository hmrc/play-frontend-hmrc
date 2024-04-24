/*
 * Copyright 2023 HM Revenue & Customs
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

@deprecated(
  "Use GovukInput template with `£` prefix instead. For examples, please see:\n" +
    "- HMRC Design Patterns: https://design.tax.service.gov.uk/hmrc-design-patterns/currency-input/, and \n" +
    "- GOV.UK Design System: https://design-system.service.gov.uk/components/text-input/#prefixes-and-suffixes. \n" +
    "CurrencyInput component will be removed in a future library version.",
  "9.9.0"
)
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
