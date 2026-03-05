/*
 * Copyright 2025 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.config

import play.api.Configuration
import play.api.mvc.RequestHeader
import play.api.inject._
import play.api.libs.typedmap.TypedKey
import ServiceNavCanBeControlledByRequestAttr.UseServiceNav
import ServiceNavCanBeControlledByQueryParam.useServiceNavQueryParam
import ServiceNavCanBeControlledByConfig.useServiceNavConfigKey

import java.net.URI
import scala.util.Try
import javax.inject.{Inject, Singleton}

trait ServiceNavigationConfig {
  def propagateViaQueryParam(href: String)(implicit request: RequestHeader): String = {
    def queryParamAlreadySet(uri: URI): Boolean =
      Option(uri.getRawQuery).exists(_.split("&").exists(_.startsWith(s"$useServiceNavQueryParam=")))

    Try(new URI(href))
      .collect {
        case uri if !queryParamAlreadySet(uri) =>
          val queryParam = s"$useServiceNavQueryParam=$forceServiceNavigation"
          new URI(
            uri.getScheme,
            uri.getRawAuthority,
            uri.getRawPath,
            uri.getRawQuery match {
              case null | ""   => queryParam
              case queryString => s"$queryString&$queryParam"
            },
            uri.getRawFragment
          ).toString
      }
      .getOrElse(href)
  }

  def forceServiceNavigation(implicit request: RequestHeader): Boolean // if some service nav not provided
}

@Singleton
class ServiceNavCanBeControlledByRequestAttr @Inject() (config: Configuration) extends ServiceNavigationConfig {
  // To allow incremental migration of services while maintaining accessibility

  private val determinedByConfig: Boolean = config.get[Boolean](useServiceNavConfigKey)

  override def forceServiceNavigation(implicit request: RequestHeader): Boolean =
    request.attrs.get(UseServiceNav).getOrElse(determinedByConfig)
}

object ServiceNavCanBeControlledByRequestAttr {
  val UseServiceNav: TypedKey[Boolean] = TypedKey[Boolean]("UseServiceNav")
}

@Singleton
class ServiceNavCanBeControlledByQueryParam @Inject() (config: Configuration) extends ServiceNavigationConfig {
  // To allow incremental migration of services while maintaining accessibility

  private val determinedByConfig: Boolean = config.get[Boolean](useServiceNavConfigKey)

  override def forceServiceNavigation(implicit request: RequestHeader): Boolean =
    request.queryString
      .get(useServiceNavQueryParam)
      .map(!_.contains("false"))
      .getOrElse(determinedByConfig)
}

object ServiceNavCanBeControlledByQueryParam {
  val useServiceNavQueryParam = "useServiceNav"
}

@Singleton
class ServiceNavCanBeControlledByConfig @Inject() (config: Configuration) extends ServiceNavigationConfig {
  // To make it easier to coordinate releases during migration

  private val determinedByConfig = config.get[Boolean](useServiceNavConfigKey)

  override def forceServiceNavigation(implicit request: RequestHeader): Boolean = determinedByConfig
}

object ServiceNavCanBeControlledByConfig {
  val useServiceNavConfigKey = "play-frontend-hmrc.forceServiceNavigation"
}

class ServiceNavCanBeControlledByRequestAttrModule
    extends SimpleModule(
      bind[ServiceNavigationConfig].to[ServiceNavCanBeControlledByRequestAttr]
    )

class ServiceNavCanBeControlledByQueryParamModule
    extends SimpleModule(
      bind[ServiceNavigationConfig].to[ServiceNavCanBeControlledByQueryParam]
    )

class DefaultServiceNavConfigModule
    extends SimpleModule(
      bind[ServiceNavigationConfig].to[ServiceNavCanBeControlledByConfig]
    )
