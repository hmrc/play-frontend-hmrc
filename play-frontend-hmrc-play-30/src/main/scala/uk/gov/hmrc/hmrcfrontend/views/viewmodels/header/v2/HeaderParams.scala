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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.header.v2

import play.api.libs.json._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.header._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.hmrcstandardpage.Banners
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{Cy, En, Language, LanguageToggle}
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats.{htmlReads, htmlWrites}

import scala.collection.immutable.SortedMap

case class HeaderParams(
  headerUrls: HeaderUrls = HeaderUrls(),
  headerNames: HeaderNames = HeaderNames(),
  headerTemplateOverrides: HeaderTemplateOverrides = HeaderTemplateOverrides(),
  headerNavigation: HeaderNavigation = HeaderNavigation(),
  banners: Banners = Banners(),
  menuButtonOverrides: MenuButtonOverrides = MenuButtonOverrides(),
  logoOverrides: LogoOverrides = LogoOverrides(),
  language: Language = En,
  private val inputLanguageToggle: Option[LanguageToggle] = None
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

object HeaderParams {
  import scala.language.implicitConversions

  implicit def headerToHeaderParams(header: Header): HeaderParams =
    HeaderParams(
      headerUrls = HeaderUrls(
        homepageUrl = header.homepageUrl,
        serviceUrl = header.serviceUrl,
        signOutHref = header.signOutHref,
        assetsPath = header.assetsPath
      ),
      headerNames = HeaderNames(
        productName = header.productName,
        serviceName = header.serviceName
      ),
      headerTemplateOverrides = HeaderTemplateOverrides(
        containerClasses = header.containerClasses,
        classes = header.classes,
        attributes = header.attributes
      ),
      headerNavigation = HeaderNavigation(
        navigation = header.navigation,
        navigationClasses = header.navigationClasses,
        navigationLabel = header.navigationLabel
      ),
      banners = Banners(
        displayHmrcBanner = header.displayHmrcBanner,
        phaseBanner = header.phaseBanner,
        userResearchBanner = header.userResearchBanner,
        additionalBannersBlock = header.additionalBannersBlock
      ),
      menuButtonOverrides = MenuButtonOverrides(
        menuButtonLabel = header.menuButtonLabel,
        menuButtonText = header.menuButtonText
      ),
      logoOverrides = LogoOverrides(
        useTudorCrown = header.useTudorCrown,
        rebrand = header.rebrand
      ),
      language = header.language,
      inputLanguageToggle = header.languageToggle
    )

  implicit def reads: Reads[HeaderParams] = Header.jsonReads.map(headerToHeaderParams)

  implicit def writes: OWrites[HeaderParams] = new OWrites[HeaderParams] {
    override def writes(headerParams: HeaderParams): JsObject = {
      def toTuple[A](key: String, value: Option[A])(implicit writes: Writes[A]): Option[(String, JsValue)] =
        value.map(key -> Json.toJson(_))

      val jsValues: Seq[(String, JsValue)] = Seq(
        toTuple("homepageUrl", Some(headerParams.headerUrls.homepageUrl)),
        toTuple("assetsPath", Some(headerParams.headerUrls.assetsPath)),
        toTuple("productName", headerParams.headerNames.productName),
        toTuple("serviceName", headerParams.headerNames.serviceName),
        toTuple("serviceName", headerParams.headerNames.serviceName),
        toTuple("serviceUrl", Some(headerParams.headerUrls.serviceUrl)),
        toTuple("navigation", headerParams.headerNavigation.navigation),
        toTuple("productName", headerParams.headerNames.productName),
        toTuple("navigationClasses", Some(headerParams.headerNavigation.navigationClasses)),
        toTuple("containerClasses", Some(headerParams.headerTemplateOverrides.containerClasses)),
        toTuple("classes", Some(headerParams.headerTemplateOverrides.classes)),
        toTuple("attributes", Some(headerParams.headerTemplateOverrides.attributes)),
        toTuple("language", Some(headerParams.language)),
        toTuple("displayHmrcBanner", Some(headerParams.banners.displayHmrcBanner)),
        toTuple("useTudorCrown", headerParams.logoOverrides.useTudorCrown),
        toTuple("signOutHref", headerParams.headerUrls.signOutHref),
        toTuple("languageToggle", headerParams.languageToggle),
        toTuple("userResearchBanner", headerParams.banners.userResearchBanner),
        toTuple("phaseBanner", headerParams.banners.phaseBanner),
        toTuple("additionalBannersBlock", headerParams.banners.additionalBannersBlock),
        toTuple("menuButtonLabel", headerParams.menuButtonOverrides.menuButtonLabel),
        toTuple("menuButtonText", headerParams.menuButtonOverrides.menuButtonText),
        toTuple("navigationLabel", headerParams.headerNavigation.navigationLabel),
        toTuple("rebrand", headerParams.logoOverrides.rebrand)
      ).flatten

      jsValues.foldLeft(Json.obj())((json, tuple) => json + tuple)
    }
  }
}
