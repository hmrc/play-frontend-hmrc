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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.accountmenu

import play.api.libs.json._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.JsonDefaultValueFormatter

case class AccountMessages(
                     href: String = "#",
                     active: Boolean = false,
                     messageCount: Option[Int] = None
                   )

object AccountMessages extends JsonDefaultValueFormatter[AccountMessages] {

  override def defaultObject: AccountMessages = AccountMessages()

  override def defaultReads: Reads[AccountMessages] = Json.reads[AccountMessages]

  override implicit def jsonWrites: OWrites[AccountMessages] = Json.writes[AccountMessages]
}
