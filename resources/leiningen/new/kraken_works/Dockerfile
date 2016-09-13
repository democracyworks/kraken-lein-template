FROM quay.io/democracyworks/didor:latest

RUN mkdir -p /usr/src/{{name}}
WORKDIR /usr/src/{{name}}

COPY profiles.clj /usr/src/{{name}}/
COPY project.clj /usr/src/{{name}}/

ARG env=production
ARG DATOMIC_USERNAME
ARG DATOMIC_PASSWORD

RUN lein with-profiles $env,datomic-repo deps
RUN lein with-profiles $env,datomic-repo immutant war --name no-code-just-deps --destination target --nrepl-start

COPY . /usr/src/{{name}}

RUN lein with-profiles $env,datomic-repo,test test
RUN lein with-profiles $env,datomic-repo immutant war --name {{name}} --destination target
