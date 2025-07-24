# play-frontend-hmrc: Creating pages

### Using the refreshed GOV.UK brand

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

For example, you can add a template `WithSidebarOnLeft.scala.html` as below:

```scala
@this()

@(sidebarContent: Html)(mainContent: Html)

<div class="govuk-grid-row">
    <div class="govuk-grid-column-one-third">
        @sidebarContent
    </div>
    <div class="govuk-grid-column-two-thirds">
      @mainContent
    </div>
</div>
```

You can then inject this into your `Layout.scala.html` and partially apply the function as above.

> [!WARNING]
> `FullWidthPageLayout` should only be used by internal services.
> The default fixed width layout should be used for all public services.

### Linking to your accessibility statement

[HmrcStandardFooter](play-frontend-hmrc-play-30/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcStandardFooter.scala.html),
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

### Allowing users to enable or disable tracking cookies
#### Integrating with tracking consent

If you intend to use Google Analytics or Optimizely to measure usage of your service, you will need to integrate with
[tracking-consent-frontend](https://www.github.com/hmrc/tracking-consent-frontend). The
[HmrcHead](play-frontend-hmrc-play-30/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcHead.scala.html)
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

`gtm.container` can be one of: `a`, `b`, `c`, `d`, `e`, `f` or `sdes`. Consult with the CIPSAGA team
to identify which GTM container you should be using in your service.

#### Adding GTM to internal services

If you would like to add GTM to an internal service, you can do so using the [HmrcInternalHead](play-frontend-hmrc-play-30/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcInternalHead.scala.html)
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

### Warning users before timing them out

In order to meet the accessibility [WCAG 2.1 Principle 2: Operable](https://www.w3.org/TR/WCAG21/#operable) you must
provide users with enough time to read and use content. In particular, WCAG 2.1 Success Criterion 2.2.1 (Timing Adjustable)
requires that users are able to turn off, adjust or extend the time limit, giving them at least 20 seconds to perform this with
a simple action.

On MDTP, users are, by default, automatically timed out of any authenticated service after 15 minutes
of inactivity. This mechanism, implemented in [SessionTimeoutFilter](https://github.com/hmrc/bootstrap-play/blob/main/bootstrap-frontend-play-30/src/main/scala/uk/gov/hmrc/play/bootstrap/frontend/filters/SessionTimeoutFilter.scala),
clears all non-allow-listed session keys after the timeout duration has elapsed. Services can override this default by adjusting the
`session.timeout` configuration key in `conf/application.conf`.

The [HmrcTimeoutDialogHelper](play-frontend-hmrc-play-30/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcTimeoutDialogHelper.scala.html)
component helps services meet this accessibility obligation by delivering an accessible timeout warning
inside a modal dialog a configurable number of seconds before they are due to be timed out. The dialog warns the user with the message
'For your security, we will sign you out in X minutes.' which is updated every minute until 60 seconds are remaining, at
which point it counts down in seconds. For screen-reader users, an audible message counts down in 20 second increments.

Users are then given the option to 'Stay signed in', which extends their session by the timeout duration, or 'Sign out'
returning them to the supplied `signOutUrl`.

#### Integrating with the timeout dialog

The instructions below assume you have set up play-frontend-hmrc as indicated above.

1. Identify the `signOutUrl` that should be used when users click 'Sign Out' on the timeout dialog. Your service may already be
   supplying a `signOutUrl` parameter to the `HmrcStandardHeader` component, which controls the sign out link in the GOV.UK
   header. Reusing this value may be a sensible choice. Refer to guidance above to understand how this argument is used by the
   timeout dialog.

1. Update your layout template to pass in the `HmrcTimeoutDialogHelper` in the HEAD element, supplying the signOutUrl as
   a parameter. For example if using `HmrcStandardPage`, pass `Some(HmrcTimeoutDialogHelper(signOutUrl = signOutUrl))` in the
   `templateOverrides.additionalHeadBlock` parameter.

#### Synchronising session between tabs

By default, `play-frontend-hmrc` synchronises session extension between different HMRC tabs (using the `BroadcastChannel`
API in browsers). In practical terms, this means that if a user sees a timeout dialog in an active tab, and clicks to
extend their session, then the timeout dialogs that have also opted into this behaviour in any background tabs will also restart the countdowns until they display their timeout warning.

This behaviour is currently flagged **on** (`true`) by default. To disable, you can either explicitly pass `Some(false)`
to the `HmrcTimeoutDialogHelper`, or you can add a boolean `false` to your `application.conf` with the key
`hmrc-timeout-dialog.enableSynchroniseTabs`.

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
