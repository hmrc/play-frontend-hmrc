# Inline play-frontend-govuk into play-frontend-hmrc

* Status: proposed
* Date: [YYYY-MM-DD when the decision was last updated] <!-- optional -->

Technical Story: PLATUI-1252

## Context and Problem Statement

play-frontend-govuk is intended as a direct Scala/Twirl port of govuk-frontend that can in theory be
used by any government department wanting a Scala/Twirl implementation of the GOV.UK design system.

play-frontend-hmrc includes play-frontend-govuk while adding to it HMRC/MDTP specific components and helpers, for example,
standardised headers and footers, standardised HMRC content for GOV.UK
components in both English and Welsh, support for cookie consent, language switching, session timeout, 
accessibility statements and more.

Inlining play-frontend-govuk into play-frontend-hmrc has the potential to simplify our 
development and testing workflow and reduce lead times for making improvements 
available to service teams.

Given we are not aware of any scenarios where it would be desirable for HMRC services to 
use play-frontend-govuk on its own nor any other government departments using play-frontend-govuk, should we fold the
govukfrontend package into play-frontend-hmrc and archive the play-frontend-govuk library?

## Decision Drivers

* The confusion and discoverability issues that having two similarly named libraries creates for service developers.
* The extended lead times caused by the need to publish play-frontend-hmrc every time a change is made to play-frontend-govuk.
* The additional cognitive friction created for maintainers in deciding which library to add changes to while maintaining
  the conceptual integrity of both.
* The split of user and maintenance documentation between the two libraries.
* The duplication of code and build scaffolding between the two libraries, for example, implicits and fixture generation  
* The overhead of supporting two libraries rather than one, for example, dependency upgrades or security fixes.
* The desirability of providing a Twirl port of govuk-frontend to other government departments.
* The possibility of other government departments using play-frontend-hmrc and ignoring any HMRC-specific features.

## Considered Options

* Option 1: Do nothing
* Option 2: Inline the govukfrontend package into play-frontend-hmrc but make no other code changes for now
* Option 3: Inline the govukfrontend package into play-frontend-hmrc and resolve as much duplication as possible

## Decision Outcome

Chosen option: "[option 1]", because [justification. e.g., only option, which meets k.o. criterion decision driver | which resolves force force | … | comes out best (see below)].

### Positive Consequences <!-- optional -->

* [e.g., improvement of quality attribute satisfaction, follow-up decisions required, …]
* …

### Negative Consequences <!-- optional -->

* [e.g., compromising quality attribute, follow-up decisions required, …]
* …

## Pros and Cons of the Options <!-- optional -->

### [option 1]

[example | description | pointer to more information | …] <!-- optional -->

* Good, because [argument a]
* Good, because [argument b]
* Bad, because [argument c]
* … <!-- numbers of pros and cons can vary -->

### [option 2]

[example | description | pointer to more information | …] <!-- optional -->

* Good, because [argument a]
* Good, because [argument b]
* Bad, because [argument c]
* … <!-- numbers of pros and cons can vary -->

### [option 3]

[example | description | pointer to more information | …] <!-- optional -->

* Good, because [argument a]
* Good, because [argument b]
* Bad, because [argument c]
* … <!-- numbers of pros and cons can vary -->

## Links <!-- optional -->

* [Link type] [Link to ADR] <!-- example: Refined by [ADR-0005](0005-example.md) -->
* … <!-- numbers of links can vary -->
