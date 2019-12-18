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

package uk.gov.hmrc.hmrcfrontend.views

trait Aliases {
  type AccountHome = viewmodels.accountmenu.AccountHome
  val AccountHome = viewmodels.accountmenu.AccountHome

  type AccountMenu = viewmodels.accountmenu.AccountMenu
  val AccountMenu = viewmodels.accountmenu.AccountMenu

  type CheckProgress = viewmodels.accountmenu.CheckProgress
  val CheckProgress = viewmodels.accountmenu.CheckProgress

  type Cy = viewmodels.accountmenu.Language.Cy
  val Cy = viewmodels.accountmenu.Language.Cy

  type En = viewmodels.accountmenu.Language.En
  val En = viewmodels.accountmenu.Language.En

  type Language = viewmodels.accountmenu.Language
  val Language = viewmodels.accountmenu.Language

  type Messages = viewmodels.accountmenu.Messages
  val Messages = viewmodels.accountmenu.Messages

  type PaperlessSettings = viewmodels.accountmenu.PaperlessSettings
  val PaperlessSettings = viewmodels.accountmenu.PaperlessSettings

  type PersonalDetails = viewmodels.accountmenu.PersonalDetails
  val PersonalDetails = viewmodels.accountmenu.PersonalDetails

  type SignOut = viewmodels.accountmenu.SignOut
  val SignOut = viewmodels.accountmenu.SignOut

  type NotificationBadge = viewmodels.notificationbadge.NotificationBadge
  val NotificationBadge = viewmodels.notificationbadge.NotificationBadge

  type PageHeading = viewmodels.pageheading.PageHeading
  val PageHeading = viewmodels.pageheading.PageHeading
}

object Aliases extends Aliases
