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

import play.api.libs.json._
import uk.gov.hmrc.helpers.views.TemplateTestHelper

/**
  * Base class for unit testing against test fixtures generated from hmrc-frontend's yaml documentation files for
  * components
  */
abstract class TemplateUnitBaseSpec[T: Reads](hmrcComponentName: String)
    extends TemplateTestHelper[T](hmrcComponentName) {

  override protected val baseFixturesDirectory: String = "/fixtures/hmrc-frontend"

  val skipBecauseOfSpellcheckOrdering = Seq(
    "character-count-spellcheck-disabled",
    "character-count-spellcheck-enabled"
  )

  override protected val skip: Seq[String] = skipBecauseOfSpellcheckOrdering

  matchTwirlAndNunjucksHtml(fixturesDirs)

}
