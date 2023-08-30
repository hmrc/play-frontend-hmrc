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

// @GENERATOR:play-routes-compiler
// @SOURCE:src-common/main/resources/hmrcfrontend.routes

package uk.gov.hmrc.hmrcfrontend.controllers

import hmrcfrontend.RoutesPrefix;
import play.api.mvc.Call
import play.api.routing.JavaScriptReverseRoute

object routes {
  val Assets: uk.gov.hmrc.hmrcfrontend.controllers.ReverseAssets =
    new uk.gov.hmrc.hmrcfrontend.controllers.ReverseAssets(RoutesPrefix.byNamePrefix())

  val KeepAliveController: uk.gov.hmrc.hmrcfrontend.controllers.ReverseKeepAliveController =
    new uk.gov.hmrc.hmrcfrontend.controllers.ReverseKeepAliveController(RoutesPrefix.byNamePrefix())

  val LanguageController: uk.gov.hmrc.hmrcfrontend.controllers.ReverseLanguageController =
    new uk.gov.hmrc.hmrcfrontend.controllers.ReverseLanguageController(RoutesPrefix.byNamePrefix())

  object javascript {
    val Assets: uk.gov.hmrc.hmrcfrontend.controllers.javascript.ReverseAssets =
      new uk.gov.hmrc.hmrcfrontend.controllers.javascript.ReverseAssets(RoutesPrefix.byNamePrefix())

    val KeepAliveController: uk.gov.hmrc.hmrcfrontend.controllers.javascript.ReverseKeepAliveController =
      new uk.gov.hmrc.hmrcfrontend.controllers.javascript.ReverseKeepAliveController(RoutesPrefix.byNamePrefix())

    val LanguageController: uk.gov.hmrc.hmrcfrontend.controllers.javascript.ReverseLanguageController =
      new uk.gov.hmrc.hmrcfrontend.controllers.javascript.ReverseLanguageController(RoutesPrefix.byNamePrefix())
  }
}

class ReverseAssets(_prefix: => String) {
  def _defaultPrefix: String =
    if (_prefix.endsWith("/")) "" else "/"

  def at(file:String): Call = {
    implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public/lib/hmrc-frontend/hmrc"))); _rrc
    Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[String]].unbind("file", file))
  }
}

class ReverseKeepAliveController(_prefix: => String) {
  def _defaultPrefix: String =
    if (_prefix.endsWith("/")) "" else "/"

  def keepAlive: Call =
    Call("GET", _prefix + { _defaultPrefix } + "keep-alive")
}

class ReverseLanguageController(_prefix: => String) {
  def _defaultPrefix: String =
    if (_prefix.endsWith("/")) "" else "/"

  def switchToLanguage(lang:String): Call =
    Call("GET", _prefix + { _defaultPrefix } + "language/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("lang", lang)))
}

package javascript {
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String =
      if (_prefix.endsWith("/")) "" else "/"

    def at: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "uk.gov.hmrc.hmrcfrontend.controllers.Assets.at",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  }

  class ReverseKeepAliveController(_prefix: => String) {
    def _defaultPrefix: String =
      if (_prefix.endsWith("/")) "" else "/"

    def keepAlive: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "uk.gov.hmrc.hmrcfrontend.controllers.KeepAliveController.keepAlive",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "keep-alive"})
        }
      """
    )
  }

  class ReverseLanguageController(_prefix: => String) {
    def _defaultPrefix: String =
      if (_prefix.endsWith("/")) "" else "/"

    def switchToLanguage: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "uk.gov.hmrc.hmrcfrontend.controllers.LanguageController.switchToLanguage",
      """
        function(lang0) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "language/" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("lang", lang0))})
        }
      """
    )
  }
}
