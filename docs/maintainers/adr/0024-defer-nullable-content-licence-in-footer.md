# Defer Inclusion of nullable `contentLicence` in Footer from govuk-frontend v5.14.0

* Status: accepted
* Date: 2026-01-21

## Context and Problem Statement

With the release of govuk-frontend v5.14.0, we are considering whether to allow the Content Licence to be excluded from the footer, which in `govuk-frontend` is acheived by setting the `contentLicence` property as `null`. Given that HMRC MDTP services should be using the `HmrcFooter` which hardcodes the content licence as Open Government Licence (OGL), and cannot be overridden there. 

## Decision Drivers

* If this is not needed in HMRC MDTP, should we be including this functionality in our library?
* Reading in an optional JSON property as `null` and preserving that as a separate value in Scala would require custom JSON reading logic
* Not including the ability to exclude content licence means that those unit tests will need to be excluded

## Considered Options

* Maintain parity of `govuk-frontend` and `play-frontend-hmrc` by allowing content licence in Footer to be excluded 
* Defer the inclusion of the excluding content licence feature until there is a demonstrated need

## Decision Outcome

Chosen option: "Defer the inclusion of the excluding content licence feature until there is a demonstrated need", because it allows us to focus on components with a clear demand and reduces the maintenance burden. This decision is reversible, and we remain open to revisiting it should a need for this functionality arise from our services.

### Positive Consequences

* Keeps the library focused on functionality that we know we need in HMRC
* Reduces unnecessary maintenance and development work of JSON readers / writers, which are known to be an area of complexity for PlatUI developers
* Flexible approach that can adapt to future demands

### Negative Consequences

* Services needing to override the content licence would have to reach out to PlatUI to add in this functionality

## Links

* [govuk-frontend v5.14.0 Release Notes](https://github.com/alphagov/govuk-frontend/releases/tag/v5.14.0)
