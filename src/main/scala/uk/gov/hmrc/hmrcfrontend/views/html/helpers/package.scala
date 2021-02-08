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

import play.api.Play
import uk.gov.hmrc.hmrcfrontend.config.{AccessibilityStatementConfig, ContactFrontendConfig, TrackingConsentConfig}
import uk.gov.hmrc.hmrcfrontend.views.config.{HmrcFooterItems => HmrcFooterItemsType}
import uk.gov.hmrc.hmrcfrontend.views.html.components.{HmrcFooter, HmrcHeader, HmrcReportTechnicalIssue}

package object helpers {
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val AccessibilityStatementConfig: AccessibilityStatementConfig = Play.current.injector.instanceOf[AccessibilityStatementConfig]

  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val ContactFrontendConfig: ContactFrontendConfig = Play.current.injector.instanceOf[ContactFrontendConfig]

  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val TrackingConsentConfig: TrackingConsentConfig = Play.current.injector.instanceOf[TrackingConsentConfig]

  type HmrcFooterItems = HmrcFooterItemsType
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcFooterItems = new HmrcFooterItems(AccessibilityStatementConfig)

  type HmrcStandardFooter = hmrcStandardFooter
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcStandardFooter = new hmrcStandardFooter(HmrcFooter, HmrcFooterItems)

  type HmrcTrackingConsentSnippet = hmrcTrackingConsentSnippet
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcTrackingConsentSnippet = new hmrcTrackingConsentSnippet(TrackingConsentConfig)

  type HmrcReportTechnicalIssueHelper = hmrcReportTechnicalIssueHelper
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcReportTechnicalIssueHelper =
    new HmrcReportTechnicalIssueHelper(HmrcReportTechnicalIssue, ContactFrontendConfig)

  type HmrcStandardHeader = hmrcStandardHeader
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcStandardHeader = new hmrcStandardHeader(HmrcHeader)
}
