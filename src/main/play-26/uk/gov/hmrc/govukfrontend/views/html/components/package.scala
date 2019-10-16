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

package uk.gov.hmrc.govukfrontend.views
package html

package object components extends Utils with Aliases {

  /**
    * Top-level implicits for all components
    */
  object implicits extends Implicits

  type GovukBackLink = govukBackLink
  @deprecated("Use DI")
  lazy val GovukBackLink = new govukBackLink()

  type GovukButton = govukButton
  @deprecated("Use DI")
  lazy val GovukButton = new govukButton()

  type GovukErrorSummary = govukErrorSummary
  @deprecated("Use DI")
  lazy val GovukErrorSummary = new govukErrorSummary()

  type GovukFieldset = govukFieldset
  @deprecated("Use DI")
  lazy val GovukFieldset = new govukFieldset()

  type GovukFooter = govukFooter
  @deprecated("Use DI")
  lazy val GovukFooter = new govukFooter()

  type GovukHeader = govukHeader
  @deprecated("Use DI")
  lazy val GovukHeader = new govukHeader()

  type GovukHint = govukHint
  @deprecated("Use DI")
  lazy val GovukHint = new govukHint()

  type GovukLabel = govukLabel
  @deprecated("Use DI")
  lazy val GovukLabel = new govukLabel()

  type GovukTag = govukTag
  @deprecated("Use DI")
  lazy val GovukTag = new govukTag()

  type GovukPhaseBanner = govukPhaseBanner
  @deprecated("Use DI")
  lazy val GovukPhaseBanner = new govukPhaseBanner(GovukTag)

  type GovukSkipLink = govukSkipLink
  @deprecated("Use DI")
  lazy val GovukSkipLink = new govukSkipLink()

  type GovukErrorMessage = govukErrorMessage
  @deprecated("Use DI")
  lazy val GovukErrorMessage = new govukErrorMessage()

  type GovukDetails = govukDetails
  @deprecated("Use DI")
  lazy val GovukDetails = new govukDetails()

  type GovukRadios = govukRadios
  @deprecated("Use DI")
  lazy val GovukRadios = new govukRadios(GovukErrorMessage, GovukFieldset, GovukHint, GovukLabel)

  type GovukFileUpload = govukFileUpload
  @deprecated("Use DI")
  lazy val GovukFileUpload = new govukFileUpload(GovukErrorMessage, GovukHint, GovukLabel)

  type GovukInput = govukInput
  @deprecated("Use DI")
  lazy val GovukInput = new govukInput(GovukErrorMessage, GovukHint, GovukLabel)

  type GovukSummaryList = govukSummaryList
  @deprecated("Use DI")
  lazy val GovukSummaryList = new govukSummaryList()

  type GovukDateInput = govukDateInput
  @deprecated("Use DI")
  val GovukDateInput = new govukDateInput(GovukErrorMessage, GovukHint, GovukFieldset, GovukInput)

  type GovukAccordion = govukAccordion
  @deprecated("Use DI")
  val GovukAccordion = new govukAccordion()

  type GovukBreadcrumbs = govukBreadcrumbs
  @deprecated("Use DI")
  val GovukBreadcrumbs = new govukBreadcrumbs()

  type GovukTextarea = govukTextarea
  @deprecated("Use DI")
  val GovukTextarea = new govukTextarea(GovukErrorMessage, GovukHint, GovukLabel)

  type GovukCharacterCount = govukCharacterCount
  @deprecated("Use DI")
  val GovukCharacterCount = new govukCharacterCount(GovukTextarea)

  type GovukCheckboxes = govukCheckboxes
  @deprecated("Use DI")
  val GovukCheckboxes = new govukCheckboxes(GovukErrorMessage, GovukFieldset, GovukHint, GovukLabel)

  type GovukSelect = govukSelect
  @deprecated("Use DI")
  val GovukSelect = new govukSelect(GovukErrorMessage, GovukHint, GovukLabel)

  type GovukInsetText = govukInsetText
  @deprecated("Use DI")
  val GovukInsetText = new govukInsetText()

  type GovukWarningText = govukWarningText
  @deprecated("Use DI")
  val GovukWarningText = new govukWarningText()

  type GovukPanel = govukPanel
  @deprecated("Use DI")
  val GovukPanel = new govukPanel()

  type GovukTable = govukTable
  @deprecated("Use DI")
  val GovukTable = new govukTable()

  type GovukTabs = govukTabs
  @deprecated("Use DI")
  val GovukTabs = new govukTabs()

  type GovukTemplate = govukTemplate
  @deprecated("Use DI")
  lazy val GovukTemplate = new govukTemplate(GovukHeader, GovukFooter, GovukSkipLink)
}
