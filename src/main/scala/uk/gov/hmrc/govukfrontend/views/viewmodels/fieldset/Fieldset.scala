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

package uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.twirl.api.{Html, HtmlFormat}

final case class Fieldset(
  describedBy: Option[String] = None,
  legend: Option[Legend] = None,
  classes: String = "",
  role: Option[String] = None,
  attributes: Map[String, String] = Map.empty,
  html: Html = HtmlFormat.empty
)

object Fieldset {

  def defaultObject: Fieldset = Fieldset()

  implicit def jsonReads: Reads[Fieldset] =
    (
      (__ \ "describedBy").readNullable[String] and
        (__ \ "legend").readNullable[Legend] and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "role").readNullable[String] and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes) and
        (__ \ "html").readWithDefault[String](defaultObject.html.toString).map(Html(_))
    )(Fieldset.apply _)

  implicit def jsonWrites: OWrites[Fieldset] =
    (
      (__ \ "describedBy").writeNullable[String] and
        (__ \ "legend").writeNullable[Legend] and
        (__ \ "classes").write[String] and
        (__ \ "role").writeNullable[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "html").write[String].contramap((html: Html) => html.body)
    )(unlift(Fieldset.unapply))

}
