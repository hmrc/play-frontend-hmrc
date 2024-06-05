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

package uk.gov.hmrc.govukfrontend.views.implicits

import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.{Fieldset, Legend}
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label
import uk.gov.hmrc.govukfrontend.views.viewmodels.summarylist.Key
import uk.gov.hmrc.govukfrontend.views.viewmodels.table.HeadCell
import uk.gov.hmrc.govukfrontend.views.viewmodels.tag.Tag

trait RichStringSupport {
  implicit class RichString(s: String) {

    def toHint: Hint = Hint(content = s.toText)

    def toText: Text = Text(s)

    def toKey: Key =
      Key(content = s.toText)

    def toHeadCell: HeadCell =
      HeadCell(content = s.toText)

    def toFieldset: Fieldset =
      Fieldset(
        legend = Some(s.toLegend)
      )

    def toTag: Tag =
      Tag(
        content = s.toText
      )

    def toLabel: Label =
      Label(
        content = s.toText
      )

    def toLegend: Legend =
      Legend(content = s.toText)

    def toOption: Option[String] =
      if (s == null || s.isEmpty) None else Some(s)

    def ltrim = s.replaceAll("^\\s+", "")

    def rtrim = s.replaceAll("\\s+$", "")

    /**
      * Indent a (multiline) string <code>n</code> spaces.
      *
      * @param n Number of spaces to indent the string. It can be a negative value, in which case it attempts to unindent
      *          n spaces, as much as possible until it hits the margin.
      * @param indentFirstLine
      * @return
      */
    def indent(n: Int, indentFirstLine: Boolean = false): String = {
      @scala.annotation.tailrec
      def recurse(m: Int, lines: Seq[String]): Seq[String] = m match {
        case 0 => lines
        case m => recurse(math.abs(m) - 1, indent1(math.signum(n), lines))
      }

      val lines         = s.split("\n", -1).toSeq // limit=-1 so if a line ends with \n include the trailing blank line
      val linesToIndent = if (indentFirstLine) lines else if (lines.length > 1) lines.tail else Nil
      val indentedLines = recurse(n, linesToIndent)

      (if (indentFirstLine) indentedLines else lines.head +: indentedLines).mkString("\n")
    }

    private def indent1(signal: Int, lines: Seq[String]) = {
      def canUnindent: Boolean =
        lines.forall(_.startsWith(" "))

      if (signal < 0 && canUnindent) {
        lines.map(_.drop(1))
      } else if (signal < 0) {
        lines
      } else {
        lines.map(" " + _)
      }
    }

    /**
      * Implements [[https://mozilla.github.io/nunjucks/templating.html#capitalize]].
      * Unlike Scala's [[scala.collection.immutable.StringLike#capitalize()]] Nunjucks' capitalize converts the first
      * letter to uppercase and the rest to lowercase.
      *
      * @return
      */
    def nunjucksCapitalize: String =
      s.toLowerCase.capitalize

  }
}
