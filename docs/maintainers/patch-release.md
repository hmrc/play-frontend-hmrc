# Creating a patch release

## Overview
Occasionally, it can be necessary to create a patch release of `play-frontend-hmrc` from an older (i.e. not latest) 
version of the library. For example, it might be necessary to bump an older version of `hmrc-frontend` or `govuk-frontend`.

An example of when this was necessary was when the Tudor Crown logo was added to `govuk-frontend`. A tight deadline was 
mandated by Government Digital Service (GDS), shortly after they had released a breaking major release (see notes from 
GDS [here](https://us1.campaign-archive.com/?e=__test_email__&u=ada1bae4b177beb9e03502ced&id=2325298b37&utm_source=GOV.%E2%80%8CUK+Design+System&utm_campaign=aabdb157a5-FRONTEND_CROWN_05022024&utm_medium=email&utm_term=0_367f0a5723-aabdb157a5-536304558])).

## Approach

The main concept is to create a branch from an older tag of `play-frontend-hmrc`, then create a "hotfix" release from 
the branch without merging the changes to main. 

## Process

1. Create a release branch for the new version, based on the tag of the older version. For example, if `main` has been 
   released as `v9.0.0` but you need to make a hotfix release from the `v8.5.0`, you will need to run:
   ```bash
   git checkout -b release/v8.5.1 tags/v8.5.0
   ``` 
2. Push your release branch to the remote, e.g.
      ```bash
   git push --set-upstream origin release/v8.5.1
   ```
3. Create a feature branch off the release branch, for the required changes, e.g. 
      ```bash
   git checkout -b PLATUI-XXXX_some-hotfix-release
   ```
4. On your local branch (in this example, `PLATUI-XXXX_some-hotfix-release`) make any necessary changes. For example, for
   the Tudor Crown changes, the version of `hmrc-frontend` was bumped from `v5.62.0` to `v5.67.0`.
5. Commit your changes to your local branch.   
6. Once you are happy with your local changes, push the branch to remote, e.g. 
      ```bash
   git push --set-upstream origin PLATUI-XXXX_some-hotfix-release
   ```  
7. Raise a PR from the feature branch into the release branch (to get the changes approved before building the new version), 
   eg. using the Github CLI `gh pr create -B release/v8.5.1`
8. **DO NOT MERGE YOUR BRANCH TO  MAIN** - this is very important as you will not be using the default build pipeline, 
   and your working branch has not been branched from `main`
9. Instead, once your PR is approved and merged, release a hotfix branch.
   1. Navigate to the `play-frontend-hmrc` build job on Jenkins
   2. From the left sidebar, click "Build with parameters"
   3. On the "Project play-frontend-hmrc" page, enter your **release branch** name (e.g. `release/v8.5.1`) and click
      the "Build" button
10. When the above job finishes running, your hotfix release branch should now be available. In this instances, as it was 
   hotfix against major version 8, the patch version is `v8.5.1`. This release will be published to HMRC's Open Artefacts,
   so can be used as dependency in a service like any other library build.

## Release branch
**The release branch should not be merged to `main` at any point.**. Once the hotfix has been released, a decision can be 
made within PlatUI on whether to keep the release branch, or to delete.

