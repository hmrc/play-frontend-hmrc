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
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.html.components._

class characterCountSpec
    extends RenderHtmlSpec(
      Seq(
        "character-count-default",
        "character-count-with-custom-rows",
        "character-count-with-default-value",
        "character-count-with-default-value-exceeding-limit",
        "character-count-with-hint",
        "character-count-with-label-as-page-heading",
        "character-count-with-threshold",
        "character-count-with-word-count"
      )
    ) {
  override implicit val reads: Reads[Html] = (
    (__ \ "id").read[String] and
      (__ \ "name").read[String] and
      (__ \ "rows").readWithDefault[Int](5) and
      (__ \ "value").readNullable[String] and
      (__ \ "maxlength").readNullable[Int] and
      (__ \ "maxwords").readNullable[Int] and
      (__ \ "threshold").readNullable[Int] and
      (__ \ "label").read[LabelParams] and
      (__ \ "hint").readNullable[HintParams] and
      (__ \ "errorMessage").readNullable[ErrorMessageParams] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(CharacterCount.apply _)
}
