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

package uk.gov.hmrc.hmrcfrontend.config

import javax.inject.Inject
import play.api.Configuration

class TrackingConsentConfig @Inject()(config: Configuration) {
  val platformHost: Option[String] =
    config.getOptional[String]("platform.frontend.host")
  val trackingConsentHost: Option[String] =
    platformHost.map(_ => "").orElse(config.getOptional[String]("tracking-consent-frontend.host"))
  val trackingConsentPath: Option[String] =
    config.getOptional[String]("tracking-consent-frontend.path")
  val optimizelyGtmPath: Option[String] =
    config.getOptional[String]("tracking-consent-frontend.optimizely-gtm-path")
  val gtmContainer: Option[String] = config.getOptional[String]("tracking-consent-frontend.gtm.container")

  def trackingUrl(): Option[String] =
    for {
      host <- trackingConsentHost
      path <- trackingConsentPath
      _    <- gtmContainer
    } yield {
      s"$host$path"
    }

  def optimizelyUrl: Option[String] = {
    for {
      baseUrl   <- config.getOptional[String]("optimizely.url")
      projectId <- config.getOptional[String]("optimizely.projectId")
    } yield {
      s"$baseUrl$projectId.js"
    }
  }

  def optimizelyGtmUrl(): Option[String] =
    for {
      host <- trackingConsentHost
      path <- optimizelyGtmPath
      _    <- gtmContainer
    } yield {
      s"$host$path"
    }
}
