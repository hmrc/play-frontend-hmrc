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

import better.files._
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch
import org.scalatest.{Matchers, WordSpec}
import scala.collection.JavaConverters._

class DiffWrapperSpec extends WordSpec with Matchers {

  "diff" should {
    "compute the diff between two strings" in new Setup {

      val differ = diffWrapper.differ

      val patch = differ.patchMake(diff)

      val patchText = differ.patchToText(patch)

      patchText shouldBe """@@ -1,9 +1,13 @@
                           |-Hello
                           |+Wonderful
                           |  Wor
                           |""".stripMargin
    }

    "diffToHtml" should {
      "write the diff Html to a file" in new Setup {

        val path = diffWrapper.diffToHtml(diff.asScala)

        val file = File(path)

        assert(file.exists)

        file.contentAsString shouldBe """<del style="background:#ffe6e6;">Hello</del><ins style="background:#e6ffe6;">Wonderful</ins><span> World!</span>""".stripMargin
      }
    }
  }
}

trait Setup {

  val diffWrapper = new DiffWrapper(new DiffMatchPatch())

  val s = "Hello World!"
  val t = "Wonderful World!"

  val diff = diffWrapper.diff(s, t)
}
