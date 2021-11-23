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

package uk.gov.hmrc.hmrcfrontend.config

import play.api.Configuration
import javax.inject.Inject

class AccountMenuConfig @Inject() (configuration: Configuration) {
  val accountHomeHref: String   = configuration.get[String]("pta-account-menu.account-home.href")
  val messagesHref: String      = configuration.get[String]("pta-account-menu.messages.href")
  val checkProgressHref: String = configuration.get[String]("pta-account-menu.check-progress.href")
  val yourProfileHref: String   = configuration.get[String]("pta-account-menu.your-profile.href")
}
