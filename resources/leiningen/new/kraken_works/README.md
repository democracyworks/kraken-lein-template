# {{name}}

TODO: Add description

## Configuration

Configuration lives in `resources/config.edn` file, using `#resource-config/env`
tagged literals for environment variables and `#resource-config/edn` for parsing
values as EDN (can be used to parse strings into ints, for example).

### Parameters

TODO: Document {{name}} configuration parameters here.

### New config parameters

1. Add new parameter to `resources/config.edn` using an env var (usually).
1. Add them to the README.md (this file) in the Running with
docker-compose section as well as documented in the existing config section.

## Usage

TODO: Add usage

## Running

### With docker-compose
{{#datomic?}}

First, create a `profiles.clj`.

```
> cp profiles.clj.sample profiles.clj
```

Edit the datomic username and password in `profiles.clj` (it won't be
added to git).
{{/datomic?}}

Build it:

```
> docker-compose build
```

Run it:

```
> docker-compose up
```

TODO: If any more env vars are needed, add them to docker-compose command above.

## License

Copyright © 2018 Democracy Works, Inc.

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
