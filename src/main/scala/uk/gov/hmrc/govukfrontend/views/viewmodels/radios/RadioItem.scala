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
package radios

import common.Contents
import play.twirl.api.Html

case class RadioItem(
  contents: Contents,
  id: Option[String]              = None,
  value: Option[String]           = None,
  label: Option[LabelParams]      = None,
  hint: Option[HintParams]        = None,
  divider: Option[String]         = None,
  checked: Boolean                = false,
  conditionalHtml: Option[Html]   = None,
  disabled: Boolean               = false,
  attributes: Map[String, String] = Map.empty
)
