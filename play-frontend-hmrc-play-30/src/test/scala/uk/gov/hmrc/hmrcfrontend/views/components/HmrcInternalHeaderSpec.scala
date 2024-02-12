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

class HmrcInternalHeaderSpec extends TemplateUnitSpec[InternalHeader, HmrcInternalHeader]("hmrcInternalHeader") {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure(
        Map(
          "play-frontend-hmrc.useTudorCrown" -> "false"
        )
      )
      .build()

  def buildAnotherApp(properties: Map[String, String] = Map.empty): Application =
    new GuiceApplicationBuilder()
      .configure(properties)
      .build()

  override def render(templateParams: InternalHeader): Try[HtmlFormat.Appendable] = {
    // The following line is needed to ensure known state of the statically initialised reverse router
    // used to calculate asset paths.
    hmrcfrontend.RoutesPrefix.setPrefix("")

    super.render(templateParams)
  }

  "internal header" should {

    """display Tudor crown logo set by config""" in {
      val anotherApp         = buildAnotherApp(
        Map(
          "play-frontend-hmrc.useTudorCrown" -> "true"
        )
      )
      val hmrcInternalHeader = anotherApp.injector.instanceOf[HmrcInternalHeader]

      val componentTry = Try(hmrcInternalHeader(InternalHeader()))

      componentTry          should be a 'success
      componentTry.get.body should include("hmrc_tudor_crest_18px_x2.png")
    }

    """display Tudor crown when no config is found""" in {
      val anotherApp         = buildAnotherApp()
      val hmrcInternalHeader = anotherApp.injector.instanceOf[HmrcInternalHeader]

      val componentTry = Try(hmrcInternalHeader(InternalHeader()))

      componentTry          should be a 'success
      componentTry.get.body should include("hmrc_tudor_crest_18px_x2.png")
    }
  }
}
