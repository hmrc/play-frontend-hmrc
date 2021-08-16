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

package uk.gov.hmrc.hmrcfrontend.views
package components

import uk.gov.hmrc.hmrcfrontend.views.html.components._
import scala.util.Try

class HmrcHeaderSpec extends TemplateUnitSpec[Header, HmrcHeader]("hmrcHeader") {

  "header" should {
    """not throw an exception if Some("") is passed as serviceName""" in {
      val params       =
        Header(serviceName = Some(""), containerClasses = "govuk-width-container", signOutHref = Some("/sign-out"))
      val componentTry = Try(component(params))

      componentTry should be a 'success
    }
  }
}
