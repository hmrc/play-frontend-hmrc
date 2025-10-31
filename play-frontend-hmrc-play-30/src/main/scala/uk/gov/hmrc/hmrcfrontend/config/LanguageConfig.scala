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

@deprecated(
  "Use SupportedLanguagesConfig for languages, and retrieve fallback URL direct from configuration",
  "Since play-frontend-hmrc v12.20.0"
)
class LanguageConfig @Inject() (config: Configuration) {
  @deprecated("Use SupportedLanguagesConfig.en instead", "Since play-frontend-hmrc v12.20.0")
  val en: String = SupportedLanguagesConfig.en

  @deprecated("Use SupportedLanguagesConfig.cy instead", "Since play-frontend-hmrc v12.20.0")
  val cy: String = SupportedLanguagesConfig.cy

  @deprecated(
    "Use language.fallback.url direct from Configuration, rather than via LanguageConfig",
    "Since play-frontend-hmrc v12.20.0"
  )
  val fallbackURL: String = config.get[String]("language.fallback.url")
}

object SupportedLanguagesConfig {
  val en: String = "en"
  val cy: String = "cy"
}
