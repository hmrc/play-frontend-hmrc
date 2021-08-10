# Inline play-frontend-govuk into play-frontend-hmrc

* Status: accepted
* Date: 2021-08-09

Technical Story: PLATUI-1252

## Context and Problem Statement

play-frontend-govuk is intended as a direct Scala/Twirl port of govuk-frontend that can in theory be
used by any government department wanting a Scala/Twirl implementation of the GOV.UK design system.

play-frontend-hmrc includes play-frontend-govuk while adding to it HMRC/MDTP specific components and helpers. Examples include
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
* The question around whether having two separate libraries helps enforce the conceptual integrity of the uk.gov.hmrc.govukfrontend 
  package as a pure Twirl port of govuk-frontend free from HMRC/MDTP specific features.
* The need to maintain the existing test strategy and not have this compromised by the merging of the two libraries.
* The desirability of maintaining git commit history for the merged govukfrontend package.
* An historical view, now superceded, that teams should be able to use play-frontend-govuk on its own.
* The desirability of providing a Twirl port of govuk-frontend to other government departments.
* The impact on teams currently using only play-frontend-govuk.
* The possibility of other government departments using play-frontend-hmrc and ignoring any HMRC-specific features.

## Considered Options

* Option 1: Do nothing
* Option 2: Inline the govukfrontend package into play-frontend-hmrc, resolving duplication where the effort involved
  in doing so is not excessive and would not result in breaking changes.

## Decision Outcome

Chosen option: Option 2, because overall we believe the benefits listed below vastly outweigh the negatives.

### Positive Consequences

* Service teams need only consult a single library repository for user documentation.
* Teams only using play-frontend-govuk will be forced to upgrade to use play-frontend-hmrc. This should reduce friction
  around migrating to use the latest HMRC components that in turn will eventually increase the consistency and
  accessibility of HMRC services.
* Lead times for changes are reduced because only one library needs to be published.
* Routine library dependency upgrade overheads are reduced.
* Fixture generation logic is no longer duplicated between two libraries
* We are in a better position to eventually consolidate the various implicit classes and helpers currently split 
  between the two libraries.
* Developer onboarding is simplified – we no longer need to explain why there are two separate libraries.

### Negative Consequences

* Potential loss of git commit history depending on merge approach taken. We would intend to investigate this 
  [approach](https://medium.com/altcampus/how-to-merge-two-or-multiple-git-repositories-into-one-9f8a5209913f), falling back
  to a link to the archived play-frontend-govuk repository if this is not possible.
* Slight risk of unintended side effects for teams upgrading to play-frontend-hmrc from play-frontend-govuk. 16 such 
  repositories have been identified to date.
* User documentation via the play-frontend-hmrc README will be less friendly to other government departments using 
  Scala/Play/Twirl. For example, they are likely to want to use `govukLayout` rather than the recommended `hmrcLayout` component.
  No departments other than HMRC have yet been identified using Scala/Play.
