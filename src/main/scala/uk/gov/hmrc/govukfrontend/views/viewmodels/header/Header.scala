/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels.header

import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonDefaultValueFormatter

/***
  * We removed assets path since the Twirl implementation of <code>govukHeader</code> uses
  * reverse routes to reference the assets.
  * FIXME: when the library is ready for Production and the assets are hosted on a CDN
  * maybe add <code>assetsPath</code> back again to read from config in Prod and use reverse routes
  * in Dev.
  *
  * @param homepageUrl
  * @param productName
  * @param serviceName
  * @param serviceUrl
  * @param navigation
  * @param navigationClasses
  * @param containerClasses
  * @param classes
  * @param attributes
  */
final case class Header(
  homepageUrl: Option[String] = None,
  productName: Option[String] = None,
  serviceName: Option[String] = None,
  serviceUrl: Option[String]  = None,
  // FIXME: we need the unusual Option[Seq[HeaderNavigation]] to represent JS `undefined` values because None maps to `undefined` nicely.
  // If we refined the type, the correct type would be something like Option[NonEmptySeq[HeaderNavigation]]
  // once https://github.com/alphagov/govuk-frontend/issues/1618 is solved we can think of a better type
  navigation: Option[Seq[HeaderNavigation]] = None,
  navigationClasses: String                 = "",
  containerClasses: Option[String]          = None,
  classes: String                           = "",
  attributes: Map[String, String]           = Map.empty,
  menuButtonLabel: Option[String]           = None,
  navigationLabel: Option[String]           = None
)

object Header extends JsonDefaultValueFormatter[Header] {

  override def defaultObject: Header = Header()

  override def defaultReads: Reads[Header] = Json.reads[Header]

  override implicit def jsonWrites: OWrites[Header] = Json.writes[Header]
}
