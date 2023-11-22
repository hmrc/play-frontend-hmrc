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

import uk.gov.hmrc.govukfrontend.views.implicits._

trait Implicits
    extends RichCharacterCountSupport
    with RichCheckboxesSupport
    with RichFormErrorsSupport
    with RichHtmlSupport
    with RichInputSupport
    with RichOptionStringSupport
    with RichRadiosSupport
    with RichSelectSupport
    with RichStringSupport
    with RichTextareaSupport
    with RichSeqStringTupleSupport
    with RichLegendSupport
    with RichActionItemSupport

object Implicits extends Implicits
