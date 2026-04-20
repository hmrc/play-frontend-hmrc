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
        s"$withoutQuery$queryString$separator$useServiceNavQueryParam$urlFragment"
      }
    }

  private def queryParamAlreadySetIn(queryString: String): Boolean =
    queryString
      .stripPrefix("?")
      .split("&")
      .exists(p => p == useServiceNavQueryParam || p.startsWith(s"$useServiceNavQueryParam="))
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
class ServiceNavCanBeControlledByQueryParam @Inject() () extends ServiceNavigationConfig {
  // To allow incremental migration of services while maintaining accessibility

  override def forceServiceNavigation(implicit request: RequestHeader): Boolean =
    request.queryString.contains(useServiceNavQueryParam)
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
