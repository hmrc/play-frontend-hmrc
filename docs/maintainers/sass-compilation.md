# play-frontend-hmrc: SASS compilation

This library manages SASS compilation for you. However, should you wish to add your own for any reason using
`sbt-sassify`, please read the steps below.

1. Import the required styles in your `app/assets/stylesheets/application.scss` file:
   ```scss
   $govuk-include-default-font-face: false;
   @import "lib/govuk-frontend/govuk/base";
   
   // Add your custom styles here
   ```
   > [!WARNING]
   > The use of some GOV.UK styles can cause external assets (like fonts and images) to be downloaded, the default path
   > these will be requested from is /assets/ which will not work for a tax service hosted on MDTP.
   >
   > A correct path, which can be set using the variable $govuk-assets-path could
   > be `/your-service/assets/lib/govuk-frontend/govuk/assets/` (if your service has an assets controller mounted at
   > `/assets` serving up your /public directory as described in step 3 below.)
   >
   > However, if you are using one of the HMRC layout components, or you are using the GovukLayout component with the
   > HmrcHead component, then you shouldn't need to load the GOV.UK fonts or any of the images, because they are added
   > to the page for you separately.
   >
   > In this case, you must set `$govuk-include-default-font-face: false;` at the top of your stylesheet to stop the
   > GOV.UK styles from trying to load the fonts automatically. If you don't, then every time someone loads your
   > stylesheet they will attempt to download the fonts from `/assets/` which will cause several 404s.

1. Add [sbt-sassify](https://github.com/irundaia/sbt-sassify) to your `/project/plugins.sbt` file.

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

1. This can then be passed into `hmrcLayout` or `govukLayout`
   as `headBlock = Some(hmrcHead(headBlock = Some(headBlock())`
