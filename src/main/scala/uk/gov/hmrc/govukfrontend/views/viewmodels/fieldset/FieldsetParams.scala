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

package uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.twirl.api.{Html, HtmlFormat}

final case class FieldsetParams(
  describedBy: Option[String]     = None,
  legend: Option[Legend]          = None,
  classes: String                 = "",
  role: Option[String]            = None,
  attributes: Map[String, String] = Map.empty,
  html: Html                      = HtmlFormat.empty
)

object FieldsetParams {

  implicit val reads: Reads[FieldsetParams] = (
    (__ \ "describedBy").readNullable[String] and
      (__ \ "legend").readNullable[Legend] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "role").readNullable[String] and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      (__ \ "html").readWithDefault[String]("").map(Html(_))
  )(FieldsetParams.apply _)

  implicit val writes: OWrites[FieldsetParams] =
    (
      (__ \ "describedBy").writeNullable[String] and
        (__ \ "legend").writeNullable[Legend] and
        (__ \ "classes").write[String] and
        (__ \ "role").writeNullable[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "html").write[String].contramap((html: Html) => html.body)
    )(unlift(FieldsetParams.unapply))
}
