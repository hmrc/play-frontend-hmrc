# play-frontend-hmrc

This library provides a number of HMRC-specific Twirl components and helpers for Scala frontend microservices running on
MDTP. It is organised into two packages:

1. components - a set of Twirl templates providing a direct port of the Nunjucks components from 
[hmrc/hmrc-frontend](https://www.github.com/hmrc/hmrc-frontend)
1. helpers
    1. wrappers designed to make using the hmrc-frontend components more straightforward and idiomatic in Scala/Play
    1. a collection of markup snippets required by MDTP microservices

This library complements and should be used in conjunction with
[play-frontend-govuk](https://github.com/hmrc/play-frontend-govuk/)

## Table of Contents

- [Getting started](#getting-started)
- [Play framework compatibility](#play-framework-compatibility)
- [Accessibility statements](#accessibility-statements)
- [Integrating with tracking consent](#integrating-with-tracking-consent)
- [Warning users before timing them out](#warning-users-before-timing-them-out)
- [Getting help](#getting-help)
- [Useful links](#useful-links)
- [Owning team readme](#owning-team-readme)
- [License](#license)

## Getting started
1.  Add the version of [play-frontend-hmrc](https://github.com/hmrc/play-frontend-hmrc/releases) specific to your Play version
in your `project/AppDependencies.scala` file. For example,
    ```sbt
    libraryDependencies += "uk.gov.hmrc" %% "play-frontend-hmrc" % "x.y.z-play-27"
    ```
    
    The library is cross-compiled for Play 2.5, 2.6 and 2.7.

1.  Import the required styles in your `app/assets/stylesheets/application.scss` file:
    ```scss
    $govuk-assets-path: "/<route-to-your-service>/assets/lib/govuk-frontend/govuk/assets/";
    $hmrc-assets-path: "/<route-to-your-service>/assets/lib/hmrc-frontend/hmrc/";
    
    @import "lib/govuk-frontend/govuk/all";
    @import "lib/hmrc-frontend/hmrc/all";
    ```

1.  Add routes for hmrc-frontend assets in `conf/app.routes`:
    ```scala
    ->         /govuk-frontend                     govuk.Routes
    ->         /hmrc-frontend                      hmrcfrontend.Routes
    ```

1. Ensure you have the correct routing for all other static assets including the compiled Javascript and images provided 
by the hmrc-frontend webjar:
    ```
    GET        /assets/*file                        controllers.Assets.versioned(path = "/public", file: Asset)
    ```

1. Update your scripts template to include the hmrc-frontend javascript assets:
    ```scala
    @this()
    
    @()
    <script src='@routes.Assets.versioned("lib/govuk-frontend/govuk/all.js")'></script>
    <script src='@routes.Assets.versioned("lib/hmrc-frontend/hmrc/all.js")'></script>
    <script src='@routes.Assets.versioned("javascripts/application.js")'></script>
    ```
   
    The scripts template generates the markup passed to the `scriptsBlock` parameter of the `govukLayout` component
    and injected immediately before the BODY end tag.

1. Initialise the hmrc-frontend components in your `app/assets/javascripts/application.js` file after
initialising govuk-frontend:
    ```js
    window.GOVUKFrontend.initAll();
    window.HMRCFrontend.initAll();
    ```
   
   Without this step, components such as the HMRC timeout dialog will not be enabled in your service.

1.  Optionally, add `TwirlKeys.templateImports` in `build.sbt`:
    ```sbt
        TwirlKeys.templateImports ++= Seq(
          "uk.gov.hmrc.govukfrontend.views.html.components._",
          "uk.gov.hmrc.govukfrontend.views.html.helpers._",
          "uk.gov.hmrc.hmrcfrontend.views.html.components._",
          "uk.gov.hmrc.hmrcfrontend.views.html.helpers._"
        )
    ```
    
    Adding this removes the need for `@import` statements in your Twirl templates. 
    If you prefer not to use this mechanism, import the components in any template that uses them as 
     follows:
    
    ```scala
    @import uk.gov.hmrc.hmrcfrontend.views.html.components._
    ```

### Useful implicits

The following import will summon [implicits](https://github.com/hmrc/play-frontend-hmrc/blob/master/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/Implicits.scala) that provide extension methods on `Play's` [Html](https://www.playframework.com/documentation/2.6.x/api/scala/play/twirl/api/Html.html) objects.
This includes useful HTML trims, pads, indents and handling HTML emptiness.
```scala
@import uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._
```

### Find working examples

You can find working examples of the use of play-frontend-hmrc in the following actively maintained repositories:
* [tracking-consent-frontend](https://www.github.com/hmrc/tracking-consent-frontend)
* [accessibility-statement-frontend](https://www.github.com/hmrc/accessibility-statement-frontend)
* [contact-frontend](https://www.github.com/hmrc/contact-frontend)

## Play Framework compatibility

The library is cross-compiled for `Play 2.5`, `Play 2.6` and `Play 2.7`. The Play 2.5 version does not support dependency
injection.

### Play 2.5

The namespace `uk.gov.hmrc.hmrcfrontend.views.html.components` exposes the components' templates as values with the prefix
`Hmrc`, ex: an `hmrcPageHeading` is available as `HmrcPageHeading`.

For example, a page heading with a corresponding section
```scala
@import uk.gov.hmrc.hmrcfrontend.views.html.components._

@()
@HmrcPageHeading(PageHeading(
  text = "Foo",
  section = Some("Section bar")
))
```

### Play 2.6 and 2.7

The same namespace exposes type aliases prefixed with `Hmrc` (ex: the type `HmrcPageHeading`) so that components can be injected into 
a controller or template. It also exposes values of the same name (ex: `HmrcPageHeading`) if you wish to use the component template directly, 
though it is preferable to use dependency injection.

Same button using DI:
```scala
@import uk.gov.hmrc.hmrcfrontend.views.html.components._

@this(hmrcPageHeading: HmrcPageHeading)

@()
@hmrcPageHeading(PageHeading(
  text = "Foo",
  section = Some("Section bar")
))
```

### Example Templates

We provide example templates using the Twirl components through a `Chrome` extension. Please refer to the 
[extension's github repository](https://github.com/hmrc/play-frontend-govuk-extension) for installation instructions.

With the extension installed, you should be able to go to the [HMRC Design System](https://design.tax.service.gov.uk/hmrc-design-patterns/), 
click on a component on the sidebar and see the `Twirl` examples matching the provided `Nunjucks` templates.

## Accessibility statements

The [hmrcStandardFooter](src/main/play-26/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/hmrcStandardFooter.scala.html) helper
 generates the standard Gov.UK footer including the standard list of footer links for HMRC.

To configure this helper to link to the new 
[Accessibility Statement service](https://www.github.com/hmrc/accessibility-statement-frontend), provide the key 
`accessibility-statement.service-path` in your `application.conf` file. This key is the path to your 
accessibility statement under https://www.tax.service.gov.uk/accessibility-statement.
 
For example, if your accessibility statement is https://www.tax.service.gov.uk/accessibility-statement/discounted-icecreams, 
this property must be set to `/discounted-icecreams` as follows:

```
accessibility-statement.service-path = "/discounted-icecreams"
```

## Integrating with tracking consent

If you intend to use Google Analytics or Optimizely to measure usage of your service, you will need to integrate with 
[tracking-consent-frontend](https://www.github.com/hmrc/tracking-consent-frontend). The 
[hmrcTrackingConsentSnippet](src/main/play-26/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/hmrcTrackingConsentSnippet.scala.html) 
component generates the necessary HTML SCRIPT tags that must be injected into the HEAD element for every page on your service.
 
Before integrating, it is important to remove any existing snippets relating to GTM or Optimizely. Tracking consent
manages the enabling of these third-party solutions based on the user's tracking preferences. If they are not removed
there is a risk the user's tracking preferences will not be honoured.

Configure your service's GTM container in `conf/application.conf`. For example, if you have been
instructed to use GTM container `a`, the configuration would appear as:

```
tracking-consent-frontend {
  gtm.container = "a"
}
```

`gtm.container` can be one of: `transitional`, `a`, `b`, `c`, `d`, `e`, `f` or `sdes`. Consult with the CIPSAGA team 
to identify which GTM container you should be using in your service.

Update your head template to include the hmrcTrackingConsentSnippet component:
    
    ```scala
    @this(hmrcTrackingConsentSnippet: HmrcTrackingConsentSnippet)
    
    @()(implicit appConfig: AppConfig, messages: Messages)
    @hmrcTrackingConsentSnippet()
    <!--[if lte IE 8]><link href='@routes.Assets.versioned("stylesheets/application-ie-8.css")' rel="stylesheet" type="text/css" /><![endif]-->
    <!--[if gt IE 8]><!--><link href='@routes.Assets.versioned("stylesheets/application.css")' media="screen" rel="stylesheet" type="text/css" /><!--<![endif]-->
    ```

If using Play 2.7 and CSPFilter, the nonce can be passed to tracking consent as follows:

```
@import views.html.helper.CSPNonce
...
@hmrcTrackingConsentSnippet(nonce = CSPNonce.get)
```

## Warning users before timing them out

In order to meet the accessibility [WCAG 2.1 Principle 2: Operable](https://www.w3.org/TR/WCAG21/#operable) you must
provide users with enough time to read and use content. In particular, WCAG 2.1 Success Criterion 2.2.1 (Timing Adjustable) 
requires that users are able to turn off, adjust or extend the time limit, giving them at least 20 seconds to perform this with
 a simple action.

On MDTP, users are, by default, automatically timed out of any authenticated service after 15 minutes
 of inactivity. This mechanism, implemented in [SessionTimeoutFilter](https://github.com/hmrc/bootstrap-play/blob/master/bootstrap-frontend-play-26/src/main/scala/uk/gov/hmrc/play/bootstrap/frontend/filters/SessionTimeoutFilter.scala), 
 clears all non-allow-listed session keys after the timeout duration has elapsed. Services can override this default by adjusting the
`session.timeout` configuration key in `conf/application.conf`.

The [hmrcTimeoutDialog](src/main/play-26/twirl/uk/gov/hmrc/hmrcfrontend/views/components/hmrcTimeoutDialog.scala.html)
 component helps services meet this accessibility obligation by delivering an accessible timeout warning
inside a modal dialog a configurable number of seconds before they are due to be timed out. The dialog warns the user with the message
'For your security, we will sign you out in X minutes.' which is updated every minute until 60 seconds are remaining, at
which point it counts down in seconds. For screen-reader users, an audible message counts down in 20 second increments.

Users are then given the option to 'Stay signed in', which extends their session by the timeout duration, or 'Sign out' 
returning them to the supplied `signOutUrl`.

### How to integrate with the timeout dialog

The instructions below assume you have set up play-frontend-hmrc as indicated above including adding the required
initialisation of hmrc-frontend to your local scripts template.

1. Identify a `signOutUrl` that will be used when users click 'Sign Out' on the timeout dialog. A good choice for this is the
url that may already supplied as the `signOutHref` parameter to the `hmrcHeader` component, which controls the sign 
out link in the gov.uk header.

1. Identify a `keepAliveUrl`, a side effect free endpoint that can be used by the timeout dialog Javascript code 
to perform an XHR GET request to refresh the user's session and keep them logged into the service. A good practice is
 to have a dedicated controller and route defined for this so its use for this purpose is explicit. This url
 will be supplied in the `keepAliveUrl` parameter to `hmrcTimeoutDialog`.
  
1. Add configuration keys into your `conf/application.conf` for the timeout duration applicable to your service and `countdown`, 
the number of seconds before that the timeout dialog will be displayed:
    ```scala
    timeoutDialog {
      timeout = 900
      countdown = 120
    }
    ```

1. Add configuration methods into your `AppConfig.scala` file:
    ```scala
    val timeoutDialogTimeout: Int = servicesConfig.getInt("timeoutDialog.timeout")
    val timeoutDialogCountdown: Int = servicesConfig.getInt("timeoutDialog.countdown")
    ```

    Note, the above uses the getInt helper in ServicesConfig from bootstrap-play.

1. Optionally, identify a `timeoutUrl` that will sign out users if they do nothing and are timed out by the timeout dialog. If this 
is not supplied, the timeout dialog will use the `signOutUrl`.

1. Update your head template to include the hmrcTimeoutDialog component, supplying the parameters identified above. For example,
    
    ```scala
    @this(hmrcTimeoutDialog: HmrcTimeoutDialog)
    
    @()(implicit appConfig: AppConfig, messages: Messages)
    <!--[if lte IE 8]><link href='@routes.Assets.versioned("stylesheets/application-ie-8.css")' rel="stylesheet" type="text/css" /><![endif]-->
    <!--[if gt IE 8]><!--><link href='@routes.Assets.versioned("stylesheets/application.css")' media="screen" rel="stylesheet" type="text/css" /><!--<![endif]-->
    @hmrcTimeoutDialog(TimeoutDialog(
      timeout = Some(appConfig.timeoutDialogTimeout),
      countdown = Some(appConfig.timeoutDialogCountdown),
      keepAliveUrl = Some(routes.KeepAliveController.keepAlive().url),
      signOutUrl = Some(appConfig.signOutUrl),
      timeoutUrl = Some(appConfig.signOutUrl),
      language = Some(messages.lang.code)
    ))
    ```

## Getting help

Please report any issues with this library in Slack at `#team-plat-ui`.

## Useful Links

- [hmrc-frontend](https://github.com/hmrc/hmrc-frontend/) - reusable Nunjucks HTML components for HMRC design patterns
- [HMRC Design Patterns](https://design.tax.service.gov.uk/hmrc-design-patterns/) - documentation for the use of `hmrc-frontend` components
- [play-frontend-govuk](https://github.com/hmrc/play-frontend-govuk/) - Twirl implementation of `govuk-frontend` components
- [govuk-frontend](https://github.com/alphagov/govuk-frontend/) - reusable Nunjucks HTML components from GOV.UK
- [GOV.UK Design System](https://design-system.service.gov.uk/components/) - documentation for the use of `govuk-frontend` components
- [GOV.UK Design System Chrome extension](https://github.com/hmrc/play-frontend-govuk-extension) - `Chrome` extension to add a Twirl tab for each example in the GOV.UK Design System

## Owning team README
Rationale for code and translation decisions, dependencies, as well as instructions for team members maintaining this repository can be found [here](/docs/maintainers/overview.md).

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
