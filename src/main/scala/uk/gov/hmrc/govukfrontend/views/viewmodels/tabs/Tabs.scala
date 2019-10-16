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

package uk.gov.hmrc.govukfrontend.views.viewmodels.tabs

import play.api.libs.json.{Json, OWrites, Reads}

case class Tabs(
  id: Option[String]              = None,
  idPrefix: Option[String]        = None,
  title: String                   = "Contents",
  items: Seq[TabItem]             = Nil,
  classes: String                 = "",
  attributes: Map[String, String] = Map.empty)

object Tabs {

  implicit val reads: Reads[Tabs] = Json.using[Json.WithDefaultValues].reads[Tabs]

  implicit val writes: OWrites[Tabs] = Json.writes[Tabs]
}
