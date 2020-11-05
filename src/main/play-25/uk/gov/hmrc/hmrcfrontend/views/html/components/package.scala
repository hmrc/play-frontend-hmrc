/*
 * Copyright 2020 HM Revenue & Customs
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

package object components extends Utils with Aliases {

  /**
    * Top-level implicits for all components
    */
  object implicits extends Implicits

  lazy val HmrcAccountMenu = hmrcAccountMenu

  lazy val HmrcBanner = hmrcBanner

  lazy val HmrcHeader = hmrcHeader

  lazy val HmrcFooter = hmrcFooter

  lazy val HmrcInternalHeader = hmrcInternalHeader

  lazy val HmrcLanguageSelect = hmrcLanguageSelect

  lazy val HmrcNewTabLink = hmrcNewTabLink

  lazy val HmrcNotificationBadge = hmrcNotificationBadge

  lazy val HmrcPageHeading = hmrcPageHeading

  lazy val HmrcTimeoutDialog = hmrcTimeoutDialog

  lazy val HmrcReportTechnicalIssue = hmrcReportTechnicalIssue

  lazy val HmrcCurrencyInput = hmrcCurrencyInput

}
