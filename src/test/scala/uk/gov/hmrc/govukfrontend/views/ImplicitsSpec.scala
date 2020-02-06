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

import java.util.UUID
import org.scalacheck.Arbitrary._
import org.scalacheck.{Gen, ShrinkLowPriority}
import org.scalatest.{Matchers, OptionValues, WordSpec}
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.data.FormError
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.govukfrontend.views.html.components.implicits._
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.errorsummary.ErrorLink
import scala.collection.immutable
import scala.util.Random

class ImplicitsSpec
    extends WordSpec
    with Matchers
    with OptionValues
    with ScalaCheckPropertyChecks
    with ShrinkLowPriority {

  import Generators._

  implicit override val generatorDrivenConfig: PropertyCheckConfiguration =
    PropertyCheckConfiguration(minSuccessful = 50)

  "RichFormErrors" when {
    "asErrorLinks" should {
      "convert FormErrors to ErrorLinks with either text or html" in {
        forAll(genFormErrorsAndMessages) {
          case (formErrors, contentConstructor, messagesStub) =>
            import messagesStub.messages
            formErrors.asErrorLinks(contentConstructor).zipWithIndex.foreach {
              case (errorLink, i) =>
                errorLink shouldBe ErrorLink(
                  href    = Some(s"#${formErrors(i).key}"),
                  content = contentConstructor(messagesStub.messages(formErrors(i).message, formErrors(i).args: _*)))
            }
        }
      }
    }

    "asErrorMessages" should {
      "convert FormErrors to ErrorMessage either text or html" in {
        forAll(genFormErrorsAndMessages) {
          case (formErrors, contentConstructor, messagesStub) =>
            import messagesStub.messages
            formErrors.asErrorMessages(contentConstructor).zipWithIndex.foreach {
              case (errorMessageParams, i) =>
                errorMessageParams shouldBe ErrorMessage(
                  content = contentConstructor(messagesStub.messages(formErrors(i).message, formErrors(i).args: _*)))
            }
        }
      }
    }

    "asErrorMessage" when {
      "finds matching message" should {
        "convert FormErrors to ErrorMessage" in {
          forAll(genFormErrorsAndMessages) {
            case (formErrors, contentConstructor, messagesStub) =>
              import messagesStub.messages
              val i             = Random.nextInt(formErrors.length)
              val randomMessage = formErrors(i).message //select random message
              formErrors.asErrorMessage(contentConstructor, randomMessage).value shouldBe ErrorMessage(
                content = contentConstructor(messagesStub.messages(formErrors(i).message, formErrors(i).args: _*)))
          }
        }
      }

      "doesn't find matching message" should {
        "return None" in {
          forAll(genFormErrorsAndMessages) {
            case (formErrors, contentConstructor, messagesStub) =>
              import messagesStub.messages
              val mismatchingMessage = UUID.randomUUID.toString
              formErrors.asErrorMessage(contentConstructor, mismatchingMessage) shouldBe None
          }
        }
      }
    }

    "asErrorMessageForField" when {
      "finds matching message for field" should {
        "convert FormErrors to ErrorMessage" in {
          forAll(genFormErrorsAndMessages) {
            case (formErrors, contentConstructor, messagesStub) =>
              import messagesStub.messages
              val i        = Random.nextInt(formErrors.length)
              val fieldKey = formErrors(i).key //select random message
              formErrors.asErrorMessageForField(contentConstructor, fieldKey).value shouldBe ErrorMessage(
                content = contentConstructor(messagesStub.messages(formErrors(i).message, formErrors(i).args: _*)))
          }
        }
      }

      "finds matching message for field" should {
        "convert FormErrors to ErrorMessage choosing first error when several errors are present for the same field" in {
          forAll(genFormErrorsAndMessagesForSameFormField) {
            case (formErrors, contentConstructor, messagesStub) =>
              import messagesStub.messages
              val i                  = Random.nextInt(formErrors.length)
              val fieldKey           = formErrors(i).key //select random message
              val firstErrorForField = formErrors.filter(_.key == fieldKey)(0)
              formErrors.asErrorMessageForField(contentConstructor, fieldKey).value shouldBe ErrorMessage(
                content =
                  contentConstructor(messagesStub.messages(firstErrorForField.message, firstErrorForField.args: _*)))
          }
        }
      }

      "finds matching message for field" should {
        "return None" in {
          forAll(genFormErrorsAndMessagesForSameFormField) {
            case (formErrors, contentConstructor, messagesStub) =>
              import messagesStub.messages
              val i        = Random.nextInt(formErrors.length)
              val fieldKey = "" //select random message
              formErrors.asErrorMessageForField(contentConstructor, fieldKey) shouldBe None
          }
        }
      }
    }
  }

  "padLeft" should {
    "add left padding to non-empty HTML" in {

      forAll(htmlGen, Gen.chooseNum(0, 5)) { (html, padCount) =>
        (html, padCount) match {
          case (HtmlExtractor(""), n)      => html.padLeft(n)              shouldBe HtmlFormat.empty
          case (HtmlExtractor(content), 0) => html.padLeft(0).body         shouldBe content
          case (HtmlExtractor(content), n) => html.padLeft(n).body.drop(1) shouldBe html.padLeft(n - 1).body
        }
      }

      lazy val htmlGen: Gen[Html] = Gen.alphaStr.map(Html(_))

      object HtmlExtractor {
        def unapply(html: Html): Option[String] =
          Some(html.body)
      }

    }
  }

  // prove the property:
  // indent(n) = indent(n-1).indent(1) if n >=0
  // indent(n) = indent(n+1).indent(-1) if n < 0
  "indent(n, _)" should {
    "be the same as indent(signum(n) * (abs(n)-1), _).indent(signum(n) * 1, _)" in {
      forAll(genIndentArgs) {
        case (s, n, indentFirstLine) =>
          s.indent(n, indentFirstLine) shouldBe s
            .indent(math.signum(n) * (math.abs(n) - 1), indentFirstLine)
            .indent(math.signum(n) * 1, indentFirstLine)
      }
    }
  }

  object Generators {

    /**
      * Generate indentation arguments for [[uk.gov.hmrc.govukfrontend.views.Implicits.RichString.indent(int, boolean)]]
      */
    val genIndentArgs: Gen[(String, Int, Boolean)] =
      for {
        // generate text to indent with a maximum of 8 lines
        nLines <- Gen.choose(0, 8)
        maxInitialIndentation = 10
        // Initial indentation for each line in the generated string: generate small indentations less often so we can test unindent more often
        initialIndentations <- Gen.listOfN(nLines, Gen.frequency((10, Gen.oneOf(0 to 3)), (90, Gen.oneOf(4 to 10))))
        // indentation for each line
        paddings = initialIndentations.map(" " * _)
        // maxIndentation > initialIndentation so we cover cases where we try to unindent (negative n) beyond the left margin
        maxIndentation = maxInitialIndentation + 1
        n <- Gen.chooseNum(-maxIndentation, maxIndentation)
        // Generate lines interspersed with blank space lines
        lineGen  = Gen.frequency((70, Gen.alphaStr), (30, Gen.const(" ")))
        padLines = (lines: Seq[String]) => paddings.zip(lines).map { case (padding, s) => padding + s }
        str <- Gen.frequency(
                (
                  90,
                  Gen
                    .listOfN(nLines, lineGen)
                    .map(padLines)
                    .map(_.mkString("\n"))),
                (10, Gen.const(""))
              )
        indentFirstLine <- arbBool.arbitrary
      } yield (str, n, indentFirstLine)

    val genMessagePair: Gen[(String, String)] = for {
      errorKey     <- genNonEmptyAlphaStr
      errorMessage <- genNonEmptyAlphaStr
    } yield (errorKey, errorMessage)

    val genMessages: Gen[Map[String, String]] = for {
      n        <- Gen.chooseNum(1, 5)
      messages <- Gen.mapOfN(n, genMessagePair)
    } yield messages

    def genFormError(messageKey: String): Gen[FormError] =
      for {
        key   <- genNonEmptyAlphaStr
        nArgs <- Gen.chooseNum(1, 5)
        args  <- Gen.listOfN(nArgs, genNonEmptyAlphaStr)
      } yield FormError(key = s"$key", message = messageKey, args = args)

    def genFormErrorForSameFormField(messageKey: String): Gen[FormError] =
      for {
        nArgs <- Gen.chooseNum(1, 5)
        args  <- Gen.listOfN(nArgs, genNonEmptyAlphaStr)
      } yield FormError(key = "FieldKey1", message = messageKey, args = args)

    val genFormErrorsAndMessages: Gen[(immutable.Seq[FormError], String => Content, MessagesHelpers)] = for {
      contentConstructor <- Gen.oneOf(Gen.const(HtmlContent.apply(_: String)), Gen.const(Text.apply _))
      n                  <- Gen.chooseNum(1, 5)
      generatedMessages  <- genMessages
      messageKey         <- Gen.oneOf(generatedMessages.keys)
      errors             <- Gen.listOfN(n, genFormError(messageKey))
    } yield
      (errors, contentConstructor, new MessagesHelpers {
        override val messagesMap = Map("default" -> generatedMessages)
      })

    val genFormErrorsAndMessagesForSameFormField: Gen[(immutable.Seq[FormError], String => Content, MessagesHelpers)] =
      for {
        contentConstructor <- Gen.oneOf(Gen.const(HtmlContent.apply(_: String)), Gen.const(Text.apply _))
        n                  <- Gen.chooseNum(2, 5)
        generatedMessages  <- genMessages
        messageKey         <- Gen.oneOf(generatedMessages.keys)
        anotherMessageKey  <- Gen.oneOf(generatedMessages.keys)
        errors             <- Gen.listOfN(n, genFormErrorForSameFormField(messageKey))
        anotherSetOfErrors <- Gen.listOf(genFormErrorForSameFormField(anotherMessageKey))
      } yield
        (errors ++ anotherSetOfErrors, contentConstructor, new MessagesHelpers {
          override val messagesMap = Map("default" -> generatedMessages)
        })
  }
}
