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
import uk.gov.hmrc.hmrcfrontend.views.html.components._

import scala.util.Try

class HmrcHeaderSpec extends TemplateUnitSpec[Header, HmrcHeader]("hmrcHeader") {

  def buildAnotherApp(properties: Map[String, String] = Map.empty): Application =
    new GuiceApplicationBuilder()
      .configure(properties)
      .build()

  "header" should {
    """not throw an exception if Some("") is passed as serviceName""" in {
      val params       =
        Header(serviceName = Some(""), containerClasses = "govuk-width-container", signOutHref = Some("/sign-out"))
      val componentTry = Try(component(params))

      componentTry.isSuccess shouldBe true
    }

    """display Tudor crown logo set by config""" in {
      val anotherApp = buildAnotherApp(
        Map(
          "play-frontend-hmrc.useTudorCrown" -> "true"
        )
      )
      val hmrcHeader = anotherApp.injector.instanceOf[HmrcHeader]

      val componentTry = Try(hmrcHeader(Header()))

      componentTry.isSuccess shouldBe true
      componentTry.get.body    should include("M33.1,9.8c.2")
    }

    """display St Edwards crown logo when set by config""" in {
      val anotherApp = buildAnotherApp(
        Map(
          "play-frontend-hmrc.useTudorCrown" -> "false"
        )
      )
      val hmrcHeader = anotherApp.injector.instanceOf[HmrcHeader]

      val componentTry = Try(hmrcHeader(Header()))

      componentTry.isSuccess shouldBe true
      componentTry.get.body    should include("M13.4,22.3c2")
    }

    """display Tudor crown when no config is found""" in {
      val anotherApp = buildAnotherApp()
      val hmrcHeader = anotherApp.injector.instanceOf[HmrcHeader]

      val componentTry = Try(hmrcHeader(Header()))

      componentTry.isSuccess shouldBe true
      componentTry.get.body    should include("M33.1,9.8c.2")
    }
  }
}
