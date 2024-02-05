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

package uk.gov.hmrc.hmrcfrontend.views.helpers

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.typedmap.TypedMap
import play.api.mvc.request.RequestAttrKey
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, _}
import play.twirl.api.Html
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.helpers.views.JsoupHelpers
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcInternalHead

class hmrcInternalHeadSpec
    extends AnyWordSpecLike
    with Matchers
    with GuiceOneAppPerSuite
    with JsoupHelpers
    with MessagesSupport {

  implicit val request: FakeRequest[_] = FakeRequest("GET", "/foo")
  val requestWithNonce: FakeRequest[_] = request.withAttrs(TypedMap(RequestAttrKey.CSPNonce -> "a-nonce"))

  "HmrcInternalHead" should {
    "include the internal GTM script tag" in {
      val hmrcInternalHead = app.injector.instanceOf[HmrcInternalHead]
      val content          = hmrcInternalHead()

      val scripts = content.select("script#hmrc-internal-gtm-script-tag")
      scripts should have size 1
    }

    "include a nonce in each script tag if supplied" in {
      val hmrcInternalHead = app.injector.instanceOf[HmrcInternalHead]
      val content          = hmrcInternalHead()(requestWithNonce, implicitly)

      val scripts = content.select("script#hmrc-internal-gtm-script-tag")
      scripts                     should have size 1
      scripts.first.attr("nonce") should be("a-nonce")
    }

    "include a nonce in the link tag if supplied" in {
      val hmrcInternalHead = app.injector.instanceOf[HmrcInternalHead]
      hmrcfrontend.RoutesPrefix.setPrefix("/some-service/hmrc-frontend")
      val content          = contentAsString(hmrcInternalHead()(requestWithNonce, implicitly))

      content should include regex
        """<link href="/some-service/hmrc-frontend/assets/hmrc-frontend-\d+.\d+.\d+.min.css" nonce="a-nonce" media="all" rel="stylesheet" type="text/css" />""".r
    }

    "include the hmrc-frontend minified css bundle" in {
      val hmrcInternalHead = app.injector.instanceOf[HmrcInternalHead]
      hmrcfrontend.RoutesPrefix.setPrefix("/some-service/hmrc-frontend")
      val content          = contentAsString(hmrcInternalHead())

      content should include regex
        """<link href="/some-service/hmrc-frontend/assets/hmrc-frontend-\d+.\d+.\d+.min.css"  +media="all" rel="stylesheet" type="text/css" />""".r
    }

    "include the supplied headBlock" in {
      val hmrcInternalHead = app.injector.instanceOf[HmrcInternalHead]
      val content          = hmrcInternalHead(headBlock = Some(Html("""<meta name="author" content="John Doe">""")))

      val metaTags = content.select("meta[name=author]")
      metaTags should have size 1
    }

    "render the correct url even if AssetsConfig has already been instantiated" in {
      val hmrcInternalHead = app.injector.instanceOf[HmrcInternalHead]
      hmrcfrontend.RoutesPrefix.setPrefix("/foo-service/hmrc-frontend")

      val links = hmrcInternalHead().select("link")

      links                    should have size 1
      links.first.attr("href") should fullyMatch regex
        """/foo-service/hmrc-frontend/assets/hmrc-frontend-\d+.\d+.\d+.min.css""".r
    }
  }
}
