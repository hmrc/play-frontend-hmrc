package uk.gov.hmrc.support

import org.scalacheck.Prop
import org.scalacheck.Prop.collect

object ScalaCheckUtils {

  type ClassifyParams = (Boolean, Any, Any)

  /**
    * Collect statistics after checking a property. See [[Prop.classify]]
    * Convenience method that avoids nesting several [[Prop.classify]] by providing a [[Stream]] of conditions.
    * A [[Stream]] is used to avoid evaluating the conditions prematurely.
    *
    * @param conditions [[Stream]] of triples (condition, ifTrue, ifFalse)
    * @param prop       a scalacheck property [[Prop]]
    * @return [[Prop]]
    */
  @scala.annotation.tailrec
  def classify(conditions: Stream[ClassifyParams])(prop: Prop): Prop = conditions match {
    case Stream.Empty       => prop
    case h #:: Stream.Empty =>
      if (h._1) collect(h._2)(prop) else collect(h._3)(prop)
    case h #:: t            =>
      classify(t)(Prop.classify(h._1, h._2, h._3)(prop))
  }
}
