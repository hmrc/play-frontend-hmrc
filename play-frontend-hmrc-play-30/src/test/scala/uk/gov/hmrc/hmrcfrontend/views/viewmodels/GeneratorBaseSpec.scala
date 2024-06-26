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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels

import org.scalacheck.Arbitrary
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._

abstract class GeneratorBaseSpec[T: ClassTag: TypeTag](
  val ignoredFields: Set[String] = Set.empty,
  val instanceCount: Int = 100
)(implicit val arbT: Arbitrary[T])
    extends AnyWordSpec
    with Matchers {

  s"Generator for ${typeOf[T]}" should {
    "generate varying data for all fields" in {
      val instances       = generateInstances
      val invariantFields = checkInvariantFieldValues(instances)
      withClue("Found fields that aren't being randomly generated:") {
        invariantFields shouldBe empty
      }
    }
  }

  // can't use LazyList because we need to support Scala 2.12
  private def generateInstances(implicit arb: Arbitrary[T]): Seq[T] =
    Stream.continually(arb.arbitrary.sample).flatten.take(instanceCount).toList

  private val mirror          = runtimeMirror(getClass.getClassLoader)
  private val caseClassFields = typeOf[T].members
    .collect {
      case m: MethodSymbol if m.isCaseAccessor => m
    }

  private def checkInvariantFieldValues(instances: Seq[T]): Set[String] =
    caseClassFields.flatMap { field =>
      val values = instances.map { instance =>
        val instanceMirror = mirror.reflect(instance)
        instanceMirror.reflectMethod(field).apply()
      }
      if (values.distinct.size == 1) Some(field.name.toString) else None
    }.toSet -- ignoredFields
}
