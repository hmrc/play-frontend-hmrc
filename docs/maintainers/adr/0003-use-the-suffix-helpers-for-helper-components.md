# Use the suffix Helper for helper components

* Status: accepted
* Date: 2020-12-03

Technical Story: PLATUI-792

## Context and Problem Statement

Facing the need to create wrappers to make using the Nunjucks components ported from hmrc-frontend more
straightforward and idiomatic on the Scala/Play MDTP platform, how should we name these components?

The components ported from hmrc-frontend include
* implementations of govuk-frontend components that support the Welsh language e.g. hmrcHeader, hmrcFooter
* components that meet specific HMRC needs e.g. hmrcBanner, hmrcInternalHeader
* components that have not been standardised by GDS but are needed by HMRC e.g. hmrcNotificationBadge, hmrcTimeoutDialog

Being entirely presentational and ported from Nunjucks means they cannot leverage features built into Scala/Play nor
make use of any MDTP platform knowledge that would simplify their use on MDTP. For example,
* they do not and should not know the url structure for common MDTP components e.g. tracking-consent-frontend, 
 contact-frontend or the accessibility-statement-frontend, services that need to wired in on every public-facing service via 
  standard headers and footers.
* they cannot make use of Play's i18n features and automatic language wiring
* they cannot make use of any knowledge encoded in the requests users make e.g. the request URL, referrer URL, cookies
  or headers.

For the above reasons, we are creating wrappers that implement standardised Play/platform wiring, to avoid teams having to
duplicate this wiring across 100s of repositories. Once implemented we will encourage teams to use these helpers rather than
the underlying Nunjucks ports.

## Decision Drivers

* The fact that the ideal component names have already been taken by the Nunjucks components.
* The preference for not relying on the package name to differentiate the components
* The preference for not repeating the word Helper in the package and component name
* The preference for having names that are unsurprising and will encourage use of the helper in preference to the underlying Nunjucks
component.
* The preference for consistency in naming across the components

## Considered Options

* Suffix with 'Helper' e.g. hmrcReportTechnicalIssue/hmrcReportTechnicalIssueHelper
* Suffix with 'Wrapper' e.g. hmrcReportTechnicalIssue/hmrcReportTechnicalIssueWrapper
* Use the same name as the Nunjucks component and differentiate using the package name
 e.g. uk.gov.hmrc.hmrcfrontend.views.html.components.hmrcReportTechnicalIssue/uk.gov.hmrc.hmrcfrontend.views.html.helpers.hmrcReportTechnicalIssue
* Use a variation of the Nunjucks component name e.g. hmrcReportTechnicalIssue/hmrcGetHelpWithThisPage

## Decision Outcome

Chosen option: "Suffix with 'Helper'", because this is the only option the team are happy with bearing in mind the decision
drivers listed above.

### Positive Consequences

* We have a consistent naming scheme
* Friction around needing to come up with new names for helpers reduced, increasing speed of development.

### Negative Consequences

* There is a risk that service teams will not know the helpers exist
* Repeating the word Helper in the component and having it in the package name is ugly
