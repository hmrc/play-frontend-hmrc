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

package uk.gov.hmrc.govukfrontend.views
package html

package object components extends Utils with Aliases with Helpers with Layouts {

  /**
   * Top-level implicits for all components
   */
  object implicits extends Implicits

  lazy val GovukBackLink = govukBackLink

  lazy val GovukButton = govukButton

  lazy val GovukErrorSummary = govukErrorSummary

  lazy val GovukFieldset = govukFieldset

  lazy val GovukFooter = govukFooter

  lazy val GovukHeader = govukHeader

  lazy val GovukHint = govukHint

  lazy val GovukLabel = govukLabel

  lazy val GovukTag = govukTag

  lazy val GovukPhaseBanner = govukPhaseBanner

  lazy val GovukSkipLink = govukSkipLink

  lazy val GovukErrorMessage = govukErrorMessage

  lazy val GovukDetails = govukDetails

  lazy val GovukRadios = govukRadios

  lazy val GovukFileUpload = govukFileUpload

  lazy val GovukInput = govukInput

  lazy val GovukSummaryList = govukSummaryList

  lazy val GovukDateInput = govukDateInput

  lazy val GovukAccordion = govukAccordion

  lazy val GovukBreadcrumbs = govukBreadcrumbs

  lazy val GovukTextarea = govukTextarea

  lazy val GovukCharacterCount = govukCharacterCount

  lazy val GovukCheckboxes = govukCheckboxes

  lazy val GovukSelect = govukSelect

  lazy val GovukInsetText = govukInsetText

  lazy val GovukWarningText = govukWarningText

  lazy val GovukPanel = govukPanel

  lazy val GovukTable = govukTable

  lazy val GovukTabs = govukTabs
}
