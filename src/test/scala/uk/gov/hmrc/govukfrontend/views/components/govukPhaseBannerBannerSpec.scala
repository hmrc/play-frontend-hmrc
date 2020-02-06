/*
 * Copyright 2020 HM Revenue & Customs
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

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.TemplateUnitSpec
import uk.gov.hmrc.govukfrontend.views.html.components._
import scala.collection.JavaConverters._
import scala.util.Try

class govukPhaseBannerBannerSpec extends TemplateUnitSpec[PhaseBanner]("govukPhaseBanner") {

  "phaseBanner" should {
    "allow additional classes to be added to the component" in {
      val classesString = "a-class another-class"
      val classes       = classesString.split("""\s+""")
      val div           = GovukPhaseBanner(PhaseBanner(classes = classesString)).select("div").first()

      div.classNames().asScala should contain allElementsOf classes
    }

    "render banner text" in {
      val text = GovukPhaseBanner(PhaseBanner(
        content = HtmlContent("This is a new service – your feedback will help us to improve it.")))
        .select(".govuk-phase-banner__text")
        .first()
        .text()

      text shouldBe "This is a new service – your feedback will help us to improve it."
    }
  }

  /**
    * Calls the Twirl template with the given parameters and returns the resulting markup
    *
    * @param templateParams
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  override def render(templateParams: PhaseBanner): Try[HtmlFormat.Appendable] =
    Try(GovukPhaseBanner(templateParams))
}
