# Do not include Feedback component from `govuk-frontend` v6.5.0

* Status: accepted
* Date: 2026-09-15

## Context and Problem Statement

With the release of `govuk-frontend` v6.5.0 a Feedback component has been added for services to collect feedback about a service during the journey.

## Decision Drivers

* There is already an established HMRC pattern for collecting feedback via `feedback-frontend` at the end of service journeys: 
* The `govuk-frontend` component is currently in a `Trial` state, meaning that its design is not finalised and is subject to ongoing research

## Considered Options

* Add Feedback component to `play-frontend-hmrc` to maintain one-to-one mirror with `govuk-frontend`
* Delay adding the component, and have further discussions about whether this should be included in HMRC service journeys

## Decision Outcome

Chosen option: "Delay adding the component, and have further discussions about whether this should be included in HMRC service journeys"

### Positive Consequences

* Design Resources, PlatUI, and `feedback-frontend` service owners will need time to consider the potential impact of changes to services in HMRC. 
Although this is an HTML component that can be wired in different ways, if it is added to the `play-frontend-hmrc` library, services may want to add 
it before there is guidance in place about how to use in HMRC services

### Negative Consequences

* HMRC services using `play-frontend-hmrc` do not have access to all the components and patterns described in the GOVUK Design System: 

## Links

* [govuk-frontend v6.5.0 Release Notes](https://github.com/alphagov/govuk-frontend/releases/tag/v6.5.0)
