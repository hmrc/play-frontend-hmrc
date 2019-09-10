import uk.gov.hmrc.playcrosscompilation.AbstractPlayCrossCompilation
import uk.gov.hmrc.playcrosscompilation.PlayVersion._

object PlayCrossCompilation extends AbstractPlayCrossCompilation(defaultPlayVersion = Play26) {
  val playRevision: String = PlayCrossCompilation.playVersion match {
    case Play25 => "2.5.19"
    case Play26 => "2.6.23"
  }

  val sbtPlayCrossCompilationVersion = "0.17.0"
}
