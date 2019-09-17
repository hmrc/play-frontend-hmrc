/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels
package checkboxes

import common.Content
import hint.HintParams
import label.LabelParams
import play.twirl.api.Html

final case class CheckboxItem(
  content: Content,
  id: Option[String]   = None,
  name: Option[String] = None,
  value: String,
  label: Option[LabelParams]      = None,
  hint: Option[HintParams]        = None,
  checked: Boolean                = false,
  conditionalHtml: Option[Html]   = None,
  disabled: Boolean               = false,
  attributes: Map[String, String] = Map.empty
)
