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

  @deprecated("Use DI")
  type BackLink = backLink
  lazy val BackLink = new backLink()

  @deprecated("Use DI")
  type Button = button
  lazy val Button = new button()

  @deprecated("Use DI")
  type ErrorSummary = errorSummary
  lazy val ErrorSummary = new errorSummary()

  @deprecated("Use DI")
  type Fieldset = fieldset
  lazy val Fieldset = new fieldset()

  @deprecated("Use DI")
  type Footer = footer
  lazy val Footer = new footer()

  @deprecated("Use DI")
  type Header = header
  lazy val Header = new header()

  @deprecated("Use DI")
  type Hint = hint
  lazy val Hint = new hint()

  @deprecated("Use DI")
  type Label = label
  lazy val Label = new label()

  @deprecated("Use DI")
  type Tag = tag
  lazy val Tag = new tag()

  @deprecated("Use DI")
  type PhaseBanner = phaseBanner
  lazy val PhaseBanner = new phaseBanner(Tag)

  @deprecated("Use DI")
  type SkipLink = skipLink
  lazy val SkipLink = new skipLink()

  @deprecated("Use DI")
  type ErrorMessage = errorMessage
  lazy val ErrorMessage = new errorMessage()

  @deprecated("Use DI")
  type Details = details
  lazy val Details = new details()

  @deprecated("Use DI")
  type Radios = radios
  lazy val Radios = new radios(ErrorMessage, Fieldset, Hint, Label)

  @deprecated("Use DI")
  type GovukTemplate = govukTemplate
  lazy val GovukTemplate = new govukTemplate(Header, Footer, SkipLink)
}
