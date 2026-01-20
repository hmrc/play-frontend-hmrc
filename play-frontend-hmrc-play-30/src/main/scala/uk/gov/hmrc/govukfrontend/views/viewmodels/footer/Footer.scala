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

package uk.gov.hmrc.govukfrontend.views.viewmodels
package footer

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Footer(
  meta: Option[Meta] = None,
  navigation: Seq[FooterNavigation] = Seq.empty,
  containerClasses: String = "",
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  contentLicence: Option[ContentLicence] = None,
  copyright: Option[Copyright] = None,
  rebrand: Option[Boolean] = None
)

object Footer {

  def defaultObject: Footer = Footer()

  implicit def jsonReads: Reads[Footer] = Json.using[Json.WithDefaultValues].reads[Footer]

  implicit def jsonWrites: OWrites[Footer] = Json.writes[Footer]

}
