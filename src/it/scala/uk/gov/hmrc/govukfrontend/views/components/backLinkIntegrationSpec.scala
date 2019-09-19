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
import uk.gov.hmrc.govukfrontend.support.IntegrationSpec
import html.components._
import viewmodels.Generators._
import viewmodels.common

object backLinkIntegrationSpec extends Properties("backLink") with IntegrationSpec[BackLinkParams] {

  override def overrideParameters(p: Test.Parameters): Test.Parameters =
    p.withMinSuccessfulTests(100)

  override def govukComponentName: String = "govukBackLink"

  override implicit val writesTemplateParams: OWrites[BackLinkParams] = BackLinkParams.writes

  override implicit val arbTemplateParams: Arbitrary[BackLinkParams] = Arbitrary {
    for {
      // Gen.alphaStr generates empty string with very low freq so we tweak the distribution a little
      href       <- Gen.frequency((75, Gen.alphaStr), (25, Gen.const("")))
      nClasses   <- Gen.chooseNum(0, 5)
      classes    <- Gen.listOfN(nClasses, Gen.alphaStr.suchThat(_.trim.nonEmpty)).map(_.mkString(" "))
      attributes <- genAttrsMap
      content    <- genContent
    } yield BackLinkParams(href, classes, attributes, content)
  }

  override def twirlTemplateAsString(backLinkParams: BackLinkParams): String =
    BackLink(backLinkParams.href, backLinkParams.classes, backLinkParams.attributes)(backLinkParams.content).body

  override def classifiers(backLinkParams: BackLinkParams): Stream[(Boolean, Any, Any)] =
    (backLinkParams.href.isEmpty, "empty href", ()) #::
      (backLinkParams.classes.isEmpty, "empty classes", ()) #::
      (backLinkParams.attributes.isEmpty, "empty attributes", ()) #::
      (backLinkParams.content.nonEmpty && backLinkParams.content.isInstanceOf[HtmlContent], "non-empty Html", ()) #::
      (backLinkParams.content.nonEmpty && backLinkParams.content.isInstanceOf[Text], "non-empty Text", ()) #::
      (!backLinkParams.content.nonEmpty, "empty content", ()) #::
      Stream.empty[(Boolean, Any, Any)]
}

case class BackLinkParams(
  href: String,
  classes: String                 = "",
  attributes: Map[String, String] = Map.empty,
  content: common.Content         = Empty
)

object BackLinkParams {
  import JsonHelpers.writesContent

  implicit val writes: OWrites[BackLinkParams] = (
    (__ \ "href").write[String] and
      (__ \ "classes").write[String] and
      (__ \ "attributes").write[Map[String, String]] and
      writesContent()
  )(unlift(BackLinkParams.unapply))
}
