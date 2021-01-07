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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.footer

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonDefaultValueFormatter
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{En, Language}

case class Footer(
  meta: Option[Meta]                = None,
  navigation: Seq[FooterNavigation] = Seq.empty,
  containerClasses: String          = "",
  classes: String                   = "",
  attributes: Map[String, String]   = Map.empty,
  language: Language                = En
)

object Footer extends JsonDefaultValueFormatter[Footer] {

  override def defaultObject: Footer = Footer()

  override def defaultReads: Reads[Footer] =
    (
      (__ \ "meta").readNullable[Meta] and
        (__ \ "navigation").read[Seq[FooterNavigation]] and
        (__ \ "containerClasses").read[String] and
        (__ \ "classes").read[String] and
        (__ \ "attributes").read[Map[String, String]] and
        (__ \ "language").read[Language]
    )(Footer.apply _)

  override implicit def jsonWrites: OWrites[Footer] =
    (
      (__ \ "meta").writeNullable[Meta] and
        (__ \ "navigation").write[Seq[FooterNavigation]] and
        (__ \ "containerClasses").write[String] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "language").write[Language]
    )(unlift(Footer.unapply))

}
