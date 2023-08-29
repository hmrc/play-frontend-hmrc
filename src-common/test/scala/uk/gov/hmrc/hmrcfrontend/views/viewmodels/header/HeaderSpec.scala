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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.header

import uk.gov.hmrc.hmrcfrontend.views.viewmodels.header.Generators._
import org.scalacheck.ShrinkLowPriority
import org.scalatest.OptionValues
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.libs.json.Json
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{Cy, En, LanguageToggle}

import scala.reflect.ClassTag

// We use this customised test class as the `hmrc-frontend` Nunjucks model
// of Header expects links for `en` and `cy` when provided with a language
// toggle, even if they are technically optional and not provided,
// implicitly mapping them to href = "" if they don't exist. Hence, to
// retain parity, we do the same upon creation of an instance of Header,
// and hence must not compare the generated version after going on a
// JsonRoundTrip with the direct input, but rather the input with the default
// values added.

class HeaderSpec
    extends AnyWordSpec
    with Matchers
    with OptionValues
    with ScalaCheckPropertyChecks
    with ShrinkLowPriority {

  "Json reads/writes" should {
    s"do a roundtrip json serialisation of ${implicitly[ClassTag[Header]]}" in {
      forAll { v: Header =>
        val linkMapWithDefaults: Option[LanguageToggle] = v.languageToggle.map { x =>
          val links = Map(En -> "", Cy -> "") ++ x.linkMap
          LanguageToggle(links.toArray: _*)
        }
        Json.toJson(v).asOpt[Header].value shouldBe v.copy(inputLanguageToggle = linkMapWithDefaults)
      }
    }
  }
}
