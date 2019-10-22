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

package uk.gov.hmrc.govukfrontend.views.viewmodels.footer

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Footer(
  meta: Option[Meta]                        = None,
  navigation: Option[Seq[FooterNavigation]] = None,
  containerClasses: String                  = "",
  classes: String                           = "",
  attributes: Map[String, String]           = Map.empty
)

object Footer {

  implicit val reads: Reads[Footer] = (
    (__ \ "meta").readNullable[Meta] and
      (__ \ "navigation").readNullable[Seq[FooterNavigation]] and
      (__ \ "containerClasses").readWithDefault[String]("") and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(Footer.apply _)

  implicit val writes: OWrites[Footer] = (
    (__ \ "meta").writeNullable[Meta] and
      (__ \ "navigation").writeNullable[Seq[FooterNavigation]] and
      (__ \ "containerClasses").write[String] and
      (__ \ "classes").write[String] and
      (__ \ "attributes").write[Map[String, String]]
  )(unlift(Footer.unapply))
}
