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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.header

import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonDefaultValueFormatter
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty, Text}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content.writesContent

final case class NavigationItem(
                                   content: Content                                                                     = Empty,
                                   href: Option[String]                                                                 = None,
                                   active: Boolean                                                                      = false,
                                   attributes: Map[String, String]                                                      = Map.empty
                                 )

object NavigationItem extends JsonDefaultValueFormatter[NavigationItem] {

  override def defaultObject: NavigationItem = NavigationItem(Empty)

  override def defaultReads: Reads[NavigationItem] =
    (
        Content.reads and
        (__ \ "href").readNullable[String] and
        (__ \ "active").read[Boolean] and
        (__ \ "attributes").read[Map[String, String]]
      )(NavigationItem.apply _)

  override implicit def jsonWrites: OWrites[NavigationItem] = OWrites { hn =>
    Json.obj(
      "href"       -> hn.href,
      "active"     -> hn.active,
      "attributes" -> hn.attributes
    ) ++ writesContent().writes(hn.content)
  }
}