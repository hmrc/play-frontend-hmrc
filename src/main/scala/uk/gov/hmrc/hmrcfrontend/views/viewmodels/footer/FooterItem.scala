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

final case class FooterItem(
  text: Option[String]            = None,
  href: Option[String]            = None,
  attributes: Map[String, String] = Map.empty
)

object FooterItem extends JsonDefaultValueFormatter[FooterItem] {

  override def defaultObject: FooterItem = FooterItem()

  override def defaultReads: Reads[FooterItem] =
    (
      (__ \ "text").readNullable[String] and
        (__ \ "href").readNullable[String] and
        (__ \ "attributes").read[Map[String, String]]
    )(FooterItem.apply _)

  override implicit def jsonWrites: OWrites[FooterItem] =
    (
      (__ \ "text").writeNullable[String] and
        (__ \ "href").writeNullable[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(unlift(FooterItem.unapply))

}
