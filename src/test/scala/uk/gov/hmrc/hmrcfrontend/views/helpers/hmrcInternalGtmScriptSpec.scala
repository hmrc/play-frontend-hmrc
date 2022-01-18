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

package uk.gov.hmrc.hmrcfrontend.views.helpers

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest
import play.api.test.Helpers.contentAsString
import play.twirl.api.Html
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.helpers.views.JsoupHelpers
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcInternalGtmScript

class hmrcInternalGtmScriptSpec
    extends AnyWordSpecLike
    with Matchers
    with GuiceOneAppPerSuite
    with JsoupHelpers
    with MessagesSupport {

  implicit val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/foo")

  "HmrcInternalGtmScript" should {
    "render the internal GTM script tag" in {
      val hmrcInternalGtmScript = app.injector.instanceOf[HmrcInternalGtmScript]
      val content               = hmrcInternalGtmScript()

      val scripts = content.select("script#hmrc-internal-gtm-script-tag")
      scripts should have size 1
    }

    "include a nonce in each script tag if supplied" in {
      val hmrcInternalGtmScript = app.injector.instanceOf[HmrcInternalGtmScript]
      val content               = hmrcInternalGtmScript(cspNonce = Some("a-nonce"))

      val scripts = content.select("script#hmrc-internal-gtm-script-tag")
      scripts                     should have size 1
      scripts.first.attr("nonce") should be("a-nonce")
    }
  }
}
