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

package uk.gov.hmrc.govukfrontend.views.viewmodels
package select

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonImplicits._

final case class SelectItem(
  value: Option[String] = None,
  text: String,
  selected: Boolean               = false,
  disabled: Boolean               = false,
  attributes: Map[String, String] = Map.empty
)

object SelectItem {

  implicit val reads: Reads[SelectItem] = (
    (__ \ "value").readsJsValueToString.map(Option[String]).orElse(Reads.pure(None)) and
      (__ \ "text").read[String] and
      (__ \ "selected").readWithDefault[Boolean](false) and
      (__ \ "disabled").readWithDefault[Boolean](false) and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(SelectItem.apply _)

  implicit val writes: OWrites[SelectItem] = (
    (__ \ "value").writeNullable[String] and
      (__ \ "text").write[String] and
      (__ \ "selected").write[Boolean] and
      (__ \ "disabled").write[Boolean] and
      (__ \ "attributes").write[Map[String, String]]
  )(unlift(SelectItem.unapply))
}
