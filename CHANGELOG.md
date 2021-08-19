# Change Log

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

For compatibility information see `govukFrontendVersion` and `hmrcFrontendVersion` in
[LibDependencies](project/LibDependencies.scala)

## [1.2.0] - 2021-08-19

### Changed

- Inlined the library play-frontend-govuk into play-frontend-hmrc. See 
[ADR](docs/maintainers/adr/0011-inline-play-frontend-govuk.md)

**Action required**:
- Remove any direct references to `play-frontend-govuk` from your `project/AppDependencies.scala` module.

## [1.1.0] - 2021-08-17

### Added

- Added `HmrcPageHeadingLabel` and `HmrcPageHeadingLegend` for constructing labels and legends as a hmrc heading with a
  section as a caption, see
  the [guidance in our readme for their usage](README.md#hmrcpageheadinglabel-and-hmrcpageheadinglegend)

### Compatible with

- [hmrc/hmrc-frontend v2.2.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.2.0)
- [hmrc/play-frontend-govuk v1.0.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v1.0.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.0.0] - 2021-08-17

### Changed

- Removed features originally added for Play 2.5 support
  - Deprecated static helpers
  - Twirl component type aliases
  - Component names starting with a lower-case letter

**Actions required**:
  - Convert all Twirl templates to use dependency injection with the `@this()` directive. See 
    [here](https://www.playframework.com/documentation/2.8.x/ScalaTemplatesDependencyInjection)
  - Replace any references to play-frontend component classes starting with a lower-case letter to upper-case.
    For example if you have references
    like `@this(govukButton: govukButton)` these will need changing to `@this(govukButton: GovukButton)`
  - Remove any instances of the wildcard import `uk.gov.hmrc.govukfrontend.views.html.helpers._`. You may
    find such references in your `build.sbt` file under `TwirlKeys.templateImports`.

### Compatible with

- [hmrc/hmrc-frontend v2.2.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.2.0)
- [hmrc/play-frontend-govuk v1.0.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v1.0.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.94.0] - 2021-08-12

### Changed

- Update `hmrcLayout` to use correct language for "Back" text on `BackLinks`

### Compatible with

- [hmrc/hmrc-frontend v2.2.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.2.0)
- [hmrc/play-frontend-govuk v0.83.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.83.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.93.0] - 2021-08-10

### Changed

- Using English values regardless of the component's language.

### Compatible with

- [hmrc/hmrc-frontend v2.2.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.2.0)
- [hmrc/play-frontend-govuk v0.83.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.83.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.92.0] - 2021-08-10

### Changed

- Added ADR for decision to create a session endpoint.

### Compatible with

- [hmrc/hmrc-frontend v2.0.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.0.0)
- [hmrc/play-frontend-govuk v0.83.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.83.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.91.0] - 2021-08-09

### Changed

- Added ADR for inlining play-frontend-govuk.

### Compatible with

- [hmrc/hmrc-frontend v2.0.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.0.0)
- [hmrc/play-frontend-govuk v0.83.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.83.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.90.0] - 2021-08-05

### Changed

- Updated version of `play-frontend-govuk` to 0.83.0
  - withFormField helpers will now return error messages with their default strings translated 

### Compatible with

- [hmrc/hmrc-frontend v2.0.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.0.0)
- [hmrc/play-frontend-govuk v0.83.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.83.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.89.0] - 2021-08-05

### Changed

- Added ADR and documentation around resolving ambiguous import compilation errors

### Compatible with

- [hmrc/hmrc-frontend v2.0.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.0.0)
- [hmrc/play-frontend-govuk v0.82.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.82.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.88.0] - 2021-07-28

### Changed

- Added `backLinkUrl` and `beforeContentBlock` parameter to `hmrcLayout`

### Compatible with

- [hmrc/hmrc-frontend v2.0.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.0.0)
- [hmrc/play-frontend-govuk v0.82.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.82.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.87.0] - 2021-07-28

### Changed

- Updated version of `play-frontend-govuk` to 0.82.0
- Refactored Rich implicits
- Removed duplicated code RichHtml, RichString, and RichOptionString

### Compatible with

- [hmrc/hmrc-frontend v2.0.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.0.0)
- [hmrc/play-frontend-govuk v0.82.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.82.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.86.0] - 2021-07-27

### Changed

- Updated version of `play-frontend-govuk` to 0.81.0
- Added `withFormFieldWithErrorAsHtml` for RichCharacterCount and RichDateInput

### Compatible with

- [hmrc/hmrc-frontend v2.0.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.0.0)
- [hmrc/play-frontend-govuk v0.81.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.81.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.85.0] - 2021-07-23

### Changed

- Added missing space before a nonce attribute in `hmrcHead` and `hmrcScripts`

### Compatible with

- [hmrc/hmrc-frontend v2.0.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.0.0)
- [hmrc/play-frontend-govuk v0.80.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.80.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.84.0] - 2021-07-21

### Changed

- Removed experimental warning for HMRC layout

### Compatible with

- [hmrc/hmrc-frontend v2.0.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.0.0)
- [hmrc/play-frontend-govuk v0.80.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.80.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.83.0] - 2021-07-13

### Changed

- Welsh translation and language parameter added to hmrcInternalHeader

### Compatible with

- [hmrc/hmrc-frontend v2.0.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.0.0)
- [hmrc/play-frontend-govuk v0.80.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.80.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.82.0] - 2021-07-08

### Fixed

- Added ability to pass custom `accessibilityStatementUrl` to `hmrcLayout`

### Compatible with

- [hmrc/hmrc-frontend v1.37.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.37.1)
- [hmrc/play-frontend-govuk v0.80.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.80.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)


## [0.81.0] - 2021-07-06

### Fixed

- Documentation housekeeping

### Compatible with

- [hmrc/hmrc-frontend v1.37.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.37.1)
- [hmrc/play-frontend-govuk v0.80.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.80.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.80.0] - 2021-07-05

### Added

- RichErrorSummary to hydrate an ErrorSummary with the errors in a Play form.

### Compatible with

- [hmrc/hmrc-frontend v1.37.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.37.1)
- [hmrc/play-frontend-govuk v0.79.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.79.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.79.0] - 2021-06-29

### Fixed

- Added static `HmrcLayout` template to `deprecatedPlay26Helpers`.

### Compatible with

- [hmrc/hmrc-frontend v1.37.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.37.1)
- [hmrc/play-frontend-govuk v0.79.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.79.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)


## [0.78.0] - 2021-06-29

### Changed

- Upgrade to govuk-frontend version 3.13.0

### Compatible with

- [hmrc/hmrc-frontend v1.37.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.37.1)
- [hmrc/play-frontend-govuk v0.79.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.79.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.77.0] - 2021-06-24

### Changed

- Remove hard dependency on the govuk-frontend webjar as per
  [ADR](https://github.com/hmrc/play-frontend-govuk/blob/master/docs/maintainers/adr/0004-remove-hard-dependency-on-the-govuk-frontend-webjar.md)
  
### Compatible with

- [hmrc/hmrc-frontend v1.36.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.36.0)
- [hmrc/play-frontend-govuk v0.78.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.78.0)
- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)

## [0.76.0] - 2021-06-21

### Changed

- README typo for hmrcLayout fixed

### Compatible with

- [hmrc/hmrc-frontend v1.36.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.36.0)
- [hmrc/play-frontend-govuk v0.77.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.77.0)
- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)


## [0.75.0] - 2021-06-21

### Changed

- README guidance for hmrcLayout updated

### Compatible with

- [hmrc/hmrc-frontend v1.36.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.36.0)
- [hmrc/play-frontend-govuk v0.77.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.77.0)
- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)

## [0.74.0] - 2021-06-18

### Changed

- Added hmrcLayout
- Update play-frontend-govuk to 0.77.0 to passing in of main content styling to govukTemplate

### Compatible with

- [hmrc/hmrc-frontend v1.36.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.36.0)
- [hmrc/play-frontend-govuk v0.77.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.77.0)
- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)

## [0.73.0] - 2021-06-18

### Fixed

- In RichDateInput, style all date inputs with `govuk-input--error` when a global date validation error
  occurs as per [GDS guidance](https://design-system.service.gov.uk/components/date-input/)

### Compatible with

- [hmrc/hmrc-frontend v1.36.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.36.0)
- [hmrc/play-frontend-govuk v0.75.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.75.0)
- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)

## [0.72.0] - 2021-06-15

### Added

- Added implicit class RichDateInput for hydrating a DateInput using a Play Field

### Compatible with

- [hmrc/hmrc-frontend v1.36.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.36.0)
- [hmrc/play-frontend-govuk v0.75.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.75.0)
- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)

## [0.71.0] - 2021-06-11

### Changed

- Added ADR for self-publishing webjar
- Update hmrc-frontend dependency to use self-published webjar

### Compatible with

- [hmrc/hmrc-frontend v1.36.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.36.0)
- [hmrc/play-frontend-govuk v0.75.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.75.0)
- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)

## [0.70.0] - 2021-06-10

### Changed

- Updated play-frontend-govuk to 0.75.0 to pull in welsh translation for skip to content link

### Compatible with

- [hmrc/hmrc-frontend v1.35.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.35.2)
- [hmrc/play-frontend-govuk v0.75.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.75.0)
- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)

## [0.69.0] - 2021-06-03

### Changed

- Updated hmrcTimeline so event content is output as html

### Compatible with

- [hmrc/hmrc-frontend v1.35.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.35.2)
- [hmrc/play-frontend-govuk v0.73.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.73.0)
- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)

## [0.68.0] - 2021-05-27

### Changed

- Uplifted version of `play-language` to major version 5.0.0

### Compatible with

- [hmrc/hmrc-frontend v1.35.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.35.2)
- [hmrc/play-frontend-govuk v0.73.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.73.0)
- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)


## [0.67.0] - 2021-05-26

### Added

- hmrcTimeline component

### Compatible with

- [hmrc/hmrc-frontend v1.35.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.35.2)
- [hmrc/play-frontend-govuk v0.73.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.73.0)
- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)

## [0.66.0] - 2021-05-24

### Changed

- Updated play-frontend-govuk to 0.73.0
- Updated hmrc-frontend to 1.35.2
- Changed hmrcAddToAList to use formWithCSRF including the novalidate attribute

### Compatible with

- [hmrc/hmrc-frontend v1.35.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.35.2)
- [hmrc/play-frontend-govuk v0.73.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.73.0)
- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)

## [0.65.0] - 2021-05-27

### Changed

- Support Play 2.8

### Compatible with

- [hmrc/hmrc-frontend v1.35.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.35.0)
- [hmrc/play-frontend-govuk v0.72.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.72.0)
- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)

## [0.64.0] - 2021-05-20

### Changed

- Updated play-frontend-govuk to 0.72.0
- Updated hmrc-frontend to 1.35.0

### Compatible with

- [hmrc/hmrc-frontend v1.35.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.35.0)
- [hmrc/play-frontend-govuk v0.72.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.72.0)
- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)

## [0.63.0] - 2021-05-14

### Changed

- Updated hmrc-frontend to 1.34.0
- Updated hmrcReportTechnicalIssue and hmrcReportTechnicalIssueHelper to point to new endpoint
  `/contact/report-technical-problem`

### Compatible with

- [hmrc/hmrc-frontend v1.34.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.34.0)
- [hmrc/play-frontend-govuk v0.71.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.71.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)


## [0.62.0] - 2021-05-10

### Added

- hmrcAddToAList component

## [0.61.0] - 2021-05-10

### Changed

- Update hmrc-frontend to 1.33.1 which contains some security and bug fixes for existing components

### Compatible with

- [hmrc/hmrc-frontend v1.33.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.33.1)
- [hmrc/play-frontend-govuk v0.71.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.71.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.60.0] - 2021-05-04

### Changed

- Add correct lang and hreflang attributes to Welsh language information link

### Compatible with

- [hmrc/hmrc-frontend v1.29.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.29.0)
- [hmrc/play-frontend-govuk v0.71.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.71.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.59.0] - 2021-04-23

### Changed

- Add Scala and Play framework compatibility notes to README

### Compatible with

- [hmrc/hmrc-frontend v1.29.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.29.0)
- [hmrc/play-frontend-govuk v0.71.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.71.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.58.0] - 2021-04-19

### Changed

- Upgrade play-frontend-govuk

### Compatible with

- [hmrc/hmrc-frontend v1.29.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.29.0)
- [hmrc/play-frontend-govuk v0.71.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.71.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.57.0] - 2021-03-19

Updated documentation

## [0.56.0] - 2021-03-19

Test formatting

## [0.55.0] - 2021-03-19

### Changed

- Added class to hmrcReportTechnicalIssue to support browser testing and bring us in line with hmrc-frontend 1.29.0

### Compatible with

- [hmrc/hmrc-frontend v1.29.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.29.0)
- [hmrc/play-frontend-govuk v0.67.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.67.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.54.0] - 2021-03-19

Nothing changed in the software, this was a change to the documentation.

## [0.53.0] - 2021-03-19

### Changed

- Added hmrcLanguageSelectHelper

## [0.52.0] - 2021-03-19

### Changed

- Added hmrcLanguageSelectHelper

### Compatible with

- [hmrc/hmrc-frontend v1.27.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.27.0)
- [hmrc/play-frontend-govuk v0.65.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.65.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.51.0] - 2021-03-11

### Changed

- Added hmrcCharacterCount with support for Welsh language

### Compatible with

- [hmrc/hmrc-frontend v1.27.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.27.0)
- [hmrc/play-frontend-govuk v0.65.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.65.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)


## [0.50.0] - 2021-03-09

### Changed

- Added Play 2.8 support

### Compatible with

- [hmrc/hmrc-frontend v1.26.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.26.2)
- [hmrc/play-frontend-govuk v0.65.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.65.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.49.0] - 2021-02-25

### Changed

- hmrcStandardFooter to support an off-platform accessibility statement url

### Compatible with

- [hmrc/hmrc-frontend v1.26.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.26.2)
- [hmrc/play-frontend-govuk v0.63.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.63.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.48.0] - 2021-02-25

### Added

- hmrcUserResearchBanner
- StandardPhaseBanner, StandardBetaBanner, StandardAlphaBanner

### Changed

- hmrcStandardHeader to support adding a phase and user research banner

### Compatible with

- [hmrc/hmrc-frontend v1.26.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.26.1)
- [hmrc/play-frontend-govuk v0.63.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.63.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.47.0] - 2021-02-25

### Fixed

- Support CSP nonce in hmrcHead and hmrcScripts.

### Compatible with

- [hmrc/hmrc-frontend v1.25.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.25.0)
- [hmrc/play-frontend-govuk v0.63.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.63.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.46.0] - 2021-02-23

### Fixed

- Bug with hmrcHead and hmrcScripts where asset routes are not configured correctly due to
the static nature of the initialisation of reverse routers.

### Compatible with

- [hmrc/hmrc-frontend v1.25.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.25.0)
- [hmrc/play-frontend-govuk v0.63.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.63.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.45.0] - 2021-02-23

### Fixed

- Ambiguous reference to buildinfo package causing unhelpful errors when
both play-frontend-govuk and play-frontend-hmrc are dependencies.

### Compatible with

- [hmrc/hmrc-frontend v1.25.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.25.0)
- [hmrc/play-frontend-govuk v0.63.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.63.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.44.0] - 2021-02-17

### Fixed

- Update hmrc-frontend version to properly include govuk-frontend v3.11.0.

### Compatible with

- [hmrc/hmrc-frontend v1.25.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.25.0)
- [hmrc/play-frontend-govuk v0.62.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.62.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.43.0] - 2021-02-15

### Added

- hmrcTimeoutDialogHelper

### Compatible with

- [hmrc/hmrc-frontend v1.24.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.24.0)
- [hmrc/play-frontend-govuk v0.62.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.62.0)
- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.42.0] - 2021-02-15

### Added

- hmrcHead and hmrcScripts helpers

### Compatible with

- [hmrc/hmrc-frontend v1.24.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.24.0)
- [hmrc/play-frontend-govuk v0.60.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.60.0)
- [alphagov/govuk-frontend v3.10.2](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.2)

## [0.41.0] - 2021-02-08

### Added

- hmrcStandardHeader helper

### Compatible with

- [hmrc/hmrc-frontend v1.23.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.23.1)
- [hmrc/play-frontend-govuk v0.60.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.60.0)
- [alphagov/govuk-frontend v3.10.2](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.2)

## [0.40.0] - 2021-02-04

### Fixed

- Removed unintentional references to non-dependency injected static objects in Twirl views.

### Compatible with

- [hmrc/hmrc-frontend v1.23.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.23.1)
- [hmrc/play-frontend-govuk v0.60.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.60.0)
- [alphagov/govuk-frontend v3.10.2](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.2)

## [0.39.0] - 2021-01-29

### Added

- ADR for adding compiled assets to hmrc-frontend

### Compatible with

- [hmrc/hmrc-frontend v1.23.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.23.1)
- [hmrc/play-frontend-govuk v0.60.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.60.0)
- [alphagov/govuk-frontend v3.10.2](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.2)

## [0.38.0] - 2021-01-21

### Changed

- SBT uplifted to 1.4.6

### Compatible with

- [hmrc/hmrc-frontend v1.23.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.23.1)
- [hmrc/play-frontend-govuk v0.60.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.60.0)
- [alphagov/govuk-frontend v3.10.2](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.2)

## [0.37.0] - 2021-01-16

### Changed

- Incremented version of play-frontend-govuk to 0.58.0

### Compatible with

- [hmrc/hmrc-frontend v1.23.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.23.0)
- [hmrc/play-frontend-govuk v0.58.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.58.0)
- [alphagov/govuk-frontend v3.10.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.1)

## [0.36.0] - 2021-01-15

### Changed

- Removed JsonDefaultValueFormatter following on from removal of Play 2.5

### Compatible with

- [hmrc/hmrc-frontend v1.23.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.23.0)
- [hmrc/play-frontend-govuk v0.57.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.57.0)
- [alphagov/govuk-frontend v3.10.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.1)

## [0.35.0] - 2021-01-07

### Changed

- Removed cross-compilation against Play 2.5
- Updated HMRC standard file headers to 2021

## [0.34.0] - 2020-12-17

### Added

- `hmrcHeader` - added documentation

## [0.33.0] - 2020-12-17

### Changed

- `hmrcTrackingConsentSnippet` - use relative URL to link to tracking consent

### Compatible with

- [hmrc/hmrc-frontend v1.23.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.23.0)
- [hmrc/play-frontend-govuk v0.56.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.56.0)
- [alphagov/govuk-frontend v3.10.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.1)

## [0.32.0] - 2020-12-11

### Changed

- `hmrcStandardFooter` - added Contact and Welsh information links

### Compatible with

- [hmrc/hmrc-frontend v1.23.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.23.0)
- [hmrc/play-frontend-govuk v0.56.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.56.0)
- [alphagov/govuk-frontend v3.10.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.1)

## [0.31.0] - 2020-12-10

### Added

- `hmrcReportTechnicalIssueHelper` - this is a helper which sets up the Report a Technical Issue component

### Compatible with

- [hmrc/hmrc-frontend v1.23.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.23.0)
- [hmrc/play-frontend-govuk v0.56.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.56.0)
- [alphagov/govuk-frontend v3.10.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.1)

## [0.30.0] - 2020-12-09

## Added

- `hmrcTimeoutDialog` - added documentation

### Compatible with

- [hmrc/hmrc-frontend v1.22.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.22.0)
- [hmrc/play-frontend-govuk v0.53.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.53.0)
- [alphagov/govuk-frontend v3.9.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.9.1)

## [0.29.0] - 2020-12-04

## Changed

- `hmrcHeader` - upgraded content as per hmrc-frontend v1.22.0

### Compatible with

- [hmrc/hmrc-frontend v1.22.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.22.0)
- [hmrc/play-frontend-govuk v0.53.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.53.0)
- [alphagov/govuk-frontend v3.9.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.9.1)

## [0.28.0] - 2020-12-01

## Changed 

- `hmrcReportTechnicalIssue` - updated content as per hmrc-frontend v1.21.0

### Compatible with

- [hmrc/hmrc-frontend v1.21.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.21.0)
- [hmrc/play-frontend-govuk v0.53.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.53.0)
- [alphagov/govuk-frontend v3.9.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.9.1)

## [0.27.0] - 2020-11-20

### Added

- `hmrcStandardFooter` - added support for additional footer items

### Compatible with

- [hmrc/hmrc-frontend v1.20.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.20.0)
- [hmrc/play-frontend-govuk v0.53.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.53.0)
- [alphagov/govuk-frontend v3.9.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.9.1)

## [0.26.0] - 2020-11-17

### Added

- `hmrcTrackingConsentSnippet` - added support for data-language

### Compatible with

- [hmrc/hmrc-frontend v1.20.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.20.0)
- [hmrc/play-frontend-govuk v0.53.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.53.0)
- [alphagov/govuk-frontend v3.9.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.9.1)

## [0.25.0] - 2020-11-12

### updated

- `hmrcReportTechnicalIssue` - added baseUrl, referrerUrl.  URL encoding service name.

### Compatible with

- [hmrc/hmrc-frontend v1.20.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.20.0)
- [hmrc/play-frontend-govuk v0.53.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.53.0)
- [alphagov/govuk-frontend v3.9.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.9.1)


## [0.24.0] - 2020-11-12

### updated

- Upgrading `govuk-frontend` version

### Compatible with

- [hmrc/hmrc-frontend v1.19.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.19.1)
- [hmrc/play-frontend-govuk v0.53.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.53.0)
- [alphagov/govuk-frontend v3.9.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.9.1)

## [0.23.0] - 2020-11-10

### Fixed

- hmrcTrackingConsentSnippet - default Nonce parameter to None

### Compatible with

- [hmrc/hmrc-frontend v1.19.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.19.0)
- [hmrc/play-frontend-govuk v0.49.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.49.0)
- [alphagov/govuk-frontend v3.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.7.0)

## [0.22.0] - 2020-11-06

### Added

- hmrcTrackingConsentSnippet

### Compatible with

- [hmrc/hmrc-frontend v1.19.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.19.0)
- [hmrc/play-frontend-govuk v0.49.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.49.0)
- [alphagov/govuk-frontend v3.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.7.0)

## [0.21.0] - 2020-11-05

### Updated

- hmrcFooter

### Fixed

- Moved the footer helper into a new `helpers` package and renamed it `HmrcStandardFooter`
- Ported the `hmrcFooter` from `hmrc-frontend`

### Compatible with

- [hmrc/hmrc-frontend v1.19.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.19.0)
- [hmrc/play-frontend-govuk v0.49.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.49.0)
- [alphagov/govuk-frontend v3.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.7.0)

## [0.20.0] - 2020-09-23

### Updated

- hmrcTimeoutDialog 
- hmrcReportTechnicalIssue
- hmrcNewTabLink

### Compatible with

- [hmrc/hmrc-frontend v1.17.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.17.0)
- [hmrc/play-frontend-govuk v0.49.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.49.0)
- [alphagov/govuk-frontend v3.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.7.0)

## [0.19.0] - 2020-09-10

### Fixed

- Added helper for Footers with standard configuration

### Compatible with

- [hmrc/hmrc-frontend v1.15.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.15.1)
- [hmrc/play-frontend-govuk v0.49.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.49.0)
- [alphagov/govuk-frontend v3.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.7.0)

## [0.18.0] - 2020-08-17

### Fixed

- Bug with hmrcHeader that threw a MatchError when serviceName was empty

### Compatible with

- [hmrc/hmrc-frontend v1.15.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.15.1)
- [hmrc/play-frontend-govuk v0.49.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.49.0)
- [alphagov/govuk-frontend v3.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.7.0)

## [0.17.0] - 2020-06-15

### Updated

- hmrcTimeoutDialog
- hmrcHeader
- hmrcNotificationBadge (input formats only)

### Compatible with

- [hmrc/hmrc-frontend v1.15.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.15.1)
- [hmrc/play-frontend-govuk v0.49.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.49.0)
- [alphagov/govuk-frontend v3.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.7.0)

## [0.16.0] - 2020-06-15

### Added

- Support for Play 2.7

### Compatible with

- [hmrc/hmrc-frontend v1.12.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.12.0)
- [hmrc/play-frontend-govuk v0.47.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.47.0)
- [alphagov/govuk-frontend v3.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.6.0)

## [0.15.0] - 2020-05-21

### Added

- Refactor build to remove dependency on x-frontend-snapshotter

### Compatible with

- [hmrc/hmrc-frontend v1.12.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.12.0)
- [hmrc/play-frontend-govuk v0.45.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.45.0)
- [alphagov/govuk-frontend v3.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.6.0)

## [0.14.0] - 2020-05-11

### Added

- hmrcCurrencyInput

### Compatible with

- [hmrc/hmrc-frontend v1.12.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.12.0)
- [hmrc/play-frontend-govuk v0.44.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.45.0)
- [alphagov/govuk-frontend v3.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.6.0)

## [0.13.0] - 2020-04-22

### Added

- hmrcReportTechnicalIssue
- hmrcLanguageSelect

### Compatible with

- [hmrc/hmrc-frontend v1.11.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.11.0)
- [alphagov/govuk-frontend v3.4.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.4.0)
