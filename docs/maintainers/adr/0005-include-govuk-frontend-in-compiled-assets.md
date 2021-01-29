# Include GOV.UK frontend in compiled assets

* Status: accepted
* Date: 2021-01-25

Technical Story: PLATUI-934 and PLATUI-935

## Context and Problem Statement

Service developers seeking to use play-frontend to build their frontend microservices need to follow around
12 separate integration steps involving two separate Scala libraries: play-frontend-govuk and play-frontend-hmrc. Failure to 
perform any one of these steps correctly, or making changes that break any of this configuration,
 results in failed builds, incorrectly styled components or broken Javascript-enabled functionality. These steps involve:
 
* Adding play-frontend-govuk and play-frontend-hmrc as SBT dependencies.
* Setting up an SBT asset pipeline to compile and optimise the CSS and Javascript into bundles suitable for production.
* Adding multiple asset routing rules.
* Adding the boilerplate SCRIPT and LINK tags to the HTML page template to link to the assets at runtime.
* Adding Javascript to initialising the GOV.UK and HMRC components.

Following investigation, we discovered that the integration process could be drastically simplified 
by providing head and script helpers that would link to pre-compiled and optimised asset bundles from 
the hmrc-frontend webjar, subsuming the govuk-frontend assets and taking responsibility
for component initialisation. This move would:

* simplify the migration path from assets-frontend/play-ui/govuk-template to play-frontend
* particularly benefit teams lacking an embedded full time frontend developer
* not radically change the way microservices integrate with play-frontend
* still allow teams to create custom components and add their own custom CSS where needed
* continue to allow for local development without reliance on networked dependencies

Overall, the total number of integration steps would be reduced from 12 to 6.

Facing these considerations, should we therefore
* change the pre-compiled hmrc-frontend bundles to also include govuk-frontend and to initialise all components
* add the pre-compiled and optimised CSS and Javascript bundles into the published hmrc-frontend npm package and
* create Twirl helper components to render the HTML snippets necessary to link to them?

## Decision Drivers

* The integration difficulties teams have reported over the past 12 months.
* The problematic nature of diagnosing build issues in frontend microservices due to the requirement for a deep 
knowledge of both Scala and frontend build tooling, which is rare.
* The pressing need to migrate services away from assets-frontend for reasons of accessibility, security and sustainability.
* That services do not generally need extensive service-specific custom CSS. UI components are heavily standardised by GDS.
* That services do not generally need extensive service-specific Javascript. The mantra of progressive
enhancement demands services build without Javascript if possible.
* That teams should be free to create custom components with custom CSS where there is a genuine need, without relying
on other teams. Historically denying teams this ability arguably led to many of the problems with assets-frontend.
* That teams should continue to be able to develop locally without relying on networked dependencies.

## Considered Options

* Add pre-compiled assets with auto-initialisation.
* Add pre-compiled assets without auto-initialisation.
* Do nothing.

## Decision Outcome

Chosen option: "Add pre-compiled assets with auto-initialisation", because this is a major step forward in improving
the ease of use of play-frontend and goes a long way to address many of the issues
users have experienced.

The change to the S3 assets is not of concern due to the fact that the existence of these
 assets was never publicised or fully documented and we have seen only a tiny number of requests for these assets
 in server logs.

### Positive Consequences

* A radically simplified integration process for Scala developers.
* Fewer moving parts in frontend microservices and reduced boilerplate.
* No changes needed by teams wanting to continue to consume the original un-compiled assets.
* Users of the npm package should not be affected by this change as it adds files only.
* Simplified usage outside Scala microservices e.g. in outage/waiting pages.
* Bundling govuk-frontend and hmrc-frontend together means there is no longer the possibility for the use
of incompatible versions of govuk-frontend with hmrc-frontend.
* Best practice is to initialise all GOV.UK and HMRC components rather than
cherry picking to avoid the risk of breaking accessibility features. Doing this automatically
means there is one less thing for teams to worry about getting right.

### Negative Consequences

* Teams using the S3-distributed hmrc-frontend assets will no longer have the option to separately initialise the
 GOVUK and HMRC components. However, they will retain access to window.GOVUKFrontend and window.HMRCFrontend if
 needed for initialisation of dynamically injected content.
* On their next upgrade, teams using the S3-distributed hmrc-frontend assets will need to remove the SCRIPT tag referencing the
govuk-frontend bundle and any calls to GOVUKFrontend.initAll() or HMRCFrontend.initAll().
* The distribution build task diverges from the pattern set by govuk-frontend. In govuk-frontend, the distributable does not
auto-initialise the govuk components. This may affect developers consuming hmrc-frontend using git rather than via the npm package.
