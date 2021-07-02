# Standalone usage

This library is intended to be used as a transitive dependency via [play-frontend-hmrc](https://github.com/hmrc/play-frontend-hmrc) 
which handles SASS compilation, routing, etc. However, if you are unable to use that dependency, and wish to use 
`play-frontend-govuk` directly, please follow the steps below.

These standalone instructions make use of assets from a govuk-frontend webjar which is manually published to
www.webjars.org and a dependency of this library. However, in a future release we may remove this dependency, at which point
you would be required to either add `"org.webjars.npm" % "govuk-frontend" % "x.y.z"` to your SBT dependencies or supply 
your own pre-compiled assets, for example, via a CDN.

1.  Add [play-frontend-govuk](https://github.com/hmrc/play-frontend-govuk/releases) to your sbt dependencies:
    ```sbt
    libraryDependencies += "uk.gov.hmrc" %% "play-frontend-govuk" % "x.y.z-play-28"
    ```

    The library is cross-compiled for `Play 2.6`, `Play 2.7`, and `Play 2.8`.

1. Ensure you have your service name and URL defined in your messages files. For example,
    ```scala
    service.name = Any tax service
    service.homePageUrl = /any-tax-service
    ``` 

1.  Create a `scss` module `app/assets/stylesheets/application.scss` and import the govuk-frontend mixins and components:
    ```
    $govuk-assets-path: "/<your-project-context-root>/assets/lib/govuk-frontend/govuk/assets/";
    
    @import "lib/govuk-frontend/govuk/all";

    /* Add your custom styles here */
    ```
    
    On MDTP, `/<your-project-context-root>` should be the route defined in your `conf/prod.routes`
    file.
    
1. Add [sbt-sassify](https://github.com/irundaia/sbt-sassify) to `project/plugins.sbt`

1. Add routing for static assets used in GovukHeader and GovukTemplate
    (for example the favicon, touch icons and crown icon) in app.routes:
    ```scala
    ->         /govuk-frontend                      govuk.Routes
    ```

1. Add routing for all other static assets including the compiled Javascript, images and fonts provided as part
   of the govuk-frontend webjar:
    ```
    GET        /assets/*file                        controllers.Assets.versioned(path = "/public", file: Asset)
    ```

1. Create a Javascript module `app/assets/javascripts/application.js` that initialises the govuk-frontend components:
   ```javascript
       window.GOVUKFrontend.initAll();
   ```

1. Create a head template to include the compiled govuk-frontend CSS:
    ```scala
    @this()
    
    @()
    <link href='@controllers.routes.Assets.versioned("stylesheets/application.css")' media="all" rel="stylesheet" type="text/css" />
    ```

1. Create a scripts template to include the govuk-frontend javascript assets:
    ```scala
    @import views.html.helper.CSPNonce
    
    @this()
    
    @()(implicit request: RequestHeader)
    <script @{CSPNonce.attr} src='@routes.Assets.versioned("lib/govuk-frontend/govuk/all.js")'></script>
    <script @{CSPNonce.attr} src='@routes.Assets.versioned("javascripts/application.js")'></script>
    ```

1.  Create a layout template that can be re-used by the individual pages within your microservice:

    ```scala
    @import views.html.helper.CSPNonce

    @this(
      govukLayout: GovukLayout,
      head: Head,
      scripts: Scripts
    )

    @(pageTitle: Option[String] = None,
      beforeContentBlock: Option[Html] = None
    )(contentBlock: Html)(implicit request: Request[_], messages: Messages)

    @govukLayout(
      pageTitle = pageTitle,
      headBlock = Some(head()),
      beforeContentBlock = beforeContentBlock,
      footerItems = Seq(FooterItem(href = Some("https://www.gov.uk/help"), text = Some("Help using GOV.UK"))),
      bodyEndBlock = Some(scripts()),
      cspNonce = CSPNonce.get
    )(contentBlock)      
    ```

1. Optionally, add TwirlKeys.templateImports in build.sbt:
    ```sbt
        TwirlKeys.templateImports ++= Seq(
          "uk.gov.hmrc.govukfrontend.views.html.components._",
          "uk.gov.hmrc.govukfrontend.views.html.helpers._"
        )
    ```
