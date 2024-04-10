# Defer Inclusion of Password Field from govuk-frontend v5.3.0

* Status: accepted
* Date: 2024-08-04

## Context and Problem Statement

With the release of govuk-frontend v5.3.0, which includes a password field, we are considering whether to integrate this feature into our frontend library. Given that authentication for most services is handled by a central service (e.g., GG or the upcoming OLFG), the need for a library component for password fields may not be widespread. There are also considerations around the engineering cost of creating and maintaining such a component.

## Decision Drivers

* Centralized authentication services reducing the need for a password field component
* Engineering cost of maintenance and development
* Potential future demand for this component

## Considered Options

* Immediately include the password field feature from govuk-frontend v5.3.0
* Defer the inclusion of the password field feature until there is a demonstrated need

## Decision Outcome

Chosen option: "Defer the inclusion of the password field feature until there is a demonstrated need", because it allows us to focus on components with a clear demand and reduces the maintenance burden. This decision is reversible, and we remain open to revisiting it should a significant need for this component arise from our services.

### Positive Consequences

* Keeps the library focused on widely used components
* Reduces unnecessary maintenance and development work
* Flexible approach that can adapt to future demands

### Negative Consequences

* Teams needing the password field immediately may have to implement their own solutions

## Pros and Cons of the Options

### Immediately include the password field feature

* Good, because it provides immediate feature completeness.
* Bad, because it likely adds an unused feature for most teams, increasing maintenance overhead.

### Defer the inclusion of the password field feature

* Good, because it aligns our resources with current needs and reduces overhead.
* Good, because it leaves room to adapt based on future demand.
* Bad, because teams with immediate needs must find alternative solutions.

## Links

* [govuk-frontend v5.3.0 Release Notes](https://github.com/alphagov/govuk-frontend/releases/tag/v5.3.0)