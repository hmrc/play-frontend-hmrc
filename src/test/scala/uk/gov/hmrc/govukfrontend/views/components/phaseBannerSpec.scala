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
import scala.collection.JavaConverters._

class phaseBannerSpec
    extends RenderHtmlSpec(
      Seq(
        "phase-banner-default"
      )
    ) {

  "phaseBanner" should {
    "allow additional classes to be added to the component" in {
      val classesString = "a-class another-class"
      val classes       = classesString.split("""\s+""")
      val div           = PhaseBanner.apply(classes = classesString)().select("div").first()

      div.classNames().asScala should contain allElementsOf classes
    }

    "render banner text" in {
      val text = PhaseBanner
        .apply()(HtmlContent("This is a new service – your feedback will help us to improve it."))
        .select(".govuk-phase-banner__text")
        .first()
        .text()

      text shouldBe "This is a new service – your feedback will help us to improve it."
    }
  }

  override implicit val reads: Reads[Html] = (
    (__ \ "tag").readNullable[TagParams] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      readsContents
  )(PhaseBanner.apply(_, _, _)(_))
}
