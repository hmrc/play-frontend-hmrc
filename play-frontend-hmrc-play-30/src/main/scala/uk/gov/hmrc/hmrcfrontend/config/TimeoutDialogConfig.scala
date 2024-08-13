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

package uk.gov.hmrc.hmrcfrontend.config

import javax.inject.Inject
import play.api.Configuration

class TimeoutDialogConfig @Inject() (configuration: Configuration) {
  def timeoutInSeconds: Int =
    configuration
      .getOptional[Int]("session.timeoutSeconds")
      .orElse(configuration.getOptional[scala.concurrent.duration.Duration]("session.timeout").map(_.toSeconds.toInt))
      .getOrElse(configuration.get[Int]("hmrc-timeout-dialog.defaultTimeoutInSeconds"))

  def countdownInSeconds: Int = configuration.get[Int]("hmrc-timeout-dialog.defaultCountdownInSeconds")

  def enableSynchroniseTabs: Boolean =
    configuration.get[Boolean]("hmrc-timeout-dialog.enableSynchroniseTabs")
}
