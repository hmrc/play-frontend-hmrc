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

package uk.gov.hmrc.hmrcfrontend.views.components

import uk.gov.hmrc.support.ScalaCheckUtils.ClassifyParams
import uk.gov.hmrc.hmrcfrontend.support.TemplateIntegrationSpec
import uk.gov.hmrc.hmrcfrontend.views.html.components._

import scala.collection.compat.immutable.LazyList
// We use the below instead of a true arbitrary as the `hmrc-frontend` Nunjucks
// model of Header is less flexible and specifically requests the href for `cy`
// and `en` to be defined.
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.header.Generators._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.header.Header
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.En

object HmrcHeaderIntegrationSpec
    extends TemplateIntegrationSpec[Header, HmrcHeader](hmrcComponentName = "hmrcHeader", seed = None) {

  override def classifiers(header: Header): LazyList[ClassifyParams] =
    (header.homepageUrl.isEmpty, "empty homepageUrl", "non-empty homepageUrl") #::
      (header.homepageUrl.length > 10, "long homepageUrl", "short homepageUrl") #::
      (header.assetsPath.isEmpty, "empty assetsPath", "non-empty assetsPath") #::
      (header.assetsPath.length > 10, "long assetsPath", "short assetsPath") #::
      (header.productName.isEmpty, "empty productName", "non-empty productName") #::
      (header.productName.exists(_.length > 10), "long productName", "short productName") #::
      (header.serviceName.isEmpty, "empty serviceName", "non-empty serviceName") #::
      (header.serviceName.exists(_.length > 10), "long serviceName", "short serviceName") #::
      (header.serviceUrl.isEmpty, "empty serviceUrl", "non-empty serviceUrl") #::
      (header.serviceUrl.length > 10, "long serviceUrl", "short serviceUrl") #::
      (header.navigation.isEmpty, "empty navigation list", "non-empty navigation list") #::
      (header.navigation.exists(_.length > 10), "long navigation list", "short navigation list") #::
      (header.navigationClasses.isEmpty, "empty navigationClasses", "non-empty navigationClasses") #::
      (header.navigationClasses.length > 10, "long navigationClasses", "short navigationClasses") #::
      (header.classes.isEmpty, "empty classes", "non-empty classes") #::
      (header.classes.length > 10, "long classes", "short classes") #::
      (header.attributes.isEmpty, "empty attributes", "non-empty attributes") #::
      (header.attributes.values.exists(_.isEmpty), "empty attributes values", "non-empty attributes values") #::
      (header.language == En, "language English", "language Cymraeg") #::
      (header.displayHmrcBanner, "displayHmrcBanner true", "displayHmrcBanner false") #::
      (header.signOutHref.isEmpty, "empty signOutHref", "non-empty signOutHref") #::
      (header.signOutHref.exists(_.length > 10), "long signOutHref", "short signOutHref") #::
      (header.additionalBannersBlock.isEmpty, "no additional banners", "with additional banners") #::
      LazyList.empty[ClassifyParams]
}
