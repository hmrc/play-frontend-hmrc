/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views

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
    val diffResult = diffWrapper.diff(nunJucksOutputHtml, twirlOutputHtml)
    val path       = diffWrapper.diffToHtml(diff = diffResult.asScala, diffFilePrefix = diffFilePrefix)
    path
  }
}
