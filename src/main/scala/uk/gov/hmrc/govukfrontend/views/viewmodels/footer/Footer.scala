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

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonDefaultValueFormatter

case class Footer(
  meta: Option[Meta]                        = None,
  //FIXME Option[Seq[T]] is used to represent the 3 possible types of values of javascript arrays: undefined, non-empty array, and empty array
  // once https://github.com/alphagov/govuk-frontend/issues/1618 is solved we can think of a better type
  navigation: Option[Seq[FooterNavigation]] = None,
  containerClasses: String                  = "",
  classes: String                           = "",
  attributes: Map[String, String]           = Map.empty
)

object Footer extends JsonDefaultValueFormatter[Footer] {

  override def defaultObject: Footer = Footer()

  override def defaultReads: Reads[Footer] =
    (
      (__ \ "meta").readNullable[Meta] and
        (__ \ "navigation").readNullable[Seq[FooterNavigation]] and
        (__ \ "containerClasses").read[String] and
        (__ \ "classes").read[String] and
        (__ \ "attributes").read[Map[String, String]]
    )(Footer.apply _)

  override implicit def jsonWrites: OWrites[Footer] =
    (
      (__ \ "meta").writeNullable[Meta] and
        (__ \ "navigation").writeNullable[Seq[FooterNavigation]] and
        (__ \ "containerClasses").write[String] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(unlift(Footer.unapply))

}
