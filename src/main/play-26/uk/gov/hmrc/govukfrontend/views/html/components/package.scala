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

  type GovukBackLink = backLink
  @deprecated("Use DI")
  lazy val GovukBackLink = new backLink()

  type GovukButton = button
  @deprecated("Use DI")
  lazy val GovukButton = new button()

  type GovukErrorSummary = errorSummary
  @deprecated("Use DI")
  lazy val GovukErrorSummary = new errorSummary()

  type GovukFieldset = fieldset
  @deprecated("Use DI")
  lazy val GovukFieldset = new fieldset()

  type GovukFooter = footer
  @deprecated("Use DI")
  lazy val GovukFooter = new footer()

  type GovukHeader = header
  @deprecated("Use DI")
  lazy val GovukHeader = new header()

  type GovukHint = hint
  @deprecated("Use DI")
  lazy val GovukHint = new hint()

  type GovukLabel = label
  @deprecated("Use DI")
  lazy val GovukLabel = new label()

  type GovukTag = tag
  @deprecated("Use DI")
  lazy val GovukTag = new tag()

  type GovukPhaseBanner = phaseBanner
  @deprecated("Use DI")
  lazy val GovukPhaseBanner = new phaseBanner(GovukTag)

  type GovukSkipLink = skipLink
  @deprecated("Use DI")
  lazy val GovukSkipLink = new skipLink()

  type GovukErrorMessage = errorMessage
  @deprecated("Use DI")
  lazy val GovukErrorMessage = new errorMessage()

  type GovukDetails = details
  @deprecated("Use DI")
  lazy val GovukDetails = new details()

  type GovukRadios = radios
  @deprecated("Use DI")
  lazy val GovukRadios = new radios(GovukErrorMessage, GovukFieldset, GovukHint, GovukLabel)

  type GovukFileUpload = fileUpload
  @deprecated("Use DI")
  lazy val GovukFileUpload = new fileUpload(GovukErrorMessage, GovukHint, GovukLabel)

  type GovukInput = input
  @deprecated("Use DI")
  lazy val GovukInput = new input(GovukErrorMessage, GovukHint, GovukLabel)

  type GovukSummaryList = summaryList
  @deprecated("Use DI")
  lazy val GovukSummaryList = new summaryList()

  type GovukDateInput = dateInput
  @deprecated("Use DI")
  val GovukDateInput = new dateInput(GovukErrorMessage, GovukHint, GovukFieldset, GovukInput)

  type GovukAccordion = accordion
  @deprecated("Use DI")
  val GovukAccordion = new accordion()

  type GovukBreadcrumbs = breadcrumbs
  @deprecated("Use DI")
  val GovukBreadcrumbs = new breadcrumbs()

  type GovukTextarea = textarea
  @deprecated("Use DI")
  val GovukTextarea = new textarea(GovukErrorMessage, GovukHint, GovukLabel)

  type GovukCharacterCount = characterCount
  @deprecated("Use DI")
  val GovukCharacterCount = new characterCount(GovukTextarea)

  type GovukCheckboxes = checkboxes
  @deprecated("Use DI")
  val GovukCheckboxes = new checkboxes(GovukErrorMessage, GovukFieldset, GovukHint, GovukLabel)

  type GovukSelect = select
  @deprecated("Use DI")
  val GovukSelect = new select(GovukErrorMessage, GovukHint, GovukLabel)

  type GovukInsetText = insetText
  @deprecated("Use DI")
  val GovukInsetText = new insetText()

  type GovukWarningText = warningText
  @deprecated("Use DI")
  val GovukWarningText = new warningText()

  type GovukPanel = panel
  @deprecated("Use DI")
  val GovukPanel = new panel()

  type GovukTable = table
  @deprecated("Use DI")
  val GovukTable = new table()

  type GovukTabs = tabs
  @deprecated("Use DI")
  val GovukTabs = new tabs()

  type GovukTemplate = govukTemplate
  @deprecated("Use DI")
  lazy val GovukTemplate = new govukTemplate(GovukHeader, GovukFooter, GovukSkipLink)
}
