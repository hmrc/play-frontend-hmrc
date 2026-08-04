/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels.panel

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.Aliases.Content
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats.attributesReads
import uk.gov.hmrc.govukfrontend.views.viewmodels.WritesUtils

case class PanelItem(
  text: Content,
  panelItemType: Option[String] = None,
  href: Option[String] = None,
  classes: Option[String] = None,
  attributes: Map[String, String] = Map.empty
)

object PanelItem {

  implicit def jsonReads: Reads[PanelItem] =
    (
      Content.readsHtmlOrText(__ \ "titleHtml", __ \ "titleText") and
        (__ \ "panelItemType").readNullable[String] and
        (__ \ "href").readNullable[String] and
        (__ \ "classes").readNullable[String] and
        (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)(attributesReads)
    )(PanelItem.apply _)

  implicit def jsonWrites: OWrites[PanelItem] =
    (
      Content.writesContent("titleHtml", "titleText") and
        (__ \ "panelItemType").writeNullable[String] and
        (__ \ "href").writeNullable[String] and
        (__ \ "classes").writeNullable[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(o => WritesUtils.unapplyCompat(unapply)(o))
}
