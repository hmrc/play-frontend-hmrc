/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.config

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

class TudorCrownConfigSpec extends AnyWordSpec with Matchers {

  def buildApp(properties: Map[String, String]): Application =
    new GuiceApplicationBuilder()
      .configure(properties)
      .build()

  "useTudorCrown" should {
    "default to true if config not found" in {
      implicit val application: Application = buildApp(Map())
      val config                            = application.injector.instanceOf[TudorCrownConfig]
      config.useTudorCrown should equal(true)
    }
  }
}
