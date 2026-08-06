# Do not include generic header component from govuk-frontend v6.3.0

* Status: accepted
* Date: 2026-07-27

## Context and Problem Statement

With the release of govuk-frontend v6.3.0 Generic Header component has been added for services that are not a part of GOV.UK proposition but would still benefit from using GOV.UK Frontend to build their service.

## Decision Drivers

* Generic Header is a component for non HMRC services, so shouldn't be added to play-frontend-hmrc

## Considered Options

* Add Generic Header component to play-frontend-hmrc to maintain one-to-one mirror with govuk-frontend
* Skip adding the component, as it would never be used by HMRC services

## Decision Outcome

Chosen option: "Skip adding the component, as it would never be used by HMRC services", because we do not need to be exact one-to-one mirror with govuk-frontend. Adding components that will never be used by any HMRC service will just unnecessary complicate the codebase and increase amount of maintenance.

### Positive Consequences

* Library won't support components that will never be used
* Less maintenance

### Negative Consequences

* play-frontend-hmrc will not include all possible components from govuk-frontend

## Links

* [govuk-frontend v6.3.0 Release Notes](https://github.com/alphagov/govuk-frontend/releases/tag/v6.3.0)
