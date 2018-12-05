FROM quay.io/democracyworks/clojure-yourkit:lein-2.8.1

RUN mkdir -p /usr/src/{{name}}
WORKDIR /usr/src/{{name}}

{{#datomic?}}
COPY profiles.clj /usr/src/{{name}}/
{{/datomic?}}
COPY project.clj /usr/src/{{name}}/
COPY ["docker/app_container", "/"]

ARG env=production
{{#datomic?}}
ARG DATOMIC_USERNAME
ARG DATOMIC_PASSWORD
{{/datomic?}}

RUN lein with-profiles $env{{#datomic?}},datomic-repo{{/datomic?}} deps

COPY . /usr/src/{{name}}

RUN lein with-profiles $env{{#datomic?}},datomic-repo{{/datomic?}},test test
RUN lein with-profiles $env{{#datomic?}},datomic-repo{{/datomic?}} uberjar

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
