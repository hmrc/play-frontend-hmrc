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

package uk.gov.hmrc.govukfrontend.views.helpers

import org.scalatest.{Matchers, WordSpec}
import play.api.{Configuration, Environment}
import play.api.http.HttpConfiguration
import play.api.i18n.{DefaultLangsProvider, DefaultMessagesApiProvider, Messages}
import play.api.mvc.Call
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.html.helpers._
import play.api.test.CSRFTokenHelper._
import uk.gov.hmrc.govukfrontend.views.JsoupHelpers

class formWithCSRFSpec extends WordSpec with Matchers with JsoupHelpers {
  "formWithCSRF" should {
    "add a CSRF token as a hidden field in the form" in {
      val conf                        = Configuration.reference
      val langs                       = new DefaultLangsProvider(conf).get
      val httpConfiguration           = HttpConfiguration.fromConfiguration(conf, Environment.simple())
      val messagesApi                 = new DefaultMessagesApiProvider(Environment.simple(), conf, langs, httpConfiguration).get
      implicit val messages: Messages = messagesApi.preferred(Seq.empty)

      implicit val request = FakeRequest().withCSRFToken

      val rendered = FormWithCSRF.apply(Call("GET", "/someUrl"))(HtmlFormat.empty)

      rendered.select("input").attr("type") shouldBe "hidden"
      rendered.select("input").attr("name") shouldBe "csrfToken"
    }
  }
}
