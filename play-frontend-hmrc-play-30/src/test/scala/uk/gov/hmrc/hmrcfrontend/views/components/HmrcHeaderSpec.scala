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
import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.hmrcfrontend.views.config.StandardBetaBanner
import uk.gov.hmrc.hmrcfrontend.views.html.components._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.header.NavigationItem
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.hmrcstandardpage.Banners
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.userresearchbanner.UserResearchBanner
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.header.v2.{HeaderNames, HeaderParams, HeaderTemplateOverrides, HeaderUrls}
import uk.gov.hmrc.helpers.MessagesSupport

import scala.util.Try

class HmrcHeaderSpec extends TemplateUnitBaseSpec[HeaderParams]("hmrcHeader") with MessagesSupport {

  private val component = app.injector.instanceOf[HmrcHeader]

  def render(templateParams: HeaderParams): Try[HtmlFormat.Appendable] = {
    implicit val request: RequestHeader = FakeRequest("GET", "/foo")
    Try(component(templateParams))
  }

  def buildAnotherApp(properties: Map[String, String] = Map.empty): Application =
    new GuiceApplicationBuilder()
      .configure(properties)
      .build()

  "header" should {
    """display Tudor crown""" in {
      val hmrcHeader = app.injector.instanceOf[HmrcHeader]

      val componentTry = Try(hmrcHeader(Header()))

      componentTry.isSuccess shouldBe true
      componentTry.get.body    should include("M33.1,9.8c.2")
    }
  }

  "HeaderParams" should {
    "do an implicit conversion" when {
      "passed a Header" in {
        val standardBetaBanner = app.injector.instanceOf[StandardBetaBanner]

        val header: Header = Header(
          homepageUrl = "/some/govuk",
          assetsPath = "/different/assets/images",
          productName = Some("Test Product"),
          serviceName = Some("Test Service"),
          serviceUrl = "/test-service",
          navigation = Some(Seq(NavigationItem())),
          navigationClasses = "some-navigation-classes",
          containerClasses = "alt-width-container",
          classes = "test-service-classes",
          attributes = Map("attrKey" -> "attrValue"),
          language = Cy,
          displayHmrcBanner = true,
          useTudorCrown = Some(true),
          signOutHref = Some("test-sign-out"),
          userResearchBanner = Some(UserResearchBanner(url = "/some-research-url")),
          phaseBanner = Some(standardBetaBanner(url = "/some-feedback-url")),
          additionalBannersBlock = Some(Html("<p>Banner text<p>")),
          menuButtonLabel = Some("menu button label"),
          menuButtonText = Some("menu button text"),
          navigationLabel = Some("Test Navigation Label"),
          rebrand = Some(true)
        )

        // This function is to check that the implicit conversion is correctly called
        // when a function expecting HeaderParams is passed an instance of Header
        def doConversion(headerParams: HeaderParams): HeaderParams = headerParams

        doConversion(header) shouldBe HeaderParams(
          headerUrls = HeaderUrls(
            homepageUrl = "/some/govuk",
            assetsPath = "/different/assets/images",
            signOutHref = Some("test-sign-out")
          ),
          headerNames = HeaderNames(
            productName = Some("Test Product")
          ),
          headerTemplateOverrides = HeaderTemplateOverrides(
            containerClasses = "alt-width-container",
            classes = "test-service-classes",
            attributes = Map("attrKey" -> "attrValue")
          ),
          banners = Banners(
            displayHmrcBanner = true,
            phaseBanner = Some(standardBetaBanner(url = "/some-feedback-url")),
            userResearchBanner = Some(UserResearchBanner(url = "/some-research-url")),
            additionalBannersBlock = Some(Html("<p>Banner text<p>"))
          ),
          language = Cy
        )
      }
    }
  }
}
