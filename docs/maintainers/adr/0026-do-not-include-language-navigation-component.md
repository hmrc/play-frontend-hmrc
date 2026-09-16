# Do not include Language Navigation component from `govuk-frontend` v6.5.0

* Status: accepted
* Date: 2026-09-15

## Context and Problem Statement

With the release of `govuk-frontend` v6.5.0 a Language Navigation component has been added for language switching on services via Service Navigation: https://design-system.service.gov.uk/components/language-navigation/

## Decision Drivers

* HMRC have had their own language select component for many years, recently re-worked as `service-navigation-language-select` in `hmrc-frontend` to work in the contact of Service Navigation.
* The `govuk-frontend` component is currently in a `Trial` state, meaning that its design is not finalised and is subject to ongoing research

## Considered Options

* Add `govuk-frontend` Language Navigation component to `play-frontend-hmrc` alongside the `hmrc-frontend` implementation
* Add `govuk-frontend` Language Navigation component to `play-frontend-hmrc` and remove the `hmrc-frontend` implementation
* Skip adding the component at this time, as frontends using `play-frontend-hmrc` should use only the `hmrc-frontend` implementation

## Decision Outcome

Chosen option: "Skip adding the component at this time, as frontends using `play-frontend-hmrc` should use only the `hmrc-frontend` implementation."

### Positive Consequences

* The `hmrc-frontend` language select is the result of many years to research and design iteration, including input from the Welsh Language Unit, and we do not want to lose all the improvements made over the years
* HMRC Design Resources can continue to work on our established alternative approach to language switching, particularly as the `govuk-frontend` version is still in a `Trial` / research stage
* By only supporting one language select implementation, service teams have one simple and consistent design approach, rather than having to chose between different components (which could also lead to accessibility issues with inconsistent navigation)

### Negative Consequences

* `play-frontend-hmrc` will not include all possible components from `govuk-frontend`
* HMRC services may have different navigation from other GOVUK services
* HMRC will need to provide their own patches and fixes going forward, rather than being able to access the `govuk-frontend` work directly

## Links

* [govuk-frontend v6.5.0 Release Notes](https://github.com/alphagov/govuk-frontend/releases/tag/v6.5.0)
