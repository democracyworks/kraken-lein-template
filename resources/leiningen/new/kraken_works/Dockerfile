FROM quay.io/democracyworks/clojure-yourkit:lein-2.7.1

RUN mkdir -p /usr/src/{{name}}
WORKDIR /usr/src/{{name}}

{{#datomic?}}
COPY profiles.clj /usr/src/{{name}}/
{{/datomic?}}
COPY project.clj /usr/src/{{name}}/

ARG env=production
{{#datomic?}}
ARG DATOMIC_USERNAME
ARG DATOMIC_PASSWORD
{{/datomic?}}

RUN lein with-profiles $env{{#datomic?}},datomic-repo{{/datomic?}} deps

COPY . /usr/src/{{name}}

RUN lein with-profiles $env{{#datomic?}},datomic-repo{{/datomic?}},test test
RUN lein with-profiles $env{{#datomic?}},datomic-repo{{/datomic?}} uberjar

CMD java ${JVM_OPTS:--XX:+UseG1GC} \
    -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap \
    -XX:MaxRAMFraction=1 \
    -javaagent:resources/jars/com.newrelic.agent.java/newrelic-agent.jar \
    $YOURKIT_AGENT_OPTION \
    -jar target/{{name}}.jar
