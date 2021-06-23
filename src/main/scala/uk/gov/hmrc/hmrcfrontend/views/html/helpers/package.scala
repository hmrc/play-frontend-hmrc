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

package uk.gov.hmrc.hmrcfrontend.views.html

import play.api.Configuration
import uk.gov.hmrc.hmrcfrontend.config.{AccessibilityStatementConfig, AssetsConfig, ContactFrontendConfig, LanguageConfig, TimeoutDialogConfig, TrackingConsentConfig}
import uk.gov.hmrc.hmrcfrontend.views.config.{HmrcFooterItems => HmrcFooterItemsType}
import uk.gov.hmrc.hmrcfrontend.views.html.components.{HmrcFooter, HmrcHeader, HmrcLanguageSelect, HmrcReportTechnicalIssue, HmrcTimeoutDialog}

package object helpers {
  private lazy val configuration = Configuration.load(
    classLoader = getClass.getClassLoader,
    properties = System.getProperties,
    directSettings = Map.empty[String, String],
    allowMissingApplicationConf = false
  )

  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val AccessibilityStatementConfig: AccessibilityStatementConfig =
    new AccessibilityStatementConfig(configuration)

  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val ContactFrontendConfig: ContactFrontendConfig = new ContactFrontendConfig(configuration)

  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val LanguageConfig: LanguageConfig = new LanguageConfig(configuration)

  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val TimeoutDialogConfig = new TimeoutDialogConfig(configuration)

  type HmrcFooterItems = HmrcFooterItemsType
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcFooterItems = new HmrcFooterItems(AccessibilityStatementConfig)

  type HmrcStandardFooter = hmrcStandardFooter
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcStandardFooter = new hmrcStandardFooter(HmrcFooter, HmrcFooterItems)

  type HmrcReportTechnicalIssueHelper = hmrcReportTechnicalIssueHelper
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcReportTechnicalIssueHelper =
    new HmrcReportTechnicalIssueHelper(HmrcReportTechnicalIssue, ContactFrontendConfig)

  type HmrcStandardHeader = hmrcStandardHeader
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcStandardHeader = new hmrcStandardHeader(HmrcHeader)

  type HmrcTimeoutDialogHelper = hmrcTimeoutDialogHelper
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcTimeoutDialogHelper = new hmrcTimeoutDialogHelper(HmrcTimeoutDialog, TimeoutDialogConfig)

  type HmrcLanguageSelectHelper = hmrcLanguageSelectHelper
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcLanguageSelectHelper = new hmrcLanguageSelectHelper(HmrcLanguageSelect, LanguageConfig)

  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val AssetsConfig = new AssetsConfig()

  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val TrackingConsentConfig: TrackingConsentConfig = new TrackingConsentConfig(configuration)

  type HmrcTrackingConsentSnippet = hmrcTrackingConsentSnippet
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcTrackingConsentSnippet = new hmrcTrackingConsentSnippet(TrackingConsentConfig)

  type HmrcHead = hmrcHead
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcHead = new hmrcHead(HmrcTrackingConsentSnippet, AssetsConfig)

  type HmrcScripts = hmrcScripts
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcScripts = new hmrcScripts(AssetsConfig)

  type HmrcLayout = hmrcLayout
}
