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

package uk.gov.hmrc.govukfrontend.views.viewmodels.footer

import play.api.libs.functional.syntax.unlift
import play.api.libs.json.{OWrites, Reads, __}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content

final case class ContentLicence(content: Content)

object ContentLicence {
  implicit val reads: Reads[ContentLicence]    = Content.reads.map(c => ContentLicence(c))
  implicit val writes: OWrites[ContentLicence] = Content.writes.contramap(_.content)
}
