/*
 * Copyright 2025 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.header

import play.api.libs.json.{OWrites, Reads}
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.viewmodels.phasebanner.PhaseBanner
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{Cy, En, Language, LanguageToggle}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.userresearchbanner.UserResearchBanner

import scala.collection.immutable.SortedMap

case class HeaderWithServiceNavigation(
  homepageUrl: String = "/",
  assetsPath: String = "/assets/images",
  productName: Option[String] = None,
  serviceName: Option[String] = None,
  serviceUrl: String = "",
  navigation: Option[Seq[NavigationItem]] = None,
  navigationClasses: String = "",
  containerClasses: String = "govuk-width-container",
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  language: Language = En,
  displayHmrcBanner: Boolean = false,
  useTudorCrown: Option[Boolean] = None,
  signOutHref: Option[String] = None,
  private val inputLanguageToggle: Option[LanguageToggle] = None,
  userResearchBanner: Option[UserResearchBanner] = None,
  phaseBanner: Option[PhaseBanner] = None,
  additionalBannersBlock: Option[Html] = None,
  menuButtonLabel: Option[String] = None,
  menuButtonText: Option[String] = None,
  navigationLabel: Option[String] = None
) {
  // We use this method instead of using the input language toggle directly
  // as the version in `hmrc-frontend` is less flexible, and sets a default
  // href for `cy` and `en` as an empty string. This behaviour is mirrored
  // here both to pass the unit tests and to maintain parity with
  // `hmrc-frontend`.
  val languageToggle: Option[LanguageToggle] = inputLanguageToggle match {
    case Some(x) =>
      val defaults: SortedMap[Language, String] = SortedMap(En -> "", Cy -> "")
      Some(LanguageToggle(defaults ++ x.linkMap))
    case None    => None
  }
}

object HeaderWithServiceNavigation {
  // TODO the bits below would be different in reality since HeaderWithServiceNavigation will
  // presumably have it's args grouped up, so no map / contramap from Header just used here
  // for convenience to check my understanding that it this kind of migration would work.

  implicit def jsonReads: Reads[HeaderWithServiceNavigation] =
    Header.jsonReads.map(Header.header2headerWithServiceNavigation)

  implicit def jsonWrites: OWrites[HeaderWithServiceNavigation] =
    Header.jsonWrites.contramap(header =>
      Header(
        header.homepageUrl,
        header.assetsPath,
        header.productName,
        header.serviceName,
        header.serviceUrl,
        header.navigation,
        header.navigationClasses,
        header.containerClasses,
        header.classes,
        header.attributes,
        header.language,
        header.displayHmrcBanner,
        header.useTudorCrown,
        header.signOutHref,
        header.inputLanguageToggle,
        header.userResearchBanner,
        header.phaseBanner,
        header.additionalBannersBlock,
        header.menuButtonLabel,
        header.menuButtonText,
        header.navigationLabel
      )
    )
}
