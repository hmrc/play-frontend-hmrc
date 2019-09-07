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

class radiosSpec
    extends RenderHtmlSpec(
      Seq(
        "radios-default",
        "radios-inline",
        "radios-inline-with-conditional-items",
        "radios-small",
        "radios-small-inline",
        "radios-small-inline-extreme",
        "radios-small-with-a-divider",
        "radios-small-with-conditional-reveal",
        "radios-small-with-disabled",
        "radios-small-with-error",
        "radios-small-with-hint",
        "radios-small-with-long-text",
        "radios-with-a-divider",
        "radios-with-a-medium-legend",
        "radios-with-all-fieldset-attributes",
        "radios-with-conditional-item-checked",
        "radios-with-conditional-items",
        "radios-with-disabled",
        "radios-with-hints-on-items",
        "radios-with-legend-as-page-heading",
        "radios-with-optional-form-group-classes-showing-group-error",
        "radios-with-very-long-option-text",
        "radios-without-fieldset"
      )
    ) {

  override implicit val reads: Reads[Html] = (
    (__ \ "fieldset").readNullable[FieldsetParams] and
      (__ \ "hint").readNullable[HintParams] and
      (__ \ "errorMessage").readNullable[ErrorMessageParams] and
      readsFormGroupClasses and
      (__ \ "idPrefix").readNullable[String] and
      (__ \ "name").read[String] and
      (__ \ "items").read[Seq[RadioItem]] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(Radios.apply _)
}
