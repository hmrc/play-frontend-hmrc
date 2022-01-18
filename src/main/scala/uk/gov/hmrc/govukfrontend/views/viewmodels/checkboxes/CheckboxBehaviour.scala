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

package uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes

import play.api.libs.json.{Format, JsError, JsResult, JsString, JsSuccess, JsValue}

sealed trait CheckboxBehaviour

object CheckboxBehaviour {
  implicit object CheckboxBehaviourFormat extends Format[CheckboxBehaviour] {
    override def reads(json: JsValue): JsResult[CheckboxBehaviour] = json match {
      case JsString("exclusive") => JsSuccess(ExclusiveCheckbox)
      case JsString(_)           => JsError("error.expected.validbehaviour")
      case _                     => JsError("error.expected.jsstring")
    }

    override def writes(b: CheckboxBehaviour): JsString = JsString(b match {
      case ExclusiveCheckbox => "exclusive"
    })
  }
}

case object ExclusiveCheckbox extends CheckboxBehaviour {
  override def toString = "exclusive"
}
