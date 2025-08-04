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
## Table of Contents

- [Getting started](#getting-started)
  - [Compatible Scala and Play Framework versions](#compatible-scala-and-play-framework-versions)
  - [Understanding library changes between versions](#understanding-library-changes-between-versions)
  - [Integrating with play-frontend-hmrc](#integrating-with-play-frontend-hmrc)
  - [Troubleshooting](#troubleshooting)
  - [Useful Links](#useful-links)
- [Using components](#using-components)
- [Creating pages](#creating-pages)
- [Advanced configuration](#advanced-configuration)
- [Owning team README](#owning-team-readme)
- [Getting help](#getting-help)
- [License](#license)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Getting started

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

## Using components
Information how to use some of the components like banners, implicit methods can be found in a separate Markdown file.

[Using components](docs/using-components.md)

## Creating pages
Information how to enable GOV.UK brand refresh, link your accessibility statement, GTM/tracking consent configuration can be found in a separate Markdown file.

[Creating pages](docs/creating-pages.md)

## Advanced configuration

Information how to add your own SASS compilation pipeline, configuring non-HMRC project to resolve play-frontend-hmrc artefacts or using Tudor Crown on GOV.UK and HMRC components can be found in a separate Markdown file.

[Advanced configuration](docs/advanced-configuration.md)

## Owning team README

Rationale for code and translation decisions, dependencies, as well as instructions for team members maintaining this repository can be found [here](/docs/maintainers/overview.md).

## Getting help

Please report any issues with this library in Slack at `#team-plat-ui`.

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
