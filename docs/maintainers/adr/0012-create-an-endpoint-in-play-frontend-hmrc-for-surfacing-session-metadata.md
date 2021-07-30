# Create an endpoint in play-frontend-hmrc for surfacing session metadata

* Status: accepted
* Date: 2021-07-29

Technical Story: PLATUI-988

## Context and Problem Statement

Tax users are sometimes, without warning, unnecessarily signed out when accessing 
MDTP services using multiple browser tabs or windows. This is a violation of [WCAG 2.1 
success criterion 2.1.1 (Timing adjustable)](https://www.w3.org/WAI/WCAG21/Understanding/timing-adjustable.html).

This problem is a consequence of the fact that a user's session can be refreshed via user activity in any 
tab or window belonging to the same browser profile. However, the Javascript-powered [hmrcTimeoutDialog](https://github.com/hmrc/play-frontend-hmrc#warning-users-before-timing-them-out) 
currently has no way of knowing about this activity following initial page load.

Solving this issue requires providing the timeout dialog component with knowledge of the actual time 
remaining on the user's active session via an endpoint that is itself excluded from 
session management.

How can we achieve this cost-effectively while minimising impact for service teams, limiting duplication of
knowledge and avoiding introducing additional coupling between frontend microservices?

## Decision Drivers

* The need to minimise code changes for service teams other than a library upgrade.
* The avoidance of requiring service teams to add any additional routing rules.
* The avoidance of requiring service teams to add any additional configuration.
* The need to limit duplication of knowledge between SessionTimeoutFilter in bootstrap-play and 
  any other library or service.
* The preference for maintaining loose coupling of frontend services and avoiding adding single points
  of failure between frontend microservices.
* The need for the endpoint used to interrogate the session to not itself affect the session. 

## Considered Options

* Option 1: add a controller and route into play-frontend-hmrc for interrogating session state without
  adding play-frontend-hmrc as a dependency of bootstrap-play
* Option 2: add a controller and route into bootstrap-play for interrogating session state, adding
  bootstrap-play as a dependency of play-frontend-hmrc
* Option 3: create a dedicated microservice hosted on the platform whose sole purpose would be to query
  session state.
* Option 4: request a change to the frontend used to manage sign in interactions with SCP to add an endpoint
  for querying session state.
* Option 5: store the time remaining on the session in an unencrypted cookie. Have the timeout dialog 
  read from this cookie.
* Option 6: as option 2 but moving all timeout dialog related functionality out of 
  play-frontend-hmrc and into bootstrap-play including the Twirl template.
* Option 7: split out all session related functionality (including the filter, timeout 
  dialog Twirl template, session and keep alive controllers) into play-session, a new session-management
  library. Make play-session a dependency of play-frontend-hmrc and play-frontend-hmrc
  a dependency of bootstrap-play.
* Option 8: as option 6 except adding play-frontend-hmrc as a dependency of 
  bootstrap-play and using the hmrcTimeoutDialog component (but not hmrcTimeoutDialogHelper) from play-frontend-hmrc. 
* Option 9: as option 1 but adding play-frontend-hmrc as a dependency of bootstrap-play.

## Decision Outcome

Chosen option: option 9, because it is an option PlatUI and PlatOps agree on, is technically feasible,
satisfies most of the decision drivers and is the smallest possible change with the least impact to service teams. The intention would be
to revisit option 7 (play-session) at a later date in order to address any outstanding concerns
around knowledge duplication.

## Pros and Cons of the Options

### Option 1

* Good, because play-frontend-hmrc already has such controllers to manage language switching and the keep alive 
  functionality used by the timeout dialog.
* Good, because most teams will already have a routing rule for hmrc-frontend routes in their 
  app.routes file.
* Bad, because duplication of knowledge between bootstrap-play and play-frontend-hmrc, 
  unless the calculated time remaining could be stored in the session itself.
* Bad, because the setting of the excluded endpoint in play-frontend-hmrc's reference.conf would 
  rely on no default setting being provided in bootstrap-play’s frontend.conf. This is non-standard and 
  introduces an implicit dependency via configuration that should be made explicit. Additionally, adding
  configuration into library's reference.conf file can lead to unexpected behaviour due to a lack of
  configuration precedency between libraries.

### Option 2

* Good, because having added the additional endpoint, we could use it for supplying additional
  metadata to frontend services in future.
* Good, because there is prior art for this in the form of the /health endpoint.
* Bad, because services would need to add an additional routing rule into their app.routes file.
* Bad, because we need a reverse router for hmrcTimeoutDialogHelper. This is problematic because
  bootstrap-play does not itself include the Play plugin so is unable to compile route files or 
  generate reverse routers. We could 
  hand-roll the router as with HealthRoutes but we would additionally need to create a 
  reverse router and call setPrefix on it in .withPrefix as is the case for auto-generated routers. Bad, 
  because this mechanism is undocumented and at risk of breaking in a future Play version.
* Bad, because play-frontend-hmrc would need to add bootstrap-play as a dependency 
  (to access the SessionController’s reverse router) - this may break consumers on earlier 
  versions of bootstrap-play.

### Option 3

* Good, because there are potentially no performance implications for consuming frontend microservices.
* Good, because the endpoint would not be frontend specific and so the exemption in the session timeout
      filter could be hard-coded.
* Bad, because it compounds the duplication of knowledge by adding another moving part to 
  session management as a networked dependency.
* Bad, because it means service developers will need to spin up yet another microservice when 
  running locally, via service manager.
* Bad, because it introduces further tight coupling and single point of failure between 
  all frontend microservices on the platform.
* Bad, because of the massive scaling requirements needed to support such an endpoint and 
  doing this simply to decode the session cookie, something the frontend microservice can 
  do itself, is arguably a poor use of resources.
* Bad, because as it stands frontends can choose their own session timeout duration and the 
  centralised service has no knowledge of the actual session timeout applicable on the service. 
  However, it’s possible this ability may be removed in a later iteration of the platform removing 
  this as an obstacle.
* Bad, because of the cost (resources, time) of supporting and maintaining another shared service. 
  The new service would need its own runbook, auditing assessment, PRA, CAB approval etc.

### Option 4

* Good, because there are potentially no performance implications for consuming frontend microservices.
* Good, because authenticated services are likely to already have this microservice as part of 
  their service manager profile
* Good, because the endpoint would not be frontend specific and so the exemption in the session timeout
  filter could be hard-coded
* Bad, because this would add a huge additional scaling requirement to this microservice 
* Bad, because we would create additional coupling between this microservice, bootstrap-play and 
  play-frontend-hmrc.
* Bad because the platform teams do not own this microservice.
* Bad, because not all services are authenticated so would not currently have any dependency 
  on this microservice.
* Bad, because this microservice has no knowledge of the actual session timeout applicable on the 
  service.

### Option 5

* Good, because the timeout dialog would not need to perform a XHR request to obtain the seconds 
  remaining in the session, it just needs to read the cookie.
* Bad, because unencrypted cookies would trigger DAST (ZAP) test failures and it’s not possible to 
  add exemptions for individual cookies. Suppressing these alerts completely would then potentially 
  mask warnings teams should do something about.
* Bad, because any new cookie may need auditing and then listing in the MDTP cookie policy, although 
  it should be regarded as an essential cookie.
* Bad, because this option may not be technically feasible as the cookie would be set on the 
  server side, requiring a server request to actually set it.
  
### Option 6

* Good, for the same reasons as option 2
* Good, because it resolves the duplication of knowledge between bootstrap-play and 
  play-frontend-hmrc - all session related logic is now in bootstrap-play except for the 
  Javascript logic in hmrc-frontend.
* Bad, for the same reasons as option 2
* Bad, because bootstrap-play does not include the Twirl plugin. Adding may cause 
  incompatibility issues.
* Bad, because bootstrap-play has not historically hosted any Twirl templates so this would 
  be a change of direction.
* Bad, because it introduces a dependency on the hmrc-frontend webjar in bootstrap-play

### Option 7

* Good, because there would be no requirement to add additional routing rules into frontend microservices 
  implying a reduced risk of broken implementations.
* Good, because this option more fully resolves the duplication of logic between bootstrap-play and
  play-frontend-hmrc
* Good, because better separation of concerns.
* Good, because tying to bootstrap-play makes it more likely less engaged teams will update 
  their frontend libraries more regularly.
* Bad, because potential for unintended side-effects for services not currently using 
  play-frontend-hmrc e.g. users of play-nunjucks/play-ui
* Bad, because it ties the PlatUI frontend library release schedule to bootstrap unless 
  microservices import play-frontend-hmrc separately. This may increase lead times 
  for the latest bug fixes/features.
* Bad, because it introduces an additional library, cross compiled for multiple Play 
  versions, that would need ongoing maintenance.
* Bad, because if we also extract the Javascript functionality from hmrc-frontend a change 
  would be needed to hmrcScripts in play-frontend-hmrc to include an additional Javascript 
  snippet. This would create additional complexity around testing and building the Javascript. 
  The spike above keeps the Javascript in hmrc-frontend for PoC purposes. Also, not all 
  teams are using hmrcScripts.
* Bad, because it’s a more impactful change affecting the standard set of Play filters 
  used by all frontend microservices. A bootstrap version bump would be required.
* Bad, because filter configuration would implicitly depend on the /hmrc-frontend routing 
  rule configured in frontend microservices (although this is relatively standard)
* Bad, because teams would need to update to use the timeout dialog helper in the new 
  library rather than the one in play-frontend-hmrc. Could be mitigated by including the 
  timeout dialog into hmrcLayout/hmrcHead.
  
### Option 8

* Good, for the same reasons as option 6.
* Good, because potentially doesn’t require the sbt Twirl plugin added to bootstrap-play.
* Good, because it conforms to the pattern of play-frontend-hmrc as a direct port of hmrc-frontend.
* Bad, for the same reasons as option 6.
* Bad, because it results in a component partially implemented in another library.
* Bad, because it adds play-frontend-hmrc as a dependency to bootstrap-play (see option 9)

### Option 9

* Good, because no code changes in consuming services and no requirement to add additional 
  routing rules. Implies faster roll-out, and reduced risk of broken implementations.
* Good, because simple, no requirement to create an additional library.
* Good, because no issues around the filter configuration default as with option 1.
* Good, because including play-frontend-hmrc into bootstrap-play reduces the number of 
  library dependencies in frontend-microservices.
* Good, because it makes explicit the implicit coupling between the Javascript timeout dialog 
  and SessionTimeoutFilter because hmrc-frontend is a dependency of play-frontend-hmrc.
* Good, because tying to bootstrap-play makes it more likely less engaged teams will update 
  their frontend libraries more regularly.
* Bad, because potential for unintended side-effects for services not currently using 
  play-frontend-hmrc e.g. users of play-nunjucks/play-ui
* Bad, because ties PlatUI frontend library release schedule to bootstrap unless microservices 
  import play-frontend-hmrc separately. This may increase lead times for the latest bug fixes/features.
* Bad, because filter remains in bootstrap-play and we still have duplication of 
  timeout calculation logic between play-frontend-hmrc and bootstrap-play 
  unless mitigated by storage of session time remaining in the session itself.

## Links

* [Timeout warning GOV.UK design system issue](https://github.com/alphagov/govuk-design-system-backlog/issues/104)
