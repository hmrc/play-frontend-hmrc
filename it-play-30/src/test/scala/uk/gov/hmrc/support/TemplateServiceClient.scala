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

import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.play.PortNumber
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.{Json, OWrites}
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.Future

trait TemplateServiceClient extends AnyWordSpecLike with WSScalaTestClient with GuiceOneAppPerSuite {

  implicit val portNumber: PortNumber = PortNumber(3000)

  implicit lazy val wsClient: WSClient = app.injector.instanceOf[WSClient]

  protected val libraryName: String

  protected val libraryVersion: String

  /**
    * Render a hmrc-frontend component using the template service
    *
    * @param componentName the hmrc-frontend component name as documented in the template service
    * @param templateParams
    * @return [[WSResponse]] with the rendered component
    */
  def render[T: OWrites](
    componentName: String,
    templateParams: T,
    libraryVersion: String = libraryVersion
  ): Future[WSResponse] = {
    val componentOrTemplateUrl = if (componentName == "govukTemplate") {
      s"template/$libraryName/$libraryVersion/default"
    } else {
      s"component/$libraryName/$libraryVersion/$componentName"
    }
    wsUrl(componentOrTemplateUrl)
      .post(Json.toJson(templateParams))
  }
}
