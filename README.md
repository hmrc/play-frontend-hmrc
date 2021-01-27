# play-frontend-hmrc

This library contains all the Twirl components and helpers needed to implement frontend microservices on the HMRC
digital tax platform.

The library transitively depends on [play-frontend-govuk](https://github.com/hmrc/play-frontend-govuk/) which is a Twirl
port of [alphagov/govuk-frontend](https://www.github.com/alphagov/govuk-frontend), 
adding to it HMRC-specific Twirl components as well as Play and platform aware helpers that make the process 
of implementing frontend microservices straightforward and idiomatic for Scala developers.

The library comprises two packages:

1. components - a set of Twirl templates providing a direct port of the Nunjucks components from 
[hmrc/hmrc-frontend](https://www.github.com/hmrc/hmrc-frontend)
1. helpers
    1. wrappers designed to make using the hmrc-frontend components more straightforward and idiomatic in Scala/Play
    1. a collection of markup snippets required by MDTP microservices

## Table of Contents

- [Getting started](#getting-started)
- [Play framework compatibility](#play-framework-compatibility)
- [Standard HMRC header](#standard-hmrc-header)
- [Helping users report technical issues](#helping-users-report-technical-issues)
- [Standard HMRC footer and accessibility statements](#standard-hmrc-footer-and-accessibility-statements)
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
    
    The library is cross-compiled for Play 2.6 and 2.7. Note, because this library transitively depends on play-frontend-govuk
    there is no need to add this as a separate dependency. If it is already included, it can be removed.

1.  Add routes for hmrc-frontend and govuk-frontend assets in `conf/app.routes`:
    ```scala
    ->         /govuk-frontend                     govuk.Routes
    ->         /hmrc-frontend                      hmrcfrontend.Routes
    ```

1. Ensure you have your service name defined in your messages files. For example,
    ```scala
    service.name = Any tax service
    ``` 

1. Create a layout template using govukLayout that you can use in all of your service pages. For example,

    ```scala
    @import uk.gov.hmrc.hmrcfrontend.views.html.helpers.{HmrcStandardFooter, HmrcStandardHeader, HmrcHead, HmrcScripts}

    @this(
        govukLayout: GovukLayout,
        hmrcHead: HmrcHead,
        hmrcStandardHeader: HmrcStandardHeader,
        hmrcStandardFooter: HmrcStandardFooter,
        hmrcScripts: HmrcScripts
    )
    
    @(pageTitle: String)(contentBlock: Html)(implicit request: RequestHeader, messages: Messages)
    
    @govukLayout(
        pageTitle = Some(pageTitle),
        headBlock = Some(hmrcHead()),
        headerBlock = Some(hmrcStandardHeader(
          serviceUrl = Some(controllers.routes.IndexController.index().url)
        )),
        scriptsBlock = Some(hmrcScripts()),
        footerBlock = Some(hmrcStandardFooter())
    )(contentBlock)
    ```
   
   If using Play 2.7 and CSPFilter, the nonce can be passed to hmrcHead as follows:
   
   ```
    @govukLayout(
        ...
        headBlock = Some(hmrcHead(nonce = CSPNonce.get)),
        ...
    )(contentBlock)
   ```

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

The library is cross-compiled for `Play 2.6` and `Play 2.7`.

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

## Standard HMRC header

The [hmrcStandardHeader](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/hmrcStandardHeader.scala.html) helper 
generates the GOV.UK header standardised for HMRC with Welsh translations, and a screen-reader friendly option to
include the HMRC banner for services that require it.

To use this component, 

1. Ensure you have your service name defined in your messages files. For example,
    ```scala
    service.name = Any tax service
    ``` 

1. If your service requires users to sign in, identify the URL that users should use to sign out of your service. If you have a
 dedicated sign out controller you can use its reverse controller. This url should be passed to the
 `signOutUrl` parameter.

1. Pass the output of hmrcStandardHeader into the headerBlock parameter of govukLayout in your main template. For example,

```scala
@govukLayout(
  pageTitle = pageTitle,
  headBlock = Some(hmrcHead(nonce = CSPNonce.get)),
  headerBlock = Some(hmrcStandardHeader(
    serviceUrl = Some(controllers.routes.IndexController.index().url),
    signOutUrl = Some(controllers.routes.SignOutController.signOut().url)
  )),
  beforeContentBlock = Some(languageSelect()),
  footerBlock = Some(hmrcStandardFooter()),
  scriptsBlock = Some(hmrcScripts())
)(contentBlock)
```

If you additionally need the [HMRC banner](https://design.tax.service.gov.uk/hmrc-design-patterns/hmrc-banner/) –
most services do not – set `displayHmrcBanner` to true.

In the exceptional case that you have a frontend microservice that has a dynamic service name, for example, because it hosts
more than one public-facing service, you can override the service name using the `serviceName` parameter.

## Helping users report technical issues

The [hmrcReportTechnicalIssueHelper](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/hmrcReportTechnicalIssueHelper.scala.html) component 
generates a link that allows users to report technical issues with your service.

To configure this helper, add the following configuration to your `application.conf`

```
contact-frontend.serviceId = "<any-service-id>"
```

serviceId helps identify your service when members of the public report technical issues. 
If your service is *not* already integrating with contact-frontend, we advise choosing an
 identifier that is specific to your service and unlikely to be used by any other service, avoiding any special characters
 or whitespace.

The component should be added to the bottom of each page on your service by including it in 
your service's main page template,

```scala
@import uk.gov.hmrc.hmrcfrontend.views.html.helpers._

@this(hmrcReportTechnicalIssueHelper: HmrcReportTechnicalIssueHelper)

...

@hmrcReportTechnicalIssueHelper()
```

## Standard HMRC footer and accessibility statements

The [hmrcStandardFooter](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/hmrcStandardFooter.scala.html) helper
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
[hmrcTrackingConsentSnippet](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/hmrcTrackingConsentSnippet.scala.html) 
component generates the necessary HTML SCRIPT tags that must be injected into the HEAD element for every page on your service.

If you are using the hmrcHead() component to integrate with play-frontend-hmrc, then this component is already being
added to your microservice – all that is additionally required is a configuration key to set the GTM container.
 
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

If your page template is already using hmrcHead then no further work is required. Otherwise, add hmrcTrackingConsentSnippet
above any other assets imported in your HEAD element. For example,
    
    ```scala
    @this(hmrcTrackingConsentSnippet: HmrcTrackingConsentSnippet)
    
    @()(implicit appConfig: AppConfig, messages: Messages)
    @hmrcTrackingConsentSnippet()
    ...
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
 of inactivity. This mechanism, implemented in [SessionTimeoutFilter](https://github.com/hmrc/bootstrap-play/blob/master/bootstrap-frontend-src/main/scala/uk/gov/hmrc/play/bootstrap/frontend/filters/SessionTimeoutFilter.scala), 
 clears all non-allow-listed session keys after the timeout duration has elapsed. Services can override this default by adjusting the
`session.timeout` configuration key in `conf/application.conf`.

The [hmrcTimeoutDialog](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/components/hmrcTimeoutDialog.scala.html)
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
url that may already supplied as the `signOutUrl` parameter to the `hmrcStandardHeader` component, which controls the sign 
out link in the gov.uk header. See related guidance above.

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

1. Update your page template to include the hmrcTimeoutDialog component, supplying the parameters identified above. For example,
    
    ```scala
    @timeoutDialog = {
      @hmrcTimeoutDialog(TimeoutDialog(
        timeout = Some(appConfig.timeoutDialogTimeout),
        countdown = Some(appConfig.timeoutDialogCountdown),
        keepAliveUrl = Some(routes.KeepAliveController.keepAlive().url),
        signOutUrl = Some(controllers.routes.SignOutController.signOut().url),
        timeoutUrl = Some(controllers.routes.SignOutController.signOut().url),
        language = Some(messages.lang.code)
      ))
    }
          
    @govukLayout(
        pageTitle = Some(pageTitle),
        headBlock = Some(hmrcHead(headBlock = Some(timeoutDialog), nonce = CSPNonce.get)),
        scriptsBlock = Some(hmrcScripts()),
        footerBlock = Some(hmrcStandardFooter())
    )(contentBlock)
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
