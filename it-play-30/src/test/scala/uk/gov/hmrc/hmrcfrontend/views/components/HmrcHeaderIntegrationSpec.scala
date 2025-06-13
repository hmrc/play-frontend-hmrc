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

package uk.gov.hmrc.hmrcfrontend.views.components

import uk.gov.hmrc.support.ScalaCheckUtils.ClassifyParams
import uk.gov.hmrc.hmrcfrontend.support.TemplateIntegrationSpec
import uk.gov.hmrc.hmrcfrontend.views.html.components._

// We use the below instead of a true arbitrary as the `hmrc-frontend` Nunjucks
// model of Header is less flexible and specifically requests the href for `cy`
// and `en` to be defined.
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.header.Generators._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.En

object HmrcHeaderIntegrationSpec
    extends TemplateIntegrationSpec[HeaderParams, HmrcHeader](hmrcComponentName = "hmrcHeader", seed = None) {

  override def classifiers(header: HeaderParams): LazyList[ClassifyParams] =
    (header.headerUrls.homepageUrl.isEmpty, "empty homepageUrl", "non-empty homepageUrl") #::
      (header.headerUrls.homepageUrl.length > 10, "long homepageUrl", "short homepageUrl") #::
      (header.headerUrls.assetsPath.isEmpty, "empty assetsPath", "non-empty assetsPath") #::
      (header.headerUrls.assetsPath.length > 10, "long assetsPath", "short assetsPath") #::
      (header.headerNames.productName.isEmpty, "empty productName", "non-empty productName") #::
      (header.headerNames.productName.exists(_.length > 10), "long productName", "short productName") #::
      (header.headerNames.serviceName.isEmpty, "empty serviceName", "non-empty serviceName") #::
      (header.headerNames.serviceName.exists(_.length > 10), "long serviceName", "short serviceName") #::
      (header.headerUrls.serviceUrl.isEmpty, "empty serviceUrl", "non-empty serviceUrl") #::
      (header.headerUrls.serviceUrl.length > 10, "long serviceUrl", "short serviceUrl") #::
      (header.headerNavigation.navigation.isEmpty, "empty navigation list", "non-empty navigation list") #::
      (header.headerNavigation.navigation.exists(_.length > 10), "long navigation list", "short navigation list") #::
      (header.headerNavigation.navigationClasses.isEmpty, "empty navigationClasses", "non-empty navigationClasses") #::
      (header.headerNavigation.navigationClasses.length > 10, "long navigationClasses", "short navigationClasses") #::
      (header.headerTemplateOverrides.classes.isEmpty, "empty classes", "non-empty classes") #::
      (header.headerTemplateOverrides.classes.length > 10, "long classes", "short classes") #::
      (header.headerTemplateOverrides.attributes.isEmpty, "empty attributes", "non-empty attributes") #::
      (header.headerTemplateOverrides.attributes.values.exists(_.isEmpty), "empty attributes values", "non-empty attributes values") #::
      (header.language == En, "language English", "language Cymraeg") #::
      (header.banners.displayHmrcBanner, "displayHmrcBanner true", "displayHmrcBanner false") #::
      (header.headerUrls.signOutHref.isEmpty, "empty signOutHref", "non-empty signOutHref") #::
      (header.headerUrls.signOutHref.exists(_.length > 10), "long signOutHref", "short signOutHref") #::
      (header.banners.additionalBannersBlock.isEmpty, "no additional banners", "with additional banners") #::
      LazyList.empty[ClassifyParams]
}
