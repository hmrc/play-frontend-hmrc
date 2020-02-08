/*
 * Copyright 2020 HM Revenue & Customs
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

import org.scalatest.WordSpec
import org.scalatestplus.play.OneAppPerTest
import play.api.Application
import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import play.filters.csrf.CSRF.Token
import play.filters.csrf.{CSRFConfigProvider, CSRFFilter}

trait CSRFSpec extends WordSpec with OneAppPerTest {
  implicit class RichFakeRequest[T](fakeRequest: FakeRequest[T])(implicit app: Application) {
    def withCSRFToken: RequestHeader = {
      val csrfConfig = app.injector.instanceOf[CSRFConfigProvider].get
      val csrfFilter = app.injector.instanceOf[CSRFFilter]
      val token      = csrfFilter.tokenProvider.generateToken

      fakeRequest
        .copyFakeRequest(
          tags =
            fakeRequest.tags ++
              Map(
                Token.NameRequestTag -> csrfConfig.tokenName,
                Token.RequestTag     -> token
              ))
        .withHeaders((csrfConfig.headerName, token))
    }
  }

  implicit lazy val request = FakeRequest().withCSRFToken
}
