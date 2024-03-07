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

import play.api.i18n.Messages
import play.api.mvc.MessagesRequest
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.html.components._

import scala.util.Try

class HmrcFooterSpec extends TemplateUnitBaseSpec[Footer]("hmrcFooter") with MessagesSupport {

  private val component = app.injector.instanceOf[HmrcFooter]

  def render(templateParams: Footer): Try[HtmlFormat.Appendable] = {
    val request = FakeRequest("GET", "/foo")
      .withTransientLang(templateParams.language.code)

    implicit val messages: Messages = new MessagesRequest(request, messagesApi).messages

    Try(component(templateParams))
  }
}
