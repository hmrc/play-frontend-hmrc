/*
 * Copyright 2020 HM Revenue & Customs
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
import java.util
import java.util.UUID
import better.files._
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch
import scala.collection.JavaConverters._
import scala.collection.mutable

case class DiffWrapper(differ: DiffMatchPatch) {

  /**
    * Compute the diff between two strings
    *
    * @param s first [[String]]
    * @param t second [[String]]
    * @return [[util.LinkedList[DiffMatchPatch.Diff]]]
    */
  def diff(s: String, t: String): util.LinkedList[DiffMatchPatch.Diff] = {
    val diffResult = differ.diffMain(s, t)
    val _          = differ.diffCleanupSemantic(diffResult)
    diffResult
  }

  /**
    * Write the diff in pretty HTML so it can be output at the end of a test failure
    *
    * @param diff
    * @return [[Path]] to the file
    */
  def diffToHtml(diff: mutable.Buffer[DiffMatchPatch.Diff], diffFilePrefix: Option[String] = None): Path = {
    // write diff as pretty html to a file
    val diffHtml = differ.diffPrettyHtml(diff.asJava)

    val userDir = System.getProperty("user.dir")
    val prefix  = diffFilePrefix.map(_ + "-").getOrElse("")
    val name    = s"${prefix}diff-${UUID.randomUUID.toString}.html"
    val file    = userDir / "target" / name

    file.overwrite(diffHtml)

    file.path
  }
}
