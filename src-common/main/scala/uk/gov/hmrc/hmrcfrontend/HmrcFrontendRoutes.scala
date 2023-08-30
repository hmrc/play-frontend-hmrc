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

package hmrcfrontend

import javax.inject.Inject

import play.api.routing.{HandlerDef, Router}
import play.api.routing.sird._
import play.core.routing.GeneratedRouter
import uk.gov.hmrc.hmrcfrontend.controllers.{Assets, KeepAliveController, LanguageController}

class Routes @Inject()(
  val errorHandler: play.api.http.HttpErrorHandler,
  assets: Assets,
  keepAliveController: KeepAliveController,
  languageController: LanguageController,
  prefix: String
) extends GeneratedRouter {

  override def documentation: Seq[(String, String, String)] =
    Seq.empty

  override def withPrefix(addPrefix: String): Router = {
    RoutesPrefix.setPrefix(prefix + addPrefix)
    new Routes(errorHandler, assets, keepAliveController, languageController, prefix + addPrefix)
  }

  private[this] val defaultPrefix: String =
    if (this.prefix.endsWith("/")) "" else "/"

  private val assetsPath =
    "/public/lib/hmrc-frontend/hmrc"

  override def routes: Router.Routes = {


    // TODO respect prefix - e.g.
    // PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
    case GET(p"/assets/$file") =>
      // Audit/Auth/Logging filters rely on HandlerDef configuration (controller)
      // in order to attach it to the request, we must extend GeneratedRouter and use the invoker tricks..
      createInvoker(
        fakeCall   = assets.at(fakeValue[String], fakeValue[String]),
        handlerDef = HandlerDef(
          classLoader    = this.getClass.getClassLoader,
          routerPackage  = "hmrcfrontend",
          controller     = "uk.gov.hmrc.hmrcfrontend.controllers.Assets",
          method         = "at",
          parameterTypes = Seq(classOf[String], classOf[String]),
          verb           = "GET",
          path           = this.prefix + "assets/$file<.+>",
          comments       = "",
          modifiers      = Seq.empty
        )
      ).call(assets.at(assetsPath, file))

      //PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("keep-alive")))
    case GET(p"/keep-alive") =>
      createInvoker(
        fakeCall   = keepAliveController.keepAlive,
        handlerDef = HandlerDef(
          classLoader    = this.getClass.getClassLoader,
          routerPackage  = "hmrcfrontend",
          controller     = "uk.gov.hmrc.hmrcfrontend.controllers.KeepAliveController",
          method         = "keepAlive",
          parameterTypes = Seq.empty,
          verb           = "GET",
          path           = this.prefix + "keep-alive",
          comments       = "",
          modifiers      = Seq.empty
        )
      ).call(keepAliveController.keepAlive)


      //PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("language/"), DynamicPart("lang", """[^/]+""",true)))
    case GET(p"/language/$lang") =>
      createInvoker(
        fakeCall   = languageController.switchToLanguage(fakeValue[String]),
        handlerDef = HandlerDef(
          classLoader    = this.getClass.getClassLoader,
          routerPackage  = "hmrcfrontend",
          controller     = "uk.gov.hmrc.hmrcfrontend.controllers.LanguageController",
          method         = "switchToLanguage",
          parameterTypes = Seq(classOf[String]),
          verb           = "GET",
          path           = this.prefix + "language/$lang<[^/]+>",
          comments       = "",
          modifiers      = Seq.empty
        )
      ).call(languageController.switchToLanguage(lang))
  }
}

object RoutesPrefix {
  private var _prefix: String =
    "/"

  def setPrefix(p: String): Unit =
    _prefix = p

  def prefix: String =
    _prefix

  val byNamePrefix: Function0[String] =
    () => prefix
}
