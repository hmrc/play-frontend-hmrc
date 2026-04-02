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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.caseworkerbanner

import play.api.libs.json.{OWrites, Reads, __}
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats.attributesReads
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}
import play.api.libs.functional.syntax._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.WritesUtils

case class CaseworkerBanner(
  content: Content = Empty,
  titleId: Option[String] = None,
  classes: String = "",
  attributes: Map[String, String] = Map.empty
)

object CaseworkerBanner {

  def defaultObject: CaseworkerBanner = CaseworkerBanner()

  implicit def jsonReads: Reads[CaseworkerBanner] =
    (
      Content.reads and
        (__ \ "titleId").readNullable[String] and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads)
    )(CaseworkerBanner.apply _)

  implicit def jsonWrites: OWrites[CaseworkerBanner] =
    (
      Content.writes and
        (__ \ "titleId").writeNullable[String] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]]
    )(o => WritesUtils.unapplyCompat(unapply)(o))
}
