# Change Log

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

For compatibility information see `govukFrontendVersion` and `hmrcFrontendVersion` in
[LibDependencies](project/LibDependencies.scala)

## [9.11.0] - 2024-05-09

### Changed

- Added an `emptyItem` parameter to AccessibleAutocomplete to provide an item with no value to the top of a list.

### Compatible with

- [hmrc/hmrc-frontend v6.17.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v6.17.0)
- [alphagov/govuk-frontend v5.3.0](https://github.com/alphagov/govuk-frontend/releases/tag/v5.3.0)

## [9.10.0] - 2024-04-24

### Changed

- Added meta-tests to ensure fixture-driven unit tests and Scalacheck-driven integration tests cover all components
- Fixed up existing gaps in coverage highlighted by these new tests

### Compatible with

- [hmrc/hmrc-frontend v6.17.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v6.17.0)
- [alphagov/govuk-frontend v5.3.0](https://github.com/alphagov/govuk-frontend/releases/tag/v5.3.0)

## [9.9.0] - 2024-04-24

### Changed

- Deprecated the `CurrencyInput` / `HmrcCurrencyInput` component. This component is now replaced by using `Input` / `GovukInput`
  with prefix of `£`. Examples of the  new pattern can be seen at: 
  - HMRC Design Patterns: https://design.tax.service.gov.uk/hmrc-design-patterns/currency-input/
  - GOV.UK Design System: https://design-system.service.gov.uk/components/text-input/#prefixes-and-suffixes
  The `CurrencyInput` / `HmrcCurrencyInput` component will be removed in a future library version.

### Compatible with

- [hmrc/hmrc-frontend v6.17.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v6.17.0)
- [alphagov/govuk-frontend v5.3.0](https://github.com/alphagov/govuk-frontend/releases/tag/v5.3.0)

## [9.8.0] - 2024-04-19

### Changed

- Added beforeInput(s) and afterInput(s) options to form groups
- Compress whitespace in integration tests (test change only)

### Compatible with

- [hmrc/hmrc-frontend v6.15.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v6.15.0)
- [alphagov/govuk-frontend v5.3.0](https://github.com/alphagov/govuk-frontend/releases/tag/v5.3.0)

## [9.7.0] - 2024-04-15

### Changed

- Added in caching with a max-age of 60 minutes for hmrc-frontend resources

### Compatible with

- [hmrc/hmrc-frontend v6.15.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v6.15.0)
- [alphagov/govuk-frontend v5.3.0](https://github.com/alphagov/govuk-frontend/releases/tag/v5.3.0)

## [9.6.0] - 2024-04-10

### Changed

- Release new version of play-frontend-hmrc that includes govuk-frontend 5.3.0
- Added ADR for [deferring the inclusion of the password field](./docs/maintainers/adr/0021-defer-inclusion-of-password-field.md) from govuk-frontend v5.3.0

### Compatible with

- [hmrc/hmrc-frontend v6.15.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v6.15.0)
- [alphagov/govuk-frontend v5.3.0](https://github.com/alphagov/govuk-frontend/releases/tag/v5.3.0)

## [9.5.0] - 2024-04-3

### Changed

- Pulled in styling fix for `HmrcAccountMenu` accessibility for Windows High Contrast Mode for submenu items

### Compatible with

- [hmrc/hmrc-frontend v6.14.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v6.14.0)
- [alphagov/govuk-frontend v5.2.0](https://github.com/alphagov/govuk-frontend/releases/tag/v5.2.0) 

## [9.4.0] - 2024-03-27

### Changed

- Added support for custom attributes on `govuk-form-group` wrappers in various input components
- Updated form group customisation model to mirror govuk-frontend

> [!WARNING]  
> If you are passing custom classes via `formGroupClasses` you must update these to use the new `FormGroup` model

eg. from

```scala
  val foo = Checkboxes(
    ...
    formGroupClasses = "my-custom classes",
    ...
  )
```
to
```scala
  val foo = Checkboxes(
    ...
    formGroup = FormGroup(classes = Some("my-custom classes")),
    ...
  )
```

### Compatible with

- [hmrc/hmrc-frontend v6.11.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v6.11.0)
- [alphagov/govuk-frontend v5.2.0](https://github.com/alphagov/govuk-frontend/releases/tag/v5.2.0)

## [9.3.0] - 2024-03-25

### Changed

- Updated `HmrcHeader` template to align with `GovukHeader`, and to take in optional `menuButtonText`, `menuButtonLabel`
  and `navigationLabel` parameters
- Removed `hmrc-header__service-name` and `hmrc-header__service-name--linked` classes from `HmrcHeader`
- Added Welsh translation for `Choose a language`

### Compatible with

- [hmrc/hmrc-frontend v6.11.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v6.11.0)
- [alphagov/govuk-frontend v5.2.0](https://github.com/alphagov/govuk-frontend/releases/tag/v5.2.0)

## [9.2.0] - 2024-03-22

### Changed

- Added default config entries for optimizely to show that our library supports it, and avoid services using it
  getting "NotOverriding" config warnings

### Compatible with

- [hmrc/hmrc-frontend v6.4.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v6.4.0)
- [alphagov/govuk-frontend v5.2.0](https://github.com/alphagov/govuk-frontend/releases/tag/v5.2.0)

## [9.1.0] - 2024-03-19

### Changed

- Updated `TaskList` viewmodel, updated missing Aliases

### Compatible with

- [hmrc/hmrc-frontend v6.4.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v6.4.0)
- [alphagov/govuk-frontend v5.2.0](https://github.com/alphagov/govuk-frontend/releases/tag/v5.2.0)


## [9.0.0] - 2024-03-11

### Changed

- Uplifted v5.2.0 of `govuk-frontend` and v6.4.0 of `hmrc-frontend`. Added GovukTaskList, reduced Internet Explorer 
  support. Major breaking release. 
- Please read the [v9.0.0 release notes](https://github.com/hmrc/play-frontend-hmrc/releases/tag/v9.0.0)
  when uplifting your frontend service, and check that it still works as expected, particularly if using custom Javascript / CSS.

### Compatible with

- [hmrc/hmrc-frontend v6.4.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v6.4.0)
- [alphagov/govuk-frontend v5.2.0](https://github.com/alphagov/govuk-frontend/releases/tag/v5.2.0)

## [8.5.0] - 2024-02-12

### Changed

- Feature flag introduced for Tudor Crown icon switch over

### Compatible with

- [hmrc/hmrc-frontend v5.66.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.66.0)
- [alphagov/govuk-frontend v4.8.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.8.0)

## [8.4.0] - 2024-01-24

### Changed

- Removed unused `summaryList.Card.content` parameter to prevent confusion

### Compatible with

- [hmrc/hmrc-frontend v5.62.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.62.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)

## [8.3.0] - 2024-01-04

### Changed

- Uplifted version of `hmrc-frontend` to `5.62.0` to pull in new stylesheet `hmrc-frontend-print-overrides.scss`

### Compatible with

- [hmrc/hmrc-frontend v5.62.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.62.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)

## [8.2.0] - 2023-12-13

### Changed

- Uplifted version of `hmrc-frontend` to `5.61.0` to pull in link scaling fix for `account-menu` / `HmrcAccountMenu`

### Compatible with

- [hmrc/hmrc-frontend v5.61.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.61.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)

## [8.1.0] - 2023-11-27

### Changed

- Uplifted version of `hmrc-frontend` to `5.59.0` to pull in accessible-autocomplete fix for Welsh content

### Compatible with

- [hmrc/hmrc-frontend v5.59.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.59.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)

## [8.0.0] - 2023-11-22

### Changed

- Cross built for Play 2.9 as well as Play 2.8
- The play version is included in the artefact name rather than the version.
  ```scala
  libraryDependencies += "uk.gov.hmrc" %% "play-frontend-hmrc" % "x.y.z-play-28"
  ```
  is replaced by
  ```scala
   libraryDependencies += "uk.gov.hmrc" %% "play-frontend-hmrc-play-28" % "x.y.z"
  ```

## [7.30.0] - 2023-11-22

### Changed

- Test refactoring

### Compatible with

- [hmrc/hmrc-frontend v5.58.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.58.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)


## [7.29.0] - 2023-11-21

### Changed

- Uplifted version of `hmrc-frontend` to `5.58.0`
- Updated `HmrcLanguageSelect` and `HmrcReportTechnicalIssue` to not display on print views via `govuk-!-display-none-print`

### Compatible with

- [hmrc/hmrc-frontend v5.58.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.58.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)

## [7.28.0] - 2023-11-17

### Changed

- Updated Welsh translations for `HMRC` and `HM Revenue & Customs`

### Compatible with

- [hmrc/hmrc-frontend v5.57.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.57.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)

## [7.27.0] - 2023-11-09

### Changed

- Uplifted version of `hmrc-frontend` to `5.56.0`
- Updated `RichSelectSupport`'s `asAccessibleAutocomplete` method to pull language from implicit `Messages`

### Compatible with

- [hmrc/hmrc-frontend v5.56.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.56.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)

## [7.26.0] - 2023-11-03

### Changed

- Uplifted version of `hmrc-frontend` to `5.55.0`
- Updated aria-label on HMRC Header nav/button, to be accessible to voice activation users

### Compatible with

- [hmrc/hmrc-frontend v5.55.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.55.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)

## [7.25.0] - 2023-11-03

### Changed

- Uplifted version of `hmrc-frontend` to `5.53.0`
- Updated `TimeoutDialog` to reflect the new experimental feature of `timeout-dialog`

### Compatible with

- [hmrc/hmrc-frontend v5.53.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.53.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)

## [7.24.0] - 2023-10-30

### Changed

- Added Welsh translation of `Back` to `GovukBackLink`
- Require an implicit instance of `Messages` to be passed through the `GovukBackLink` component
- Uplifted version of `hmrc-frontend` to `5.51.0`

### Compatible with

- [hmrc/hmrc-frontend v5.51.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.51.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)

## [7.23.0] - 2023-10-09

### Changed

- Added Welsh translation of default title `Contents` to `GovukTabs`
- Change type of `title` from `String` to `Option[String]` in `Tab` to support the translation change
- Require an implicit instance of `Messages` to be passed through the `GovukTabs` component

### Compatible with

- [hmrc/hmrc-frontend v5.49.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.49.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)

## [7.22.0] - 2023-10-05

### Changed

- Added wrapper around deprecated HmrcLayout to surface deprecation warning that was being swallowed by Play

### Compatible with

- [hmrc/hmrc-frontend v5.49.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.49.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)


## [7.21.0] - 2023-09-29

### Changed

- Uplifted version of `hmrc-frontend` to `5.49.0`, to allow people the use of the utility class to hide stuff when javascript is disabled

### Compatible with

- [hmrc/hmrc-frontend v5.49.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.49.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)


## [7.20.0] - 2023-09-18

### Changed

- Uplifted version of `hmrc-frontend` to `5.48.0`, to fix regression with .js-hidden

### Compatible with

- [hmrc/hmrc-frontend v5.48.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.48.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)


## [7.19.0] - 2023-08-11

### Changed

- Uplifted version of `hmrc-frontend` to `5.46.0`, for backlink to be hidden when referrer is on a different domain

### Compatible with

- [hmrc/hmrc-frontend v5.46.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.46.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)


## [7.18.0] - 2023-08-10

### Changed

- Updated version of `hmrc-frontend` to [v5.45.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.45.0)
- As part of above uplift, Webjar for `accessible-autocmplete` removed from `sbt` dependencies. If required, can be
  manually added to dependencies as `org.webjars.npm % accessible-autocomplete % xx.xx.xx`, where `xx.xx.xx` is the
  [latest version](https://mvnrepository.com/artifact/org.webjars.npm/accessible-autocomplete)

### Compatible with

- [hmrc/hmrc-frontend v5.45.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.45.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)


## [7.17.0] - 2023-08-03

### Changed

- Updated version of `hmrc-frontend` to [v5.44.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.44.0)

### Compatible with

- [hmrc/hmrc-frontend v5.44.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.44.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)


## [7.16.0] - 2023-07-25

### Changed

- Included `ExitThePage` component from `govuk-frontend` into `HmrcStandardPage` with documentation

### Compatible with

- [hmrc/hmrc-frontend v5.41.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.41.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)


## [7.15.0] - 2023-07-21

### Changed

- Updated version of `govuk-frontend` to [v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)
- Added new `ExitThePage` component from `govuk-frontend`, with Welsh translations of default text

### Compatible with

- [hmrc/hmrc-frontend v5.41.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.41.0)
- [alphagov/govuk-frontend v4.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.7.0)


## [7.14.0] - 2023-06-27

### Changed

- Add Welsh translations for default content in [GovukWarningText](src/main/twirl/uk/gov/hmrc/govukfrontend/views/components/GovukWarningText.scala.html)

### Compatible with

- [hmrc/hmrc-frontend v5.37.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.37.0)
- [alphagov/govuk-frontend v4.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.6.0)

## [7.13.0] - 2023-06-19

### Changed

- Use `LazyList` instead of deprecated `Stream` (using `scala-collection-compat` library for backward compatibility with Scala 2.12)

### Compatible with

- [hmrc/hmrc-frontend v5.37.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.37.0)
- [alphagov/govuk-frontend v4.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.6.0)

## [7.12.0] - 2023-06-14

### Changed

- Add Welsh translations for default content in [GovukPagination](src/main/twirl/uk/gov/hmrc/govukfrontend/views/components/GovukPagination.scala.html)

### Compatible with

- [hmrc/hmrc-frontend v5.37.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.37.0)
- [alphagov/govuk-frontend v4.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.6.0)

## [7.11.0] - 2023-06-13

### Changed

- Restore previous behaviour of `mainContentLayout` and `pageLayout` in
[HmrcLayout](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcLayout.scala.html) helper.
In a previous version (v6.3.0)[https://github.com/hmrc/play-frontend-hmrc/releases/tag/v6.3.0],
the semantics of passing None changed unintentionally - from causing no layout to be applied, to falling back to applying the default layouts.
If you pass None to either of these parameters, please double-check your usage after upgrading to this version,
to ensure there are no unexpected layout changes as a result.

### Compatible with

- [hmrc/hmrc-frontend v5.37.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.37.0)
- [alphagov/govuk-frontend v4.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.6.0)

## [7.10.0] - 2023-06-08

### Changed

- Add Welsh translations for default content in [GovukNotificationBanner](src/main/twirl/uk/gov/hmrc/govukfrontend/views/components/GovukNotificationBanner.scala.html)

### Compatible with

- [hmrc/hmrc-frontend v5.37.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.37.0)
- [alphagov/govuk-frontend v4.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.6.0)

## [7.9.0] - 2023-06-07

### Changed

- Uplifted version of `hmrc-frontend` to `5.37.0`, to enable new GOVUK link styling

### Compatible with

- [hmrc/hmrc-frontend v5.37.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.37.0)
- [alphagov/govuk-frontend v4.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.6.0)

## [7.8.0] - 2023-06-02

### Changed

- Updated the position of the user research banner in the HmrcHeader component to comply with design system guidance
- Ensured that auto-generated integration test examples encode HTML (test code only change)

### Compatible with

- [hmrc/hmrc-frontend v5.36.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.36.0)
- [alphagov/govuk-frontend v4.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.6.0)

## [7.7.0] - 2023-05-03

### Changed

- Add empty alt attribute to logo IE8 fallback PNG IN `HmrcHeader`

### Compatible with

- [hmrc/hmrc-frontend v5.31.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.31.0)
- [alphagov/govuk-frontend v4.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.6.0)

## [7.6.0] - 2023-05-02

### Changed

- Add empty alt attribute to logo IE8 fallback PNG IN `GovukHeader`

### Compatible with

- [hmrc/hmrc-frontend v5.30.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.30.0)
- [alphagov/govuk-frontend v4.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.6.0)

## [7.5.0] - 2023-04-27

### Changed

- Uplifted version of `govuk-frontend` to `4.6.0`
- Uplifted version of `hmrc-frontend` to `5.30.0`

### Compatible with

- [hmrc/hmrc-frontend v5.30.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.30.0)
- [alphagov/govuk-frontend v4.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.6.0)

## [7.4.0] - 2023-04-24

### Changed

- Updated hmrc-frontend version to 5.28.0 to get a fix for a timeout dialog bug
  which could cause the timeout dialog to get stuck in an infinite loop and
  start counting backwards if the user timed out and the timeout redirection
  request took longer than a second to load.

### Compatible with

- [hmrc/hmrc-frontend v5.28.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.28.0)
- [alphagov/govuk-frontend v4.5.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.5.0)

## [7.3.0] - 2023-03-28

### Changed

- Fixed a bug with HmrcAccessibleAutocompleteJavascript introduced in v7.0.0 that prevented the autocomplete javascript
  from loading causing fields to fallback to select menus.

### Compatible with

- [hmrc/hmrc-frontend v5.26.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.26.0)
- [alphagov/govuk-frontend v4.5.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.5.0)


## [7.2.0] - 2023-03-28

### Changed

- Fixed a bug with the divider RadioItem being marked as checked when `withFormField(field)` is provided a field with a value set to None

### Compatible with

- [hmrc/hmrc-frontend v5.26.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.26.0)
- [alphagov/govuk-frontend v4.5.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.5.0)

## [7.1.0] - 2023-03-17

### Changed

- Updated markup for the HmrcUserResearchBanner component
- Added hideCloseButton parameter to UserResearchBanner viewmodel
- Bumped hmrc-frontend version to v5.26.0

### Compatible with

- [hmrc/hmrc-frontend v5.26.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.26.0)
- [alphagov/govuk-frontend v4.5.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.5.0)


## [7.0.0] - 2023-03-15

### Changed

- Removed explicit `cspNonce` parameter from various templates - get it from the current request instead

### Compatible with

- [hmrc/hmrc-frontend v5.24.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.24.0)
- [alphagov/govuk-frontend v4.5.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.5.0)


## [6.8.0] - 2023-03-08

### Changed

- Added guidance on XSS prevention to README.
- Reorganised README into more cohesive sections, and used [doctoc](https://github.com/thlorenz/doctoc) to generate the table of contents.

### Compatible with

- [hmrc/hmrc-frontend v5.24.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.24.0)
- [alphagov/govuk-frontend v4.5.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.5.0)


## [6.7.0] - 2023-02-23

### Changed

- Uplifted version of `govuk-frontend` to `4.5.0`
- Uplifted version of `hmrc-frontend` to `5.24.0`
- Added new summary `Card` functionality to `SummaryList`, as implemented in `govuk-frontend`
- Patched tests for `GovukTemplate`. `govuk-frontend` now allows passing in of custom Opengraph URL, but at this time we
have decided not to replicate in `play-frontend-hmrc`

### Compatible with

- [hmrc/hmrc-frontend v5.24.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.24.0)
- [alphagov/govuk-frontend v4.5.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.5.0)


## [6.6.0] - 2023-02-20

### Changed

- Updated the `HmrcCharacterCount` to use the underlying `GovukCharacterCount`, with translations passed in.
- As part of `HmrcCharacterCount` changes, classes on the `HmrcCharacterCount` have now changed from `hmrc-character-count`
to `govuk-character-count`. Tests relying on classes applied to the character, such as UI tests, may need to be updated
to reflect this.

### Compatible with

- [hmrc/hmrc-frontend v5.23.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.23.0)
- [alphagov/govuk-frontend v4.4.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.4.1)


## [6.5.0] - 2023-02-16

### Changed

- Added Welsh translations for `Menu` text and aria labels to the `HmrcHeader` component.

### Compatible with

- [hmrc/hmrc-frontend v5.21.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.21.0)
- [alphagov/govuk-frontend v4.4.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.4.1)


## [6.4.0] - 2023-02-06

### Changed

- Added `bodyAttributes` option parameter to the `GovukTemplate`. You can now set attributes in the `<body>` element of
  page template

### Compatible with

- [hmrc/hmrc-frontend v5.19.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.19.0)
- [alphagov/govuk-frontend v4.4.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.4.1)


## [6.3.0] - 2023-01-23

### Changed

- Deprecated `HmrcLayout` template; added new `HmrcStandardPage` template to replace it.

### Compatible with

- [hmrc/hmrc-frontend v5.19.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.19.0)
- [alphagov/govuk-frontend v4.4.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.4.1)


## [6.2.0] - 2023-01-12

### Changed

- Added new `GovukPagination` Twirl component with `Pagination` view from `govuk-frontend`.

### Compatible with

- [hmrc/hmrc-frontend v5.19.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.19.0)
- [alphagov/govuk-frontend v4.4.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.4.1)


## [6.1.0] - 2023-01-12

### Changed

- Documentation change: `CHANGELOG.md` update regarding `h2` elements in `GovukErrorSummary`

### Compatible with

- [hmrc/hmrc-frontend v5.19.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.19.0)
- [alphagov/govuk-frontend v4.4.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.4.1)


## [6.0.0] - 2023-01-09

### Changed

- Added `values` parameter to [GovukCheckboxes](src/main/twirl/uk/gov/hmrc/govukfrontend/views/components/GovukCheckboxes.scala.html) component, as an alternative way of pre-checking checkbox items
- Added `value` parameter to [GovukRadios](src/main/twirl/uk/gov/hmrc/govukfrontend/views/components/GovukRadios.scala.html) component, as an alternative way of pre-checking a radio item
- Changed [GovukRadios](src/main/twirl/uk/gov/hmrc/govukfrontend/views/components/GovukRadios.scala.html) component to throw if more than one `RadioItem` is `checked`
- Added `value` parameter to [GovukSelect](src/main/twirl/uk/gov/hmrc/govukfrontend/views/components/GovukSelect.scala.html) component, as an alternative way of pre-selecting a select item
- Updated `README` to include links on CSP configuration for Google Analytics 4
- Updated maintainers document for upgrading, to include the process for identifying Govuk component updates

### Compatible with

- [hmrc/hmrc-frontend v5.19.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.19.0)
- [alphagov/govuk-frontend v4.4.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.4.1)


## [5.5.0] - 2023-01-04

### Changed

- Updated version of `hmrc-frontend` to `5.19.0`
- Updated `HmrcFooter` and `StandardPhaseBanner` to include the class `govuk-!-display-none-print` for improved
  accessibility

### Compatible with

- [hmrc/hmrc-frontend v5.19.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.19.0)
- [alphagov/govuk-frontend v4.4.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.4.1)


## [5.4.0] - 2023-01-03

### Changed

- Auto-update of copyright headers only

### Compatible with

- [hmrc/hmrc-frontend v5.16.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.16.0)
- [alphagov/govuk-frontend v4.4.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.4.1)


## [5.3.0] - 2022-12-22

### Changed

- Updated `hmrc-frontend` to v5.16.0
- Harmonised wording of links that "open in new tab" to align with GDS/WLU guidance

### Compatible with

- [hmrc/hmrc-frontend v5.16.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.16.0)
- [alphagov/govuk-frontend v4.4.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.4.1)


## [5.2.0] - 2022-12-19

### Changed

- Update `hmrc-frontend` to v5.15.0 to pull in accessibility bugfix from `govuk-frontend` v4.4.1
- Documentation change: added steps to publish `govuk-frontend` as a WebJar to `upgrading.md`

### Compatible with

- [hmrc/hmrc-frontend v5.15.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.15.0)
- [alphagov/govuk-frontend v4.4.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.4.1)


## [5.1.0] - 2022-12-15

### Changed

- Changed default value of `disableAutoFocus` in `ErrorSummary` to reflect `govuk-frontend` logic

### Compatible with

- [hmrc/hmrc-frontend v5.14.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.14.0)
- [alphagov/govuk-frontend v4.4.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.4.0)


## [5.0.0] - 2022-12-14

### Changed

- Updated `play-language` to v6.0.0

### Breaking changes

[play-language](https://github.com/hmrc/play-language) no longer depends on the deprecated [url-builder](https://github.com/hmrc/url-builder) library,
so, if your service depends on url-builder, you'll need to [add a direct dependency](https://github.com/hmrc/url-builder#adding-to-your-service) in your `sbt` setup.

### Compatible with

- [hmrc/hmrc-frontend v5.14.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.14.0)
- [alphagov/govuk-frontend v4.4.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.4.0)

## [4.1.0] - 2022-12-13

### Changed

- Updated `hmrc-frontend` to v5.14.0 to fix issue with timeout dialog

### Compatible with

- [hmrc/hmrc-frontend v5.14.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.14.0)
- [alphagov/govuk-frontend v4.4.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.4.0)


## [4.0.0] - 2022-12-12

### Changed

- Updated `hmrc-frontend` to v5.12.0

### Breaking changes

You must make the following changes when you migrate to this release.

- Govuk design system team removed the id attribute from the GovukErrorSummary heading, if you were using this as a selector in your tests you should now use the class `govuk-error-summary__title` instead.
- GovukErrorSummary now needs an `Option` value for the `disableAutoFocus` field in the `ErrorSummary` model.
- GovukButton now needs an `Option` value for the `preventDoubleClick` field in the `Button` model.

### Compatible with

- [hmrc/hmrc-frontend v5.12.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.12.0)
- [alphagov/govuk-frontend v4.4.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.4.0)

## [3.34.0] - 2022-11-24

### Changed

- Updated `hmrc-frontend` to v5.11.3

### Compatible with

- [hmrc/hmrc-frontend v5.11.3](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.11.3)
- [alphagov/govuk-frontend v4.3.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.3.1)


## [3.33.0] - 2022-11-11

### Changed

- Updated the Play `sbt-plugin` to v2.8.18

### Compatible with

- [hmrc/hmrc-frontend v5.11.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.11.1)
- [alphagov/govuk-frontend v4.3.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.3.1)


## [3.32.0] - 2022-10-07

### Changed

- Updated `hmrc-frontend` to v5.11.1, the new accessible autocomplete css was pulling in webfonts by mistake causing network errors where they weren't available at the path expected by govuk-frontend config.

### Compatible with

- [hmrc/hmrc-frontend v5.11.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.11.1)
- [alphagov/govuk-frontend v4.3.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.3.1)

## [3.31.0] - 2022-10-04

### Changed

- `README` updated

### Compatible with

- [hmrc/hmrc-frontend v5.11.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.11.0)
- [alphagov/govuk-frontend v4.3.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.3.1)

## [3.30.0] - 2022-10-04

### Changed

- Added an implicit helper method `asAccessibleAutocomplete` for the `Select` component, that transforms it into an accessible autocomplete component.
- Updated `hmrc-frontend` to v5.11.0

### Compatible with

- [hmrc/hmrc-frontend v5.11.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.11.0)
- [alphagov/govuk-frontend v4.3.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.3.1)

## [3.29.0] - 2022-09-28

### Changed

- Added the ability to pass a custom `BackLink` to `HmrcLayout` instead of just a `backUrl`
- Added a helper `BackLink.mimicsBrowserBackButtonViaJavaScript` which mimics the browser Back button
if the referrer is on the same domain
- Updated `hmrc-frontend` to v5.10.0

### Compatible with

- [hmrc/hmrc-frontend v5.10.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.10.0)
- [alphagov/govuk-frontend v4.3.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.3.1)

## [3.28.0] - 2022-09-23

### Changed

- Added in helpers to add CSS and Javascript for `accessible-autocomplete` from `hmrc-frontend`
- Updated `hmrc-frontend` to v5.8.0

### Compatible with

- [hmrc/hmrc-frontend v5.8.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.8.0)
- [alphagov/govuk-frontend v4.3.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.3.1)

## [3.27.0] - 2022-09-15

### Changed

- Test coverage review

### Compatible with

- [hmrc/hmrc-frontend v5.7.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.7.0)
- [alphagov/govuk-frontend v4.3.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.3.1)


## [3.26.0] - 2022-09-15

### Changed

- Documentation update only

### Compatible with

- [hmrc/hmrc-frontend v5.7.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.7.0)
- [alphagov/govuk-frontend v4.3.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.3.1)

## [3.25.0] - 2022-09-12

### Changed

- Updated `hmrc-frontend` to v5.7.0
- Add `data-synchronise-tabs` to `HmrcTimeoutDialog` and `synchroniseTabs` to `TimeoutDialog.scala`
- Add `synchroniseTabs` to `HmrcTimeoutDialogHelper` and use `reference.conf`

### Compatible with

- [hmrc/hmrc-frontend v5.7.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.7.0)
- [alphagov/govuk-frontend v4.3.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.3.1)

## [3.24.0] - 2022-08-31

### Changed

- Updated `hmrc-frontend` to v5.5.0
- Use `BLOCK_TAGS_MAX` instead of `ALL_TAGS` when compressing HTML, to properly compare whitespace that could affect inline layout
- Fix existing twirl templates / patched examples to align with nunjucks output (previously tests were passing because we weren't considering significant whitespace)
- Reinstate previously-skipped examples where unit tests now pass

### Compatible with

- [hmrc/hmrc-frontend v5.5.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.5.0)
- [alphagov/govuk-frontend v4.3.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.3.1)

## [3.23.0] - 2022-08-22

### Changed

- Updated `hmrc-frontend` to v5.4.0
- Updated `govuk-frontend` to v4.3.1
- Corrected broken link in `README` for `govuk-frontend` repository on Github
- Updated `README` to include more information on the User Research Banner.

### Compatible with

- [hmrc/hmrc-frontend v5.4.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.4.0)
- [alphagov/govuk-frontend v4.3.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.3.1)

## [3.22.0] - 2022-06-14

### Changed

- Updated `hmrc-frontend` to v5.3.0
- Updated `govuk-frontend` to v4.2.0

### Compatible with

- [hmrc/hmrc-frontend v5.3.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.3.0)
- [alphagov/govuk-frontend v4.2.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.2.0)

## [3.21.0] - 2022-05-30

### Changed

- Updated `hmrc-frontend` to v5.2.0
- Updated the `HmrcCharacterCount.scala.html` to remove `aria-live` in live with `govuk-frontend` v4.1.0 and
  `hmrc-frontend` v5.2.0

### Compatible with

- [hmrc/hmrc-frontend v5.2.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.2.0)
- [alphagov/govuk-frontend v4.1.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.1.0)

## [3.20.0] - 2022-05-24

### Changed

- Updated `hmrc-frontend` to v5.1.0
- Updated `govuk-frontend` to v4.1.0

### Compatible with

- [hmrc/hmrc-frontend v5.1.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.1.0)
- [alphagov/govuk-frontend v4.1.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.1.0)

## [3.19.0] - 2022-05-20

### Changed

- Updated `HmrcUserResearchBanner`, we've added extra visually-hidden text to the "No thanks" button to help users of assistive technology more easily understand the purpose of the button and what will happen when it's used.
- Updated `hmrc-frontend` to v5.0.6

### Compatible with

- [hmrc/hmrc-frontend v5.0.6](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.0.6)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)

## [3.18.0] - 2022-05-19

### Changed

- Updated `HmrcUserResearchBanner` to have a heading `<h2>` element for improved accessibility
- Updated `hmrc-frontend` to v5.0.5

### Compatible with

- [hmrc/hmrc-frontend v5.0.5](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.0.5)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)


## [3.17.0] - 2022-05-12

### Changed

- Updated play-language to 5.3.0

### Compatible with

- [hmrc/hmrc-frontend v5.0.4](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.0.4)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)

## [3.16.0] - 2022-05-10

### Changed

- Added new `apply()` method for `StandardBetaBanner`, taking in an implicit `ContactFrontendConfig` and constructing URL
using values from `application.conf`

### Compatible with

- [hmrc/hmrc-frontend v5.0.4](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.0.4)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)


## [3.15.0] - 2022-04-28

### Changed

- Updated profile link text in `HmrcAccountMenu` from "Your profile" to "Profile and settings" in English and Welsh

### Compatible with

- [hmrc/hmrc-frontend v5.0.4](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.0.4)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)

## [3.14.0] - 2022-04-12

### Fixed

- Removed trailing spaces from `HmrcNewTabLink`/`HmrcNewTabLinkHelper`, so that consuming services can embed these in surrounding content without having to trim.

### Compatible with

- [hmrc/hmrc-frontend v5.0.3](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.0.3)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)

## [3.13.0] - 2022-04-07

### Fixed

- Fixed `HmrcPageHeading` missing the default `govuk-caption-xl` css style.

### Compatible with

- [hmrc/hmrc-frontend v5.0.3](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.0.3)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)

## [3.12.0] - 2022-04-07

### Fixed

- Corrected `CHANGELOG` which had got out of alignment with actual versions (`v3.10.0` was listed here as `v4.0.0`,
  `v3.11.0` was listed here as `v4.1.0`)

### Compatible with

- [hmrc/hmrc-frontend v5.0.3](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.0.3)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)

## [3.11.0] - 2022-03-31

### Fixed

- Fixed translation of "opens in a new tab" in `HmrcNewTabLink`

### Compatible with

- [hmrc/hmrc-frontend v5.0.3](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.0.3)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)

## [3.10.0] - 2022-03-31

### Changed

- Update the list-with-actions component and added the ability to add custom classes to the row element
- Updated hmrc-frontend to 5.0.2

### Compatible with

- [hmrc/hmrc-frontend v5.0.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v5.0.2)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)

## [3.9.0] - 2022-03-29

### Changed

- Updated HmrcPageHeading component to accept additional classes for header, h1 and caption elements

### Compatible with

- [hmrc/hmrc-frontend v4.9.1](https://github.com/hmrc/hmrc-frontend/releases/tag/v4.9.1)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)

## [3.8.0] - 2022-03-25

### Changed

- Updated hmrc-frontend to 4.9.0

### Compatible with

- [hmrc/hmrc-frontend v4.9.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v4.9.0)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)

## [3.7.0] - 2022-03-21

### Changed

- Builds for Scala 2.13 in addition to 2.12

### Compatible with

- [hmrc/hmrc-frontend v4.7.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v4.7.0)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)

## [3.6.0] - 2022-03-17

### Changed

- Updated HmrcHeader from `hmrc-frontend` changes to align with header from `govuk-frontend`

### Compatible with

- [hmrc/hmrc-frontend v4.7.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v4.7.0)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)

## [3.5.0] - 2022-02-28

### Changed

- Updated integration guidance for hmrc timeout dialog in README around use of signOutUrl

### Compatible with

- [hmrc/hmrc-frontend v4.5.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v4.5.0)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)

## [3.4.0] - 2022-02-09

### Changed

- Uplifted to use v4.0.1 of [govuk-frontend](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)
- `ErrorSummary` updated to take in boolean parameter `disableAutoFocus`, set to false by default, which is used in
  `GovukErrorSummary` template.

### Compatible with

- [hmrc/hmrc-frontend v4.5.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v4.5.0)
- [alphagov/govuk-frontend v4.0.1](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.1)

## [3.3.0] - 2022-02-08

### Fixed

- Updated `HmrcHeader` Twirl template to align with the changes made in hmrc-frontend v4.4.0 which fixes behaviour of
  navigation dropdown menu on mobile by adding "govuk-" prefix to data-module attribute of header.

### Compatible with

- [hmrc/hmrc-frontend v4.4.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v4.4.0)
- [alphagov/govuk-frontend v4.0.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.0)

## [3.2.0] - 2022-02-03

### Fixed

- Updated `GovukHeader` Twirl template to align with the changes made in govuk-frontend v4.0.0 (missed in previous uplift)

### Compatible with

- [hmrc/hmrc-frontend v4.3.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v4.3.0)
- [alphagov/govuk-frontend v4.0.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.0)

## [3.1.0] - 2022-01-28

### Added

- Added `HmrcPageHeadingHelper` component which allows the language parameter to be passed in implicitly, instead of directly using the `HmrcPageHeading` component in which the language parameter would need to be provided manually.

### Compatible with

- [hmrc/hmrc-frontend v4.3.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v4.3.0)
- [alphagov/govuk-frontend v4.0.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.0)

## [3.0.0] - 2022-01-28

### Changed

- Uplifted to use v4.0.0 of govuk-frontend. Please read the [release notes](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.0)
  when uplifting your service, and check that components still display as expected.
- `HmrcPageHeading` now supports Welsh language (note that the language parameter will need to passed in manually for
  Welsh translation).

### Compatible with

- [hmrc/hmrc-frontend v4.3.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v4.3.0)
- [alphagov/govuk-frontend v4.0.0](https://github.com/alphagov/govuk-frontend/releases/tag/v4.0.0)

## [2.0.0] - 2022-01-18

### Removed

- Major version has been bumped because this is the first release since we've had to remove builds for Play 2.6 and
  Play 2.7 support from the library. No breaking API changes have been made.

### Changed

- HmrcReportTechnicalIssue component tabnabbing protections are now only added if a non-empty referrerUrl has been
  provided. May trigger a DAST alert if you're not using our helper and not supplying a referrerUrl, recommendation is
  to upgrade your usage to the HmrcReportTechnicalIssueHelper or explicitly supply a referrerUrl.

### Compatible with

- [hmrc/hmrc-frontend v3.4.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v3.4.0)
- [alphagov/govuk-frontend v3.14.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.14.0)

## [1.31.0] - 2021-12-07

### Updated

- Set default version to Play 2.8 in PlayCrossCompilation

### Compatible with

- [hmrc/hmrc-frontend v3.4.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v3.4.0)
- [alphagov/govuk-frontend v3.14.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.14.0)

## [1.30.0] - 2021-12-06

### Updated

- Added an optional `Business account link` to the `HmrcAccountMenu` component.

### Compatible with

- [hmrc/hmrc-frontend v3.4.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v3.4.0)
- [alphagov/govuk-frontend v3.14.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.14.0)

## [1.29.0] - 2021-12-02

### Added

- Added `HmrcInternalGtmScript` and `HmrcInternalHead` helpers to add GTM snippet to internal services.

### Compatible with

- [hmrc/hmrc-frontend v3.1.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v3.1.0)
- [alphagov/govuk-frontend v3.14.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.14.0)

## [1.28.0] - 2021-12-02

### Added

- Added inline SVG to `HmrcInternalHeader` & `HmrcBanner`.

### Compatible with

- [hmrc/hmrc-frontend v3.1.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v3.1.0)
- [alphagov/govuk-frontend v3.14.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.14.0)

## [1.27.0] - 2021-11-24

### Updated

- Added helper methods `withUrlsFromConfig` and `withMessagesCount` to the `AccountMenu` allowing for menu item links to be passed in via configuration and for the setting of message count on the messages link.

### Compatible with

- [hmrc/hmrc-frontend v3.1.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v3.1.0)
- [alphagov/govuk-frontend v3.14.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.14.0)

## [1.26.0] - 2021-11-16

### Updated

- [Documented known issue with tracking consent integration when services set `platform.frontend.host` locally](docs/maintainers/journal/2021-11-15-tracking-consent-config-assumes-platform-tech-host-not-set-locally.md).

### Compatible with

- [hmrc/hmrc-frontend v3.1.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v3.1.0)
- [alphagov/govuk-frontend v3.14.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.14.0)

## [1.25.0] - 2021-11-10

### Updated

- hmrc-frontend to v3.1.0 which brings govuk-frontend to v3.14.0

### Compatible with

- [hmrc/hmrc-frontend v3.1.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v3.1.0)
- [alphagov/govuk-frontend v3.14.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.14.0)

## [1.24.0] - 2021-11-05

### Updated

- Added page layout argument to layout components, to allow internal services to use a full width layout.

### Compatible with

- [hmrc/hmrc-frontend v3.0.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v3.0.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.23.0] - 2021-11-05

### Updated

- Added "yourProfile" parameter to account menu and removed "paperlessSettings" and "personalDetails" to match the
  latest implementation from PTA.

### Compatible with

- [hmrc/hmrc-frontend v3.0.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v3.0.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.22.0] - 2021-10-19

### Added

- Added `withHeading` and `withHeadingAndSectionCaption` helper methods for form inputs
- Added various implicit conversions for `String` to nested case classes via `RichStringSupport`
- Added various implicit conversions for `Seq[(String, String)]` to nested case classes via `RichSeqStringTupleSupport`
- Added `HmrcYesNoRadioItems` helper

### Compatible with

- [hmrc/hmrc-frontend v2.7.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.7.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.21.0] - 2021-10-15

### Fixed

- Fixed incomplete pattern match in `HmrcNewTabLink`, and corrected `HmrcNewLinkHelper` to use `lang.language` not `
lang.code`

### Compatible with

- [hmrc/hmrc-frontend v2.7.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.7.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.20.0] - 2021-10-13

### Changed

- Removed two no longer used dependencies ahead of bootstrap-play integration
- Updated test fixtures for hmrc-frontend to 2.7.0

### Compatible with

- [hmrc/hmrc-frontend v2.7.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.7.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.19.0] - 2021-10-07

### Fixed

- Updated version of hmrc-frontend to 2.7.0 which has a fix for timeout dialog navigation issues for screen readers

### Compatible with

- [hmrc/hmrc-frontend v2.7.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.7.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.18.0] - 2021-10-07

### Fixed

- Updated `HmrcReportTechnicalIssue` component to include `rel="noreferrer noopener"` to guard against reverse
tabnapping.
- Fixed use of unsafe `.get` on optional `language` parameter in `HmrcNewTabLink`

### Compatible with

- [hmrc/hmrc-frontend v2.6.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.6.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)


## [1.17.0] - 2021-10-05

### Added

- Added `HmrcNewTabLinkHelper` and associated viewmodel `NewTabLinkHelper` so that services can add a link to a new tab
which uses an implicit `Messages` for internationalization, rather than having to pass in an explicit string representing
a language code.

### Compatible with

- [hmrc/hmrc-frontend v2.5.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.5.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.16.0] - 2021-10-04

### Fixed

- Fixed typo in `README.md`

### Compatible with

- [hmrc/hmrc-frontend v2.5.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.5.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)


## [1.15.0] - 2021-10-01

### Added

- Added a "Troubleshooting" section to the README, with a link to the "Typography" section of GOV.UK Design System

### Compatible with

- [hmrc/hmrc-frontend v2.5.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.5.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)


## [1.14.0] - 2021-09-27

### Changed

- Added additionalBannersBlock param to hmrcHeader, hmrcStandardHeader, and hmrcLayout which allows inclusion of custom
  html at the end of the header element

### Compatible with

- [hmrc/hmrc-frontend v2.5.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.5.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.13.0] - 2021-09-27

### Changed

- Added a dependency on v2.0.0 of `play-frontend-govuk`. This is an empty release of the deprecated library to force
eviction of any incorrectly added direct dependencies on non-empty `play-frontend-govuk`

### Compatible with

- [hmrc/hmrc-frontend v2.4.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.4.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)


## [1.12.0] - 2021-09-23

### Changed

- hmrcHeader to render service name in a span when no service url is provided

### Compatible with

- [hmrc/hmrc-frontend v2.4.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.4.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.11.0] - 2021-09-21

### Added

- hmrcListWithActions component

### Compatible with

- [hmrc/hmrc-frontend v2.3.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.3.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.10.0] - 2021-09-20

### Added

- Translation for the "this.section.is" message used in the visually hidden prefix of section captions for page
  headings.

### Compatible with

- [hmrc/hmrc-frontend v2.2.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.2.2)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.9.0] - 2021-09-14

### Changed

- Removed `govuk.routes` and associated Assets controller, all images now served from `hmrc-frontend` webjar
- Removed `GovukHeaderIntegrationSpec` as no longer compatible with library assets path

**Action required**:

- Remove the following from your `routes` file in your service if present:
```scala
->         /govuk-frontend                                   govuk.Routes
```
- Failure to do so will result in a compilation error as `govuk.Routes` has been deleted.

### Compatible with

- [hmrc/hmrc-frontend v2.2.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.2.2)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.8.0] - 2021-09-10

### Changed

- Default branch changed to main, updated README to reflect

### Compatible with

- [hmrc/hmrc-frontend v2.2.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.2.2)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.7.0] - 2021-09-09

### Added

- ADR concerning the introduction of implicit conversions.

### Compatible with

- [hmrc/hmrc-frontend v2.2.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.2.2)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.6.0] - 2021-09-09

### Changed

- Further removal of duplicated integration test code following merge in of `play-frontend-govuk`

### Compatible with

- [hmrc/hmrc-frontend v2.2.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.2.2)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.5.0] - 2021-09-09

### Changed

- Removing duplicated code following merge in of `play-frontend-govuk`

### Compatible with

- [hmrc/hmrc-frontend v2.2.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.2.2)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.4.0] - 2021-09-03

### Changed

- Updated version of `hmrc-frontend`
- Fixed `HmrcUserResearchBanner.scala.html` to remove reverse tabnapping vulnerability

### Compatible with

- [hmrc/hmrc-frontend v2.2.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.2.2)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [1.3.0] - 2021-08-31

### Changed

- Updated version of `sbt-auto-build` plugin to generate Twirl headers

### Compatible with

- [hmrc/hmrc-frontend v2.2.0](https://github.com/hmrc/hmrc-frontend/releases/tag/v2.2.0)
- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

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
  [ADR](https://github.com/hmrc/play-frontend-govuk/blob/main/docs/maintainers/adr/0004-remove-hard-dependency-on-the-govuk-frontend-webjar.md)

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
