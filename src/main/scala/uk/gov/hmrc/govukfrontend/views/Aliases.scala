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

  type BackLink = viewmodels.backlink.BackLink
  val BackLink = viewmodels.backlink.BackLink

  type Button = viewmodels.button.Button
  val Button = viewmodels.button.Button

  type Radios = viewmodels.radios.Radios
  val Radios = viewmodels.radios.Radios

  type RadioItem = viewmodels.radios.RadioItem
  val RadioItem = viewmodels.radios.RadioItem

  type Panel = viewmodels.panel.Panel
  val Panel = viewmodels.panel.Panel

  type CharacterCount = viewmodels.charactercount.CharacterCount
  val CharacterCount = viewmodels.charactercount.CharacterCount

  type DateInput = viewmodels.dateinput.DateInput
  val DateInput = viewmodels.dateinput.DateInput

  type InputItem = viewmodels.dateinput.InputItem
  val InputItem = viewmodels.dateinput.InputItem

  type Input = viewmodels.input.Input
  val Input = viewmodels.input.Input

  type PrefixOrSuffix = viewmodels.input.PrefixOrSuffix
  val PrefixOrSuffix = viewmodels.input.PrefixOrSuffix

  type Details = viewmodels.details.Details
  val Details = viewmodels.details.Details

  type FileUpload = viewmodels.fileupload.FileUpload
  val FileUpload = viewmodels.fileupload.FileUpload

  type InsetText = viewmodels.insettext.InsetText
  val InsetText = viewmodels.insettext.InsetText

  type Footer = viewmodels.footer.Footer
  val Footer = viewmodels.footer.Footer

  type FooterNavigation = viewmodels.footer.FooterNavigation
  val FooterNavigation = viewmodels.footer.FooterNavigation

  type Meta = viewmodels.footer.Meta
  val Meta = viewmodels.footer.Meta

  type FooterItem = viewmodels.footer.FooterItem
  val FooterItem = viewmodels.footer.FooterItem

  type Header = viewmodels.header.Header
  val Header = viewmodels.header.Header

  type HeaderNavigation = viewmodels.header.HeaderNavigation
  val HeaderNavigation = viewmodels.header.HeaderNavigation

  type Tag = viewmodels.tag.Tag
  val Tag = viewmodels.tag.Tag

  type PhaseBanner = viewmodels.phasebanner.PhaseBanner
  val PhaseBanner = viewmodels.phasebanner.PhaseBanner

  type SkipLink = viewmodels.skiplink.SkipLink
  val SkipLink = viewmodels.skiplink.SkipLink

  type Legend = viewmodels.fieldset.Legend
  val Legend = viewmodels.fieldset.Legend

  type Fieldset = viewmodels.fieldset.Fieldset
  val Fieldset = viewmodels.fieldset.Fieldset

  type Hint = viewmodels.hint.Hint
  val Hint = viewmodels.hint.Hint

  type Label = viewmodels.label.Label
  val Label = viewmodels.label.Label

  type ErrorMessage = viewmodels.errormessage.ErrorMessage
  val ErrorMessage = viewmodels.errormessage.ErrorMessage

  type ErrorSummary = viewmodels.errorsummary.ErrorSummary
  val ErrorSummary = viewmodels.errorsummary.ErrorSummary

  type ErrorLink = viewmodels.errorsummary.ErrorLink
  val ErrorLink = viewmodels.errorsummary.ErrorLink

  type Checkboxes = viewmodels.checkboxes.Checkboxes
  val Checkboxes = viewmodels.checkboxes.Checkboxes

  type CheckboxItem = viewmodels.checkboxes.CheckboxItem
  val CheckboxItem = viewmodels.checkboxes.CheckboxItem

  type SummaryList = viewmodels.summarylist.SummaryList
  val SummaryList = viewmodels.summarylist.SummaryList

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

  type Accordion = viewmodels.accordion.Accordion
  val Accordion = viewmodels.accordion.Accordion

  type Section = viewmodels.accordion.Section
  val Section = viewmodels.accordion.Section

  type Breadcrumbs = viewmodels.breadcrumbs.Breadcrumbs
  val Breadcrumbs = viewmodels.breadcrumbs.Breadcrumbs

  type BreadcrumbsItem = viewmodels.breadcrumbs.BreadcrumbsItem
  val BreadcrumbsItem = viewmodels.breadcrumbs.BreadcrumbsItem

  type Select = viewmodels.select.Select
  val Select = viewmodels.select.Select

  type SelectItem = viewmodels.select.SelectItem
  val SelectItem = viewmodels.select.SelectItem

  type Table = viewmodels.table.Table
  val Table = viewmodels.table.Table

  type TableRow = viewmodels.table.TableRow
  val TableRow = viewmodels.table.TableRow

  type HeadCell = viewmodels.table.HeadCell
  val HeadCell = viewmodels.table.HeadCell

  type Tabs = viewmodels.tabs.Tabs
  val Tabs = viewmodels.tabs.Tabs

  type TabItem = viewmodels.tabs.TabItem
  val TabItem = viewmodels.tabs.TabItem

  type TabPanel = viewmodels.tabs.TabPanel
  val TabPanel = viewmodels.tabs.TabPanel

  type Textarea = viewmodels.textarea.Textarea
  val Textarea = viewmodels.textarea.Textarea

  type WarningText = viewmodels.warningtext.WarningText
  val WarningText = viewmodels.warningtext.WarningText

}

object Aliases extends Aliases
