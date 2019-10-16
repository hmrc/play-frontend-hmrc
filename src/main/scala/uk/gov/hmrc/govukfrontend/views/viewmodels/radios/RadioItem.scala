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
package radios

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.html.components._
import CommonJsonFormats._

final case class RadioItem(
  content: Content                = Empty,
  id: Option[String]              = None,
  value: Option[String]           = None,
  label: Option[LabelParams]      = None,
  hint: Option[HintParams]        = None,
  divider: Option[String]         = None,
  checked: Boolean                = false,
  conditionalHtml: Option[Html]   = None,
  disabled: Boolean               = false,
  attributes: Map[String, String] = Map.empty
)

object RadioItem {

  implicit val reads: Reads[RadioItem] = (
    Content.reads and
      (__ \ "id").readNullable[String] and
      (__ \ "value").readNullable[String] and
      (__ \ "label").readNullable[LabelParams] and
      (__ \ "hint").readNullable[HintParams] and
      (__ \ "divider").readNullable[String] and
      (__ \ "checked").readWithDefault[Boolean](false) and
      readsConditionalHtml and
      (__ \ "disabled").readWithDefault[Boolean](false) and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(RadioItem.apply _)

  implicit val writes: OWrites[RadioItem] = (
    Content.writes and
      (__ \ "id").writeNullable[String] and
      (__ \ "value").writeNullable[String] and
      (__ \ "label").writeNullable[LabelParams] and
      (__ \ "hint").writeNullable[HintParams] and
      (__ \ "divider").writeNullable[String] and
      (__ \ "checked").write[Boolean] and
      writesConditionalHtml and
      (__ \ "disabled").write[Boolean] and
      (__ \ "attributes").write[Map[String, String]]
  )(unlift(RadioItem.unapply))

}
