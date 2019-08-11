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

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.components.errorSummarySpec.reads
import uk.gov.hmrc.govukfrontend.views.html.components._

class errorSummarySpec extends RenderHtmlSpec(Seq("error-summary-default"))

object errorSummarySpec {
  case class Params(
    title: Contents                 = Empty,
    description: Contents           = Empty,
    errorList: Seq[ErrorLink]       = Nil,
    classes: String                 = "",
    attributes: Map[String, String] = Map.empty
  )

  import RenderHtmlSpec._

  implicit val reads: Reads[HtmlString] = (
    (__ \ "titleHtml")
      .read[String]
      .map(HtmlContent(_))
      .widen[Contents]
      .orElse((__ \ "titleText").read[String].map(Text).widen[Contents]) and
      (__ \ "descriptionHtml")
        .read[String]
        .map(HtmlContent(_))
        .widen[Contents]
        .orElse((__ \ "descriptionText").read[String].map(Text).widen[Contents]) and
      (__ \ "errorList").readWithDefault[Seq[ErrorLink]](Nil) and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(
    (title, description, errorList, classes, attributes) =>
      tagger[HtmlStringTag][String](
        ErrorSummary
          .apply(title, description, errorList, classes, attributes)
          .body))
}
