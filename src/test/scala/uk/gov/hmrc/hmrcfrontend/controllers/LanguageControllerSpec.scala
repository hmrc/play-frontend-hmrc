/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.controllers

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.http.{HeaderNames, Status}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest
import play.api.test.Helpers._

class LanguageControllerSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite {
  implicit lazy val fakeRequest: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/foo")

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder().configure(Map("play.i18n.langs" -> List("en", "cy"))).build()

  "LanguageSwitchController" must {
    "return a 303" in {
      val controller = app.injector.instanceOf[LanguageController]
      val result     = controller.switchToLanguage("en")(fakeRequest)

      status(result) mustBe Status.SEE_OTHER
    }

    "set the PLAY_LANG cookie correctly for Welsh" in {
      val controller = app.injector.instanceOf[LanguageController]
      val result     = controller.switchToLanguage("cy")(fakeRequest)

      cookies(result).get("PLAY_LANG").isDefined mustBe true
      cookies(result).get("PLAY_LANG").get.value mustBe "cy"
    }

    "set the PLAY_LANG cookie correctly for English" in {
      val controller = app.injector.instanceOf[LanguageController]
      val result     = controller.switchToLanguage("en")(fakeRequest)

      cookies(result).get("PLAY_LANG").isDefined mustBe true
      cookies(result).get("PLAY_LANG").get.value mustBe "en"
    }

    "redirect to the REFERER header url as a path only" in {
      implicit val fakeRequestWithReferrer: FakeRequest[AnyContentAsEmpty.type] = fakeRequest.withHeaders(
        HeaderNames.REFERER -> "http://localhost:12345/my-service-page"
      )
      val controller                                                            = app.injector.instanceOf[LanguageController]
      val result                                                                = controller.switchToLanguage("en")(fakeRequestWithReferrer)
      redirectLocation(result) mustBe Some("/my-service-page")
    }

    "redirect to the default url if no REFERER header set" in {
      val controller = app.injector.instanceOf[LanguageController]

      val result = controller.switchToLanguage("en")(fakeRequest)
      redirectLocation(result) mustBe Some("https://www.gov.uk/government/organisations/hm-revenue-customs")
    }

    "redirect to a different default url if no REFERER header set" in {
      val app = new GuiceApplicationBuilder()
        .configure(Map("language.fallback.url" -> "https://www.example.com/fallback"))
        .build()

      val controller = app.injector.instanceOf[LanguageController]

      val result = controller.switchToLanguage("en")(fakeRequest)
      redirectLocation(result) mustBe Some("https://www.example.com/fallback")
    }
  }
}
