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
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats.htmlWrites
import uk.gov.hmrc.govukfrontend.views.viewmodels.servicenavigation.ServiceNavigation
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.header._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.hmrcstandardpage.Banners
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.Language.LanguageFormat
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{En, Language}
import play.api.libs.functional.syntax._

case class HeaderParams(
  headerUrls: HeaderUrls = HeaderUrls(),
  headerNames: HeaderNames = HeaderNames(),
  headerTemplateOverrides: HeaderTemplateOverrides = HeaderTemplateOverrides(),
  banners: Banners = Banners(),
  serviceNavigation: Option[ServiceNavigation] = None,
  language: Language = En
)

object HeaderParams {
  def defaultObject: HeaderParams = HeaderParams()

  import scala.language.implicitConversions

  implicit def headerToHeaderParams(header: Header): HeaderParams =
    HeaderParams(
      headerUrls = HeaderUrls(
        homepageUrl = header.homepageUrl,
        signOutHref = header.signOutHref,
        assetsPath = header.assetsPath
      ),
      headerNames = HeaderNames(
        productName = header.productName
      ),
      headerTemplateOverrides = HeaderTemplateOverrides(
        containerClasses = header.containerClasses,
        classes = header.classes,
        attributes = header.attributes
      ),
      banners = Banners(
        displayHmrcBanner = header.displayHmrcBanner,
        phaseBanner = header.phaseBanner,
        userResearchBanner = header.userResearchBanner,
        additionalBannersBlock = header.additionalBannersBlock
      ),
      language = header.language
    )

  implicit def reads: Reads[HeaderParams] =
    (
      __.readWithDefault[HeaderUrls](HeaderUrls.defaultObject) and
        __.readWithDefault[HeaderNames](HeaderNames.defaultObject) and
        __.readWithDefault[HeaderTemplateOverrides](HeaderTemplateOverrides.defaultObject) and
        __.readWithDefault[Banners](Banners.defaultObject) and
        (__ \ "serviceNavigation").readNullable[ServiceNavigation] and
        (__ \ "language").readWithDefault[Language](En)
    )(HeaderParams.apply _)

  implicit def writes: OWrites[HeaderParams] = new OWrites[HeaderParams] {
    override def writes(headerParams: HeaderParams): JsObject = {
      def toTuple[A](key: String, value: Option[A])(implicit writes: Writes[A]): Option[(String, JsValue)] =
        value.map(key -> Json.toJson(_))

      val jsValues: Seq[(String, JsValue)] = Seq(
        toTuple("homepageUrl", Some(headerParams.headerUrls.homepageUrl)),
        toTuple("assetsPath", Some(headerParams.headerUrls.assetsPath)),
        toTuple("productName", headerParams.headerNames.productName),
        toTuple("containerClasses", Some(headerParams.headerTemplateOverrides.containerClasses)),
        toTuple("classes", Some(headerParams.headerTemplateOverrides.classes)),
        toTuple("attributes", Some(headerParams.headerTemplateOverrides.attributes)),
        toTuple("language", Some(headerParams.language)),
        toTuple("displayHmrcBanner", Some(headerParams.banners.displayHmrcBanner)),
        toTuple("signOutHref", headerParams.headerUrls.signOutHref),
        toTuple("userResearchBanner", headerParams.banners.userResearchBanner),
        toTuple("phaseBanner", headerParams.banners.phaseBanner),
        toTuple("additionalBannersBlock", headerParams.banners.additionalBannersBlock),
        toTuple("serviceNavigation", headerParams.serviceNavigation),
        toTuple("headerClasses", Some(headerParams.headerTemplateOverrides.headerClasses)),
        toTuple("headerAttributes", Some(headerParams.headerTemplateOverrides.headerAttributes))
      ).flatten

      jsValues.foldLeft(Json.obj())((json, tuple) => json + tuple)
    }
  }
}
