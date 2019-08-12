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
  lazy val BackLink = new backLink()

  @deprecated("Use DI")
  lazy val Button = new button()

  @deprecated("Use DI")
  lazy val ErrorSummary = new errorSummary()

  @deprecated("Use DI")
  lazy val Fieldset = new fieldset()

  @deprecated("Use DI")
  lazy val Footer = new footer()

  @deprecated("Use DI")
  lazy val Header = new header()

  @deprecated("Use DI")
  lazy val Hint = new hint()

  @deprecated("Use DI")
  lazy val Label = new label()

  @deprecated("Use DI")
  lazy val Tag = new tag()

  @deprecated("Use DI")
  lazy val PhaseBanner = new phaseBanner(Tag)

  @deprecated("Use DI")
  lazy val SkipLink = new skipLink()

  @deprecated("Use DI")
  lazy val GovukTemplate = new govukTemplate(Header, Footer, SkipLink)
}
