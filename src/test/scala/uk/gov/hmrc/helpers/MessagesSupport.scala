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

package uk.gov.hmrc.helpers

import play.api.http.HttpConfiguration
import play.api.i18n._
import play.api.mvc.MessagesRequest
import play.api.test.FakeRequest
import play.api.{Configuration, Environment}

trait MessagesSupport {

  implicit lazy val messagesApi: MessagesApi = {

    val environment = Environment.simple()

    val configuration = Configuration.load(environment)

    val langs = new DefaultLangs(Seq(Lang("en"), Lang("cy")))

    new DefaultMessagesApiProvider(
      environment = environment,
      config = configuration,
      langs = langs,
      httpConfiguration = new HttpConfiguration()
    ).get
  }

  implicit val messages: Messages = new MessagesRequest(FakeRequest(), messagesApi).messages
}
