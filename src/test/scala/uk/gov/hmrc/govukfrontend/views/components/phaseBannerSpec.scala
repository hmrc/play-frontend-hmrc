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

import org.jsoup.Jsoup
import play.api.libs.functional.syntax._
import play.api.libs.json._
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

      val bannerHtml = PhaseBanner.apply(classes = classesString)()

      val div = Jsoup.parse(bannerHtml.body).select("div").first()

      val classNames = div.classNames().asScala

      classNames should contain allElementsOf classes
    }

    "render banner text" in {
      val bannerHtml =
        PhaseBanner.apply()(HtmlContent("This is a new service – your feedback will help us to improve it."))

      val text = Jsoup.parse(bannerHtml.body).select(".govuk-phase-banner__text").first().text()

      text shouldBe "This is a new service – your feedback will help us to improve it."
    }
  }

  implicit val readsTagParams: Reads[TagParams] = (
    readsContents and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(TagParams.apply _)

  override implicit val reads: Reads[HtmlString] = (
    readsContents and
      (__ \ "tag").readNullable[TagParams] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(
    (contents, tag, classes, attributes) =>
      tagger[HtmlStringTag][String](
        PhaseBanner
          .apply(tag, classes, attributes)(contents)
          .body))
}
