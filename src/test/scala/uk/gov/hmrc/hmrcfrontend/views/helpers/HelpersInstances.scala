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

package uk.gov.hmrc.hmrcfrontend.views.helpers

import org.scalatestplus.play.BaseOneAppPerSuite
import uk.gov.hmrc.hmrcfrontend.config.{AccessibilityStatementConfig, AssetsConfig, ContactFrontendConfig, TimeoutDialogConfig, TrackingConsentConfig}
import uk.gov.hmrc.hmrcfrontend.views.html.components.{HmrcFooter, HmrcHeader, HmrcReportTechnicalIssue, HmrcTimeoutDialog}
import uk.gov.hmrc.hmrcfrontend.views.html.helpers._

trait HelpersInstances {
  this: BaseOneAppPerSuite =>

  lazy val AccessibilityStatementConfig: AccessibilityStatementConfig =
    app.injector.instanceOf[AccessibilityStatementConfig]

  lazy val ContactFrontendConfig: ContactFrontendConfig = app.injector.instanceOf[ContactFrontendConfig]

  lazy val TrackingConsentConfig: TrackingConsentConfig = app.injector.instanceOf[TrackingConsentConfig]

  lazy val AssetsConfig: AssetsConfig = app.injector.instanceOf[AssetsConfig]

  lazy val TimeoutDialogConfig: TimeoutDialogConfig = app.injector.instanceOf[TimeoutDialogConfig]

  lazy val HmrcFooterItems = new HmrcFooterItems(AccessibilityStatementConfig)

  lazy val HmrcStandardFooter = new hmrcStandardFooter(HmrcFooter, HmrcFooterItems)

  lazy val HmrcTrackingConsentSnippet = new hmrcTrackingConsentSnippet(TrackingConsentConfig)

  lazy val HmrcReportTechnicalIssueHelper =
    new HmrcReportTechnicalIssueHelper(HmrcReportTechnicalIssue, ContactFrontendConfig)

  lazy val HmrcStandardHeader = new hmrcStandardHeader(HmrcHeader)

  lazy val HmrcHead = new hmrcHead(HmrcTrackingConsentSnippet, AssetsConfig)

  lazy val HmrcScripts = new hmrcScripts(AssetsConfig)

  lazy val HmrcTimeoutDialogHelper = new hmrcTimeoutDialogHelper(HmrcTimeoutDialog, TimeoutDialogConfig)
}
