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
import play.api.libs.json.OWrites
import play.twirl.api.{HtmlFormat, Template1}
import uk.gov.hmrc.govukfrontend.views.GovukFrontendDependency.govukFrontendVersion
import uk.gov.hmrc.support.TemplateIntegrationBaseSpec

import scala.util.Try
import scala.reflect.ClassTag

/**
  * Base class for integration testing a Twirl template against the Nunjucks template rendering service
  *
  * @tparam T Type representing the input parameters of the Twirl template
  */
abstract class TemplateIntegrationSpec[T: OWrites: Arbitrary, C <: Template1[T, HtmlFormat.Appendable]: ClassTag](
  govukComponentName: String,
  seed: Option[String] = None,
  maximumCompression: Boolean = false
) extends TemplateIntegrationBaseSpec[T](govukComponentName, seed, maximumCompression) {

  protected val libraryName: String = "govuk"

  protected val libraryVersion: String = govukFrontendVersion

  private val component: C = app.injector.instanceOf[C]

  def render(templateParams: T): Try[HtmlFormat.Appendable] =
    Try(component.render(templateParams))

}
