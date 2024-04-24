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

package uk.gov.hmrc.support

import org.jsoup.Jsoup
import org.scalacheck.Prop.{forAll, secure}
import org.scalacheck.{Arbitrary, Properties, ShrinkLowPriority, Test}
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.{Json, OWrites}
import uk.gov.hmrc.helpers.views.TemplateDiff.templateDiffPath
import uk.gov.hmrc.helpers.views.{JsoupHelpers, PreProcessor, TemplateValidationException, TwirlRenderer}
import uk.gov.hmrc.support.Implicits._
import uk.gov.hmrc.support.ScalaCheckUtils.{ClassifyParams, classify}


import scala.collection.compat.immutable.LazyList
import scala.util.{Failure, Success}

/**
  * Base class for integration testing a Twirl template against the Nunjucks template rendering service
  */
abstract class TemplateIntegrationBaseSpec[T: OWrites: Arbitrary](
  componentName: String,
  seed: Option[String] = None
) extends Properties(componentName)
    with TemplateServiceClient
    with PreProcessor
    with TwirlRenderer[T]
    with ShrinkLowPriority
    with JsoupHelpers
    with ScalaFutures
    with IntegrationPatience
    with GuiceOneAppPerSuite {

  /**
    * [[LazyList]] of [[org.scalacheck.Prop.classify]] conditions to collect statistics on a property
    * Used to check the distribution of generated data
    *
    * @param templateParams
    * @return [[LazyList[ClassifyParams]] of arguments to [[org.scalacheck.Prop.classify]]
    */
  def classifiers(templateParams: T): LazyList[ClassifyParams] =
    LazyList.empty[ClassifyParams]

  override def overrideParameters(p: Test.Parameters): Test.Parameters =
    p.withMinSuccessfulTests(20)

  /**
    * Property that renders the Twirl template and uses the template rendering service to render the equivalent
    * Nunjucks template, and checks the outputs are equal. If provided, it uses the [[classifiers]] to collect statistics
    * about the template parameters to analyse the distribution of the generators.
    */
  propertyWithSeed(s"$componentName should render the same markup as the nunjucks renderer", seed) = forAll {
    templateParams: T =>
      classify(classifiers(templateParams))(secure {

        val response = render(componentName, templateParams)

        val nunJucksOutputHtml = response.futureValue.bodyAsString

        val tryRenderTwirl =
          render(templateParams)
            .transform(html => Success(html.body), f => Failure(new TemplateValidationException(f.getMessage)))

        tryRenderTwirl match {

          case Success(twirlOutputHtml)                      =>
            val preProcessedTwirlHtml    = compressHtml(normaliseHtml(twirlOutputHtml), maximumCompression = true)
            val preProcessedNunjucksHtml = compressHtml(normaliseHtml(nunJucksOutputHtml), maximumCompression = true)
            val prop                     = preProcessedTwirlHtml == preProcessedNunjucksHtml

            if (!prop) {
              reportDiff(
                preProcessedTwirlHtml = preProcessedTwirlHtml,
                preProcessedNunjucksHtml = preProcessedNunjucksHtml,
                templateParams = templateParams,
                templateParamsJson = Json.prettyPrint(Json.toJson(templateParams))
              )
            }

            prop
          case Failure(TemplateValidationException(message)) =>
            println(s"Failed to validate the parameters for the $componentName template")
            println(s"Exception: $message")
            println("Skipping property evaluation")

            true
          case Failure(exception)                            =>
            throw exception
        }
      })
  }

  private def reportDiff(
    preProcessedTwirlHtml: String,
    preProcessedNunjucksHtml: String,
    templateParams: T,
    templateParamsJson: String
  ): Unit = {

    val diffPath =
      templateDiffPath(
        twirlOutputHtml = preProcessedTwirlHtml,
        nunJucksOutputHtml = preProcessedNunjucksHtml,
        diffFilePrefix = Some(componentName)
      )

    println(s"Diff between Twirl and Nunjucks outputs (please open diff HTML file in a browser): file://$diffPath\n")

    println("-" * 80)
    println("Twirl")
    println("-" * 80)

    val formattedTwirlHtml = Jsoup.parseBodyFragment(preProcessedTwirlHtml).body.html
    println(s"\nTwirl output:\n$formattedTwirlHtml\n")

    println(s"\nparameters: ")
    pprint.pprintln(templateParams, width = 80, height = 500)

    println("-" * 80)
    println("Nunjucks")
    println("-" * 80)

    val formattedNunjucksHtml = Jsoup.parseBodyFragment(preProcessedNunjucksHtml).body.html
    println(s"\nNunjucks output:\n$formattedNunjucksHtml\n")

    println(s"\nparameters: ")
    println(templateParamsJson)
  }

}
