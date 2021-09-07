package uk.gov.hmrc.helpers.views

import java.nio.file.Path
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch

import scala.collection.JavaConverters._

object TemplateDiff {

  lazy val diffWrapper = DiffWrapper(new DiffMatchPatch())

  /** Compute the diff and print the path to the diff file
    *
    * @param nunJucksOutputHtml
    * @param twirlOutputHtml
    * @param diffFilePrefix
    */
  def templateDiffPath(
    twirlOutputHtml: String,
    nunJucksOutputHtml: String,
    diffFilePrefix: Option[String] = None
  ): Path = {
    val diffResult = diffWrapper.diff(twirlOutputHtml, nunJucksOutputHtml)
    val path       = diffWrapper.diffToHtml(diff = diffResult.asScala, diffFilePrefix = diffFilePrefix)
    path
  }
}
