/*
 * Copyright 2021 HM Revenue & Customs
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

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.mvc.{AnyContentAsEmpty, Call}
import play.api.test.FakeRequest
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.{JsoupHelpers, MessagesHelpers}

class formWithCSRFSpec
    extends AnyWordSpec
    with Matchers
    with JsoupHelpers
    with MessagesHelpers
    with CSRFSpec
    with GuiceOneAppPerSuite {

  val formWithCSRF = app.injector.instanceOf[FormWithCSRF]

  "formWithCSRF" should {
    val postAction = Call(method = "POST", url = "/the-post-url")

    "render with the correct action attribute" in {
      val form =
        formWithCSRF
          .apply(action = postAction)(HtmlFormat.empty)
          .select("form")

      form.attr("action") shouldBe "/the-post-url"
    }

    "render with the correct action including a fragment" in {
      val form =
        formWithCSRF
          .apply(action = postAction.withFragment("tab"))(HtmlFormat.empty)
          .select("form")

      form.attr("action") shouldBe "/the-post-url#tab"
    }

    "render with the correct method" in {
      val getCall = Call(method = "GET", url = "/the-post-url")

      val form =
        formWithCSRF
          .apply(action = getCall)(HtmlFormat.empty)
          .select("form")

      form.attr("method") shouldBe "GET"
    }

    "render with the novalidate attribute" in {
      val getCall = Call(method = "GET", url = "/the-post-url")

      val form =
        formWithCSRF
          .apply(action = getCall)(HtmlFormat.empty)
          .select("form")

      form.hasAttr("novalidate") shouldBe true
    }

    "not render duplicate novalidate attributes" in {
      val form =
        formWithCSRF.apply(action = postAction, 'novalidate -> "novalidate")(HtmlFormat.empty)

      form.toString should include("<form method=\"POST\" novalidate action=\"/the-post-url\">")
    }
    "render the passed attributes" in {
      val form =
        formWithCSRF
          .apply(action = postAction, 'attribute1 -> "value1")(HtmlFormat.empty)
          .select("form")

      form.attr("attribute1") shouldBe "value1"
    }

    "render multiple attributes" in {
      val form =
        formWithCSRF
          .apply(action = postAction, 'attribute1 -> "value1", 'attribute2 -> "value2")(HtmlFormat.empty)
          .select("form")

      form.attr("attribute1") shouldBe "value1"
      form.attr("attribute2") shouldBe "value2"
    }

    "render the contents of the form" in {
      val content = Html("<p>Content</p>")
      val form    =
        formWithCSRF
          .apply(action = postAction)(content)
          .select("p")

      form.outerHtml shouldBe "<p>Content</p>"
    }

    "not render the CSRF token if the request does not contain the token" in {
      implicit val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest()
      val form                                                  =
        formWithCSRF.apply(action = postAction)(HtmlFormat.empty)

      val input = form.select("input")
      input.size shouldBe 0
    }

    "render the CSRF token" in {
      val form =
        formWithCSRF.apply(action = postAction)(HtmlFormat.empty)

      val input = form.select("input")
      input.size shouldBe 1

      input.attr("type")       shouldBe "hidden"
      input.attr("name")       shouldBe "csrfToken"
      input.attr("value").length should be > 0
    }
  }
}
