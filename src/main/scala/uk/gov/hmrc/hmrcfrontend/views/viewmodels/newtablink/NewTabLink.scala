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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.newtablink

import play.api.libs.json.{Json, OWrites, Reads}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.JsonDefaultValueFormatter

case class NewTabLink(
  text: String              = "",
  href: Option[String]      = None,
  language: Option[String]  = None,
  classList: Option[String] = None
)

object NewTabLink extends JsonDefaultValueFormatter[NewTabLink] {

  override def defaultObject: NewTabLink = NewTabLink()

  override def defaultReads: Reads[NewTabLink] = Json.reads[NewTabLink]

  override implicit def jsonWrites: OWrites[NewTabLink] = Json.writes[NewTabLink]
}
