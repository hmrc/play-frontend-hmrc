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

package uk.gov.hmrc.govukfrontend.views.components

import play.api.libs.json._
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.html.components._

class fieldsetSpec extends TemplateUnitSpec("govukFieldset") {

  case class Params(
    describedBy: Option[String]     = None,
    legend: Option[Legend]          = None,
    classes: String                 = "",
    role: Option[String]            = None,
    attributes: Map[String, String] = Map.empty,
    html: String                    = ""
  )

  override implicit val reads: Reads[Html] =
    Json
      .using[Json.WithDefaultValues]
      .reads[Params]
      .map {
        case Params(describedBy, legend, classes, role, attributes, html) =>
          Fieldset.apply(describedBy, legend, classes, role, attributes)(Html(html))
      }
}
