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

package uk.gov.hmrc.govukfrontend.views.viewmodels.details

import play.api.libs.json._
import play.api.libs.functional.syntax._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content

case class Details(
  id: Option[String]              = None,
  open: Boolean                   = false,
  classes: String                 = "",
  attributes: Map[String, String] = Map.empty,
  summary: Content,
  content: Content
)

object Details {

  implicit val reads: Reads[Details] = (
    (__ \ "id").readNullable[String] and
      (__ \ "open").readWithDefault[Boolean](false) and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      Content.readsHtmlOrText((__ \ "summaryHtml"), (__ \ "summaryText")) and
      Content.reads
  )(Details.apply _)

  implicit val writes = (
    (__ \ "id").writeNullable[String] and
      (__ \ "open").write[Boolean] and
      (__ \ "classes").write[String] and
      (__ \ "attributes").write[Map[String, String]] and
      Content.writesContent("summaryHtml", "summaryText") and
      Content.writes
  )(unlift(Details.unapply))
}
