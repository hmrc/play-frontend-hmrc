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

package uk.gov.hmrc.hmrcfrontend.views.html.helpers

import play.api.i18n.Messages
import play.api.mvc.RequestHeader
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.govukfrontend.views.Aliases.{BackLink, PageLayout, PhaseBanner}
import uk.gov.hmrc.govukfrontend.views.html.components.{FixedWidthPageLayout, TwoThirdsMainContent}
import uk.gov.hmrc.govukfrontend.views.viewmodels.servicenavigation.ServiceNavigation
import uk.gov.hmrc.hmrcfrontend.views.Aliases.Header

import javax.inject.Inject

class HmrcLayout @Inject() (
  deprecatedHmrcLayout: DeprecatedHmrcLayout,
  twoThirdsMainContent: TwoThirdsMainContent,
  fixedWidthPageLayout: FixedWidthPageLayout
) {
  @deprecated(
    "Use HmrcStandardPage template instead - see DeprecatedHmrcLayout for details of how HmrcLayout parameters map to HmrcStandardPage",
    "6.3.0"
  )
  def apply(
    serviceName: Option[String] = None,
    pageTitle: Option[String] = None,
    isWelshTranslationAvailable: Boolean = false,
    signOutUrl: Option[String] = None,
    serviceUrl: Option[String] = None,
    userResearchBannerUrl: Option[String] = None,
    accessibilityStatementUrl: Option[String] = None,
    backLinkUrl: Option[String] = None,
    displayHmrcBanner: Boolean = false,
    phaseBanner: Option[PhaseBanner] = None,
    additionalHeadBlock: Option[Html] = None,
    additionalScriptsBlock: Option[Html] = None,
    beforeContentBlock: Option[Html] = None,
    mainContentLayout: Option[Html => Html] = Some(twoThirdsMainContent(_)),
    additionalBannersBlock: Option[Html] = None,
    pageLayout: Option[PageLayout => Html] = Some(fixedWidthPageLayout(_)),
    headerContainerClasses: String = Header.defaultObject.containerClasses,
    backLink: Option[BackLink] = None,
    serviceNavigation: Option[ServiceNavigation] = None
  )(contentBlock: Html)(implicit request: RequestHeader, messages: Messages): HtmlFormat.Appendable =
    deprecatedHmrcLayout(
      serviceName = serviceName,
      pageTitle = pageTitle,
      isWelshTranslationAvailable = isWelshTranslationAvailable,
      signOutUrl = signOutUrl,
      serviceUrl = serviceUrl,
      userResearchBannerUrl = userResearchBannerUrl,
      accessibilityStatementUrl = accessibilityStatementUrl,
      backLinkUrl = backLinkUrl,
      displayHmrcBanner = displayHmrcBanner,
      phaseBanner = phaseBanner,
      additionalHeadBlock = additionalHeadBlock,
      additionalScriptsBlock = additionalScriptsBlock,
      beforeContentBlock = beforeContentBlock,
      mainContentLayout = mainContentLayout,
      additionalBannersBlock = additionalBannersBlock,
      pageLayout = pageLayout,
      headerContainerClasses = headerContainerClasses,
      backLink = backLink,
      serviceNavigation = serviceNavigation
    )(contentBlock)
}
