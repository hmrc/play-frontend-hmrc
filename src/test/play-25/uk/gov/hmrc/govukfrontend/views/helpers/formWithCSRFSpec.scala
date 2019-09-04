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
import org.scalatestplus.play.OneAppPerTest
import play.api.i18n.{DefaultLangs, DefaultMessagesApi}
import play.api.mvc.Call
import play.api.test.FakeRequest
import play.api.{Configuration, Environment}
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.JsoupHelpers
import scala.language.postfixOps
import uk.gov.hmrc.govukfrontend.views.html.helpers._

class formWithCSRFSpec extends WordSpec with Matchers with JsoupHelpers with OneAppPerTest with CSRFSpec {
  "formWithCSRF" should {
    "add a CSRF token as a hidden field in the form" in {
      val messagesApi =
        new DefaultMessagesApi(Environment.simple(), Configuration.reference, new DefaultLangs(Configuration.reference))
      implicit val messages = messagesApi.preferred(Seq.empty)

      implicit val request = addToken(FakeRequest())

      val rendered = FormWithCSRF.apply(Call("GET", "/someUrl"))(HtmlFormat.empty)

      rendered.select("input").attr("type") shouldBe "hidden"
      rendered.select("input").attr("name") shouldBe "csrfToken"
    }
  }
}
