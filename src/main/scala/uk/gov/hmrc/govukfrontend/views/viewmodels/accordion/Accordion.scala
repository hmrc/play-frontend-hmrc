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

package uk.gov.hmrc.govukfrontend.views.viewmodels.accordion

import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonDefaultValueFormatter

case class Accordion(
  id: String                      = "",
  headingLevel: Int               = 2,
  classes: String                 = "",
  attributes: Map[String, String] = Map.empty,
  items: Seq[Section]             = Nil
)

object Accordion extends JsonDefaultValueFormatter[Accordion] {

  override def defaultObject: Accordion = Accordion()

  override def defaultReads: Reads[Accordion] = Json.reads[Accordion]

  override implicit def jsonWrites: OWrites[Accordion] = Json.writes[Accordion]
}
