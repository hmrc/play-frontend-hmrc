# Change Log

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## [0.75.0] - 2021-06-21

### Changed

- README guidance for hmrcLayout updated

### Compatible with

- [hmrc/hmrc-frontend v1.35.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.35.2)
- [hmrc/play-frontend-govuk v0.77.0](https://github.com/hmrc/play-frontend-govuk/releases/tag/v0.77.0)
- [alphagov/govuk-frontend v3.12.0](https://github.com/alphagov/govuk-frontend/releases/tag/v3.12.0)


## [0.74.0] - 2021-06-18

### Changed

- Added hmrcLayout
- Update play-frontend-govuk to 0.77.0 to passing in of main content styling to govukTemplate

### Compatible with

- [hmrc/hmrc-frontend v1.35.2](https://github.com/hmrc/hmrc-frontend/releases/tag/v1.35.2)
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
