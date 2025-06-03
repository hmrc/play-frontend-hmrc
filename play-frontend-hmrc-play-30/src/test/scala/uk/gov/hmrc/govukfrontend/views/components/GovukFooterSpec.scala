/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views
package components

import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import uk.gov.hmrc.govukfrontend.views.html.components._

class GovukFooterSpec extends TemplateUnitSpec[Footer, GovukFooter]("govukFooter") {

  def buildAnotherApp(properties: Map[String, String] = Map.empty): Application =
    new GuiceApplicationBuilder()
      .configure(properties)
      .build()

  "output of rebrand enabled" should {
    "match output of rebrand disabled" when {
      "rebrand is enabled by argument" in {
        val appWithRebrand    = buildAnotherApp(Map("play-frontend-hmrc.useRebrand" -> "true"))
        val appWithoutRebrand = buildAnotherApp(Map("play-frontend-hmrc.useRebrand" -> "false"))

        val viewWithRebrand    = appWithRebrand.injector.instanceOf[GovukFooter]
        val viewWithoutRebrand = appWithoutRebrand.injector.instanceOf[GovukFooter]

        viewWithRebrand.apply() shouldBe viewWithoutRebrand.apply(Footer(rebrand = Some(true)))
      }
    }
  }
}
