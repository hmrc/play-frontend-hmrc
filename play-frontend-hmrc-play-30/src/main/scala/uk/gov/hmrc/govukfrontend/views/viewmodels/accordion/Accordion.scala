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

package uk.gov.hmrc.govukfrontend.views.viewmodels.accordion

import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._
import play.api.libs.functional.syntax._
import uk.gov.hmrc.govukfrontend.views.viewmodels.WritesUtils

case class Accordion(
  id: String = "",
  headingLevel: Int = 2,
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  items: Seq[Section] = Nil,
  hideSection: Option[String] = None,
  hideAllSections: Option[String] = None,
  hideSectionAriaLabel: Option[String] = None,
  showSection: Option[String] = None,
  showAllSections: Option[String] = None,
  showSectionAriaLabel: Option[String] = None,
  rememberExpanded: Option[Boolean] = None
)

object Accordion {

  def defaultObject: Accordion = Accordion()

  implicit def jsonReads: Reads[Accordion] = (
    (__ \ "id").readWithDefault[String](defaultObject.id) and
      (__ \ "headingLevel").readWithDefault[Int](defaultObject.headingLevel) and
      (__ \ "classes").readWithDefault[String](defaultObject.classes) and
      (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads) and
      (__ \ "items").readWithDefault[Seq[Section]](defaultObject.items)(forgivingSeqReads[Section]) and
      (__ \ "hideSectionText").readNullable[String] and
      (__ \ "hideAllSectionsText").readNullable[String] and
      (__ \ "hideSectionAriaLabelText").readNullable[String] and
      (__ \ "showSectionText").readNullable[String] and
      (__ \ "showAllSectionsText").readNullable[String] and
      (__ \ "showSectionAriaLabelText").readNullable[String] and
      (__ \ "rememberExpanded").readNullable[Boolean]
  )(Accordion.apply _)

  implicit def jsonWrites: OWrites[Accordion] = (
    (__ \ "id").write[String] and
      (__ \ "headingLevel").write[Int] and
      (__ \ "classes").write[String] and
      (__ \ "attributes").write[Map[String, String]] and
      (__ \ "items").write[Seq[Section]] and
      (__ \ "hideSectionText").writeNullable[String] and
      (__ \ "hideAllSectionsText").writeNullable[String] and
      (__ \ "hideSectionAriaLabelText").writeNullable[String] and
      (__ \ "showSectionText").writeNullable[String] and
      (__ \ "showAllSectionsText").writeNullable[String] and
      (__ \ "showSectionAriaLabelText").writeNullable[String] and
      (__ \ "rememberExpanded").writeNullable[Boolean]
    )(o => WritesUtils.unapplyCompat(unapply)(o))
}
