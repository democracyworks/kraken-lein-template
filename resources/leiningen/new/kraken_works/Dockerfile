FROM quay.io/democracyworks/clojure-yourkit:lein-2.7.1

RUN mkdir -p /usr/src/{{name}}
WORKDIR /usr/src/{{name}}

COPY profiles.clj /usr/src/{{name}}/
COPY project.clj /usr/src/{{name}}/

ARG env=production
ARG DATOMIC_USERNAME
ARG DATOMIC_PASSWORD

RUN lein with-profiles $env,datomic-repo deps

COPY . /usr/src/{{name}}

RUN lein with-profiles $env,datomic-repo,test test
RUN lein with-profiles $env,datomic-repo uberjar

CMD java ${JVM_OPTS:--XX:+UseG1GC} \
    -Ddatomic.objectCacheMax=${DATOMIC_OBJ_CACHE_MAX:-256m} \
    -javaagent:resources/jars/com.newrelic.agent.java/newrelic-agent.jar \
    $YOURKIT_AGENT_OPTION \
    -jar target/{{name}}.jar
