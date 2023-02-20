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

package uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist

import play.api.libs.functional.syntax._
import play.api.libs.json.{Json, OWrites, Reads, __}
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats.attributesReads
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

case class Card(
  title: Option[CardTitle] = None,
  content: Content = Empty,
  actions: Option[Actions] = None,
  classes: String = "",
  attributes: Map[String, String] = Map.empty
)

object Card {

  def defaultObject: Card = Card()

  implicit def jsonReads: Reads[Card] = (
    (__ \ "title").readNullable[CardTitle] and
      Content.reads and
      (__ \ "actions").readNullable[Actions] and
      (__ \ "classes").readWithDefault[String](defaultObject.classes) and
      (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads)
  )(Card.apply _)

  implicit def jsonWrites: OWrites[Card] = Json.writes[Card]

}
