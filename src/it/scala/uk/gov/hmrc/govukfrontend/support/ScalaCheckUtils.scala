package uk.gov.hmrc.govukfrontend.support

import org.scalacheck.Prop
import org.scalacheck.Prop.collect

object ScalaCheckUtils {

  /**
    * Collect statistics after checking a property. See [[Prop.classify]]
    * Convenience method that avoids nesting several [[Prop.classify]] by providing a [[Stream]] of conditions.
    * Stream is used to avoid evaluating the conditions prematurely.
    *
    * @param conditions
    * @param prop
    * @return [[Prop]]
    */
  @scala.annotation.tailrec
  def classify(conditions: Stream[(Boolean, Any, Any)])(prop: Prop): Prop = conditions match {
    case Stream.Empty => prop
    case h #:: Stream.Empty =>
      if (h._1) collect(h._2)(prop) else collect(h._3)(prop)
    case h #:: t =>
      classify(t)(Prop.classify(h._1, h._2, h._3)(prop))
  }
}
