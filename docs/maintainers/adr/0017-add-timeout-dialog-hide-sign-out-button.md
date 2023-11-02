# Add timeout-dialog hideSignOutButton

* Status: accepted
* Date: 2023-11-01

Technical Story: PLATUI-2588

## Context and Problem Statement

In the hmrc-frontend Github repository, a PR was raised by the DWP Access to Work team. They wanted to use the
`timeout-dialog` from hmrc-frontend and be able to disable the sign out button or link, as their service doesn't have log
in functionality. More context for this request is on the original Github issue: https://github.com/hmrc/hmrc-frontend/issues/316

In discussion with the Design Resources team, it was agreed that:
1. The `hideSignOutButton` functionality be included in the `hmrc-frontend` library for cross-Government teams with a
   specific use case
1. The `hideSignOutButton` would NOT be documented or encouraged to HMRC MDTP microservices, because there has not been 
   a demonstrated need for this change on HMRC. Further research would be required before publicising this a Production 
   feature for HMRC.

## Decision Drivers

* The desire to work collaboratively cross-Government, and make our libraries useful to other departments
* The need to not introduce functionality into the HMRC production libraries that has not been tested for HMRC users

## Considered Options

* Do not implement `hideSignOutButton` in `hmrc-frontend`
* Implement `hideSignOutButton` in `hmrc-frontend` via `timout-dialog`, but not in `play-frontend-hmrc` via `HmrcTimoutDialog.scala.html`
* Implement `hideSignOutButton` in both `hmrc-frontend` via `timout-dialog`, and `play-frontend-hmrc` via `HmrcTimoutDialog.scala.html`

## Decision Outcome

Chosen option: "Implement `hideSignOutButton` in both `hmrc-frontend` via `timout-dialog`, and `play-frontend-hmrc` via 
`HmrcTimoutDialog.scala.html`". Chosen because we want to include the feature in `hmrc-frontend` to support other 
Government departments with a proven use case and need, but our design principles and testing strategy begin to break 
down when `hmrc-fronted` and `play-frontend-hmrc` diverge. Therefore, this will be added as an undocumented feature 
so as not to encourage its use for HMRC production microservices

### Positive Consequences

* The `timeout-dialog` in `hmrc-frontend` will be more appropriate to cross-Government departments, reducing a need for
  forks or other code duplication
* The `HmrcTimeoutDialog` component will remain aligned with `timeout-dialog` but we will not be publicising the new 
  feature as part of `play-frontend-hmrc`.

### Negative Consequences

* There will be an undocumented and untested feature in `play-frontend-hmrc`. This ADR is intended to document the 
  reasoning behind this design decision.

## Links

* Github issue: https://github.com/hmrc/hmrc-frontend/issues/316
* `hmrc-frontend` release implementing this change: https://github.com/hmrc/hmrc-frontend/releases/tag/v5.52.0
