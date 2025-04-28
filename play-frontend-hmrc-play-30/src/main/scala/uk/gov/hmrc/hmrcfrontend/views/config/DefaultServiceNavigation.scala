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

package uk.gov.hmrc.hmrcfrontend.views.config

import play.api.Configuration
import play.api.mvc.RequestHeader
import uk.gov.hmrc.govukfrontend.views.viewmodels.servicenavigation.ServiceNavigation

import javax.inject.Inject

trait DefaultServiceNavigation {
  def get(implicit rh: RequestHeader): Option[ServiceNavigation]
}

// Took this from Oscar's draft PRs
// Have method to return ServiceNavigation if the feature flag is enabled.
// Can be injected by Service Teams to avoid creating Service Navigation manually.
// We cannot pass any arguments to the ServiceNavigation at this point, because it's used as default value for argument in view.
class DefaultServiceNavigationFromConfig @Inject() (configuration: Configuration) extends DefaultServiceNavigation {
  private val enabledThroughConfig =
    configuration.get[Boolean]("play-frontend-hmrc.enable-default-service-navigation")

  override def get(implicit rh: RequestHeader): Option[ServiceNavigation] = {
    // we just have it as ServiceNavigation() because at the moment the idea
    // is that HmrcHeader will use what is supplied as a default and into it
    // put the language select links into a slot and pass in the service name
    // so this doesn't actually need to pre-populate anything
    Option.when(enabledThroughConfig)(ServiceNavigation())
  }
}
