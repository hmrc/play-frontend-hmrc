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

package uk.gov.hmrc.hmrcfrontend.views.components

import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.HmrcFrontendDependency.hmrcFrontendVersion
import uk.gov.hmrc.hmrcfrontend.views.html.components._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.charactercount.Generators._
import uk.gov.hmrc.support.TemplateIntegrationBaseSpec

import scala.util.Try

object HmrcCharacterCountIntegrationSpec
    extends TemplateIntegrationBaseSpec[CharacterCount](
      componentName = "hmrcCharacterCount",
      seed = None
    )
    with MessagesSupport {

  protected val libraryName: String = "hmrc"

  protected val libraryVersion: String = hmrcFrontendVersion

  private val component = app.injector.instanceOf[HmrcCharacterCount]

  override def render(characterCount: CharacterCount): Try[HtmlFormat.Appendable] = {
    implicit val request: RequestHeader = FakeRequest("GET", "/foo")
    Try(component(characterCount))
  }
}
