# Change Log

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## [1.0.0] - 2021-08-05

### Changed

- Removed deprecated static helpers

### Compatible with

- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.84.0] - 2021-08-05

### Changed

- Documentation changes only

### Compatible with

- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.83.0] - 2021-08-05

### Changed

- Implicit helpers that can construct error messages will now construct them with their "Error:" prefix translated for the current language.

### Compatible with

- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.82.0] - 2021-07-27

### Changed

- Refactored structure of Rich implicit helpers into separate package

### Compatible with

- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.81.0] - 2021-07-27

### Changed

- Added `withFormFieldWithErrorAsHtml(field: play.api.data.Field)` to Rich implicit helpers.

### Compatible with

- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.80.0] - 2021-07-02

### Changed

- Documentation housekeeping

### Compatible with

- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.79.0] - 2021-06-29

### Changed

- Upgrade to govuk-frontend v3.13.0

### Compatible with

- [alphagov/govuk-frontend v3.13.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.13.0)

## [0.78.0] - 2021-06-24

### Changed

- Remove hard dependency on govuk-frontend webjar and provide assetsPath override
  (see [ADR](docs/maintainers/adr/0004-remove-hard-dependency-on-the-govuk-frontend-webjar.md))

## [0.77.0] - 2021-06-15

### Changed

- Added a `twoThirdsOneThirdMainContent.scala.html` template to be used in `govukLayout.scala.html` to add a sidebar on
the right hand side of a page.

### Compatible with

- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)

## [0.76.0] - 2021-06-14

### Changed

- Decoupled the wrapping of a `contentBlock` in the `govukLayout` from the enforcement of two thirds width styling, and 
  allowed the passing in of custom HTML wrapping around the `contentBlock`.

### Compatible with

- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)

## [0.75.0] - 2021-06-10

### Changed

- Add welsh translation for skip link to `govukTemplate`

### Compatible with

- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)


## [0.74.0] - 2021-06-03

### Changed

- Removed dependencies on deprecated Bintray

### Compatible with

- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)


## [0.73.0] - 2021-05-18

### Changed

- Changed ordering of attributes within formWithCSRF

### Compatible with

- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)


## [0.72.0] - 2021-05-18

### Changed

- Upgrade to govuk-frontend v3.12.0

### Compatible with

- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)


## [0.71.0] - 2021-04-19

### Updated

- Changelog update

### Compatible with

- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.70.0] - 2021-04-19

### Fixed

- RichCheckboxes to take into account multiple values

### Compatible with

- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.69.0] - 2021-03-29

### Changed

- Added documentation

### Compatible with

- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.68.0] - 2021-03-23

### Changed

- Test formatting

### Compatible with

- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.67.0] - 2021-03-23

### Changed

- Added documentation

### Compatible with

- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.66.0] - 2021-03-22

### Changed

- Library formatting

### Compatible with

- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.65.0] - 2021-03-22

### Changed

- Added Play 2.8 Support

### Compatible with

- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.64.0] - 2021-02-25

### Changed

- Added Play 2.8 Support

### Compatible with

- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.63.0] - 2021-02-22

### Changed

- Added RichInput, RichCheckboxes, RichSelect, RichTextarea, RichCharacterCount implicit helper classes, 
  to enhance the underlying classes with a `.withFormField(field: play.api.data.Field)` method 

### Compatible with

- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.62.0] - 2021-02-16

### Changed

- Upgrade to govuk-frontend v3.11.0

### Compatible with

- [alphagov/govuk-frontend v3.11.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.11.0)

## [0.61.0] - 2021-02-15

### Changed

- Added a RichRadios implicit helper class, to enhance Radios with a `.withFormField(field: Field)` method

### Compatible with

- [alphagov/govuk-frontend v3.10.2](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.2)

## [0.60.0] - 2021-01-19

### Changed

- Upgrade to govuk-frontend v3.10.2

### Compatible with

- [alphagov/govuk-frontend v3.10.2](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.2)

## [0.59.0] - 2021-01-18

### Changed

- Uplift SBT to 1.4.6 and associated plugins

### Compatible with

- [alphagov/govuk-frontend v3.10.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.1)

## [0.58.0] - 2020-12-15

### Changed

- Removed JsonDefaultValueFormatter

### Compatible with

- [alphagov/govuk-frontend v3.10.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.1)

## [0.57.0] - 2020-12-07

### Changed

- Removed support for Play 2.5
- Updated HMRC headers to 2021

### Compatible with

- [alphagov/govuk-frontend v3.10.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.1)

## [0.56.0] - 2020-12-07

### Fixed

- Added NotificationBanner model and govukNotification banner template

### Added

- Upgrade to govuk-frontend v3.10.1

### Compatible with

- [alphagov/govuk-frontend v3.10.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.1)

## [0.55.0] - 2020-11-26

### Added

- Upgrade to govuk-frontend v3.10.0

### Compatible with

- [alphagov/govuk-frontend v3.10.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.10.0)

## [0.54.0] - 2020-11-19

### Fixed

- Bug with govukSummaryList

### Compatible with

- [alphagov/govuk-frontend v3.9.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.9.1)

## [0.53.0] - 2020-10-15

### Changed

- FormWithCSRF to toggle novalidate on by default

### Compatible with

- [alphagov/govuk-frontend v3.9.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.9.1)

## [0.52.0] - 2020-10-15

### Added

- Upgrade to govuk-frontend v3.9.1

### Compatible with

- [alphagov/govuk-frontend v3.9.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.9.1)

## [0.51.0] - 2020-10-15

### Fixed

- Missing lang attribute from govukLayout

### Compatible with

- [alphagov/govuk-frontend v3.8.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.8.1)

## [0.50.0] - 2020-08-14

### Added

- Upgrade to govuk-frontend v3.8.1

### Compatible with

- [alphagov/govuk-frontend v3.8.1](https://github.com/alphagov/govuk-frontend/releases/tag/v3.8.1)

## [0.49.0] - 2020-07-03

### Added

- Set the default GOVUK link to the main GOVUK homepage

### Compatible with

- [alphagov/govuk-frontend v3.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.7.0)

## [0.48.0] - 2020-06-19

### Added

- Upgrade to govuk-frontend v3.7.0
- `content: Content` in HeaderNavigation to allow for HTML in navigation items
(`text: Option[String]` has been kept to maintain backward source-compatibility)
- `collapseOnMobile: Boolean` added to Breadcrumbs
- This version is not binary compatible with 0.47.0

### Compatible with

- [alphagov/govuk-frontend v3.7.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.7.0)

## [0.47.0] - 2020-06-12

### Added

- Support for Play 2.7

### Compatible with

- [alphagov/govuk-frontend v3.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.6.0)

## [0.45.0] - 2020-05-21

### Added

- Refactor build to remove dependency on x-frontend-snapshotter

### Compatible with

- [alphagov/govuk-frontend v3.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.6.0)

## [0.44.0] - 2020-05-06

### Fixed

- A bug with govukCharacterCount

### Compatible with

- [alphagov/govuk-frontend v3.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.6.0)

## [0.43.0] - 2020-04-29

### Added

- Upgrade govuk-frontend to v3.6.0

### Compatible with

- [alphagov/govuk-frontend v3.6.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.6.0)

## [0.42.0] - 2020-04-24

### Added

- Support for URL fragments in formWithCSRF helper

### Compatible with

- [alphagov/govuk-frontend v3.5.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.5.0)

## [0.41.0] - 2020-04-16

### Fixed

- Issues with govukLayout and govukTemplate components
- Increased unit test coverage

### Compatible with

- [alphagov/govuk-frontend v3.5.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.5.0)
