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
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.twirl.api.Html
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.helpers.views.JsoupHelpers
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcHead

class hmrcHeadSpec
    extends AnyWordSpecLike
    with Matchers
    with GuiceOneAppPerSuite
    with JsoupHelpers
    with MessagesSupport {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .configure(
        Map(
          "optimizely.url"                          -> "https://cdn.optimizely.com/",
          "optimizely.projectId"                    -> "1234567",
          "tracking-consent-frontend.gtm.container" -> "d"
        )
      )
      .build()

  implicit val request: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/foo")

  "hmrcHead" should {
    "include the tracking consent script tag" in {
      val hmrcHead = app.injector.instanceOf[HmrcHead]
      val content  = hmrcHead()

      val scripts = content.select("script#tracking-consent-script-tag")
      scripts should have size 1
    }

    "include a nonce in each script tag if supplied" in {
      val hmrcHead = app.injector.instanceOf[HmrcHead]
      val content  = hmrcHead(nonce = Some("a-nonce"))

      val scripts = content.select("script#tracking-consent-script-tag")
      scripts                     should have size 1
      scripts.first.attr("nonce") should be("a-nonce")
    }

    "include a nonce in the IE9+ link tag if supplied" in {
      val hmrcHead = app.injector.instanceOf[HmrcHead]
      hmrcfrontend.RoutesPrefix.setPrefix("/some-service/hmrc-frontend")
      val content  = contentAsString(hmrcHead(nonce = Some("a-nonce")))

      content should include regex
        """<!--\[if gt IE 8\]><!-->
          |<link href="/some-service/hmrc-frontend/assets/hmrc-frontend-\d+.\d+.\d+.min.css" media="all" rel="stylesheet" type="text/css" nonce="a-nonce" />
          |<!--<!\[endif\]-->""".stripMargin.r
    }

    "include the hmrc-frontend minified css bundle" in {
      val hmrcHead = app.injector.instanceOf[HmrcHead]
      hmrcfrontend.RoutesPrefix.setPrefix("/some-service/hmrc-frontend")
      val content  = contentAsString(hmrcHead())

      content should include regex
        """<!--\[if gt IE 8\]><!-->
          |<link href="/some-service/hmrc-frontend/assets/hmrc-frontend-\d+.\d+.\d+.min.css" media="all" rel="stylesheet" type="text/css" />
          |<!--<!\[endif\]-->""".stripMargin.r
    }

    "include the hmrc-frontend minified ie8 css bundle and minified html5shiv bundle" in {
      val hmrcHead = app.injector.instanceOf[HmrcHead]
      hmrcfrontend.RoutesPrefix.setPrefix("/some-service/hmrc-frontend")
      val content  = contentAsString(hmrcHead())

      content should include regex
        """<!--\[if lte IE 8\]>
          |<script src="/some-service/hmrc-frontend/assets/vendor/html5shiv.min.js"></script>
          |<link href="/some-service/hmrc-frontend/assets/hmrc-frontend-ie8-\d+.\d+.\d+.min.css" media="all" rel="stylesheet" type="text/css" />
          |<!\[endif\]-->""".stripMargin.r
    }

    "include the supplied headBlock" in {
      val hmrcHead = app.injector.instanceOf[HmrcHead]
      val content  = hmrcHead(headBlock = Some(Html("""<meta name="author" content="John Doe">""")))

      val metaTags = content.select("meta[name=author]")
      metaTags should have size 1
    }

    "render the correct url even if AssetsConfig has already been instantiated" in {
      val hmrcHead = app.injector.instanceOf[HmrcHead]
      hmrcfrontend.RoutesPrefix.setPrefix("/foo-service/hmrc-frontend")

      val links = hmrcHead().select("link")

      links                    should have size 1
      links.first.attr("href") should fullyMatch regex
        """/foo-service/hmrc-frontend/assets/hmrc-frontend-\d+.\d+.\d+.min.css""".r
    }
  }
}
