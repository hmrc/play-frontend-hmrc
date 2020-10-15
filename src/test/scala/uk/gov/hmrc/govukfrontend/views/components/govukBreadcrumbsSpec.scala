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
import uk.gov.hmrc.govukfrontend.views.viewmodels.breadcrumbs.Breadcrumbs

import scala.util.Try

class govukBreadcrumbsSpec extends TemplateUnitSpec[Breadcrumbs]("govukBreadcrumbs") {

  /**
    * Calls the Twirl template with the given parameters and returns the resulting markup
    *
    * @param templateParams
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  override def render(templateParams: Breadcrumbs): Try[HtmlFormat.Appendable] =
    Try(GovukBreadcrumbs(templateParams))

  "breadcrumb" should {

    "render the attributes in order" in {
      val params = Breadcrumbs(attributes = Map(
        "id" -> "my-navigation",
        "role" -> "navigation"
      ))
      val component = GovukBreadcrumbs(params)

      component.body should include("<div class=\"govuk-breadcrumbs \" id=\"my-navigation\" role=\"navigation\">")
    }

    "render the attributes in order when input is reversed" in {
      val params = Breadcrumbs(attributes = Map(
        "role" -> "navigation",
        "id" -> "my-navigation"
      ))
      val component = GovukBreadcrumbs(params)

      component.body should include("<div class=\"govuk-breadcrumbs \" role=\"navigation\" id=\"my-navigation\">")
    }
  }
}
