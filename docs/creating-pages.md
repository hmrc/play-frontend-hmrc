[Back to README.md](../README.md)

# Creating pages

Information how to enable GOV.UK brand refresh, link your accessibility statement, GTM/tracking consent configuration can be found in a separate Markdown file.

## Table of Contents

- [Using the refreshed GOV.UK brand](#using-the-refreshed-govuk-brand)
- [Creating HMRC-style pages](#creating-hmrc-style-pages)
  - [Using the HMRC standard page template](#using-the-hmrc-standard-page-template)
  - [Adding a sidebar to your Layout](#adding-a-sidebar-to-your-layout)
    - [Finding working examples](#finding-working-examples)
- [Integrating with shared HMRC services](#integrating-with-shared-hmrc-services)
  - [Linking to your accessibility statement](#linking-to-your-accessibility-statement)
  - [Allowing users to enable or disable tracking cookies](#allowing-users-to-enable-or-disable-tracking-cookies)
    - [Integrating with tracking consent](#integrating-with-tracking-consent)
    - [Adding GTM to internal services](#adding-gtm-to-internal-services)
- [Warning users before timing them out](#warning-users-before-timing-them-out)
  - [Integrating with the timeout dialog](#integrating-with-the-timeout-dialog)
  - [Synchronising session between tabs](#synchronising-session-between-tabs)
  - [Customising the timeout dialog](#customising-the-timeout-dialog)

## Using the refreshed GOV.UK brand

It's possible to enable the GOV.UK rebrand as of v12.3.0 of play-frontend-hmrc. Enable it by setting the following configuration:
```
play-frontend-hmrc {
    useRebrand = true
}
```

Before deploying to production:
- ensure that your service works correctly when using the refreshed GOV.UK branding through exploratory testing
- plan your release to production in relation to other services which your users' journeys might span and whether any coordination with them is needed

Specific rebrand questions can be directed to the HMRC Slack channel #event-govuk-rebrand

[back to top](#creating-pages)

## Creating HMRC-style pages
### Using the HMRC standard page template
The [`HmrcStandardPage`](/play-frontend-hmrc-play-30/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcStandardPage.scala.html) helper
generates a standard HMRC page layout including the `HmrcStandardHeader`, `HmrcStandardFooter`, Welsh language toggle, and various banners.
This helper takes [`HmrcStandardPageParams`](/play-frontend-hmrc-play-30/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/viewmodels/hmrcstandardpage/HmrcStandardPageParams.scala)
which includes the following members:
* [`ServiceURLs`](/play-frontend-hmrc-play-30/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/viewmodels/hmrcstandardpage/ServiceURLs.scala) - containing service-specific URLs that will typically need setting once
* [`Banners`](/play-frontend-hmrc-play-30/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/viewmodels/hmrcstandardpage/Banners.scala) - containing details of which banners should be displayed
* [`TemplateOverrides`](/play-frontend-hmrc-play-30/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/viewmodels/hmrcstandardpage/TemplateOverrides.scala) - containing custom layout wrapper overrides or HTML to inject (perhaps set for the whole service, or on a page-by-page basis)

To use this component,

1. Create a custom layout template `Layout.scala.html` to suit your service's needs, for example:

```scala
@import uk.gov.hmrc.hmrcfrontend.views.config.StandardBetaBanner
@import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcStandardPage
@import uk.gov.hmrc.hmrcfrontend.views.viewmodels.layout._
@import config.AppConfig
@import uk.gov.hmrc.anyfrontend.controllers.routes

@this(hmrcStandardPage: HmrcStandardPage, standardBetaBanner: StandardBetaBanner)

@(pageTitle: String, appConfig: AppConfig)(contentBlock: Html)(implicit request: RequestHeader, messages: Messages)

@hmrcStandardPage(
  HmrcStandardPageParams(
    serviceURLs = ServiceURLs(
      serviceUrl = Some(routes.IndexController.index().url),
      signOutUrl = Some(routes.SignOutController.signOut().url)
    ),
    banners = Banners(
      phaseBanner = Some(standardBetaBanner(url = appConfig.betaFeedbackUrl))
    ),
    serviceName = serviceName,
    pageTitle = pageTitle,
    isWelshTranslationAvailable = true /* or false if your service has not been translated */
  )(contentBlock)
```

The parameters that can be passed into the `HmrcStandardPage` are as follows:

      | Parameter                                  | Description                                                       | Example                                                     |
      | ------------------------------------------ | ----------------------------------------------------------------- | ----------------------------------------------------------- |
      | `service.serviceUrl`                       | This will be bound to HmrcStandardHeader                          | `Some(routes.IndexController.index().url)`                  |
      | `service.signOutUrl`                       | Passing a value will display the sign out link                    | `Some(routes.SignOutController.signOut().url)`              |
      | `service.accessibilityStatementUrl`        | Passing a value will override the accessibility statement URL in the [footer](#accessibility-statement-links)                  ||
      | `banners.displayHmrcBanner`                | Setting to true will display the [HMRC banner](https://design.tax.service.gov.uk/hmrc-design-patterns/hmrc-banner/)            ||
      | `banners.phaseBanner`                      | Passing a value will display alpha or beta banner.                | `Some(standardBetaBanner())`                                |
      | `banners.userResearchBanner`               | Passing a value will display the user research banner             | `Some(UserResearchBanner(url = appConfig.userResearchUrl))` |
      | `banners.additionalBannersBlock`           | Pass extra html into the header, intended for custom banners.     | `Some(attorneyBanner)`                                      |
      | `templateOverrides.additionalHeadBlock`    | Passing a value will add additional content in the HEAD element   |                                                             |
      | `templateOverrides.additionalScriptsBlock` | Passing a value will add additional scripts at the end of the BODY|                                                             |
      | `templateOverrides.beforeContentBlock`     | Passing a value will add content between the header and the main element. This content will override any `isWelshTranslationAvailable`, `backLink` or `backLinkUrl` parameter.||
      | `templateOverrides.mainContentLayout`      | Passing value will override the default two thirds layout         |                                                             |
      | `templateOverrides.pageLayout`             | Allow internal services to use a full width layout.               | `Some(fixedWidthPageLayout(_))`                             |
      | `templateOverrides.headerContainerClasses` | Allow internal services to use a full width header.               | `"govuk-width-container"`                                   |
      | `serviceName`                              | Pass a value only if your service has a dynamic service name      |                                                             |
      | `pageTitle`                                | This will be bound to govukLayout                                 |                                                             |
      | `isWelshTranslationAvailable`              | Setting to true will display the language toggle                  | `true`                                                      |
      | `backLink`                                 | Passing a value will display a back link                          | `Some(BackLink(href = ..., attributes = ...))`              |
      | `exitThisPage`                             | Passing a value will display an "Exit This Page" button           | `Some(ExitThisPage())`                                      |

[back to top](#creating-pages)

### Adding a sidebar to your Layout

The `HmrcStandardPage` by default renders the main `contentBlock` of the page in
[two thirds width content](https://design-system.service.gov.uk/styles/layout/).

However, if you wish to override the styling of the main content, you can do so by passing in an optional `mainContentLayout`
parameter of type `Option[Html => Html]`, which will apply wrapping content around your `contentBlock`.

If you wish to create a layout with [two thirds, one third styling](https://design-system.service.gov.uk/styles/layout/)
(for example if your page has a sidebar), there is a helper `TwoThirdsOneThirdMainContent.scala.html` which can be used
as follows:

```scala
@import uk.gov.hmrc.govukfrontend.views.html.components.TwoThirdsOneThirdMainContent
@import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcStandardPage
@import uk.gov.hmrc.hmrcfrontend.views.viewmodels.hmrcstandardpage._

@this (
  hmrcStandardPage: HmrcStandardPage,
  twoThirdsOneThirdMainContent: TwoThirdsOneThirdMainContent
)

@(pageTitle: String, isWelshTranslationAvailable: Boolean = true)(contentBlock: Html)(implicit request: RequestHeader, messages: Messages)

@sidebar = {
  <h2 class="govuk-heading-xl">This is my sidebar</h2>
  <p class="govuk-body">There is my sidebar content</p>
}

@hmrcStandardPage(
  banners = Banners(displayHmrcBanner = true),
  templateOverrides = TemplateOverrides(
    mainContentLayout = Some(twoThirdsOneThirdMainContent(sidebar))
  ),
  pageTitle = Some(pageTitle),
  isWelshTranslationAvailable = isWelshTranslationAvailable
)(contentBlock)
```

Alternatively, you can declare any template and pass it through as a function or partially applied function that has the
signature `Html => Html`.

> [!WARNING]
> `FullWidthPageLayout` should only be used by internal services.
> The default fixed width layout should be used for all public services.

#### Finding working examples

You can find working examples of the use of play-frontend-hmrc in the following actively maintained repositories:
* [tracking-consent-frontend](https://www.github.com/hmrc/tracking-consent-frontend)
* [accessibility-statement-frontend](https://www.github.com/hmrc/accessibility-statement-frontend)
* [contact-frontend](https://www.github.com/hmrc/contact-frontend)
* [help-frontend](https://www.github.com/hmrc/help-frontend)

[back to top](#creating-pages)

## Integrating with shared HMRC services
### Linking to your accessibility statement

[HmrcStandardFooter](/play-frontend-hmrc-play-30/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcStandardFooter.scala.html),
included as part of `HmrcStandardPage`, generates the standard GOV.UK footer including the standardised list of footer
links for tax services.

To configure this helper to link to the
[accessibility statement service](https://www.github.com/hmrc/accessibility-statement-frontend), provide the key
`accessibility-statement.service-path` in your `application.conf` file. This key is the path to your
accessibility statement under the [accessibility-statement-frontend|https://github.com/hmrc/accessibility-statement-frontend] service.

For example, if your accessibility statement is https://www.tax.service.gov.uk/accessibility-statement/paye,
this property must be set to `/paye` as follows:

```hocon
accessibility-statement.service-path = "/paye"
```

In the exceptional case that you need to link to an accessibility statement not hosted
within accessibility-statement-frontend, the default behaviour can be overridden by supplying an
`accessibilityStatementUrl` parameter to `HmrcStandardFooter`.

[back to top](#creating-pages)

### Allowing users to enable or disable tracking cookies
#### Integrating with tracking consent

If you intend to use Google Analytics or Optimizely to measure usage of your service, you will need to integrate with
[tracking-consent-frontend](https://www.github.com/hmrc/tracking-consent-frontend). The
[HmrcHead](/play-frontend-hmrc-play-30/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcHead.scala.html)
helper generates the necessary HTML SCRIPT tags that must be injected into the HEAD element for every page on your
service provided it is configured correctly as below.

Before integrating, it is important to remove any existing snippets relating to GTM, GA or Optimizely. If they are not removed
there is a risk the user’s tracking preferences will not be honoured correctly.

Configure your service’s GTM container in `conf/application.conf`. For example, if you have been
instructed to use GTM container `a`, the configuration would appear as:

```hocon
tracking-consent-frontend {
  gtm.container = "a"
}
```

`gtm.container` can be one of: `a`, `b`, `c`, `d`, `e`, `f` or `sdes`. Consult with the CIP Advanced Search and 
Dashboarding (CIP ASAD) team to identify which GTM container you should be using in your service.

[back to top](#creating-pages)

#### Adding GTM to internal services

If you would like to add GTM to an internal service, you can do so using the [HmrcInternalHead](/play-frontend-hmrc-play-30/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcInternalHead.scala.html)
helper, which will add the GTM snippet in the `<head>` block. It should be used as demonstrated below in your own
`Layout.scala`.

```scala
@import uk.gov.hmrc.govukfrontend.views.html.components.GovukLayout
@import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcInternalHead
@import uk.gov.hmrc.hmrcfrontend.views.html.components.HmrcInternalHeader
@import uk.gov.hmrc.hmrcfrontend.views.viewmodels.internalheader.InternalHeader

@this(
        govukLayout: GovukLayout,
        hmrcInternalHead: HmrcInternalHead,
        hmrcInternalHeader: HmrcInternalHeader
)
@(pageTitle: Option[String] = None)(contentBlock: Html)(implicit request: Request[_], messages: Messages)

@govukLayout(
  pageTitle = pageTitle,
  headBlock = Some(hmrcInternalHead()),
  headerBlock = Some(hmrcInternalHeader(InternalHeader()))
)(contentBlock)
```

[back to top](#creating-pages)

### Warning users before timing them out

In order to meet the accessibility [WCAG 2.1 Principle 2: Operable](https://www.w3.org/TR/WCAG21/#operable) you must
provide users with enough time to read and use content. In particular, WCAG 2.1 Success Criterion 2.2.1 (Timing Adjustable)
requires that users are able to turn off, adjust or extend the time limit, giving them at least 20 seconds to perform this with
a simple action.

On MDTP, users are, by default, automatically timed out of any authenticated service after 15 minutes
of inactivity. This mechanism, implemented in [SessionTimeoutFilter](https://github.com/hmrc/bootstrap-play/blob/main/bootstrap-frontend-play-30/src/main/scala/uk/gov/hmrc/play/bootstrap/frontend/filters/SessionTimeoutFilter.scala),
clears all non-allow-listed session keys after the timeout duration has elapsed. Services can override this default by adjusting the
`session.timeout` configuration key in `conf/application.conf`.

The [HmrcTimeoutDialogHelper](/play-frontend-hmrc-play-30/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcTimeoutDialogHelper.scala.html)
component helps services meet this accessibility obligation by delivering an accessible timeout warning
inside a modal dialog a configurable number of seconds before they are due to be timed out. The dialog warns the user with the message
'For your security, we will sign you out in X minutes.' which is updated every minute until 60 seconds are remaining, at
which point it counts down in seconds. For screen-reader users, an audible message counts down in 20 second increments.

Users are then given the option to 'Stay signed in', which extends their session by the timeout duration, or 'Sign out'
returning them to the supplied `signOutUrl`.

[back to top](#creating-pages)

#### Integrating with the timeout dialog

The instructions below assume you have set up play-frontend-hmrc as indicated above.

1. Identify the `signOutUrl` that should be used when users click 'Sign Out' on the timeout dialog. Your service may already be
   supplying a `signOutUrl` parameter to the `HmrcStandardHeader` component, which controls the sign out link in the GOV.UK
   header. Reusing this value may be a sensible choice. Refer to guidance above to understand how this argument is used by the
   timeout dialog.

1. Update your layout template to pass in the `HmrcTimeoutDialogHelper` in the HEAD element, supplying the signOutUrl as
   a parameter. For example if using `HmrcStandardPage`, pass `Some(HmrcTimeoutDialogHelper(signOutUrl = signOutUrl))` in the
   `templateOverrides.additionalHeadBlock` parameter.

[back to top](#creating-pages)

#### Synchronising session between tabs

By default, `play-frontend-hmrc` synchronises session extension between different HMRC tabs (using the `BroadcastChannel`
API in browsers). In practical terms, this means that if a user sees a timeout dialog in an active tab, and clicks to
extend their session, then the timeout dialogs that have also opted into this behaviour in any background tabs will also restart the countdowns until they display their timeout warning.

This behaviour is currently flagged **on** (`true`) by default. To disable, you can either explicitly pass `Some(false)`
to the `HmrcTimeoutDialogHelper`, or you can add a boolean `false` to your `application.conf` with the key
`hmrc-timeout-dialog.enableSynchroniseTabs`.

[back to top](#creating-pages)

#### Customising the timeout dialog
By default, the timeout dialog will redirect to the supplied signOutUrl if they do nothing after the timeout duration
has elapsed. If you wish users to be redirected to a different URL, a separate `timeoutUrl` can be supplied.

If your service has a timeout duration different to that configured in the `session.timeout` configuration key
used by bootstrap-play, it can be overridden using the `timeout` parameter. Likewise, the
number of seconds warning can be adjusted using the `countdown` parameter.

If you need to perform special logic to keep the user’s session alive, the default keep alive mechanism can
be overridden using the `keepAliveUrl` parameter. This must be a side effect free endpoint that implements
HTTP GET and can be called via an XHR request from the timeout dialog Javascript code. A good practice is to
have a dedicated controller and route defined for this so its use for this purpose is explicit. This url
will be supplied in the `keepAliveUrl` parameter to `HmrcTimeoutDialog`. Do not use `#` in case the current URL
does not implement HTTP GET.

| Parameter         | Description                                                                                                                                                 | Example |
| ----------------- |-------------------------------------------------------------------------------------------------------------------------------------------------------------| ------- |
| `signOutUrl`      | The url that will be used when users click 'Sign Out'                                                                                                       | Some(routes.SignOutController.signOut().url) |
| `timeoutUrl`      | The url that the timeout dialog will redirect to following timeout. Defaults to the `signOutUrl`.                                                           | Some(routes.TimeoutController.timeOut().url) |
| `keepAliveUrl`    | A endpoint used to keep the user’s session alive                                                                                                            | Some(routes.KeepAliveController.keepAlive().url)
| `timeout`         | The timeout duration where this differs from `session.timeout`                                                                                              | 1800 |
| `countdown`       | The number of seconds before timeout the dialog is displayed. The default is 120.                                                                           | 240 |
| `synchroniseTabs` | Allow the timeout dialog to use the `BroadcastChannel` to communicate session activity to other background tabs. Defaults to `Some(true)`, i.e. enabled. di | Some(true) |

The timeout dialog’s content can be customised using the following parameters:

| Parameter             | Description                                                   | Example |
| --------------------- | ------------------------------------------------------------- | ------- |
| `title`               | The text to use as a title for the dialog                     | Some(messages("hmrc-timeout-dialog.title")) |
| `message`             | The message displayed to the user                             | Some(messages("hmrc-timeout-dialog.message")) |
| `messageSuffix`       | Any additional text to be displayed after the timer           | Some(messages("hmrc-timeout-dialog.message-suffix")) |
| `keepAliveButtonText` | The text on the button that keeps the user signed in          | Some(messages("hmrc-timeout-dialog.keep-alive-button-text")) |
| `signOutButtonText`   | The text for the link which takes the user to a sign out page | Some(messages("hmrc-timeout-dialog.sign-out-button-text")) |

[back to top](#creating-pages)
