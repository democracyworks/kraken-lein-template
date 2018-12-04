#!/usr/bin/env bash
set -o errexit

NAMESPACE_CONFIGS=("$@")
for NS in "${NAMESPACE_CONFIGS[@]}";
do
   script/deploy "${NS}"
done
