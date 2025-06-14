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
import play.twirl.api.Html
import uk.gov.hmrc.hmrcfrontend.views.config.StandardBetaBanner
import uk.gov.hmrc.hmrcfrontend.views.html.components._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.header.NavigationItem
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.hmrcstandardpage.Banners
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.userresearchbanner.UserResearchBanner
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.header.v2.{HeaderNames, HeaderNavigation, HeaderParams, HeaderTemplateOverrides, HeaderUrls, LogoOverrides, MenuButtonOverrides}
import uk.gov.hmrc.helpers.MessagesSupport

import scala.util.Try

class HmrcHeaderSpec extends TemplateUnitSpec[HeaderParams, HmrcHeader]("hmrcHeader") with MessagesSupport {

  def buildAnotherApp(properties: Map[String, String] = Map.empty): Application =
    new GuiceApplicationBuilder()
      .configure(properties)
      .build()

  "header" should {
    """not throw an exception if Some("") is passed as serviceName""" in {
      val params       =
        Header(serviceName = Some(""), containerClasses = "govuk-width-container", signOutHref = Some("/sign-out"))
      val componentTry = Try(component(params))

      componentTry.isSuccess shouldBe true
    }

    """display Tudor crown logo set by config""" in {
      val anotherApp = buildAnotherApp(
        Map(
          "play-frontend-hmrc.useTudorCrown" -> "true"
        )
      )
      val hmrcHeader = anotherApp.injector.instanceOf[HmrcHeader]

      val componentTry = Try(hmrcHeader(Header()))

      componentTry.isSuccess shouldBe true
      componentTry.get.body    should include("M33.1,9.8c.2")
    }

    """display St Edwards crown logo when set by config""" in {
      val anotherApp = buildAnotherApp(
        Map(
          "play-frontend-hmrc.useTudorCrown" -> "false"
        )
      )
      val hmrcHeader = anotherApp.injector.instanceOf[HmrcHeader]

      val componentTry = Try(hmrcHeader(Header()))

      componentTry.isSuccess shouldBe true
      componentTry.get.body    should include("M13.4,22.3c2")
    }

    """display Tudor crown when no config is found""" in {
      val anotherApp = buildAnotherApp()
      val hmrcHeader = anotherApp.injector.instanceOf[HmrcHeader]

      val componentTry = Try(hmrcHeader(Header()))

      componentTry.isSuccess shouldBe true
      componentTry.get.body    should include("M33.1,9.8c.2")
    }
  }

  "output of rebrand enabled" should {
    "match output of rebrand disabled" when {
      "rebrand is enabled by argument" in {
        val appWithRebrand    = buildAnotherApp(Map("play-frontend-hmrc.useRebrand" -> "true"))
        val appWithoutRebrand = buildAnotherApp(Map("play-frontend-hmrc.useRebrand" -> "false"))

        val viewWithRebrand    = appWithRebrand.injector.instanceOf[HmrcHeader]
        val viewWithoutRebrand = appWithoutRebrand.injector.instanceOf[HmrcHeader]

        viewWithRebrand.apply(Header()) shouldBe viewWithoutRebrand.apply(Header(rebrand = Some(true)))
      }
    }
  }

  "HeaderParams" should {
    "do an implicit conversion" when {
      "passed a Header" in {
        val standardBetaBanner = app.injector.instanceOf[StandardBetaBanner]

        val header = Header(
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
            serviceUrl = "/test-service",
            assetsPath = "/different/assets/images",
            signOutHref = Some("test-sign-out")
          ),
          headerNames = HeaderNames(
            productName = Some("Test Product"),
            serviceName = Some("Test Service")
          ),
          headerTemplateOverrides = HeaderTemplateOverrides(
            containerClasses = "alt-width-container",
            classes = "test-service-classes",
            attributes = Map("attrKey" -> "attrValue")
          ),
          headerNavigation = HeaderNavigation(
            navigation = Some(Seq(NavigationItem())),
            navigationClasses = "some-navigation-classes",
            navigationLabel = Some("Test Navigation Label")
          ),
          banners = Banners(
            displayHmrcBanner = true,
            phaseBanner = Some(standardBetaBanner(url = "/some-feedback-url")),
            userResearchBanner = Some(UserResearchBanner(url = "/some-research-url")),
            additionalBannersBlock = Some(Html("<p>Banner text<p>"))
          ),
          menuButtonOverrides = MenuButtonOverrides(
            menuButtonLabel = Some("menu button label"),
            menuButtonText = Some("menu button text")
          ),
          logoOverrides = LogoOverrides(
            useTudorCrown = Some(true),
            rebrand = Some(true)
          ),
          language = Cy
        )
      }
    }
  }
}
