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

package uk.gov.hmrc.govukfrontend.views.html

import uk.gov.hmrc.govukfrontend.views.{Aliases, Utils}

package object components extends Utils with Aliases {

  type BackLink = backLink
  @deprecated("Use DI")
  lazy val BackLink = new backLink()

  type Button = button
  @deprecated("Use DI")
  lazy val Button = new button()

  type ErrorSummary = errorSummary
  @deprecated("Use DI")
  lazy val ErrorSummary = new errorSummary()

  type Fieldset = fieldset
  @deprecated("Use DI")
  lazy val Fieldset = new fieldset()

  type Footer = footer
  @deprecated("Use DI")
  lazy val Footer = new footer()

  type Header = header
  @deprecated("Use DI")
  lazy val Header = new header()

  type Hint = hint
  @deprecated("Use DI")
  lazy val Hint = new hint()

  type Label = label
  @deprecated("Use DI")
  lazy val Label = new label()

  type Tag = tag
  @deprecated("Use DI")
  lazy val Tag = new tag()

  type PhaseBanner = phaseBanner
  @deprecated("Use DI")
  lazy val PhaseBanner = new phaseBanner(Tag)

  type SkipLink = skipLink
  @deprecated("Use DI")
  lazy val SkipLink = new skipLink()

  type ErrorMessage = errorMessage
  @deprecated("Use DI")
  lazy val ErrorMessage = new errorMessage()

  type Details = details
  @deprecated("Use DI")
  lazy val Details = new details()

  type Radios = radios
  @deprecated("Use DI")
  lazy val Radios = new radios(ErrorMessage, Fieldset, Hint, Label)

  type FileUpload = fileUpload
  @deprecated("Use DI")
  lazy val FileUpload = new fileUpload(ErrorMessage, Hint, Label)

  type Input = input
  @deprecated("Use DI")
  lazy val Input = new input(ErrorMessage, Hint, Label)

  type SummaryList = summaryList
  @deprecated("Use DI")
  lazy val SummaryList = new summaryList()

  type DateInput = dateInput
  @deprecated("Use DI")
  val DateInput = new dateInput(ErrorMessage, Hint, Fieldset, Input)

  type Accordion = accordion
  @deprecated("Use DI")
  val Accordion = new accordion()

  type Breadcrumbs = breadcrumbs
  @deprecated("Use DI")
  val Breadcrumbs = new breadcrumbs()

  type Textarea = textarea
  @deprecated("Use DI")
  val Textarea = new textarea(ErrorMessage, Hint, Label)

  type GovukTemplate = govukTemplate
  @deprecated("Use DI")
  lazy val GovukTemplate = new govukTemplate(Header, Footer, SkipLink)
}
