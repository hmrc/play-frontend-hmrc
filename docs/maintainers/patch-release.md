# Creating a patch release

## Overview
Occasionally, it can be necessary to create a patch release of `play-frontend-hmrc` from an older (i.e. not lastest) 
version of the library. For example, it might be necessary to bump an older version of `hmrc-frontend` or `govuk-frontend`.

An example of when this was necessary was when the Tudor Crown logo was added to `govuk-frontend`. A tight deadline was 
mandated by Government Digital Service (GDS), shortly after they had released a breaking major release (see notes from 
GDS [here](https://us1.campaign-archive.com/?e=__test_email__&u=ada1bae4b177beb9e03502ced&id=2325298b37&utm_source=GOV.%E2%80%8CUK+Design+System&utm_campaign=aabdb157a5-FRONTEND_CROWN_05022024&utm_medium=email&utm_term=0_367f0a5723-aabdb157a5-536304558])).

## Approach

The main concept is to create a branch from an older tag of `play-frontend-hmrc`, then create a "hotfix" release from 
the branch without merging the changes to main.

## Process

1. Locally check out your older tag of `play-frontend-hmrc` to a local branch. For example, if `main` has been released
   as `v9.0.0` but you need to make a hotfix release from the `v8.5.0`, you will need to run:
   ```bash
   git checkout -b PLATUI-XXXX_some-hotfix-release v8.5.0
   ```  
2. On your local branch (in this example, `PLATUI-XXXX_some-hotfix-release`) make any necessary changes. For example, for
   the Tudor Crown changes, the version of `hmrc-frontend` was bumped from `v5.62.0` to `v5.67.0`.
3. Commit your changes to your local branch.   
4. Once you are happy with your local changes, push the branch to remote, e.g. 
      ```bash
   git push --set-upstream origin PLATUI-XXXX_some-hotfix-release
   ```  
5. Open a pull request as a **draft** and review as normal
6. **DO NOT MERGE YOUR BRANCH TO  MAIN** - this is very important as you will not be using the default build pipeline, 
   and your working branch has not been branched from `main`
7. Instead, release a hotfix branch.
   1. Navigate to the `play-frontend-hmrc` build job on Jenkins
   2. From the left sidebar, click "Build with parameters"
   3. On the "Project play-frontend-hmrc" page, enter your branch name (e.g. `PLATUI-XXXX_some-hotfix-release`) and click
      the "Build" button
8. When the above job finishes running, your hotfix release branch should now be available. In this instances, as it was 
   hotfix against major version 8, the patch version is `v8.5.1`.

## Hotfix branch
**The hotfix branch should not be merged to `main` at any point.**. Once the hotfix has been released, a decision can be 
made within PlatUI on whether to keep the hotfix branch, or to delete.

