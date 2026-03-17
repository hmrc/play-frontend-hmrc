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

package uk.gov.hmrc.govukfrontend.views
package components

import uk.gov.hmrc.govukfrontend.views.html.components._

import scala.util.Try

class GovukHeaderSpec extends TemplateUnitSpec[Header, GovukHeader]("govukHeader") {

  // The following line is needed to ensure known state of the statically initialised reverse router
  // used to calculate asset paths
  hmrcfrontend.RoutesPrefix.setPrefix("")

  "header" should {
    """display Tudor crown logo """ in {
      val govukHeader = app.injector.instanceOf[GovukHeader]

      val componentTry = Try(govukHeader(Header()))

      componentTry          should be a Symbol("success")
      componentTry.get.body should include("M33.1,9.8c.2")
    }
  }
}
