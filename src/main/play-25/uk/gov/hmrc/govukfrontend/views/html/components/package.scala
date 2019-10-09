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

  lazy val GovukBackLink = backLink

  lazy val GovukButton = button

  lazy val GovukErrorSummary = errorSummary

  lazy val GovukFieldset = fieldset

  lazy val GovukFooter = footer

  lazy val GovukHeader = header

  lazy val GovukHint = hint

  lazy val GovukLabel = label

  lazy val GovukTag = tag

  lazy val GovukPhaseBanner = phaseBanner

  lazy val GovukSkipLink = skipLink

  lazy val GovukErrorMessage = errorMessage

  lazy val GovukDetails = details

  lazy val GovukRadios = radios

  lazy val GovukFileUpload = fileUpload

  lazy val GovukInput = input

  lazy val GovukSummaryList = summaryList

  lazy val GovukDateInput = dateInput

  lazy val GovukAccordion = accordion

  lazy val GovukBreadcrumbs = breadcrumbs

  lazy val GovukTextarea = textarea

  lazy val GovukCharacterCount = characterCount

  lazy val GovukCheckboxes = checkboxes

  lazy val GovukSelect = select

  lazy val GovukInsetText = insetText

  lazy val GovukWarningText = warningText

  lazy val GovukPanel = panel

  lazy val GovukTable = table

  lazy val GovukTabs = tabs

  lazy val GovukTemplate = govukTemplate
}
