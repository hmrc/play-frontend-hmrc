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

  lazy val BackLink = backLink

  lazy val Button = button

  lazy val ErrorSummary = errorSummary

  lazy val Fieldset = fieldset

  lazy val Footer = footer

  lazy val Header = header

  lazy val Hint = hint

  lazy val Label = label

  lazy val Tag = tag

  lazy val PhaseBanner = phaseBanner

  lazy val SkipLink = skipLink

  lazy val ErrorMessage = errorMessage

  lazy val Details = details

  lazy val Radios = radios

  lazy val FileUpload = fileUpload

  lazy val Input = input

  lazy val SummaryList = summaryList

  lazy val DateInput = dateInput

  lazy val Accordion = accordion

  lazy val Breadcrumbs = breadcrumbs

  lazy val Textarea = textarea

  lazy val CharacterCount = characterCount

  lazy val Checkboxes = checkboxes

  lazy val Select = select

  lazy val GovukTemplate = govukTemplate
}
