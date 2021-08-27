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

package uk.gov.hmrc.govukfrontend.views.viewmodels
package checkboxes

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label

final case class CheckboxItem(
  content: Content = Empty,
  id: Option[String] = None,
  name: Option[String] = None,
  value: String = "",
  label: Option[Label] = None,
  hint: Option[Hint] = None,
  checked: Boolean = false,
  conditionalHtml: Option[Html] = None,
  disabled: Boolean = false,
  attributes: Map[String, String] = Map.empty,
  behaviour: Option[CheckboxBehaviour] = None,
  divider: Option[String] = None
)

object CheckboxItem {

  def defaultObject: CheckboxItem = CheckboxItem()

  implicit def jsonReads: Reads[CheckboxItem] =
    (
      Content.reads and
        (__ \ "id").readNullable[String] and
        (__ \ "name").readNullable[String] and
        (__ \ "value").readWithDefault[String](defaultObject.value)(CommonJsonFormats.forgivingStringReads) and
        (__ \ "label").readNullable[Label] and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "checked").readWithDefault[Boolean](defaultObject.checked) and
        readsConditionalHtml and
        (__ \ "disabled").readWithDefault[Boolean](defaultObject.disabled) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(
          CommonJsonFormats.attributesReads
        ) and
        (__ \ "behaviour").readNullable[CheckboxBehaviour] and
        (__ \ "divider").readNullable[String]
    )(CheckboxItem.apply _)

  implicit def jsonWrites: OWrites[CheckboxItem] =
    (
      Content.writes and
        (__ \ "id").writeNullable[String] and
        (__ \ "name").writeNullable[String] and
        (__ \ "value").write[String] and
        (__ \ "label").writeNullable[Label] and
        (__ \ "hint").writeNullable[Hint] and
        (__ \ "checked").write[Boolean] and
        (__ \ "conditional" \ "html").writeNullable[String].contramap((html: Option[Html]) => html.map(_.body)) and
        (__ \ "disabled").write[Boolean] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "behaviour").writeNullable[CheckboxBehaviour] and
        (__ \ "divider").writeNullable[String]
    )(unlift(CheckboxItem.unapply))
}
