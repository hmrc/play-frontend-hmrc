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

package uk.gov.hmrc.govukfrontend.views

import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.html.layouts._

trait Layouts {

  type GovukLayout = govukLayout
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val GovukLayout = new govukLayout(GovukTemplate, GovukHeader, GovukFooter, GovukBackLink, TwoThirdsMainContent)

  type GovukTemplate = govukTemplate
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val GovukTemplate = new govukTemplate(GovukHeader, GovukFooter, GovukSkipLink)

  type TwoThirdsMainContent = twoThirdsMainContent
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val TwoThirdsMainContent = new twoThirdsMainContent

  type TwoThirdsOneThirdMainContent = twoThirdsOneThirdMainContent
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val TwoThirdsOneThirdMainContent = new twoThirdsOneThirdMainContent
}
