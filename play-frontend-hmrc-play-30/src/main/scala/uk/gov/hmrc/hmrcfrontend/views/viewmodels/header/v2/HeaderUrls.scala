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

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class HeaderUrls(
  homepageUrl: String = "/",
  serviceUrl: String = "",
  assetsPath: String = "/assets/images",
  signOutHref: Option[String] = None
)

object HeaderUrls {

  def defaultObject: HeaderUrls = HeaderUrls()

  implicit def jsonReads: Reads[HeaderUrls] =
    (
      (__ \ "homepageUrl").readWithDefault[String](defaultObject.homepageUrl) and
        (__ \ "serviceUrl").readWithDefault[String](defaultObject.serviceUrl) and
        (__ \ "assetsPath").readWithDefault[String](defaultObject.assetsPath) and
        (__ \ "signOutHref").readNullable[String]
    )(HeaderUrls.apply _)
}
