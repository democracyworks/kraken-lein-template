#!/usr/bin/env bash
shopt -s nocasematch

if [[ -z "${DEPLOYMENT_NAME}" ]]; then
  echo "DEPLOYMENT_NAME unset! Exiting"
  exit 1
elif [[ "${CGROUP_HEAP}" = true ]]; then
  echo "JVM Heap UseCGroupMemoryLimitForHeap"
  export JVM_LINE="\
    -XX:+UnlockExperimentalVMOptions \
    -XX:+UseCGroupMemoryLimitForHeap \
    -XX:MaxRAMFraction=${MaxRAMFraction:-2}"
else
  echo "JVM Heap statically set"
  export JVM_LINE="-Xms${XMS:-1g} -Xmx${XMX:-1g}"
fi

java ${JVM_OPTS:--XX:+UseG1GC -XX:+ExitOnOutOfMemoryError} \
  ${JVM_LINE} \
  -javaagent:resources/jars/com.newrelic.agent.java/newrelic-agent.jar \
  -jar target/${DEPLOYMENT_NAME}.jar
