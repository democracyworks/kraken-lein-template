#!/usr/bin/env bash

set -euo pipefail

## Allow these options to vary independently so users don't have to override
## both.
JVM_OPTS="${JVM_OPTS:--XX:+UseG1GC -XX:+ExitOnOutOfMemoryError}"
JVM_MEMORY_OPTS="${JVM_MEMORY_OPTS:--XX:MaxRAMPercentage=70}"

echo "JVM_OPTS=${JVM_OPTS}"
echo "JVM_MEMORY_OPTS=${JVM_MEMORY_OPTS}"

# shellcheck disable=SC2086
exec java ${JVM_OPTS} ${JVM_MEMORY_OPTS} \
  -jar target/{{name}}.jar
