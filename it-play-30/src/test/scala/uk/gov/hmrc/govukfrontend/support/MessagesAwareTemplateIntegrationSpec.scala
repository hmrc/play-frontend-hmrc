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

package uk.gov.hmrc.govukfrontend.support

import org.scalacheck.Arbitrary
import play.api.i18n.Messages
import play.api.libs.json.OWrites
import play.twirl.api.{HtmlFormat, Template2}
import uk.gov.hmrc.govukfrontend.views.GovukFrontendDependency.govukFrontendVersion
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.support.TemplateIntegrationBaseSpec

import scala.reflect.ClassTag
import scala.util.Try

/**
  * Base class for integration testing a Twirl template against the Nunjucks template rendering service,
  * where the template takes a viewmodel plus an implicit Messages API
  *
  * @tparam T Type representing the input parameters of the Twirl template
  */
abstract class MessagesAwareTemplateIntegrationSpec[T: OWrites: Arbitrary, C <: Template2[
  T,
  Messages,
  HtmlFormat.Appendable
]: ClassTag](
  govukComponentName: String,
  seed: Option[String] = None
) extends TemplateIntegrationBaseSpec[T](govukComponentName, seed)
    with MessagesSupport {

  protected val libraryName: String = "govuk"

  protected val libraryVersion: String = govukFrontendVersion

  private val component: C = app.injector.instanceOf[C]

  def render(templateParams: T): Try[HtmlFormat.Appendable] =
    Try(component.render(templateParams, implicitly))

}
