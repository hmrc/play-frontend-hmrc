package uk.gov.hmrc.govukfrontend.support

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
