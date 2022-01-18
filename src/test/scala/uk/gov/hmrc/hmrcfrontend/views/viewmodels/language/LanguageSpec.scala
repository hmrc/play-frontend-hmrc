/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.language

import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.JsonRoundtripSpec
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.Generators._

class LanguageSpec extends JsonRoundtripSpec[Language] {

  "compare" should {

    "mark English as equal in order to English" in {
      val x: Language = En
      val y: Language = En

      x.compare(y) shouldEqual 0
    }

    "mark any language later in order if this language is English and that language is not" in {
      val x: Language = En
      val y: Language = Cy

      x.compare(y) should be < 0
    }

    "mark that language as earlier in order if it is English and this language is not" in {
      val x: Language = Cy
      val y: Language = En

      x.compare(y) should be > 0
    }

    "delegate to a comparison on the name values of the languages if neither language is English" in {
      val x: Language = Cy
      val y: Language = Cy

      x.compare(y) shouldEqual x.name.compare(y.name)
    }

  }

}
