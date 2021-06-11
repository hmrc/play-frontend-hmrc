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

package uk.gov.hmrc.govukfrontend.views.viewmodels.maincontent

import play.api.libs.json.{JsObject, JsResult, JsValue, Json, OWrites, Reads}
import play.twirl.api.Html

final case class MainContent(
  content: Html
)

object MainContent {
  implicit val mainContentWrites: OWrites[MainContent] = new OWrites[MainContent] {
    override def writes(mainContent: MainContent): JsObject = Json.obj(
      "content" -> mainContent.content.toString()
    )
  }

  implicit val mainContentReads: Reads[MainContent] = new Reads[MainContent] {
    override def reads(json: JsValue): JsResult[MainContent] =
      (json \ "content").validate[String].map(contentString => MainContent(Html(contentString)))
  }
}
