/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views

trait Aliases {
  type Banner = viewmodels.banner.Banner
  val Banner = viewmodels.banner.Banner

  type Banners = viewmodels.hmrcstandardpage.Banners
  val Banners = viewmodels.hmrcstandardpage.Banners

  val Cy = viewmodels.language.Cy

  val En = viewmodels.language.En

  type Header = viewmodels.header.Header
  val Header = viewmodels.header.Header

  type HeaderParams = viewmodels.header.v2.HeaderParams
  val HeaderParams = viewmodels.header.v2.HeaderParams

  type Footer = viewmodels.footer.Footer
  val Footer = viewmodels.footer.Footer

  type FooterNavigation = viewmodels.footer.FooterNavigation
  val FooterNavigation = viewmodels.footer.FooterNavigation

  type Meta = viewmodels.footer.Meta
  val Meta = viewmodels.footer.Meta

  type FooterItem = viewmodels.footer.FooterItem
  val FooterItem = viewmodels.footer.FooterItem

  type InternalHeader = viewmodels.internalheader.InternalHeader
  val InternalHeader = viewmodels.internalheader.InternalHeader

  type Language = viewmodels.language.Language
  val Language = viewmodels.language.Language

  type LanguageSelect = viewmodels.language.LanguageSelect
  val LanguageSelect = viewmodels.language.LanguageSelect

  type LanguageToggle = viewmodels.language.LanguageToggle
  val LanguageToggle = viewmodels.language.LanguageToggle

  type NavigationItem = viewmodels.header.NavigationItem
  val NavigationItem = viewmodels.header.NavigationItem

  type NewTabLink = viewmodels.newtablink.NewTabLink
  val NewTabLink = viewmodels.newtablink.NewTabLink

  type NotificationBadge = viewmodels.notificationbadge.NotificationBadge
  val NotificationBadge = viewmodels.notificationbadge.NotificationBadge

  type PageHeading = viewmodels.pageheading.PageHeading
  val PageHeading = viewmodels.pageheading.PageHeading

  type TimeoutDialog = viewmodels.timeoutdialog.TimeoutDialog
  val TimeoutDialog = viewmodels.timeoutdialog.TimeoutDialog

  type ReportTechnicalIssue = viewmodels.reporttechnicalissue.ReportTechnicalIssue
  val ReportTechnicalIssue = viewmodels.reporttechnicalissue.ReportTechnicalIssue

  type UserResearchBanner = viewmodels.userresearchbanner.UserResearchBanner
  val UserResearchBanner = viewmodels.userresearchbanner.UserResearchBanner

  type CharacterCount = viewmodels.charactercount.CharacterCount
  val CharacterCount = viewmodels.charactercount.CharacterCount

  type AddToAList = viewmodels.addtoalist.AddToAList
  val AddToAList = viewmodels.addtoalist.AddToAList

  type ListItem = viewmodels.addtoalist.ListItem
  val ListItem = viewmodels.addtoalist.ListItem

  type ItemType = viewmodels.addtoalist.ItemType
  val ItemType = viewmodels.addtoalist.ItemType

  type Timeline = viewmodels.timeline.Timeline
  val Timeline = viewmodels.timeline.Timeline

  type Event = viewmodels.timeline.Event
  val Event = viewmodels.timeline.Event

  type ListWithActions = viewmodels.listwithactions.ListWithActions
  val ListWithActions = viewmodels.listwithactions.ListWithActions

  type ListWithActionsItem = viewmodels.listwithactions.ListWithActionsItem
  val ListWithActionsItem = viewmodels.listwithactions.ListWithActionsItem

  type ListWithActionsAction = viewmodels.listwithactions.ListWithActionsAction
  val ListWithActionsAction = viewmodels.listwithactions.ListWithActionsAction
}

object Aliases extends Aliases
