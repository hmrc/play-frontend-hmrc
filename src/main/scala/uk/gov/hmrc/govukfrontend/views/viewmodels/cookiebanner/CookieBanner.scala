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

package uk.gov.hmrc.govukfrontend.views.viewmodels.cookiebanner

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._

case class CookieBanner(
  id: String = "",
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  hidden: Boolean = false,
  ariaLabel: Option[String] = None,
  messages: Seq[Message] = Seq.empty
)

object CookieBanner {

  def defaultObject: CookieBanner = CookieBanner()

  implicit def jsonReads: Reads[CookieBanner] = (
    (__ \ "id").readWithDefault[String](defaultObject.id) and
      (__ \ "classes").readWithDefault[String](defaultObject.classes) and
      (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads) and
      (__ \ "hidden").readWithDefault[Boolean](defaultObject.hidden) and
      (__ \ "ariaLabel").readNullable[String] and
      (__ \ "messages").read[Seq[Message]]
  )(CookieBanner.apply _)

  implicit def jsonWrites: OWrites[CookieBanner] = (
    (__ \ "id").write[String] and
      (__ \ "classes").write[String] and
      (__ \ "attributes").write[Map[String, String]] and
      (__ \ "hidden").write[Boolean] and
      (__ \ "ariaLabel").writeNullable[String] and
      (__ \ "messages").write[Seq[Message]]
  )(unlift(CookieBanner.unapply))
}
