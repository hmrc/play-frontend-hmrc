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
import ServiceNavigationCanBeControlledByRequestAttr.UseServiceNavigation
import ServiceNavigationCanBeControlledByQueryParam.useServiceNavigationQueryParam
import ServiceNavigationCanBeControlledByConfig.useServiceNavigationConfigKey

import javax.inject.{Inject, Singleton}

trait ServiceNavigationConfig {
  def forceServiceNavigation(implicit request: RequestHeader): Boolean

  def propagateViaQueryParam(url: String)(implicit request: RequestHeader): String =
    if (!forceServiceNavigation) url
    else {
      val (urlWithQuery, urlFragment) = url.span(_ != '#')
      val (withoutQuery, queryString) = urlWithQuery.span(_ != '?')
      if (queryParamAlreadySetIn(queryString)) url
      else {
        val separator = if (queryString.isEmpty) "?" else "&"
        s"$withoutQuery$queryString$separator$useServiceNavigationQueryParam$urlFragment"
      }
    }

  private def queryParamAlreadySetIn(queryString: String): Boolean =
    queryString
      .stripPrefix("?")
      .split("&")
      .exists(p => p == useServiceNavigationQueryParam || p.startsWith(s"$useServiceNavigationQueryParam="))
}

@Singleton
class ServiceNavigationCanBeControlledByRequestAttr @Inject() (config: Configuration) extends ServiceNavigationConfig {
  // To allow incremental migration of services while maintaining accessibility

  private val determinedByConfig: Boolean = config.get[Boolean](useServiceNavigationConfigKey)

  override def forceServiceNavigation(implicit request: RequestHeader): Boolean =
    request.attrs.get(UseServiceNavigation).getOrElse(determinedByConfig)
}

object ServiceNavigationCanBeControlledByRequestAttr {
  val UseServiceNavigation: TypedKey[Boolean] = TypedKey[Boolean]("UseServiceNavigation")
}

@Singleton
class ServiceNavigationCanBeControlledByQueryParam @Inject() () extends ServiceNavigationConfig {
  // To allow incremental migration of services while maintaining accessibility

  override def forceServiceNavigation(implicit request: RequestHeader): Boolean =
    request.queryString.contains(useServiceNavigationQueryParam)
}

object ServiceNavigationCanBeControlledByQueryParam {
  val useServiceNavigationQueryParam = "useServiceNavigation"
}

@Singleton
class ServiceNavigationCanBeControlledByConfig @Inject() (config: Configuration) extends ServiceNavigationConfig {
  // To make it easier to coordinate releases during migration

  private val determinedByConfig = config.get[Boolean](useServiceNavigationConfigKey)

  override def forceServiceNavigation(implicit request: RequestHeader): Boolean = determinedByConfig
}

object ServiceNavigationCanBeControlledByConfig {
  val useServiceNavigationConfigKey = "play-frontend-hmrc.forceServiceNavigation"
}

class ServiceNavigationCanBeControlledByRequestAttrModule
    extends SimpleModule(
      bind[ServiceNavigationConfig].to[ServiceNavigationCanBeControlledByRequestAttr]
    )

class ServiceNavigationCanBeControlledByQueryParamModule
    extends SimpleModule(
      bind[ServiceNavigationConfig].to[ServiceNavigationCanBeControlledByQueryParam]
    )

class DefaultServiceNavigationConfigModule
    extends SimpleModule(
      bind[ServiceNavigationConfig].to[ServiceNavigationCanBeControlledByConfig]
    )
