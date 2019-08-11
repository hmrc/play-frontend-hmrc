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

package uk.gov.hmrc.govukfrontend
package views
package components

trait Aliases {
  type Contents = viewmodels.common.Contents

  val Empty = viewmodels.common.Empty

  type HtmlContent = viewmodels.common.HtmlContent
  val HtmlContent = viewmodels.common.HtmlContent

  type Text = viewmodels.common.Text
  val Text = viewmodels.common.Text

  val NonEmptyHtml = viewmodels.common.NonEmptyHtml

  val NonEmptyText = viewmodels.common.NonEmptyText

  type FooterParams = viewmodels.footer.Params
  val FooterParams = viewmodels.footer.Params

  type FooterNavigation = viewmodels.footer.Navigation
  val FooterNavigation = viewmodels.footer.Navigation

  type FooterMeta = viewmodels.footer.Meta
  val FooterMeta = viewmodels.footer.Meta

  type FooterItem = viewmodels.footer.Item
  val FooterItem = viewmodels.footer.Item

  type HeaderParams = viewmodels.header.Params
  val HeaderParams = viewmodels.header.Params

  type HeaderNavigation = viewmodels.header.Navigation
  val HeaderNavigation = viewmodels.header.Navigation

  type TagParams = viewmodels.tag.Params
  val TagParams = viewmodels.tag.Params

  type ErrorLink = viewmodels.errorsummary.ErrorLink
  val ErrorLink = viewmodels.errorsummary.ErrorLink

  type Legend = viewmodels.fieldset.Legend
  val Legend = viewmodels.fieldset.Legend
}

object Aliases extends Aliases
