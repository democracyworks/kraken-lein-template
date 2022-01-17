FROM clojure:openjdk-11-lein-2.9.8

RUN useradd -m -r -g root {{name}}

RUN mkdir -p /usr/src/{{name}}

WORKDIR /usr/src/{{name}}

## Add files changing less often first for fetching dependencies as a Docker
## build cache optimization.
{{#datomic?}}
COPY profiles.clj /usr/src/{{name}}/
{{/datomic?}}
COPY project.clj /usr/src/{{name}}/

{{#datomic?}}
ARG DATOMIC_USERNAME
ARG DATOMIC_PASSWORD
{{/datomic?}}

RUN lein deps

## Add the rest of the tree with files that change more often.
COPY . .

RUN lein do test, uberjar

COPY docker/app_container/ /

## Drop privileges.
USER {{name}}

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
