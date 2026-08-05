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

package uk.gov.hmrc.play.language

import com.google.inject.Inject
import org.scalatestplus.play.PlaySpec
import play.api.i18n.{Lang, MessagesApi}
import play.api.mvc.{ControllerComponents, Cookie}
import play.api.test.Helpers._
import play.api.test.{FakeRequest, PlayRunners}

class TestLanguageController @Inject() (languageUtils: LanguageUtils, cc: ControllerComponents)
    extends LanguageController(languageUtils, cc) {

  val EnglishLangCode = "en"
  val WelshLangCode   = "cy"

  val English: Lang = Lang(EnglishLangCode)
  val Welsh: Lang   = Lang(WelshLangCode)

  override protected def fallbackURL = "http://gov.uk/fallback"

  override def languageMap: Map[String, Lang] = Map("english" -> English, "cymraeg" -> Welsh)
}

class LanguageControllerSpec extends PlaySpec with PlayRunners {

  val EnglishLangCode = "en"
  val WelshLangCode   = "cy"

  val English: Lang = Lang(EnglishLangCode)
  val Welsh: Lang   = Lang(WelshLangCode)

  private val refererValue   = "/gov.uk"
  private val fallbackValue  = "http://gov.uk/fallback"
  private val maliciousValue = "https://www.bad.host/path?a=b#foo"

  "The switch language endpoint" should {

    "change to welsh when language is set to Welsh" in {
      running() { app =>
        val sut                               = app.injector.instanceOf[TestLanguageController]
        implicit val messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]
        val res                               = sut.switchToLanguage("cymraeg")(FakeRequest())
        cookies(res).get(messagesApi.langCookieName) match {
          case Some(c: Cookie) => c.value.mustBe(WelshLangCode)
          case _               => fail("PLAY_LANG cookie was not cy")
        }
      }
    }

    "respond with a See Other (303) status when a referrer is in the header." in {
      running() { app =>
        val sut     = app.injector.instanceOf[TestLanguageController]
        val request = FakeRequest().withHeaders(REFERER -> refererValue)
        val res     = sut.switchToLanguage("english")(request)
        status(res)           must be(SEE_OTHER)
        redirectLocation(res) must be(Some(refererValue))
      }
    }

    "respond with a See Other (303) status when no referrer is in the header." in {
      running() { app =>
        val sut = app.injector.instanceOf[TestLanguageController]
        val res = sut.switchToLanguage("english")(FakeRequest())
        status(res)           must be(SEE_OTHER)
        redirectLocation(res) must be(Some(fallbackValue))
      }
    }

    "set the language in a cookie." in {
      running() { app =>
        val sut                               = app.injector.instanceOf[TestLanguageController]
        implicit val messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]
        val res                               = sut.switchToLanguage("english")(FakeRequest())
        cookies(res).get(messagesApi.langCookieName) match {
          case Some(c: Cookie) => c.value must be(EnglishLangCode)
          case _               => fail("PLAY_LANG cookie was not found.")
        }
      }
    }

    "redirect to fallback value when referer is invalid" in {
      running() { app =>
        val sut     = app.injector.instanceOf[TestLanguageController]
        val request = FakeRequest().withHeaders(REFERER -> "not a [valid url]!!\n")
        val res     = sut.switchToLanguage("english")(request)
        status(res)           must be(SEE_OTHER)
        redirectLocation(res) must be(Some(fallbackValue))
      }
    }

    "redirect to fallback value when referer has no path" in {
      running() { app =>
        val sut     = app.injector.instanceOf[TestLanguageController]
        val request = FakeRequest().withHeaders(REFERER -> "http://gov.uk")
        val res     = sut.switchToLanguage("english")(request)
        status(res)           must be(SEE_OTHER)
        redirectLocation(res) must be(Some(fallbackValue))
      }
    }

    "redirect to a relative url" in {
      running() { app =>
        val sut     = app.injector.instanceOf[TestLanguageController]
        val request = FakeRequest().withHeaders(REFERER -> maliciousValue)
        val res     = sut.switchToLanguage("english")(request)
        status(res)           must be(SEE_OTHER)
        redirectLocation(res) must be(Some(s"/path?a=b#foo"))
      }
    }

    "prevent bypassing the relative uri by passing a second hostname after the first in the referer" in {
      running() { app =>
        val sut     = app.injector.instanceOf[TestLanguageController]
        val request = FakeRequest().withHeaders(REFERER -> s"http://scarificial-hostname/$maliciousValue")
        val res     = sut.switchToLanguage("english")(request)
        status(res)           must be(SEE_OTHER)
        redirectLocation(res) must be(Some(s"/$maliciousValue"))
      }
    }

    "redirect to a relative url when referer url has an auth section" in {
      running() { app =>
        val sut     = app.injector.instanceOf[TestLanguageController]
        val request = FakeRequest().withHeaders(REFERER -> s"https://test:foo@www.bad.host/path?a=b#foo")
        val res     = sut.switchToLanguage("english")(request)
        status(res)           must be(SEE_OTHER)
        redirectLocation(res) must be(Some("/path?a=b#foo"))
      }
    }

    "url encode all parts of the url in location header" in {
      running() { app =>
        val sut     = app.injector.instanceOf[TestLanguageController]
        val request = FakeRequest().withHeaders(
          REFERER -> s"https://www.tax.service.gov.uk/path%20with%20space?query=%C2%A320#%C2%A320"
        )
        val res     = sut.switchToLanguage("english")(request)
        status(res)           must be(SEE_OTHER)
        redirectLocation(res) must be(Some("/path%20with%20space?query=%C2%A320#%C2%A320"))
      }
    }
  }

}
