#!/bin/bash
# Locally publishes every supported play version for every supported scala version

set -o errexit   # abort on nonzero exitstatus
set -o nounset   # abort on unbound variable
set -o pipefail  # don't hide errors within pipes

env PLAY_VERSION=3.0 sbt clean +publishLocal

# NOTE:
# we have separate sbt build config for each play version and not all versions are
# cross compatible with the same scala versions. For example, 2.8 is our last to
# have support for scala 2.12
