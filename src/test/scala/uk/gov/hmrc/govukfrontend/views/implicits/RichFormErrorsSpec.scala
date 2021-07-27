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

package uk.gov.hmrc.govukfrontend.views.implicits

import org.scalacheck.{Gen, ShrinkLowPriority}
import org.scalatest.OptionValues
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import play.api.data.FormError
import uk.gov.hmrc.govukfrontend.views.MessagesHelpers
import uk.gov.hmrc.govukfrontend.views.html.components.implicits._
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.errorsummary.ErrorLink

import java.util.UUID
import scala.collection.immutable
import scala.util.Random

class RichFormErrorsSpec
    extends AnyWordSpec
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
        forAll(genFormErrorsAndMessages) { case (formErrors, contentConstructor, messagesStub) =>
          import messagesStub.messages
          formErrors.asErrorLinks(contentConstructor).zipWithIndex.foreach { case (errorLink, i) =>
            errorLink shouldBe ErrorLink(
              href = Some(s"#${formErrors(i).key}"),
              content = contentConstructor(messagesStub.messages(formErrors(i).message, formErrors(i).args: _*))
            )
          }
        }
      }
    }

    "asErrorMessages" should {
      "convert FormErrors to ErrorMessage either text or html" in {
        forAll(genFormErrorsAndMessages) { case (formErrors, contentConstructor, messagesStub) =>
          import messagesStub.messages
          formErrors.asErrorMessages(contentConstructor).zipWithIndex.foreach { case (errorMessageParams, i) =>
            errorMessageParams shouldBe ErrorMessage(
              content = contentConstructor(messagesStub.messages(formErrors(i).message, formErrors(i).args: _*))
            )
          }
        }
      }
    }

    "asErrorMessage" when {
      "finds matching message" should {
        "convert FormErrors to ErrorMessage" in {
          forAll(genFormErrorsAndMessages) { case (formErrors, contentConstructor, messagesStub) =>
            import messagesStub.messages
            val i             = Random.nextInt(formErrors.length)
            val randomMessage = formErrors(i).message //select random message
            formErrors.asErrorMessage(contentConstructor, randomMessage).value shouldBe ErrorMessage(
              content = contentConstructor(messagesStub.messages(formErrors(i).message, formErrors(i).args: _*))
            )
          }
        }
      }

      "doesn't find matching message" should {
        "return None" in {
          forAll(genFormErrorsAndMessages) { case (formErrors, contentConstructor, messagesStub) =>
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
          forAll(genFormErrorsAndMessages) { case (formErrors, contentConstructor, messagesStub) =>
            import messagesStub.messages
            val i        = Random.nextInt(formErrors.length)
            val fieldKey = formErrors(i).key //select random message
            formErrors.asErrorMessageForField(contentConstructor, fieldKey).value shouldBe ErrorMessage(
              content = contentConstructor(messagesStub.messages(formErrors(i).message, formErrors(i).args: _*))
            )
          }
        }
      }

      "finds matching message for field" should {
        "convert FormErrors to ErrorMessage choosing first error when several errors are present for the same field" in {
          forAll(genFormErrorsAndMessagesForSameFormField) { case (formErrors, contentConstructor, messagesStub) =>
            import messagesStub.messages
            val i                  = Random.nextInt(formErrors.length)
            val fieldKey           = formErrors(i).key //select random message
            val firstErrorForField = formErrors.filter(_.key == fieldKey)(0)
            formErrors.asErrorMessageForField(contentConstructor, fieldKey).value shouldBe ErrorMessage(
              content =
                contentConstructor(messagesStub.messages(firstErrorForField.message, firstErrorForField.args: _*))
            )
          }
        }
      }

      "finds matching message for field" should {
        "return None" in {
          forAll(genFormErrorsAndMessagesForSameFormField) { case (formErrors, contentConstructor, messagesStub) =>
            import messagesStub.messages
            val i        = Random.nextInt(formErrors.length)
            val fieldKey = "" //select random message
            formErrors.asErrorMessageForField(contentConstructor, fieldKey) shouldBe None
          }
        }
      }
    }
  }

  object Generators {

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
    } yield (
      errors,
      contentConstructor,
      new MessagesHelpers {
        override val messagesMap = Map("default" -> generatedMessages)
      }
    )

    val genFormErrorsAndMessagesForSameFormField: Gen[(immutable.Seq[FormError], String => Content, MessagesHelpers)] =
      for {
        contentConstructor <- Gen.oneOf(Gen.const(HtmlContent.apply(_: String)), Gen.const(Text.apply _))
        n                  <- Gen.chooseNum(2, 5)
        generatedMessages  <- genMessages
        messageKey         <- Gen.oneOf(generatedMessages.keys)
        anotherMessageKey  <- Gen.oneOf(generatedMessages.keys)
        errors             <- Gen.listOfN(n, genFormErrorForSameFormField(messageKey))
        anotherSetOfErrors <- Gen.listOf(genFormErrorForSameFormField(anotherMessageKey))
      } yield (
        errors ++ anotherSetOfErrors,
        contentConstructor,
        new MessagesHelpers {
          override val messagesMap = Map("default" -> generatedMessages)
        }
      )
  }
}
