# play-frontend-hmrc: SASS compilation

This library manages SASS compilation for you. However, should you wish to add your own for any reason using 
`sbt-sassify`, please read the steps below.

1.  Import the required styles in your `app/assets/stylesheets/application.scss` file:
    ```scss
    @import "lib/govuk-frontend/govuk/base";
    
    // Add your custom styles here
    ```
Ensure [sbt-sassify](https://github.com/irundaia/sbt-sassify) is added to plugins.sbt

1. Ensure you have the correct routing for all other static assets including the compiled Javascript and images provided
   by the hmrc-frontend webjar:
    ```
    GET        /assets/*file                        controllers.Assets.versioned(path = "/public", file: Asset)
    ```
   
1. You will need to create a custom `headBlock` template to include your styles. The content of the file might look 
   something like this:
```html
@this()

@()
<link href='@controllers.routes.Assets.versioned("stylesheets/application.css")' media="all" rel="stylesheet" type="text/css" />
```

1. This can then be passed into the `hmrcHead` as follows:
```html
@import uk.gov.hmrc.hmrcfrontend.views.html.helpers.{HmrcStandardFooter, HmrcStandardHeader, HmrcHead, HmrcScripts}

@this(
    govukLayout: GovukLayout,
    hmrcHead: HmrcHead,
    hmrcStandardHeader: HmrcStandardHeader,
    hmrcStandardFooter: HmrcStandardFooter,
    hmrcScripts: HmrcScripts,
    // This is your custom headBlock
    headBlock: HeadBlock
)

@(pageTitle: String)(contentBlock: Html)(implicit request: RequestHeader, messages: Messages)

@govukLayout(
    pageTitle = Some(pageTitle),
    headBlock = Some(hmrcHead(headBlock = Some(headBlock()),
    headerBlock = Some(hmrcStandardHeader(
      serviceUrl = Some(controllers.routes.IndexController.index().url)
    )),
    scriptsBlock = Some(hmrcScripts()),
    footerBlock = Some(hmrcStandardFooter())
)(contentBlock)
```
