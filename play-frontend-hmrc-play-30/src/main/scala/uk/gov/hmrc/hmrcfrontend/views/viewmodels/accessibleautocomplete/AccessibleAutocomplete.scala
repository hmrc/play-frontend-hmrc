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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.accessibleautocomplete

case class AccessibleAutocomplete(
  defaultValue: Option[String] =
    None, // Please read on the usage of the defaultValue property at https://www.npmjs.com/package/accessible-autocomplete under the `Null options` heading
  showAllValues : Boolean         = false,
  autoSelect    : Boolean         = false,
  minLength     : Option[Int]     = None,
  emptyItem     : Option[String]  = None
) {
  val dataModule: String = "hmrc-accessible-autocomplete"
}
