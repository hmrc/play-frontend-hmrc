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

package uk.gov.hmrc.play.language

import play.api.i18n.{I18nSupport, Lang}
import play.api.mvc._

import java.net.URI
import scala.util.Try

/**
  * LanguageController that switches the language of the current web application.
  *
  * This trait provides a means of switching the current language and redirecting the user
  * back to their original location. It expects a fallbackURL to be defined when implemented.
  * It also expects a languageMap to be defined, this provides a way of mapping strings to Lang objects.
  */
abstract class LanguageController(languageUtils: LanguageUtils, cc: ControllerComponents)
    extends AbstractController(cc)
    with I18nSupport {

  /** A URL to fallback to if there is no referrer found in the request header * */
  protected def fallbackURL: String

  /** A map from a String to Lang object * */
  protected def languageMap: Map[String, Lang]

  private val SwitchIndicatorKey       = "switching-language"
  private val FlashWithSwitchIndicator = Flash(Map(SwitchIndicatorKey -> "true"))

  /**
    * A public interface to switch to a new language.
    *
    * The language must be defined within the language map else the current language will be used.
    *
    * This function expects a language string as a parameter and will use this to switch
    * the current application language. This function expects a referrer value within
    * the request header and will redirect the user back to that value.
    * If it is not set then the redirect will be to the fallbackURL.
    *
    * The returned Redirect object will also contain a flashing parameter which can be
    * detected by controllers in order to show different behaviour if wanted.
    *
    * @param language - The language string to switch to.
    * @return Redirect to referrer or fallbackURL, with new language. Or fallbackURL with default lang.
    */

  def switchToLanguage(language: String): Action[AnyContent] = Action { implicit request =>
    val enabled: Boolean = languageMap.get(language).exists(languageUtils.isLangAvailable)
    val lang: Lang       =
      if (enabled) languageMap.getOrElse(language, languageUtils.getCurrentLang)
      else languageUtils.getCurrentLang

    val redirectURL: String = request.headers
      .get(REFERER)
      .flatMap(asRelativeUrl)
      .getOrElse(fallbackURL)
    Redirect(redirectURL).withLang(Lang.apply(lang.code)).flashing(FlashWithSwitchIndicator)
  }

  private def asRelativeUrl(url: String): Option[String] =
    for {
      uri      <- Try(new URI(url)).toOption
      path     <- Option(uri.getRawPath).filterNot(_.isEmpty)
      query    <- Option(uri.getRawQuery).map("?" + _).orElse(Some(""))
      fragment <- Option(uri.getRawFragment).map("#" + _).orElse(Some(""))
    } yield s"$path$query$fragment"

}
