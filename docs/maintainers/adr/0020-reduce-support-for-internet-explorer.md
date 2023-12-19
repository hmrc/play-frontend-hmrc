# Reduce support for Internet Explorer

* Status: accepted
* Date: 2023-12-19

Technical Story: PLATUI-2686

## Context and Problem Statement

[govuk-frontend v5.0.0](https://github.com/alphagov/govuk-frontend/releases/tag/v5.0.0)
reduces support for Internet Explorer (IE) versions 8 through 10 by preventing loading of its Javascript (JS)
and removing various IE-specific polyfills.  IE11 is still supported via graceful degradation to a non-JS experience.

We propose to follow the same approach in both our
[hmrc-frontend](https://github.com/hmrc/hmrc-frontend/)
and
[play-frontend-hmrc](https://github.com/hmrc/play-frontend-hmrc/)
libraries, preventing loading of our own JS and removing any IE-specific polyfills.

## Decision Drivers

* Direction taken by Government Digital Service
* Industry trends to drop support for IE
* Overhead of maintaining and testing IE-specific implementations
* Minimal number of users still using IE. A recent sample found the number was less than 0.6% of users over a 2-week period,
whereas the [GDS Service Manual](https://www.gov.uk/service-manual/technology/designing-for-different-browsers-and-devices)
only mandates support for IE11 where at least 2% of a service's users are using it.
* Security risk to end-users of continuing to use a browser beyond its [End-Of-Life in June 2022](https://learn.microsoft.com/en-us/lifecycle/products/internet-explorer-11)
* Impact to service teams using our libraries

## Considered Options

* Drop support for IE entirely in our libraries
* Provide minimal support for IE in our libraries
* Continue supporting IE in our libraries

## Decision Outcome

Chosen option: "Provide minimal support for IE in our libraries" because, whilst we don't want to continue supporting
outdated browsers that pose a security risk to the few end users that are still using them,
we recognise that dropping all support may end up breaking many services, requiring a lot of service teams' effort to fix.
If there are components like the timeout dialog that are required to work in all browsers,
we'll work out a way to load these separately.

## Pros and Cons of the Options

### Drop support for IE entirely in our libraries

* Good, because we no longer need to maintain and test IE-specific JS and other polyfills
* Good, because we won't have to fix up any of our own JS which relied on the GOVUK-provided JS/polyfills
* Good, because this will nudge any remaining IE users towards a more modern, secure browser
* Good, because it aligns with industry trends where major web technologies are moving away from IE support
* Good, because removing legacy browser compatibility can lead to better performance / user experience for all users

* Bad, because some services may be relying on our provided JS/polyfills, requiring them to retest and fix
* Bad, because it may prevent a small number of users from using some services without technical assistance
* Bad, because it could potentially exclude users in corporate environments where IE is still used
* Bad, because it ideally requires clear communication to inform users about the change and support them in moving to a supported browser

### Provide minimal support for IE in our libraries

This would be a halfway house where we prevent most of our own JS from loading in IE, but keep certain polyfills
that teams might be relying on for their services to look as expected in IE (eg. html5shiv, which is a shim for newer
HTML5 layout elements that are otherwise unsupported in IE).

* Good, because it provides a balance between modernisation and backward compatibility, allowing for a more gradual transition away from IE
* Good, because it could reduce the risk of breaking critical functionality in larger/older services that are slow to adapt
* Good, because we no longer need to maintain and test other IE-specific JS and polyfills
* Good, because we won't have to fix up any of our own JS which relied on the GOVUK-provided JS/polyfills

* Bad, because it creates ambiguity about the level of support for IE
* Bad, because it could lead to inconsistent user experience across different services / browsers
* Bad, because it still incurs some maintenance costs and complexity, even if reduced

### Continue supporting IE in our libraries

* Good, because services relying on our provided JS/polyfills will continue to work
* Good, because any remaining IE users won't experience any technical problems and won't need more support

* Bad, because these users will continue to be at risk due to using an insecure browser
* Bad, because we will have to fix up any of our own JS which relied on the GOVUK-provided JS/polyfills
* Bad, because we will have to continue maintaining / testing IE-specific JS and polyfills
