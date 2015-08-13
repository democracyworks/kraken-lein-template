FROM quay.io/democracyworks/didor:latest

RUN mkdir -p /usr/src/{{name}}
WORKDIR /usr/src/{{name}}

COPY profiles.clj /usr/src/{{name}}/
COPY project.clj /usr/src/{{name}}/

RUN lein deps
RUN lein immutant war --name no-code-just-deps --destination target --nrepl-start

COPY . /usr/src/{{name}}

RUN lein test
RUN lein immutant war --name {{name}} --destination target
