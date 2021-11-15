---
tags:
  - known-issue
  - wont-fix
  - PLATUI-1399
---

The HmrcTrackingConsentSnippet helper uses the `platform.frontend.host`config variable to build the URLs for the JavaScript and CSS required to display the cookie consent banner.

The helper assumes that this config variable be empty when running the service locally. That it will only be set by the platform configuration, where it will be something like “www.tax.service.gov.uk”, depending on the environment it is running on.

However, we have found one instance where a service team was depending on `platform.frontend.host` always being set, and so added a local default to their configuration which then broke their local integration with tracking-consent-frontend.

For this team, that meant that some of their visual regression tests which checked for the cookie consent banner were failing locally because, with `platform.frontend.host` set, the helper output incorrect URLs for the JavaScript and CSS.

We could not quickly come up with a non-breaking change to avoid this in play-frontend-hmrc, so the work around here was for the service team to keep their local fallback for `platform.frontend.host` in a different variable.
