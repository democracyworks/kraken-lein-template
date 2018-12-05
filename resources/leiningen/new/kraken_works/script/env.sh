#!/bin/bash

apk add --update nodejs
npm install -g rok8s-scripts@v7.11.4

export CI_SHA1=$BUILDKITE_COMMIT 
export CI_BRANCH=$BUILDKITE_BRANCH 
export CI_BUILD_NUM=$BUILDKITE_BUILD_NUMBER
