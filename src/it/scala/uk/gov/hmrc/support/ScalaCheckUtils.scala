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
    case l if l.isEmpty       => prop
    case h #:: t if t.isEmpty =>
      if (h._1) collect(h._2)(prop) else collect(h._3)(prop)
    case h #:: t              =>
      classify(t)(Prop.classify(h._1, h._2, h._3)(prop))
  }
}
