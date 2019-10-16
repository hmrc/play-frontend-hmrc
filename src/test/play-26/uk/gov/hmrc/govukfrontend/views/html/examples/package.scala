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

  lazy val BackLinkDefault = new backlink.default()

  lazy val ButtonDefault = new button.default()

  lazy val ButtonStart = new button.start()

  lazy val ButtonSecondary = new button.secondary()

  lazy val ButtonSecondaryCombo = new button.secondaryCombo()

  lazy val ButtonWarning = new button.warning()

  lazy val ButtonDisabled = new button.disabled()

  lazy val ButtonPreventDoubleClick = new button.preventDoubleClick()

  lazy val ErrorSummaryDefault = new errorsummary.default()

  lazy val ErrorSummaryLinking = new errorsummary.linking(GovukInput, GovukErrorSummary)

  lazy val ErrorSummaryLinkingMultipleFields = new errorsummary.linkingMultipleFields(GovukDateInput)

  lazy val ErrorSummaryLinkingCheckboxesRadios = new errorsummary.linkingCheckboxesRadios(GovukCheckboxes)

  lazy val ErrorSummaryFullPageExample =
    new errorsummary.fullPageExample(GovukBackLink, GovukButton, GovukDateInput, GovukErrorSummary)

  lazy val FieldsetAddressGroup = new fieldset.addressGroup(GovukInput)

  lazy val FieldsetDefault = new fieldset.default()

  lazy val ErrorMessageDefault = new errormessage.default(GovukDateInput)

  lazy val ErrorMessageLegend = new errormessage.legend(GovukCheckboxes)

  lazy val ErrorMessageLabel = new errormessage.label(GovukInput)

  lazy val FooterDefault = new footer.default()

  lazy val FooterWithNavigation = new footer.withNavigation()

  lazy val FooterWithMeta = new footer.withMeta()

  lazy val FooterFull = new footer.full()

  lazy val HeaderDefault = new header.default()

  lazy val HeaderWithServiceName = new header.withServiceName()

  lazy val HeaderWithServiceNameAndNavigation = new header.withServiceNameAndNavigation()

  lazy val PanelDefault = new panel.default()

  lazy val DetailsDefault = new details.default()

  lazy val InputDefault = new textinput.default()

  lazy val InputFixedWidth = new textinput.inputFixedWidth()

  lazy val InputFluidWidth = new textinput.inputFluidWidth()

  lazy val InputHintText = new textinput.inputHintText()

  lazy val InputError = new textinput.error()

  lazy val TextareaDefault = new textarea.default()

  lazy val TextareaError = new textarea.error()

  lazy val TextareaSpecifyingRows = new textarea.specifyingRows()

  lazy val RadiosDefault = new radios.default()

  lazy val RadiosConditionalReveal = new radios.conditionalReveal(GovukInput)

  lazy val RadiosDivider = new radios.divider()

  lazy val RadiosError = new radios.error()

  lazy val RadiosHint = new radios.hint()

  lazy val RadiosSmall = new radios.small()

  lazy val RadiosStacked = new radios.stacked()

  lazy val SummaryListDefault = new summarylist.default()

  lazy val SummaryListWithoutActions = new summarylist.withoutActions()

  lazy val SummaryListWithoutBorders = new summarylist.withoutBorders()
}
