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

package uk.gov.hmrc.hmrcfrontend.views.helpers

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.i18n.{Lang, Messages}
import play.api.inject.guice.GuiceApplicationBuilder
import uk.gov.hmrc.helpers.views.JsoupHelpers
import uk.gov.hmrc.hmrcfrontend.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcLanguageSelectHelper

import scala.collection.immutable.List

class hmrcLanguageSelectHelperSpec
    extends AnyWordSpec
    with Matchers
    with JsoupHelpers
    with MessagesSupport
    with GuiceOneAppPerSuite {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder().configure(Map("play.i18n.langs" -> List("en", "cy"))).build()

  "HmrcLanguageSelectHelper" must {
    "Return a language select" in {
      val languageSelect = app.injector.instanceOf[HmrcLanguageSelectHelper]
      val content        = languageSelect()

      content.select(".hmrc-language-select") must have size 1
    }

    "Provide a link to Welsh by default" in {
      val languageSelect = app.injector.instanceOf[HmrcLanguageSelectHelper]
      val content        = languageSelect()

      content
        .select(".govuk-link")
        .text mustBe "Newid yr iaith ir Gymraeg Cymraeg"
    }

    "Provide a link to English is language cookie is set to Welsh" in {
      implicit val welshMessages: Messages = messagesApi.preferred(Seq(Lang("cy")))
      val languageSelect                   = app.injector.instanceOf[HmrcLanguageSelectHelper]
      val content                          = languageSelect()(welshMessages)

      content
        .select(".govuk-link")
        .text mustBe "Change the language to English English"
    }
  }
}
