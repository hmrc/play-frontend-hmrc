/*
 * Copyright 2022 HM Revenue & Customs
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

import com.googlecode.htmlcompressor.compressor.HtmlCompressor
import com.googlecode.htmlcompressor.compressor.HtmlCompressor.{ALL_TAGS, BLOCK_TAGS_MAX}

trait PreProcessor {

  private lazy val compressor = new HtmlCompressor()

  /** *
    * Compresses markup to remove irrelevant whitespace differences.
    *
    * @param html
    * @return
    */
  def compressHtml(html: String, maximumCompression: Boolean = false): String = {
    compressor.setRemoveSurroundingSpaces(if (maximumCompression) ALL_TAGS else BLOCK_TAGS_MAX)
    compressor.compress(asDecimalSpecialCharacters(html: String))
  }

  private def asDecimalSpecialCharacters(html: String) = {
    val findHexadecimalCharacterIdentifier = """&#x(\d+)""".r
    findHexadecimalCharacterIdentifier.replaceAllIn(
      html,
      hexadecimalCharacterMatches => {
        val hexadecimalString = hexadecimalCharacterMatches group 1
        val asDecimal         = Integer.parseInt(hexadecimalString, 16)
        s"&#$asDecimal"
      }
    )
  }
}
