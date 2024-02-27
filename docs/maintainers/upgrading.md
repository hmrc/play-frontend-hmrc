# Upgrading play-frontend-hmrc

> **Warning**
> When you bump govuk-frontend version, you need to manually check release changes for new components and new parameters that need to be added to our Twirl implementations. They won't currently be automatically detected as missing by our automated tests. Read [comparing differences](#comparing-differences) below for ways you can do this.

## Check for updated Govuk components

To ensure that our play-frontend-hmrc library stays up to date with govuk-frontend, 
it is important to regularly review the release [notes](https://github.com/alphagov/govuk-frontend/releases) for govuk-frontend to identify any component updates or new additions. 
If there are changes to components, we should create tickets to discuss as a team whether we want to incorporate those changes into our forked components in the play-frontend-hmrc library.

As an example, there was previously an update to the header Nunjucks macro that added the ability to localize the 
text of the mobile navigation menu toggle button using the menuButtonText parameter.

### Release note from Govuk with the updated component change

```
Localise the navigation menu toggle button
When using the header Nunjucks macro, you can now translate the text of the mobile navigation menu toggle button by using the menuButtonText parameter.

You should avoid lengthy values for the menuButtonText parameter. If the text is too long it can overflow and cause visual issues.

This was added in pull request #2720: Add parameter to localise mobile menu toggle button.
```

A ticket was created to track the work that would be required to incorporate an update to the header component that added the ability to localize the text of the mobile navigation menu toggle button using the menuButtonText parameter into our forked component. However, this ticket still needs to be discussed with relevant parties to determine if we want to introduce this change into our forked component.

### Ticket created to capture change 

```
Description

As a platform developer
I need to update the hmrc-frontend header component to include the new localisation option
So that we are keeping our hmrc components in sync with govuk components

What happens if we don’t do it?

We risk not keeping hmrc components in sync with its dependant govuk's library components with its latest enhancements and updates.

Acceptance Criteria

I know this ticket is done when…

Teams can change the menu button label using the menuButtonText property in our header component

Implementation detail: we need to confirm with DR(Design Resources) that we should add this parameter to hmrc fork of header, or whether instead we should just add a translation for this bit of copy, since most hmrc components don't let people provide translation themselves, they just take a lang param and have fixed translations for english and welsh
```

In some cases, the decision to incorporate updates to components may not be solely up to our team and may require input from design resources. Therefore, we may need to reach out to them for their input on these decisions.

## Basic Steps

1. Update the value of `govukFrontendVersion` or `hmrcFrontendVersion` in [LibDependencies](../../project/LibDependencies.scala) 
   according to the applicable version of [govuk-frontend](https://github.com/alphagov/govuk-frontend/tags) or
   [hmrc-frontend](https://github.com/hmrc/hmrc-frontend/tags).
1. If upgrading `govuk-frontend`, [manually publish as a WebJar](#steps-to-manually-publish-govuk-frontend-as-a-webjar).
1. With `x-govuk-component-renderer` running locally (see below), run `sbt generateGovukFixtures` for `govuk-frontend`
     upgrades or `sbt generateHmrcFixtures` for `hmrc-frontend` upgrades.
1. Run the unit tests: `sbt clean test`.
1. Run the integration tests: `sbt clean it:test`.
1. Compare the two versions of `govuk-frontend` or `hmrc-frontend` (outgoing vs incoming) using a diff tool 
   as shown [below](#examining-components-for-failed-tests).
   
### Steps to manually publish `govuk-frontend` as a WebJar
1. Navigate to https://www.webjars.org/
1. Click the blue "Add a WebJar" button
1. Select type as "NPM"
1. Enter govuk-frontend as the package name, and select 4.1.1 as the version
1. Click the "Deploy!" button

### Comparing Differences
Since some of the components have dependencies, it is easier to start upgrading by starting from components with no dependencies, or, alternatively, from the components on the bottom of a dependency graph, and work our way up.

We should first look at the components for which tests failed. It is also important to verify that there are no changes in the other components which were not flagged up by failing tests. Again, this situation might be mitigated by increasing test coverage with generative testing.

#### Examining Components for Failed Tests
 
For each failed test, make a diff between the revisions of the affected component for the old hmrc-frontend version and the new one.

Ex: For upgrading from `v1.4.0` to `v1.5.0`: https://github.com/hmrc/hmrc-frontend/compare/v1.4.0...v1.5.0

If there are many moved/renamed files, the heuristics used by git/github to detect such changes may not be accurate, and it is better to use an appropriate diff tool to perform the comparisons.

The important files to diff are the `.yaml` files describing the component's interface and the component's Nunjucks template implementation, usually named `template.njk`.

Ex:

Assuming we setup an appropriate diff tool to use with git, the following example shows how to diff the `.yaml` and `template.njk` files for a given component. Instructions for using `IntelliJ`’s diff and merge tool with `git` can be found [here](https://gist.github.com/rambabusaravanan/1d1902e599c9c680319678b0f7650898).

Given revisions for `hmrc-frontend v1.4.0` of `df35695` and `hmrc-frontend v1.5.0` of `dcfad17`:

```bash
git difftool df35695:src/components/page-heading/page-heading.yaml dcfad17:src/components/page-heading/page-heading.yaml

git difftool df35695:src/components/page-heading/template.njk dcfad17:src/components/page-heading/template.njk
```

#### Looking at Other Components

Now we should verify all the other components for differences and improve test coverage in case those differences were
not detected by the tests.

Ex: For the `file-upload` govuk-frontend component there was a new parameter added but none of the test cases detected the change because 
of insufficient test coverage.
```bash
git diff 8370f97:src/components/file-upload/file-upload.yaml                 3ef1d76:src/govuk/components/file-upload/file-upload.yaml
```

```diff
diff --git a/src/components/file-upload/file-upload.yaml b/src/govuk/components/file-upload/file-upload.yaml
index 51d2d346..713db75d 100644
--- a/src/components/file-upload/file-upload.yaml
+++ b/src/govuk/components/file-upload/file-upload.yaml
@@ -11,6 +11,10 @@ params:
   type: string
   required: false
   description: Optional initial value of the input
+- name: describedBy
+  type: string
+  required: false
+  description: One or more element IDs to add to the `aria-describedby` attribute, used to provide additional descriptive information for screenreader users.
 - name: label
   type: object
   required: true
```
### Comparing forked components in other repos
As noted above, some of our `play-frontend-hmrc` components are not direct ports, but are manually created and need to be kept manually aligned with `govuk-frontend` in particular. 

For example, if there are changs to the `GovukCookieBanner` template or styles, these should be compared against the cookie banner in `tracking-consent-frontend` (see [banner.ts](https://github.com/hmrc/tracking-consent-frontend/blob/main/js/src/ui/banner.ts)).

#### Adding New Components
When running the above steps, there will be test failures if existing components have changed. However, there will be no
failures for new components which have been added to `hmrc-frontend` or `govuk-frontend` but which have not yet been implemented in 
`play-frontend-hmrc`.

Therefore, it is important to look at the diffs between versions of `govuk-frontend`, and read the CHANGELOG.

To add a new component:
- Add the view model as a Scala case class in the folder: `src/main/scala/uk/gov/hmrc/(hmrc|govuk)frontend/views/viewmodels`
- Add an alias to the view model in `src/main/scala/uk/gov/hmrc/(hmrc|govuk)frontend/views/Aliases.scala`
- Add the Twirl template in the folder: `src/main/twirl/uk/gov/hmrc/(hmrc|govuk)frontend/views/components`
- Add the template unit test: `src/test/scala/uk/gov/hmrc/(hmrc|govuk)frontend/views/components`. This should extend 
`TemplateUnitSpec[YourViewModel, YourComponent]("yourComponent")`
- Add the template integration test: `src/it/scala/uk/gov/hmrc/(hmrc|govuk)frontend/views/components`. This should extend
`TemplateIntegrationSpec[YourViewModel, YourComponent]("yourComponent")`. You will also need to create new Generator
classes in `src/test/scala/uk/gov/hmrc/(hmrc|govuk)frontend/views/viewmodels`

## Useful Links
- [x-govuk-component-renderer](https://github.com/hmrc/x-govuk-component-renderer) - service that returns HTML for `govuk-frontend` and `hmrc-frontend` component input parameters in the form of JSON objects - useful for confirming Twirl HTML outputs in integration tests
- [GOV.UK Design System](https://design-system.service.gov.uk/components/) - documentation for the use of `govuk-frontend` components
- [govuk-frontend](https://github.com/alphagov/govuk-frontend/) - reusable Nunjucks HTML components from GOV.UK
- [hmrc-frontend](https://github.com/hmrc/hmrc-frontend/) - reusable Nunjucks HTML components for HMRC design patterns
- [HMRC Design Patterns](https://design.tax.service.gov.uk/hmrc-design-patterns/) - documentation for the use of `hmrc-frontend` components
