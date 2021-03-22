/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.config

import javax.inject.Inject
import uk.gov.hmrc.hmrcfrontend.controllers.routes.Assets
import uk.gov.hmrc.hmrcfrontend.views.HmrcFrontendDependency

class AssetsConfig @Inject() () {
  private val hmrcFrontendVersion = HmrcFrontendDependency.hmrcFrontendVersion

  lazy val html5ShivJsUrl: String        = Assets.at("vendor/html5shiv.min.js").url
  lazy val hmrcFrontendCssUrl: String    = hmrcFrontendAssetUrl(s"hmrc-frontend-$hmrcFrontendVersion.min.css")
  lazy val hmrcFrontendIe8CssUrl: String = hmrcFrontendAssetUrl(s"hmrc-frontend-ie8-$hmrcFrontendVersion.min.css")
  lazy val hmrcFrontendJsUrl: String     = hmrcFrontendAssetUrl(s"hmrc-frontend-$hmrcFrontendVersion.min.js")

  private def hmrcFrontendAssetUrl(filename: String) = Assets.at(filename).url
}
