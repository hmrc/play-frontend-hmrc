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

import play.twirl.api.{HtmlFormat, Template1}
import scala.reflect.ClassTag
import play.api.libs.json._

import scala.util.Try

/**
  * Base class for unit testing against test fixtures generated from hmrc-frontend's yaml documentation files for
  * components
  */
abstract class TemplateWithComponentUnitSpec[T: Reads, C <: Template1[T, HtmlFormat.Appendable]: ClassTag](
  hmrcComponentName: String
) extends TemplateUnitBaseSpec[T](hmrcComponentName) {

  protected val component: C = app.injector.instanceOf[C]

  /**
    * Calls the Twirl template with the given parameters and returns the resulting markup
    *
    * @param templateParams: T
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  override def render(templateParams: T): Try[HtmlFormat.Appendable] =
    Try(component.render(templateParams))
}
