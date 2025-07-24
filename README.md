# play-frontend-hmrc

This library contains all the Twirl components and helpers needed to implement frontend microservices on the HMRC
tax platform.

play-frontend-hmrc is a Scala Twirl implementation of
[govuk-frontend](https://www.github.com/alphagov/govuk-frontend) and
[hmrc-frontend](https://www.github.com/hmrc/hmrc-frontend), adding to it Play, HMRC and tax platform-specific
components and helpers that make the process
of implementing frontend microservices straightforward and idiomatic for Scala developers.


<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

- [Setup and getting started](#setup-and-getting-started)
  - [Compatible Scala and Play Framework versions](#compatible-scala-and-play-framework-versions)
  - [Understanding library changes between versions](#understanding-library-changes-between-versions)
  - [Integrating with play-frontend-hmrc](#integrating-with-play-frontend-hmrc)
- [Creating pages](#creating-pages)
  - [Using the HMRC standard page template](#using-the-hmrc-standard-page-template)
  - [Using the refreshed GOV.UK brand](#using-the-refreshed-govuk-brand)
  - [Adding a sidebar to your Layout](#adding-a-sidebar-to-your-layout)
  - [Linking to your accessibility statement](#linking-to-your-accessibility-statement)
  - [Allowing users to enable or disable tracking cookies](#allowing-users-to-enable-or-disable-tracking-cookies)
    - [Integrating with tracking consent](#integrating-with-tracking-consent)
    - [Adding GTM to internal services](#adding-gtm-to-internal-services)
  - [Warning users before timing them out](#warning-users-before-timing-them-out)
    - [Integrating with the timeout dialog](#integrating-with-the-timeout-dialog)
    - [Synchronising session between tabs](#synchronising-session-between-tabs)
    - [Customising the timeout dialog](#customising-the-timeout-dialog)
- [Using components](#using-components)
  - [Finding Twirl templates for GOV.UK and HMRC design system components](#finding-twirl-templates-for-govuk-and-hmrc-design-system-components)
  - [Using the components](#using-the-components)
  - [Useful implicits](#useful-implicits)
    - [withFormField](#withformfield)
    - [withHeading and withHeadingAndCaption](#withheading-and-withheadingandcaption)
    - [RichDateInput](#richdateinput)
    - [RichErrorSummary](#richerrorsummary)
    - [RichStringSupport](#richstringsupport)
  - [Adding a beta feedback banner](#adding-a-beta-feedback-banner)
  - [Adding a User Research Banner](#adding-a-user-research-banner)
  - [Helping users report technical issues](#helping-users-report-technical-issues)
  - [Adding a dynamic character count to a text input](#adding-a-dynamic-character-count-to-a-text-input)
  - [Adding accessible autocomplete to a select input](#adding-accessible-autocomplete-to-a-select-input)
    - [Transforming a `<select>` element into an Accessible Autocomplete element](#transforming-a-select-element-into-an-accessible-autocomplete-element)
  - [Opening links in a new tab](#opening-links-in-a-new-tab)
- [Advanced configuration](#advanced-configuration)
  - [Adding your own SASS compilation pipeline](#adding-your-own-sass-compilation-pipeline)
  - [Configuring non-HMRC projects to resolve play-frontend-hmrc artefacts](#configuring-non-hmrc-projects-to-resolve-play-frontend-hmrc-artefacts)
  - [Using the Tudor Crown on GOV.UK and HMRC components](#using-the-tudor-crown-on-govuk-and-hmrc-components)
- [Getting help](#getting-help)
  - [Troubleshooting](#troubleshooting)
  - [Useful Links](#useful-links)
- [Owning team README](#owning-team-readme)
- [License](#license)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Setup and getting started

### Compatible Scala and Play Framework versions

This library is currently compatible with:
* Play 3.0 - Scala 2.13 / 3
* Play 2.9 - Scala 2.13

### Understanding library changes between versions

We summarise what's changed between versions, and importantly any actions that may be required when upgrading past a specific version within our [changelog](CHANGELOG.md).

### Integrating with play-frontend-hmrc

1. Add the library to the project dependencies:
    ```scala
    libraryDependencies += "uk.gov.hmrc" %% "play-frontend-hmrc-play-xx" % "x.y.z"
    ```

   Where play-xx is your version of Play (e.g. play-30).

   Note, this has changed since 7.x.x, previously the play version was included in the artefact version, it is now included in the artefact name.

2. Add a route for the hmrc-frontend static assets in `conf/app.routes`:
    ```scala
    ->         /hmrc-frontend                      hmrcfrontend.Routes
    ```

3. Define your service name in your messages files. For example,
    ```scala
    service.name = Any tax service
    ```

   If you have a dynamic service name you can skip this step and pass the
   serviceName into `HmrcStandardPage` or `HmrcStandardHeader`.

4. Create a layout template for your pages using the [HMRC standard page](#using-the-hmrc-standard-page-template) template

5. Problems with styling? Check our [Troubleshooting](#troubleshooting) section.

## Creating pages

### Using the HMRC standard page template
The [`HmrcStandardPage`](play-frontend-hmrc-play-30/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcStandardPage.scala.html) helper
generates a standard HMRC page layout including the `HmrcStandardHeader`, `HmrcStandardFooter`, Welsh language toggle, and various banners.
This helper takes [`HmrcStandardPageParams`](play-frontend-hmrc-play-30/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/viewmodels/hmrcstandardpage/HmrcStandardPageParams.scala)
which includes the following members:
* [`ServiceURLs`](play-frontend-hmrc-play-30/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/viewmodels/hmrcstandardpage/ServiceURLs.scala) - containing service-specific URLs that will typically need setting once
* [`Banners`](play-frontend-hmrc-play-30/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/viewmodels/hmrcstandardpage/Banners.scala) - containing details of which banners should be displayed
* [`TemplateOverrides`](play-frontend-hmrc-play-30/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/viewmodels/hmrcstandardpage/TemplateOverrides.scala) - containing custom layout wrapper overrides or HTML to inject (perhaps set for the whole service, or on a page-by-page basis)

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


## Using components

### Finding Twirl templates for GOV.UK and HMRC design system components

We provide example templates using the Twirl components through a `Chrome` extension. Please refer to the
[extension’s github repository](https://github.com/hmrc/play-frontend-govuk-extension) for installation instructions.

With the extension installed, you can go to the
[GOVUK Design System components](https://design-system.service.gov.uk/components/)
or
[HMRC Design System patterns](https://design.tax.service.gov.uk/hmrc-design-patterns/)
pages, click on a component on the sidebar and see the `Twirl` examples matching the provided `Nunjucks` templates.

### Using the components

For information on how to use the components please see: [play-frontend-hmrc: Using components](COMPONENTS.md)

## Advanced configuration
### Adding your own SASS compilation pipeline

This library will manage SASS compilation for you. However, should you need for any reason to add your own SASS
compilation pipeline, follow the [steps detailed here](docs/maintainers/sass-compilation.md).

### Configuring non-HMRC projects to resolve play-frontend-hmrc artefacts

HMRC services get this configuration via the [sbt-auto-build library](https://github.com/hmrc/sbt-auto-build/blob/1bb9f5437ed5c2027b4c967585a2dd9a9a6740d0/src/main/scala/uk/gov/hmrc/SbtAutoBuildPlugin.scala#L55), external consumers will need to add the repository below to their SBT config themselves:

```scala
resolvers += MavenRepository("HMRC-open-artefacts-maven2", "https://open.artefacts.tax.service.gov.uk/maven2")
```

### Using the Tudor Crown on GOV.UK and HMRC components

As of February 2024, there is a requirement for Government departments to use the Tudor Crown logo for HRH King Charles
III. This new logo has been added into both the [govuk-frontend](https://github.com/alphagov/govuk-frontend) and
[hmrc-frontend](https://github.com/hmrc/hmrc-frontend) libraries. Additionally, the `hmrc-frontend` library has an updated
HMRC Crest roundel incorporating the Tudor Crown.

The Tudor Crown is available, and shown by default, in `v8.5.0` and higher of `play-frontend-hmrc`.

## Getting help

Please report any issues with this library in Slack at `#team-plat-ui`.

### Troubleshooting

If you are adding HTML elements to your page such as `<h1>` or `<p>`, you will need to add the CSS classes for the [GDS
Transport fonts](https://design-system.service.gov.uk/styles/typeface/) from the GOV.UK Design System. A full list of
the CSS classes can be found at https://design-system.service.gov.uk/styles/type-scale/.

These styles have already been applied to the components supplied in `play-frontend-hmrc`, but you will need to manually add the
styles to your service's own HTML elements.

### Useful Links

- [HMRC Design Patterns](https://design.tax.service.gov.uk/hmrc-design-patterns/) - documentation for the use of `hmrc-frontend` components
- [hmrc-frontend](https://github.com/hmrc/hmrc-frontend/) - reusable Nunjucks HTML components for HMRC design patterns
- [GOV.UK Design System](https://design-system.service.gov.uk/components/) - documentation for the use of `govuk-frontend` components
- [govuk-frontend](https://github.com/alphagov/govuk-frontend/) - reusable Nunjucks HTML components from GOV.UK
- [GOV.UK Design System Chrome extension](https://github.com/hmrc/play-frontend-govuk-extension) - `Chrome` extension to add a Twirl tab for each example in the GOV.UK Design System

## Owning team README

Rationale for code and translation decisions, dependencies, as well as instructions for team members maintaining this repository can be found [here](/docs/maintainers/overview.md).

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").


