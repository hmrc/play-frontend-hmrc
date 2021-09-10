# Remove hard dependency on the govuk-frontend WebJar

* Status: accepted
* Date: 2021-06-24

Technical Story: PLATUI-1236

## Context and Problem Statement

play-frontend-govuk relies on a webjar for [alphagov/govuk-frontend](https://www.github.com/alphagov/govuk-frontend)
published to www.webjars.org. This has a number of drawbacks:
* publishing is a manual process
* it can take many hours to complete
* webjars.org has been down in the past and HMRC has no support arrangements with webjars.org

The main impact of the above is an excessive lead time for making improvements in the
underlying govuk-frontend library available in production via play-frontend-govuk.

Previously we considered self-publishing this WebJar, as we did for the hmrc-frontend WebJar (see related [ADR]((../adr/0009-self-publish-webjar.md)
)). However, this is complicated by the fact that we do not own govuk-frontend and
self-publishing would involve additional engineering and ongoing maintenance.

Since v0.42.0 of [play-frontend-hmrc](https://github.com/hmrc/play-frontend-hmrc/releases/tag/v0.42.0), we have recommended teams 
use the minified CSS and JS bundles provided as part of the now self-published `hmrc-frontend` webjar via `hmrcHead` and `hmrcScripts`. 
These bundles include the CSS and JS from both govuk-frontend and hmrc-frontend. For teams using this approach, the only
assets still being retrieved from the govuk-frontend webjar are the handful of icons referenced
in `govukTemplate` and `govukHeader`. Architecturally, this split of assets between the two libraries is awkward and a potential source
of confusion for future users and maintainers, addressing it would reduce complexity and ease maintenance.

Bearing the above in mind, should we remove the hard dependency on the govuk-frontend webjar by:
* inlining the images into this repository, for teams directly using `govukTemplate` `govukLayout` or 
  `govukHeader`
* provide a mechanism to override the `assetsPath` parameter in 
`govukTemplate` and `govukHeader` so that `play-frontend-hmrc` can supply its own images and 
* remove the govuk-frontend webjar dependency now or at some point in the future?

## Decision Drivers

* The need to make improvements and upgrades to govuk-frontend
  available in play-frontend-govuk quickly
* The increasing user base of play-frontend-govuk, and accelerating demand for new features and
  improvements.
* The desire to reduce boilerplate in consuming services.
* The high number of services still referencing `lib/govuk-frontend/govuk/all.js`
* The fact that the images have not changed for a long time (since September 2018)
* The need to minimise the impact of breaking changes on service teams.
* The hardship, frustration and toil the current manual publishing process is causing the team. 

## Considered Options

* Option 1: Do nothing
* Option 2 (non-breaking): Inline the images and provide override mechanism but keep govuk-frontend dependency for now, advising
  teams to move over to `hmrcHead` and `hmrcScripts` because a breaking change will be made eventually
* Option 3 (breaking): Inline the images, provide override mechanism and remove govuk-frontend dependency

## Decision Outcome

Chosen option: Option 2, because doing so will (a) put is in a better position to eliminate the use of the webjar eventually,
(b) allow us to make changes to play-frontend-hmrc to eliminate the `/govuk-frontend` route and (c) not introduce any
breaking changes.

## Pros and Cons of the Options

### Option 1

* Bad, because it doesn't get us any closer to removing the govuk-frontend webjar eventually
* Bad, because it persists the architecturally problematic split between assets delivered via
  the govuk-frontend and hmrc-frontend webjars, with most delivered via the latter
* Bad, because consuming libraries or services still have no way to provide an alternative assets path
* Bad, because we continue to have a hard dependency on webjars.org
* Good, because no further work

### Option 2

* Good, because we can remove the need to include the govuk-frontend route when integrating with play-frontend-hmrc
* Good, because consuming libraries and services can override the assets path to supply assets from a different location
* Good, because teams using the latest helpers no longer use any assets from the govuk-frontend webjar
* Bad, because we still need to publish the govuk-frontend webjar on each new release of govuk-frontend
* Bad, because the inlined images may become out of date.

### Option 3

* Good, because we can remove the need to include the govuk-frontend route when integrating with play-frontend-hmrc
* Good, because consuming libraries and services can override the assets path to supply assets from a different location
* Good, because teams using the latest helpers no longer use any assets from the govuk-frontend webjar
* Good, because we would no longer need to publish the govuk-frontend webjar reducing lead times for porting
  new versions of govuk-frontend
* Good, because more teams will be required to use the latest helpers improving platform consistency and frontend performance
* Bad, because teams upgrading to the latest version of play-frontend-govuk would have to either
  upgrade to use the latest helpers or publish and add the correct version of the govuk-frontend webjar to their dependencies.
* Bad, because the inlined images may become out of date.
  
## Links

* Relates to [play-frontend-hmrc/ADR-0009](../adr/0009-self-publish-webjar.md)
