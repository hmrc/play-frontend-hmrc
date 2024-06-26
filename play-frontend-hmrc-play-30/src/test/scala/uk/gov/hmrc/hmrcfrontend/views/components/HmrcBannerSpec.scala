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

package uk.gov.hmrc.hmrcfrontend.views
package components

import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.hmrcfrontend.views.html.components._

import scala.util.Try

class HmrcBannerSpec extends TemplateUnitSpec[Banner, HmrcBanner]("hmrcBanner") {

  def buildAnotherApp(properties: Map[String, String] = Map.empty): Application =
    new GuiceApplicationBuilder()
      .configure(properties)
      .build()

  override def render(templateParams: Banner): Try[HtmlFormat.Appendable] = {
    // The following line is needed to ensure known state of the statically initialised reverse router
    // used to calculate asset paths.
    hmrcfrontend.RoutesPrefix.setPrefix("")

    super.render(templateParams)
  }

  "banner" should {

    """display Tudor crown logo set by config""" in {
      val anotherApp = buildAnotherApp(
        Map(
          "play-frontend-hmrc.useTudorCrown" -> "true"
        )
      )
      val hmrcBanner = anotherApp.injector.instanceOf[HmrcBanner]

      val componentTry = Try(hmrcBanner(Banner()))

      componentTry          should be a Symbol("success")
      componentTry.get.body should include("m28.5,16.6c.82-.34")
    }

    """display St Edwards crown logo when set by config""" in {
      val anotherApp = buildAnotherApp(
        Map(
          "play-frontend-hmrc.useTudorCrown" -> "false"
        )
      )
      val hmrcBanner = anotherApp.injector.instanceOf[HmrcBanner]

      val componentTry = Try(hmrcBanner(Banner()))

      componentTry          should be a Symbol("success")
      componentTry.get.body should include("M104.32,73.72,101")
    }

    """display HMRC Crest with Tudor Crown  when no config is found""" in {
      val anotherApp = buildAnotherApp()
      val hmrcBanner = anotherApp.injector.instanceOf[HmrcBanner]

      val componentTry = Try(hmrcBanner(Banner()))

      componentTry          should be a Symbol("success")
      componentTry.get.body should include("m28.5,16.6c.82-.34")
    }
  }
}
