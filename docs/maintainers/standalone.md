# play-frontend-govuk: Standalone usage

This library is intended to be use as a transitive dependency via [play-frontend-hmrc](https://github.com/hmrc/play-frontend-hmrc) 
which handles SASS compilation, routing, etc. However, if you are unable to use that dependency, and wish to use 
`play-frontend-govuk` directly, please follow the steps below.

## Adding to a project directly 

1.  Add [Twirl](https://github.com/hmrc/play-frontend-govuk/releases) library in the App dependencies.
    ```sbt
    //build.sbt for Play 2.6
    libraryDependencies += "uk.gov.hmrc" %% "play-frontend-govuk" % "x.y.z-play-26"
    //or Play 2.7
    libraryDependencies += "uk.gov.hmrc" %% "play-frontend-govuk" % "x.y.z-play-27"
    ```

1.  Add SASS assets to app/assets/stylesheets in application.scss to inherit / extend GovUk style assets / elements, e.g.:
    ```
    $govuk-assets-path: "/<your-project-context-root>/assets/lib/govuk-frontend/govuk/assets/";
    
    @import "lib/govuk-frontend/govuk/all";
    
    .app-reference-number {
      display: block;
      font-weight: bold;
    }
    ```
Ensure [sbt-sassify](https://github.com/irundaia/sbt-sassify) is added to plugins.sbt

1.  Add routing for static assets used in GovukHeader and GovukTemplate
    (for example the favicon, touch icons and crown icon) in app.routes:
    ```scala
    ->         /govuk-frontend                      govuk.Routes
    ```

1. Add routing for all other static assets including the compiled Javascript, images and fonts provided as part
   of the govuk-frontend webjar:
    ```
    GET        /assets/*file                        controllers.Assets.versioned(path = "/public", file: Asset)
    ```

1.  Add TwirlKeys.templateImports in build.sbt:
    ```sbt
        TwirlKeys.templateImports ++= Seq(
          "uk.gov.hmrc.govukfrontend.views.html.components._",
          "uk.gov.hmrc.govukfrontend.views.html.helpers._"
        )
    ```

1.  Use GovukLayout from library to create standard views out of the box
    ```scala
    @govukLayout(
        pageTitle = pageTitle,
        headBlock = Some(head()),
        beforeContentBlock = beforeContentBlock,
        footerItems = Seq(FooterItem(href = Some("https://govuk-prototype-kit.herokuapp.com/"), text = Some("GOV.UK Prototype Kit v9.1.0"))),
        bodyEndBlock = Some(scripts()))(contentBlock)
    ```
