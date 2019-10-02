/*
 * Copyright 2019 HM Revenue & Customs
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
package components

import org.scalacheck._
import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.support.TemplateIntegrationSpec
import html.components._
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.support.ScalaCheckUtils.ClassifyParams
import viewmodels.Generators._
import scala.util.Try

object backLinkTemplateIntegrationSpec extends TemplateIntegrationSpec[BackLinkParams]("govukBackLink") {

  override def overrideParameters(p: Test.Parameters): Test.Parameters =
    p.withMinSuccessfulTests(50)

  override def twirlRender(backLinkParams: BackLinkParams): Try[HtmlFormat.Appendable] =
    Try(
      BackLink(href = backLinkParams.href, classes = backLinkParams.classes, attributes = backLinkParams.attributes)(
        backLinkParams.content)
    )

  override def classifiers(backLinkParams: BackLinkParams): Stream[ClassifyParams] =
    (backLinkParams.href.isEmpty, "empty href", "non-empty href") #::
      (backLinkParams.classes.isEmpty, "empty classes", "non-empty classes") #::
      (backLinkParams.attributes.isEmpty, "empty attributes", "non-empty attributes") #::
      (backLinkParams.content.nonEmpty && backLinkParams.content.isInstanceOf[HtmlContent], "non-empty Html", ()) #::
      (backLinkParams.content.nonEmpty && backLinkParams.content.isInstanceOf[Text], "non-empty Text", ()) #::
      (!backLinkParams.content.nonEmpty, "empty content", ()) #::
      Stream.empty[ClassifyParams]
}

case class BackLinkParams(
  href: String,
  classes: String                 = "",
  attributes: Map[String, String] = Map.empty,
  content: Content                = Empty
)

object BackLinkParams {
  import JsonHelpers.writesContent

  implicit val writes: OWrites[BackLinkParams] = (
    (__ \ "href").write[String] and
      (__ \ "classes").write[String] and
      (__ \ "attributes").write[Map[String, String]] and
      writesContent
  )(unlift(BackLinkParams.unapply))

  implicit val arbTemplateParams: Arbitrary[BackLinkParams] = Arbitrary {
    for {
      href       <- genAlphaStrOftenEmpty()
      classes    <- genClasses()
      attributes <- genAttributes()
      content    <- genContent
    } yield BackLinkParams(href, classes, attributes, content)
  }
}
