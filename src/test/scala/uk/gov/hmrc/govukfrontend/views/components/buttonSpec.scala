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

class buttonSpec
    extends RenderHtmlSpec(
      Seq(
        "button-default",
        "button-disabled",
        "button-link",
        "button-link-disabled",
        "button-start-link",
        "button-input",
        "button-input-disabled",
        "button-prevent-double-click",
        "button-with-active-state",
        "button-with-focus-state",
        "button-Secondary",
        "button-Secondary-link",
        "button-Warning",
        "button-Warning-link"
      )
    ) {

  "button element" should {
    "render the default example" in {
      val component = Button.apply()(Text("Save and continue")).select(".govuk-button")

      component.first.tagName shouldBe "button"
      component.text          should include("Save and continue")
    }
  }

  override implicit val reads: Reads[Html] = (
    (__ \ "element").readNullable[String] and
      (__ \ "name").readNullable[String] and
      (__ \ "input").readNullable[String] and
      (__ \ "value").readNullable[String] and
      (__ \ "disabled").readWithDefault[Boolean](false) and
      (__ \ "href").readNullable[String] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      (__ \ "preventDoubleClick").readWithDefault[Boolean](false) and
      readsContent
  )(Button.apply(_, _, _, _, _, _, _, _, _)(_))
}
