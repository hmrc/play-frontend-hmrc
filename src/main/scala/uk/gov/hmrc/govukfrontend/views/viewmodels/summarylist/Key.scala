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

package uk.gov.hmrc.govukfrontend.views.viewmodels
package summarylist

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

final case class Key(content: Content = Empty, classes: String = "")

object Key extends JsonDefaultValueFormatter[Key] {

  override def defaultObject: Key = Key()

  override def defaultReads: Reads[Key] =
    (
      Content.reads and
        (__ \ "classes").read[String]
    )(Key.apply _)

  override implicit def jsonWrites: OWrites[Key] =
    (
      Content.writes and
        (__ \ "classes").write[String]
    )(unlift(Key.unapply))

}
