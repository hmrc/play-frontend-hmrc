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

package uk.gov.hmrc.support

import org.scalacheck.Prop
import org.scalacheck.Prop.collect

import scala.collection.compat.immutable.LazyList
import scala.collection.compat.immutable.LazyList.#::

object ScalaCheckUtils {

  type ClassifyParams = (Boolean, Any, Any)

  /**
    * Collect statistics after checking a property. See [[Prop.classify]]
    * Convenience method that avoids nesting several [[Prop.classify]] by providing a [[LazyList]] of conditions.
    * A [[LazyList]] is used to avoid evaluating the conditions prematurely.
    *
    * @param conditions [[LazyList]] of triples (condition, ifTrue, ifFalse)
    * @param prop       a scalacheck property [[Prop]]
    * @return [[Prop]]
    */
  @scala.annotation.tailrec
  def classify(conditions: LazyList[ClassifyParams])(prop: Prop): Prop = conditions match {
    case LazyList()       => prop
    case h #:: LazyList() =>
      if (h._1) collect(h._2)(prop) else collect(h._3)(prop)
    case h #:: t          =>
      classify(t)(Prop.classify(h._1, h._2, h._3)(prop))
    case _                => throw new MatchError(s"Unexpected match error on $conditions")
  }
}
