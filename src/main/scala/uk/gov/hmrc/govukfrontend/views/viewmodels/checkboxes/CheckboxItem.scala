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
package checkboxes

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.twirl.api.Html
import CommonJsonFormats._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label

final case class CheckboxItem(
  content: Content,
  id: Option[String]   = None,
  name: Option[String] = None,
  value: String,
  label: Option[Label]      = None,
  hint: Option[Hint]        = None,
  checked: Boolean                = false,
  conditionalHtml: Option[Html]   = None,
  disabled: Boolean               = false,
  attributes: Map[String, String] = Map.empty
)

object CheckboxItem {
  implicit val readsCheckboxItem: Reads[CheckboxItem] = (
    Content.reads and
      (__ \ "id").readNullable[String] and
      (__ \ "name").readNullable[String] and
      (__ \ "value").read[String] and
      (__ \ "label").readNullable[Label] and
      (__ \ "hint").readNullable[Hint] and
      (__ \ "checked").readWithDefault[Boolean](false) and
      readsConditionalHtml and
      (__ \ "disabled").readWithDefault[Boolean](false) and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
    )(CheckboxItem.apply _)

  implicit val writesCheckboxItem: OWrites[CheckboxItem] = (
    Content.writes and
      (__ \ "id").writeNullable[String] and
      (__ \ "name").writeNullable[String] and
      (__ \ "value").write[String] and
      (__ \ "label").writeNullable[Label] and
      (__ \ "hint").writeNullable[Hint] and
      (__ \ "checked").write[Boolean] and
      (__ \ "conditional" \ "html").writeNullable[String].contramap((html: Option[Html]) => html.map(_.body)) and
      (__ \ "disabled").write[Boolean] and
      (__ \ "attributes").write[Map[String, String]]
    )(unlift(CheckboxItem.unapply))

}
