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
  type Contents = viewmodels.common.Contents

  val Empty = viewmodels.common.Empty

  type HtmlContent = viewmodels.common.HtmlContent
  val HtmlContent = viewmodels.common.HtmlContent

  type Text = viewmodels.common.Text
  val Text = viewmodels.common.Text

  val NonEmptyHtml = viewmodels.common.NonEmptyHtml

  val NonEmptyText = viewmodels.common.NonEmptyText

  type FooterNavigation = viewmodels.footer.Navigation
  val FooterNavigation = viewmodels.footer.Navigation

  type FooterMeta = viewmodels.footer.Meta
  val FooterMeta = viewmodels.footer.Meta

  type FooterItem = viewmodels.footer.Item
  val FooterItem = viewmodels.footer.Item

  type HeaderParams = viewmodels.header.HeaderParams
  val HeaderParams = viewmodels.header.HeaderParams

  type HeaderNavigation = viewmodels.header.Navigation
  val HeaderNavigation = viewmodels.header.Navigation

  type TagParams = viewmodels.tag.TagParams
  val TagParams = viewmodels.tag.TagParams

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

  type VisuallyHiddenText = viewmodels.errormessage.VisuallyHiddenText

  type ShowText = viewmodels.errormessage.ShowText
  val ShowText = viewmodels.errormessage.ShowText

  val HideText = viewmodels.errormessage.HideText

  type ErrorMessageParams = viewmodels.errormessage.ErrorMessageParams
  val ErrorMessageParams = viewmodels.errormessage.ErrorMessageParams

  type Row = viewmodels.summarylist.Row
  val Row = viewmodels.summarylist.Row

  type Key = viewmodels.summarylist.Key
  val Key = viewmodels.summarylist.Key

  type Value = viewmodels.summarylist.Value
  val Value = viewmodels.summarylist.Value

  type Actions = viewmodels.summarylist.Actions
  val Actions = viewmodels.summarylist.Actions

  type ActionItem = viewmodels.summarylist.Item
  val ActionItem = viewmodels.summarylist.Item

  type InputParams = viewmodels.dateinput.InputParams
  val InputParams = viewmodels.dateinput.InputParams

  type Section = viewmodels.accordion.Section
  val Section = viewmodels.accordion.Section

  type BreadcrumbsItem = viewmodels.breadcrumbs.Item
  val BreadcrumbsItem = viewmodels.breadcrumbs.Item
}

object Aliases extends Aliases
