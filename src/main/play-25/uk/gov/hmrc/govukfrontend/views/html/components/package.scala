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
import uk.gov.hmrc.govukfrontend.views.html.components.backLink_Scope0.backLink
import uk.gov.hmrc.govukfrontend.views.html.components.button_Scope0.button
import uk.gov.hmrc.govukfrontend.views.html.components.errorMessage_Scope0.errorMessage
import uk.gov.hmrc.govukfrontend.views.html.components.errorSummary_Scope0.errorSummary
import uk.gov.hmrc.govukfrontend.views.html.components.fieldset_Scope0.fieldset
import uk.gov.hmrc.govukfrontend.views.html.components.footer_Scope0.footer
import uk.gov.hmrc.govukfrontend.views.html.components.govukTemplate_Scope0.govukTemplate_Scope1.govukTemplate
import uk.gov.hmrc.govukfrontend.views.html.components.header_Scope0.header
import uk.gov.hmrc.govukfrontend.views.html.components.hint_Scope0.hint
import uk.gov.hmrc.govukfrontend.views.html.components.label_Scope0.label
import uk.gov.hmrc.govukfrontend.views.html.components.phaseBanner_Scope0.phaseBanner
import uk.gov.hmrc.govukfrontend.views.html.components.skipLink_Scope0.skipLink
import uk.gov.hmrc.govukfrontend.views.html.components.tag_Scope0.tag

package object components extends Utils with Aliases {

  lazy val BackLink = new backLink()

  lazy val Button = new button()

  lazy val ErrorSummary = new errorSummary()

  lazy val Fieldset = new fieldset()

  lazy val Footer = new footer()

  lazy val Header = new header()

  lazy val Hint = new hint()

  lazy val Label = new label()

  lazy val Tag = new tag()

  lazy val PhaseBanner = new phaseBanner()

  lazy val SkipLink = new skipLink()

  lazy val ErrorMessage = new errorMessage()

  lazy val GovukTemplate = new govukTemplate()
}
