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

package uk.gov.hmrc.govukfrontend.controllers

import org.scalatest.TestData
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test._

class AssetsSpec extends PlaySpec with Results with GuiceOneAppPerTest {

  override def newAppForTest(testData: TestData): Application =
    new GuiceApplicationBuilder()
      .configure(testData.configMap ++ Map("play.http.router" -> "govuk.Routes"))
      .build()

  "Asset controller " must {
    "serve an asset in the public assets folder" in {
      status(route(app, FakeRequest("GET", "/assets/assets/images/favicon.ico")).get) must be(200)
    }
  }

}
