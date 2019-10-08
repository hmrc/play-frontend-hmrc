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

trait Aliases {
  type Content = viewmodels.content.Content
  val Content = viewmodels.content.Content

  val Empty = viewmodels.content.Empty

  type HtmlContent = viewmodels.content.HtmlContent
  val HtmlContent = viewmodels.content.HtmlContent

  type Text = viewmodels.content.Text
  val Text = viewmodels.content.Text

  val NonEmptyHtml = viewmodels.content.NonEmptyHtml

  val NonEmptyText = viewmodels.content.NonEmptyText

  type BackLinkParams = viewmodels.backlink.BackLinkParams
  val BackLinkParams = viewmodels.backlink.BackLinkParams

  type ButtonParams = viewmodels.button.ButtonParams
  val ButtonParams = viewmodels.button.ButtonParams

  type RadiosParams = viewmodels.radios.RadiosParams
  val RadiosParams = viewmodels.radios.RadiosParams

  type PanelParams = viewmodels.panel.PanelParams
  val PanelParams = viewmodels.panel.PanelParams

  type CharacterCountParams = viewmodels.charactercount.CharacterCountParams
  val CharacterCountParams = viewmodels.charactercount.CharacterCountParams

  type DateInputParams = viewmodels.dateinput.DateInputParams
  val DateInputParams = viewmodels.dateinput.DateInputParams

  type InputParams = viewmodels.input.InputParams
  val InputParams = viewmodels.input.InputParams

  type DetailsParams = viewmodels.details.DetailsParams
  val DetailsParams = viewmodels.details.DetailsParams

  type FileUploadParams = viewmodels.fileupload.FileUploadParams
  val FileUploadParams = viewmodels.fileupload.FileUploadParams

  type InsetTextParams = viewmodels.insettext.InsetTextParams
  val InsetTextParams = viewmodels.insettext.InsetTextParams

  type FooterParams = viewmodels.footer.FooterParams
  val FooterParams = viewmodels.footer.FooterParams

  type FooterNavigation = viewmodels.footer.FooterNavigation
  val FooterNavigation = viewmodels.footer.FooterNavigation

  type Meta = viewmodels.footer.Meta
  val Meta = viewmodels.footer.Meta

  type FooterItem = viewmodels.footer.FooterItem
  val FooterItem = viewmodels.footer.FooterItem

  type HeaderParams = viewmodels.header.HeaderParams
  val HeaderParams = viewmodels.header.HeaderParams

  type HeaderNavigation = viewmodels.header.HeaderNavigation
  val HeaderNavigation = viewmodels.header.HeaderNavigation

  type TagParams = viewmodels.tag.TagParams
  val TagParams = viewmodels.tag.TagParams

  type PhaseBannerParams = viewmodels.phasebanner.PhaseBannerParams
  val PhaseBannerParams = viewmodels.phasebanner.PhaseBannerParams

  type SkipLinkParams = viewmodels.skiplink.SkipLinkParams
  val SkipLinkParams = viewmodels.skiplink.SkipLinkParams

  type ErrorLink = viewmodels.errorsummary.ErrorLink
  val ErrorLink = viewmodels.errorsummary.ErrorLink

  type Legend = viewmodels.fieldset.Legend
  val Legend = viewmodels.fieldset.Legend

  type FieldsetParams = viewmodels.fieldset.FieldsetParams
  val FieldsetParams = viewmodels.fieldset.FieldsetParams

  type HintParams = viewmodels.hint.HintParams
  val HintParams = viewmodels.hint.HintParams

  type LabelParams = viewmodels.label.LabelParams
  val LabelParams = viewmodels.label.LabelParams

  type RadioItem = viewmodels.radios.RadioItem
  val RadioItem = viewmodels.radios.RadioItem

  type ErrorMessageParams = viewmodels.errormessage.ErrorMessageParams
  val ErrorMessageParams = viewmodels.errormessage.ErrorMessageParams

  type ErrorSummaryParams = viewmodels.errorsummary.ErrorSummaryParams
  val ErrorSummaryParams = viewmodels.errorsummary.ErrorSummaryParams

  type CheckboxesParams = viewmodels.checkboxes.CheckboxesParams
  val CheckboxesParams = viewmodels.checkboxes.CheckboxesParams

  type SummaryListParams = viewmodels.summarylist.SummaryListParams
  val SummaryListParams = viewmodels.summarylist.SummaryListParams

  type SummaryListRow = viewmodels.summarylist.SummaryListRow
  val SummaryListRow = viewmodels.summarylist.SummaryListRow

  type Key = viewmodels.summarylist.Key
  val Key = viewmodels.summarylist.Key

  type Value = viewmodels.summarylist.Value
  val Value = viewmodels.summarylist.Value

  type Actions = viewmodels.summarylist.Actions
  val Actions = viewmodels.summarylist.Actions

  type ActionItem = viewmodels.summarylist.ActionItem
  val ActionItem = viewmodels.summarylist.ActionItem

  type InputItem = viewmodels.dateinput.InputItem
  val InputItem = viewmodels.dateinput.InputItem

  type AccordionParams = viewmodels.accordion.AccordionParams
  val AccordionParams = viewmodels.accordion.AccordionParams

  type Section = viewmodels.accordion.Section
  val Section = viewmodels.accordion.Section

  type BreadcrumbsParams = viewmodels.breadcrumbs.BreadcrumbsParams
  val BreadcrumbsParams = viewmodels.breadcrumbs.BreadcrumbsParams

  type BreadcrumbsItem = viewmodels.breadcrumbs.BreadcrumbsItem
  val BreadcrumbsItem = viewmodels.breadcrumbs.BreadcrumbsItem

  type CheckboxItem = viewmodels.checkboxes.CheckboxItem
  val CheckboxItem = viewmodels.checkboxes.CheckboxItem

  type SelectParams = viewmodels.select.SelectParams
  val SelectParams = viewmodels.select.SelectParams

  type SelectItem = viewmodels.select.SelectItem
  val SelectItem = viewmodels.select.SelectItem

  type TableParams = viewmodels.table.TableParams
  val TableParams = viewmodels.table.TableParams

  type TableRow = viewmodels.table.TableRow
  val TableRow = viewmodels.table.TableRow

  type HeadCell = viewmodels.table.HeadCell
  val HeadCell = viewmodels.table.HeadCell

  type TabsParams = viewmodels.tabs.TabsParams
  val TabsParams = viewmodels.tabs.TabsParams

  type TabItem = viewmodels.tabs.TabItem
  val TabItem = viewmodels.tabs.TabItem

  type TabPanel = viewmodels.tabs.TabPanel
  val TabPanel = viewmodels.tabs.TabPanel

  type TextareaParams = viewmodels.textarea.TextareaParams
  val TextareaParams = viewmodels.textarea.TextareaParams

  type WarningTextParams = viewmodels.warningtext.WarningTextParams
  val WarningTextParams = viewmodels.warningtext.WarningTextParams

}

object Aliases extends Aliases
