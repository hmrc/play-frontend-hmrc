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

package uk.gov.hmrc.hmrcfrontend.views.implicits

import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.TableFor5
import org.scalatest.wordspec.AnyWordSpec
import play.api.Configuration
import play.api.i18n.{Lang, Messages}
import play.api.test.FakeRequest
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.config.AccountMenuConfig
import uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.accountmenu._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.En

class RichAccountMenuSpec extends AnyWordSpec with Matchers with MessagesSupport {

  "Given an account menu and an account menu config, calling default url helper methods" should {

    "return a copy of the account menu with default urls replaced with those from config" in new Context {
      val originalAccountMenu: AccountMenu = AccountMenu()
      originalAccountMenu shouldBe AccountMenu(
        accountHome = AccountHome(),
        messages = AccountMessages(),
        checkProgress = CheckProgress(),
        yourProfile = YourProfile(),
        businessTaxAccount = None,
        language = En
      )

      val updatedAccountMenu: AccountMenu = originalAccountMenu.withUrlsFromConfig()(enMessages, FakeRequest())
      updatedAccountMenu shouldBe AccountMenu(
        accountHome = AccountHome(href = defaultAccountHomeHref),
        messages = AccountMessages(href = defaultMessagesHref),
        checkProgress = CheckProgress(href = defaultCheckProgressHref),
        yourProfile = YourProfile(href = defaultYourProfileHomeHref),
        businessTaxAccount = None,
        language = En
      )
    }

    "return a copy of the account menu with default urls and correctly set active links" in new Context {
      val activeLinkCombinations
        : TableFor5[AccountHome, AccountMessages, CheckProgress, YourProfile, Option[BusinessTaxAccount]] =
        Table(
          (
            "accountHomeActive",
            "messagesActive",
            "checkProgressActive",
            "yourProfileActive",
            "businessTaxAccountActive"
          ),
          (AccountHome(), AccountMessages(), CheckProgress(), YourProfile(), None),
          (AccountHome(), AccountMessages(), CheckProgress(), YourProfile(), Some(BusinessTaxAccount())),
          (AccountHome(active = true), AccountMessages(), CheckProgress(), YourProfile(), Some(BusinessTaxAccount())),
          (AccountHome(), AccountMessages(active = true), CheckProgress(), YourProfile(), Some(BusinessTaxAccount())),
          (AccountHome(), AccountMessages(), CheckProgress(active = true), YourProfile(), Some(BusinessTaxAccount())),
          (AccountHome(), AccountMessages(), CheckProgress(), YourProfile(active = true), Some(BusinessTaxAccount())),
          (AccountHome(), AccountMessages(), CheckProgress(), YourProfile(), Some(BusinessTaxAccount(active = true)))
        )

      forAll(activeLinkCombinations) {
        (
          accountHomeActive: AccountHome,
          messagesActive: AccountMessages,
          checkProgressActive: CheckProgress,
          yourProfileActive: YourProfile,
          businessTaxAccountActive: Option[BusinessTaxAccount]
        ) =>
          val accountMenu: AccountMenu = AccountMenu(
            accountHome = accountHomeActive,
            messages = messagesActive,
            checkProgress = checkProgressActive,
            yourProfile = yourProfileActive,
            businessTaxAccount = businessTaxAccountActive
          ).withUrlsFromConfig()(enMessages, FakeRequest())

          accountMenu shouldBe AccountMenu(
            accountHome = AccountHome(href = defaultAccountHomeHref, accountHomeActive.active),
            messages = AccountMessages(href = defaultMessagesHref, messagesActive.active),
            checkProgress = CheckProgress(href = defaultCheckProgressHref, checkProgressActive.active),
            yourProfile = YourProfile(href = defaultYourProfileHomeHref, yourProfileActive.active),
            businessTaxAccount = businessTaxAccountActive.map(_.copy(href = defaultBusinessTaxAccountHref)),
            language = En
          )
      }
    }

    "return a copy of the account menu with default urls and correctly set message count" in new Context {
      val messageCount             = 13
      val accountMenu: AccountMenu = AccountMenu()
        .withUrlsFromConfig()(enMessages, FakeRequest())
        .withMessagesCount(messageCount)

      accountMenu shouldBe AccountMenu(
        accountHome = AccountHome(href = defaultAccountHomeHref),
        messages = AccountMessages(href = defaultMessagesHref, messageCount = Some(messageCount)),
        checkProgress = CheckProgress(href = defaultCheckProgressHref),
        yourProfile = YourProfile(href = defaultYourProfileHomeHref),
        language = En
      )
    }

    "return a copy of the account menu with a business tax account link with its default url replaced from config" in new Context {
      val businessTaxAccount: Option[BusinessTaxAccount] = Some(BusinessTaxAccount())

      val originalAccountMenu: AccountMenu = AccountMenu(businessTaxAccount = businessTaxAccount)
      originalAccountMenu shouldBe AccountMenu(
        accountHome = AccountHome(),
        messages = AccountMessages(),
        checkProgress = CheckProgress(),
        yourProfile = YourProfile(),
        businessTaxAccount = Some(BusinessTaxAccount()),
        language = En
      )

      val updatedAccountMenu: AccountMenu = originalAccountMenu.withUrlsFromConfig()(enMessages, FakeRequest())
      updatedAccountMenu shouldBe AccountMenu(
        accountHome = AccountHome(href = defaultAccountHomeHref),
        messages = AccountMessages(href = defaultMessagesHref),
        checkProgress = CheckProgress(href = defaultCheckProgressHref),
        yourProfile = YourProfile(href = defaultYourProfileHomeHref),
        businessTaxAccount = Some(BusinessTaxAccount(href = defaultBusinessTaxAccountHref)),
        language = En
      )
    }
  }

  trait Context {
    val defaultHost = "localhost:110110"

    val defaultAccountHomeHref        = s"$defaultHost/personal-account"
    val defaultMessagesHref           = s"$defaultHost/personal-account/messages"
    val defaultCheckProgressHref      = s"$defaultHost/track"
    val defaultYourProfileHomeHref    = s"$defaultHost/personal-account/your-profile"
    val defaultBusinessTaxAccountHref = s"$defaultHost/business-tax/home"

    val enMessages: Messages = messagesApi.preferred(Seq(Lang("en")))
    val cyMessages: Messages = messagesApi.preferred(Seq(Lang("cy")))

    implicit val accountMenuConfig: AccountMenuConfig = new AccountMenuConfig(
      Configuration.from(
        Map(
          "pta-account-menu.account-home.href"         -> defaultAccountHomeHref,
          "pta-account-menu.messages.href"             -> defaultMessagesHref,
          "pta-account-menu.check-progress.href"       -> defaultCheckProgressHref,
          "pta-account-menu.your-profile.href"         -> defaultYourProfileHomeHref,
          "pta-account-menu.business-tax-account.href" -> defaultBusinessTaxAccountHref
        )
      )
    )
  }
}
