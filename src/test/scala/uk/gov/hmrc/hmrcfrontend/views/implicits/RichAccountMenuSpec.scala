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
import org.scalatest.wordspec.AnyWordSpec
import play.api.Configuration
import play.api.i18n.{Lang, Messages}
import play.api.test.FakeRequest
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.config.AccountMenuConfig
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.accountmenu._
import uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.En
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop.TableFor4

class RichAccountMenuSpec extends AnyWordSpec with Matchers with MessagesSupport {

  "Given an account menu and an account menu config, calling default url helper methods" should {

    "return a copy of the account menu with default urls replaced with those from config" in new Context {
      val originalAccountMenu: AccountMenu = AccountMenu()
      val updatedAccountMenu: AccountMenu  = originalAccountMenu.withUrlsFromConfig()(enMessages, FakeRequest())

      updatedAccountMenu shouldBe AccountMenu(
        accountHome = AccountHome(href = defaultAccountHomeHref),
        messages = AccountMessages(href = defaultMessagesHref),
        checkProgress = CheckProgress(href = defaultCheckProgressHref),
        yourProfile = YourProfile(href = defaultYourProfileHomeHref),
        language = En
      )
    }

    "return a copy of the account menu with default urls and correctly set active links" in new Context {
      val activeLinkCombinations: TableFor4[AccountHome, AccountMessages, CheckProgress, YourProfile] =
        Table(
          ("accountHomeActive", "messagesActive", "checkProgressActive", "yourProfileActive"),
          (AccountHome(active = true), AccountMessages(), CheckProgress(), YourProfile()),
          (AccountHome(), AccountMessages(active = true), CheckProgress(), YourProfile()),
          (AccountHome(), AccountMessages(), CheckProgress(active = true), YourProfile()),
          (AccountHome(), AccountMessages(), CheckProgress(), YourProfile(active = true))
        )

      forAll(activeLinkCombinations) {
        (
          accountHomeActive: AccountHome,
          messagesActive: AccountMessages,
          checkProgressActive: CheckProgress,
          yourProfileActive: YourProfile
        ) =>
          val accountMenu: AccountMenu = AccountMenu(
            accountHome = accountHomeActive,
            messages = messagesActive,
            checkProgress = checkProgressActive,
            yourProfile = yourProfileActive
          ).withUrlsFromConfig()(enMessages, FakeRequest())

          accountMenu shouldBe AccountMenu(
            accountHome = AccountHome(href = defaultAccountHomeHref, accountHomeActive.active),
            messages = AccountMessages(href = defaultMessagesHref, messagesActive.active),
            checkProgress = CheckProgress(href = defaultCheckProgressHref, checkProgressActive.active),
            yourProfile = YourProfile(href = defaultYourProfileHomeHref, yourProfileActive.active),
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
  }

  trait Context {
    val defaultHost = "localhost:110110"

    val defaultAccountHomeHref     = s"$defaultHost/personal-account"
    val defaultMessagesHref        = s"$defaultHost/personal-account/messages"
    val defaultCheckProgressHref   = s"$defaultHost/track"
    val defaultYourProfileHomeHref = s"$defaultHost/personal-account/your-profile"

    val enMessages: Messages = messagesApi.preferred(Seq(Lang("en")))
    val cyMessages: Messages = messagesApi.preferred(Seq(Lang("cy")))

    implicit val accountMenuConfig: AccountMenuConfig = new AccountMenuConfig(
      Configuration.from(
        Map(
          "pta-account-menu.account-home.href"   -> defaultAccountHomeHref,
          "pta-account-menu.messages.href"       -> defaultMessagesHref,
          "pta-account-menu.check-progress.href" -> defaultCheckProgressHref,
          "pta-account-menu.your-profile.href"   -> defaultYourProfileHomeHref
        )
      )
    )
  }
}
