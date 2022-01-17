#!/bin/bash

export CI_SHA1=$BUILDKITE_COMMIT
export CI_BRANCH=$BUILDKITE_BRANCH
export CI_BUILD_NUM=$BUILDKITE_BUILD_NUMBER

## See: https://docs.docker.com/engine/reference/commandline/tag/#extended-description
export CI_BRANCH_DOCKER_TAG=$(echo -n "${CI_BRANCH}" \
  | sed -E 's/[^[:alnum:]_.-]+/-/g' \
  | sed -E 's/^[.-]*(.+)/\1/g' \
  | cut -c -128)
