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

package uk.gov.hmrc.hmrcfrontend.views.implicits

import play.api.i18n.Messages
import play.api.mvc.RequestHeader
import uk.gov.hmrc.hmrcfrontend.config.AccountMenuConfig
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.accountmenu.{AccountMenu, BusinessTaxAccount}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{Cy, En}

trait RichAccountMenuSupport extends {

  implicit class RichAccountMenu(accountMenu: AccountMenu)(implicit accountMenuConfig: AccountMenuConfig) {

    def withUrlsFromConfig()(implicit messages: Messages, request: RequestHeader): AccountMenu = {

      def updateDefault(originalHref: String, hrefFromConfig: String): String =
        if (originalHref == "#") hrefFromConfig else originalHref

      accountMenu.copy(
        accountHome = accountMenu.accountHome.copy(
          href = updateDefault(accountMenu.accountHome.href, accountMenuConfig.accountHomeHref)
        ),
        messages = accountMenu.messages.copy(
          href = updateDefault(accountMenu.messages.href, accountMenuConfig.messagesHref)
        ),
        checkProgress = accountMenu.checkProgress.copy(
          href = updateDefault(accountMenu.checkProgress.href, accountMenuConfig.checkProgressHref)
        ),
        yourProfile = accountMenu.yourProfile.copy(
          href = updateDefault(accountMenu.yourProfile.href, accountMenuConfig.yourProfileHref)
        ),
        businessTaxAccount = accountMenu.businessTaxAccount.map(businessAccount =>
          businessAccount.copy(href = updateDefault(businessAccount.href, accountMenuConfig.businessTaxAccountHref))
        ),
        language = if (messages.lang.code == "cy") Cy else En
      )
    }

    def withMessagesCount(count: Int): AccountMenu = accountMenu.copy(
      messages = accountMenu.messages.copy(messageCount = Some(count))
    )
  }

}
