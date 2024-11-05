/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.helpers.views

import scala.annotation.tailrec

object FindUnbalancedHtmlTags {
  // DISCLAIMER this would not work if the output included commented out html, but not a use case we handle
  // if you did need to handle it, the simplest way would be to strip out all comments from the input first

  private val selfClosingTagNames =
    List("br", "hr", "img", "input", "link", "meta", "source", "base", "!--", "!DOCTYPE")

  sealed trait TestResult
  case object HtmlTagsAreBalanced extends TestResult
  case class Unclosed(tags: Seq[String]) extends TestResult
  case class Unopened(tag: String) extends TestResult
  case class Malformed(html: String) extends TestResult

  def check(html: String): TestResult =
    check(html, openedTags = Nil)

  @tailrec
  private def check(html: String, openedTags: List[String]): TestResult =
    html.dropWhile(_ != '<').span(_ != '>') match {
      case ("", "")                 =>
        if (openedTags.isEmpty) {
          HtmlTagsAreBalanced
        } else {
          Unclosed(openedTags)
        }
      case (tagHtml, remainingHtml) =>
        if (tagHtml.tail.exists(_ == '<') || remainingHtml == "") {
          Malformed(tagHtml)
        } else {
          val tagName = tagHtml.tail.takeWhile(!_.isWhitespace)
          if (tagName.head == '/') {
            val closedTagName = tagName.tail
            openedTags match {
              case `closedTagName` :: remainingOpenedTags => check(remainingHtml, remainingOpenedTags)
              case _                                      =>
                if (openedTags.contains(closedTagName)) {
                  Unclosed(List(openedTags.head))
                } else {
                  Unopened(closedTagName)
                }
            }
          } else {
            if (tagHtml.last == '/' || selfClosingTagNames.contains(tagName)) {
              check(remainingHtml, openedTags)
            } else {
              check(remainingHtml, tagName :: openedTags)
            }
          }
        }
    }
}
