FROM public.ecr.aws/docker/library/clojure:openjdk-11-lein AS build

RUN mkdir -p /usr/src/{{name}}

WORKDIR /usr/src/{{name}}

## Add files changing less often first for fetching dependencies as a Docker
## build cache optimization.
COPY project.clj /usr/src/{{name}}/

ARG DATOMIC_USERNAME
ARG DATOMIC_PASSWORD

RUN lein deps

## Add the rest of the tree with files that change more often.
COPY . .

RUN lein do test, uberjar

FROM public.ecr.aws/docker/library/openjdk:11-jre

RUN useradd -m -r -g root {{name}}

RUN mkdir -p /srv/{{name}}/target

## Copy the uberjar from the build image.
COPY --from=build /usr/src/{{name}}/target/{{name}}.jar /srv/{{name}}/target/

COPY docker/entrypoint.sh /usr/local/bin/entrypoint.sh

WORKDIR /srv/{{name}}

## Drop privileges.
USER {{name}}

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
