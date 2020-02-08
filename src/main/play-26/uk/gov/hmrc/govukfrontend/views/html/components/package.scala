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

package object components extends  Utils with Aliases with Layouts with Helpers {

  /**
    * Top-level implicits for all components
    */
  object implicits extends Implicits

  type GovukBackLink = govukBackLink
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukBackLink = new govukBackLink()

  type GovukButton = govukButton
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukButton = new govukButton()

  type GovukErrorSummary = govukErrorSummary
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukErrorSummary = new govukErrorSummary()

  type GovukFieldset = govukFieldset
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukFieldset = new govukFieldset()

  type GovukFooter = govukFooter
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukFooter = new govukFooter()

  type GovukHeader = govukHeader
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukHeader = new govukHeader()

  type GovukHint = govukHint
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukHint = new govukHint()

  type GovukLabel = govukLabel
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukLabel = new govukLabel()

  type GovukTag = govukTag
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukTag = new govukTag()

  type GovukPhaseBanner = govukPhaseBanner
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukPhaseBanner = new govukPhaseBanner(GovukTag)

  type GovukSkipLink = govukSkipLink
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukSkipLink = new govukSkipLink()

  type GovukErrorMessage = govukErrorMessage
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukErrorMessage = new govukErrorMessage()

  type GovukDetails = govukDetails
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukDetails = new govukDetails()

  type GovukRadios = govukRadios
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukRadios = new govukRadios(GovukErrorMessage, GovukFieldset, GovukHint, GovukLabel)

  type GovukFileUpload = govukFileUpload
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukFileUpload = new govukFileUpload(GovukErrorMessage, GovukHint, GovukLabel)

  type GovukInput = govukInput
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukInput = new govukInput(GovukErrorMessage, GovukHint, GovukLabel)

  type GovukSummaryList = govukSummaryList
  @deprecated(message="Use DI", since="Play 2.6")
  lazy val GovukSummaryList = new govukSummaryList()

  type GovukDateInput = govukDateInput
  @deprecated(message="Use DI", since="Play 2.6")
  val GovukDateInput = new govukDateInput(GovukErrorMessage, GovukHint, GovukFieldset, GovukInput)

  type GovukAccordion = govukAccordion
  @deprecated(message="Use DI", since="Play 2.6")
  val GovukAccordion = new govukAccordion()

  type GovukBreadcrumbs = govukBreadcrumbs
  @deprecated(message="Use DI", since="Play 2.6")
  val GovukBreadcrumbs = new govukBreadcrumbs()

  type GovukTextarea = govukTextarea
  @deprecated(message="Use DI", since="Play 2.6")
  val GovukTextarea = new govukTextarea(GovukErrorMessage, GovukHint, GovukLabel)

  type GovukCharacterCount = govukCharacterCount
  @deprecated(message="Use DI", since="Play 2.6")
  val GovukCharacterCount = new govukCharacterCount(GovukTextarea, GovukHint)

  type GovukCheckboxes = govukCheckboxes
  @deprecated(message="Use DI", since="Play 2.6")
  val GovukCheckboxes = new govukCheckboxes(GovukErrorMessage, GovukFieldset, GovukHint, GovukLabel)

  type GovukSelect = govukSelect
  @deprecated(message="Use DI", since="Play 2.6")
  val GovukSelect = new govukSelect(GovukErrorMessage, GovukHint, GovukLabel)

  type GovukInsetText = govukInsetText
  @deprecated(message="Use DI", since="Play 2.6")
  val GovukInsetText = new govukInsetText()

  type GovukWarningText = govukWarningText
  @deprecated(message="Use DI", since="Play 2.6")
  val GovukWarningText = new govukWarningText()

  type GovukPanel = govukPanel
  @deprecated(message="Use DI", since="Play 2.6")
  val GovukPanel = new govukPanel()

  type GovukTable = govukTable
  @deprecated(message="Use DI", since="Play 2.6")
  val GovukTable = new govukTable()

  type GovukTabs = govukTabs
  @deprecated(message="Use DI", since="Play 2.6")
  val GovukTabs = new govukTabs()
}
