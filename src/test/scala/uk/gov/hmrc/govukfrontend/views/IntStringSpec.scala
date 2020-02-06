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

import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import play.api.libs.json.{JsArray, JsBoolean, JsNull, JsNumber, JsResult, JsString, JsSuccess, JsValue, Json, Reads}

import scala.util.Try


class IntStringSpec extends WordSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  "fields for IntString" should {
    "return int for int field" in {
      forAll { int: Int =>
        val intString = IntString(int)
        intString.int shouldBe int
      }
    }

    "return string form of int for str field" in {
      forAll { int: Int =>
        val intString = IntString(int)
        intString.str shouldBe int.toString
      }
    }
  }

  "apply" should {
    "apply directly on int parameter" in {
      forAll { int: Int =>
        IntString.apply(int)
      }
    }

    "apply with success on int-like string" in {
      forAll { int: Int =>
        val validIntString: String = int.toString
        IntString(validIntString).isSuccess
      }
    }

    "apply with failure on non int-like string" in {
      forAll {invalidIntString: String =>
        whenever (Try(invalidIntString.toInt).isFailure) {
          val attempt = IntString(invalidIntString)
          assert(attempt.isFailure)
          assert(attempt.failed.get.isInstanceOf[NumberFormatException])
        }
      }
    }
  }

  "implicit reads" should {

    "parse JsNumber with integer numbers as IntString" in {
      forAll { int: Int =>
        val jsNumber = JsNumber(int)
        val intString = jsNumber.as[IntString]
        intString.int shouldBe int
      }
    }

    "fail to parse JsNumber with non-integer numbers as IntString" in {
      forAll { bigDecimal: BigDecimal =>
        whenever (!bigDecimal.isValidInt) {
          val jsNumber = JsNumber(bigDecimal)
          val attempt = Try(jsNumber.as[IntString])
          assert(attempt.isFailure)
          attempt.failed.map(_.getMessage).get should include("error.expected.validinteger")
        }
      }
    }

    "parse integer-like JsString as IntString" in {
      forAll { int: Int =>
        val validIntString: String = int.toString
        val jsString = JsString(validIntString)
        val intString = jsString.as[IntString]
        intString.int shouldBe int
        intString.str shouldBe validIntString
      }
    }

    "fail to parse JsString with non integer-like numbers as IntString" in {
      forAll { invalidIntString: String =>
        whenever (Try(invalidIntString.toInt).isFailure) {
          val jsString = JsString(invalidIntString)
          val attempt = Try(jsString.as[IntString])
          assert(attempt.isFailure)
          attempt.failed.map(_.getMessage).get should include("error.expected.integerstring")
        }
      }
    }

    "fail to parse JsValues that are neither JsNumbers nor JsStrings to IntString" in {
      val invalidJsValues: Iterable[JsValue] = Iterable(JsArray(), JsBoolean(false), JsBoolean(true), JsNull, Json.obj("foo" -> "bar"))

      invalidJsValues.foreach { jsValue =>
        val attempt = Try(jsValue.as[IntString])
        assert(attempt.isFailure, s"- JsValue [${jsValue.getClass}: $jsValue] was unexpectedly able to be parsed as IntString instead of failing.")
        withClue(s"- JsValue [${jsValue.getClass}: $jsValue] had incorrect error"){
          attempt.failed.map(_.getMessage).get should include("error.expected.integerjsstringorjsnumber")
        }
      }
    }
  }

  "explicit reads" should {

    "give means to map JsValues that can successfully be parsed as IntString to Int" in {
      val reads: Reads[IntString] = implicitly[Reads[IntString]]
      val intermediateReads: Reads[Int] = reads.int
      forAll { int: Int =>

        val jsInt = JsNumber(int)
        val parsedInt = jsInt.as[Int](intermediateReads)
        int shouldBe parsedInt

        val validIntString: String = int.toString
        val jsString = JsString(validIntString)
        val intParsedFromString = jsString.as[Int](intermediateReads)
        int shouldBe intParsedFromString
      }
    }

    "give means to map JsValues that can successfully be parsed as IntString to String" in {
      val reads: Reads[IntString] = implicitly[Reads[IntString]]
      val intermediateReads: Reads[String] = reads.str
      forAll { int: Int =>

        val jsInt = JsNumber(int)
        val strParsedFromInt = jsInt.as[String](intermediateReads)
        int.toString shouldBe strParsedFromInt

        val validIntString: String = int.toString
        val jsString = JsString(validIntString)
        val strParsedFromIntString = jsString.as[String](intermediateReads)
        validIntString shouldBe strParsedFromIntString
      }
    }

    "give means to map JsValues that can successfully be parsed as Some[IntString] to Some[Int]" in {
      forAll { int: Int =>
        val expectedIntOption = Some(int)

        val optReadsStub = new Reads[Option[IntString]] {
          def reads(jsValue: JsValue): JsResult[Option[IntString]] = JsSuccess(Some(jsValue.as[IntString]))
        }

        val intermediateReads: Reads[Option[Int]] = optReadsStub.int
        JsString(s"$int").as[Option[Int]](intermediateReads) shouldBe expectedIntOption
      }
    }

    "give means to map JsValues that can successfully be parsed as None[IntString] to None[Int]" in {
      val optReadsStub = new Reads[Option[IntString]] {
        def reads(jsValue: JsValue): JsResult[Option[IntString]] = JsSuccess(None)
      }

      val intermediateReads: Reads[Option[Int]] = optReadsStub.int
      JsString("foo").as[Option[Int]](intermediateReads) shouldBe None
    }

    "give means to map JsValues that can successfully be parsed as Some[IntString] to Some[String]" in {
      forAll { int: Int =>
        val expectedStrOption = Some(int.toString)

        val optReadsStub = new Reads[Option[IntString]] {
          def reads(jsValue: JsValue): JsResult[Option[IntString]] = JsSuccess(Some(IntString(int)))
        }

        val intermediateReads: Reads[Option[String]] = optReadsStub.str
        JsString("foo").as[Option[String]](intermediateReads) shouldBe expectedStrOption
      }
    }

    "give means to map JsValues that can successfully be parsed as None[IntString] to None[String]" in {
      val optReadsStub = new Reads[Option[IntString]] {
        def reads(jsValue: JsValue): JsResult[Option[IntString]] = JsSuccess(None)
      }

      val intermediateReads: Reads[Option[String]] = optReadsStub.str
      JsString("foo").as[Option[String]](intermediateReads) shouldBe None
    }
  }

  "writes" should {
    "output IntString as JsString" in {
      forAll { int: Int =>
        val intString = IntString(int)
        Json.toJson(intString) shouldBe JsString(int.toString)
      }
    }
  }

}
