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

package uk.gov.hmrc.hmrcfrontend.views
package html

import uk.gov.hmrc.govukfrontend.views.html.components.{GovukErrorMessage, GovukHint, GovukLabel}

package object components extends Utils with Aliases {

  /**
    * Top-level implicits for all components
    */
  object implicits extends Implicits

  type HmrcAccountMenu = hmrcAccountMenu
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcAccountMenu = new hmrcAccountMenu(HmrcNotificationBadge)

  type HmrcBanner = hmrcBanner
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcBanner = new hmrcBanner()

  type HmrcHeader = hmrcHeader
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcHeader = new hmrcHeader(HmrcBanner)

  type HmrcFooter = hmrcFooter
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcFooter = new hmrcFooter()

  type HmrcInternalHeader = hmrcInternalHeader
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcInternalHeader = new hmrcInternalHeader()

  type HmrcLanguageSelect = hmrcLanguageSelect
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcLanguageSelect = new hmrcLanguageSelect()

  type HmrcNewTabLink = hmrcNewTabLink
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcNewTabLink = new hmrcNewTabLink()

  type HmrcNotificationBadge = hmrcNotificationBadge
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcNotificationBadge = new hmrcNotificationBadge()

  type HmrcPageHeading = hmrcPageHeading
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcPageHeading = new hmrcPageHeading()

  type HmrcTimeoutDialog = hmrcTimeoutDialog
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcTimeoutDialog = new hmrcTimeoutDialog()

  type HmrcReportTechnicalIssue = hmrcReportTechnicalIssue
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcReportTechnicalIssue = new hmrcReportTechnicalIssue()

  type HmrcCurrencyInput = hmrcCurrencyInput
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcCurrencyInput = new hmrcCurrencyInput(GovukErrorMessage, GovukHint, GovukLabel)
}
