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

package uk.gov.hmrc.govukfrontend.views.viewmodels

import org.scalacheck.{Arbitrary, ShrinkLowPriority}
import org.scalatest.OptionValues
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.libs.json.{Json, Reads, Writes}

import scala.reflect.ClassTag

class JsonRoundtripSpec[T: Reads: Writes: Arbitrary: ClassTag]
    extends AnyWordSpec
    with Matchers
    with OptionValues
    with ScalaCheckPropertyChecks
    with ShrinkLowPriority {

  "Json reads/writes" should {
    s"do a roundtrip json serialisation of ${implicitly[ClassTag[T]]}" in {
      forAll { v: T =>
        withClue(
          s"\n !!!JSON DOES NOT MATCH!!! \n\n SERIALISED JSON: \n ${Json.toJson[T](v).asOpt[T].value} \n\n JSON VALUE: \n $v \n\n"
        ) {
          Json.toJson[T](v).asOpt[T].value shouldBe v
        }
      }
    }
  }
}
