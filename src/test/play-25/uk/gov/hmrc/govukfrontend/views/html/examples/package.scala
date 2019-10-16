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

import uk.gov.hmrc.govukfrontend.views.html.components._

package object examples {

  lazy val BackLinkDefault = backlink.default

  lazy val ButtonDefault = button.default

  lazy val ButtonStart = button.start

  lazy val ButtonSecondary = button.secondary

  lazy val ButtonSecondaryCombo = button.secondaryCombo

  lazy val ButtonWarning = button.warning

  lazy val ButtonDisabled = button.disabled

  lazy val ButtonPreventDoubleClick = button.preventDoubleClick

  lazy val ErrorSummaryDefault = errorsummary.default

  lazy val ErrorSummaryLinking = errorsummary.linking

  lazy val ErrorSummaryLinkingMultipleFields = errorsummary.linkingMultipleFields

  lazy val ErrorSummaryLinkingCheckboxesRadios = errorsummary.linkingCheckboxesRadios

  lazy val ErrorSummaryFullPageExample = errorsummary.fullPageExample

  lazy val FieldsetAddressGroup = fieldset.addressGroup

  lazy val FieldsetDefault = fieldset.default

  lazy val ErrorMessageDefault = errormessage.default

  lazy val ErrorMessageLegend = errormessage.legend

  lazy val ErrorMessageLabel = errormessage.label

  lazy val FooterDefault = footer.default

  lazy val FooterWithNavigation = footer.withNavigation

  lazy val FooterWithMeta = footer.withMeta

  lazy val FooterFull = footer.full

  lazy val HeaderDefault = header.default

  lazy val HeaderWithServiceName = header.withServiceName

  lazy val HeaderWithServiceNameAndNavigation = header.withServiceNameAndNavigation

  lazy val PanelDefault = panel.default

  lazy val DetailsDefault = details.default

  lazy val InputDefault = textinput.default

  lazy val InputFixedWidth = textinput.inputFixedWidth

  lazy val InputFluidWidth = textinput.inputFluidWidth

  lazy val InputHintText = textinput.inputHintText

  lazy val InputError = textinput.error

  lazy val TextareaDefault = textarea.default

  lazy val TextareaError = textarea.error

  lazy val TextareaSpecifyingRows = textarea.specifyingRows

  lazy val RadiosDefault = radios.default

  lazy val RadiosConditionalReveal = radios.conditionalReveal

  lazy val RadiosDivider = radios.divider

  lazy val RadiosError = radios.error

  lazy val RadiosHint = radios.hint

  lazy val RadiosSmall = radios.small

  lazy val RadiosStacked = radios.stacked

  lazy val SummaryListDefault = summarylist.default

  lazy val SummaryListWithoutActions = summarylist.withoutActions

  lazy val SummaryListWithoutBorders = summarylist.withoutBorders
}
