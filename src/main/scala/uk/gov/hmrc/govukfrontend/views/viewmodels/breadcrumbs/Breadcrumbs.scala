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

package uk.gov.hmrc.govukfrontend.views.viewmodels.breadcrumbs

import play.api.libs.json.{Json, OWrites, Reads}
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._

case class Breadcrumbs(
  items: Seq[BreadcrumbsItem] = Nil,
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  collapseOnMobile: Boolean = false
)

object Breadcrumbs {

  def defaultObject: Breadcrumbs = Breadcrumbs()

  implicit def jsonReads: Reads[Breadcrumbs] =
    ((__ \ "items").readWithDefault[Seq[BreadcrumbsItem]](defaultObject.items) and
      (__ \ "classes").readWithDefault[String](defaultObject.classes) and
      (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads) and
      (__ \ "collapseOnMobile").readWithDefault[Boolean](defaultObject.collapseOnMobile))(Breadcrumbs.apply _)

  implicit def jsonWrites: OWrites[Breadcrumbs] = Json.writes[Breadcrumbs]
}
