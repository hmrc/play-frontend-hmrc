# Do not port timeout-dialog hideSignOutButton

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
1. The `hideSignOutButton` would NOT be available to HMRC MDTP microservices, because there has not been a demonstrated 
need for this change on HMRC. Further research would be required before making this a Production feature for HMRC.

## Decision Drivers

* The desire to work collaboratively cross-Government, and make our libraries useful to other departments
* The need to not introduce functinality into the HMRC production libraries that has not been tested for HMRC users

## Considered Options

* Do not implement `hideSignOutButton` in `hmrc-frontend`
* Implement `hideSignOutButton` in `hmrc-frontend` via `timout-dialog`, but not in `play-frontend-hmrc` via `HmrcTimoutDialog.scala.html`
* Implement `hideSignOutButton` in both `hmrc-frontend` via `timout-dialog`, and `play-frontend-hmrc` via `HmrcTimoutDialog.scala.html`

## Decision Outcome

Chosen option: "Implement `hideSignOutButton` in `hmrc-frontend` but not `play-frontend-hmrc`", because we can do this 
to support other Government departments with a proven use case and need, but without introducing features in Play / Scala
libraries that have not been tested (or requested) for HMRC production microservices

### Positive Consequences

* The `timeout-dialog` in `hmrc-frontend` will be more appropriate to cross-Government departments, reducing a need for 
  forks or other code duplication
* The `HmrcTimeoutDialog` component will not need to be changed, and there is no need to worry about user testing a 
  feature for which there has no been no demand on the MDTP platform.

### Negative Consequences

* There will be a divergence between `hmrc-frontend` and `play-frontend-hmrc`, which could cause confusion in the future.
This ADR is intended to document the reasoning behind this divergence.
* This divergence will require the unit tests for the `timeout-dialog` / `HmrcTimoutDialog` to be patched

## Links

* Github issue: https://github.com/hmrc/hmrc-frontend/issues/316
* `hmrc-frontend` release implementing this change: https://github.com/hmrc/hmrc-frontend/releases/tag/v5.52.0
