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

package uk.gov.hmrc.govukfrontend.views.viewmodels.footer

import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonDefaultValueFormatter

final case class FooterNavigation(
  title: Option[String]          = None,
  columns: Option[Int]           = None,
  //FIXME Option[Seq[T]] is used to represent the 3 possible types of values of javascript arrays: undefined, non-empty array, and empty array
  // once https://github.com/alphagov/govuk-frontend/issues/1618 is solved we can think of a better type
  items: Option[Seq[FooterItem]] = None
)

object FooterNavigation extends JsonDefaultValueFormatter[FooterNavigation] {

  override def defaultObject: FooterNavigation = FooterNavigation()

  override def defaultReads: Reads[FooterNavigation] = Json.reads[FooterNavigation]

  override implicit def jsonWrites: OWrites[FooterNavigation] = Json.writes[FooterNavigation]
}
