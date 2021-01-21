import uk.gov.hmrc.playcrosscompilation.AbstractPlayCrossCompilation
import uk.gov.hmrc.playcrosscompilation.PlayVersion._

object PlayCrossCompilation extends AbstractPlayCrossCompilation(defaultPlayVersion = Play26) {
  val playRevision: String = PlayCrossCompilation.playVersion match {
    case Play26 => "2.6.23"
    case Play27 => "2.7.5"
  }
}
