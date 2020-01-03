/*
 * Copyright 2020 HM Revenue & Customs
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

import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.Generators._
import org.scalacheck.ShrinkLowPriority
import org.scalatest.{Matchers, OptionValues, WordSpec}
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.libs.json.Json
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.Language.{Cy, En}

import scala.collection.immutable.SortedMap
import scala.reflect.ClassTag

// We use this customised test class as the `hmrc-frontend` Nunjucks model
// of LanguageSelect expects links for `en` and `cy` even if they are
// technically optional and not provided, implicitly mapping them to href
// = "" if they don't exist. Hence, to retain parity, we do the same upon
// creation of an instance of LanguageSelect, and hence must not compare
// the generated version after going on a JsonRoundTrip with the direct
// input, but rather the input with the default values added.

class LanguageSelectSpec
  extends WordSpec
    with Matchers
    with OptionValues
    with ScalaCheckPropertyChecks
    with ShrinkLowPriority {

  "Json reads/writes" should {
    s"do a roundtrip json serialisation of ${implicitly[ClassTag[LanguageSelect]]}" in {
      forAll { v: LanguageSelect =>
        val linkMapWithDefaults: SortedMap[Language, String] = SortedMap(En -> "", Cy -> "") ++ v.languageToggle.linkMap
        Json.toJson(v).asOpt[LanguageSelect].value shouldBe LanguageSelect(v.language, linkMapWithDefaults.toArray: _*)
      }
    }
  }
}