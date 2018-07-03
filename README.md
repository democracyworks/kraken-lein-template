# kraken-works lein template

![KRAKEN off the starboard!](http://orig03.deviantart.net/29af/f/2013/169/7/a/kraken_by_lozanox-d69kdxz.jpg)

A Leiningen template for calling forth a new kraken-works component project from the
deep.

This is used for microservices that speak [RabbitMQ](https://www.rabbitmq.com/) (via [Kehaar](https://github.com/democracyworks/kehaar)), and use [Datomic](http://www.datomic.com).

## Usage

Issue the following summons at the wizard prompt:

```
> lein new kraken-works <name>
```

Follow the TODOs printed out and in the README.md

### Options

Any options should be separated by `--` so that they are not interpreted as
options to the `lein new` command; this means you'll have a lone `--` in the middle of your command which may look weird, but it will be correct:

```
> lein new kraken-works <name> -- [OPTIONS]
```

* `--no-datomic` Exclude datomic-specific code, resources, and files.

## Installing locally

> This is only necessary if you're testing local changes.
> Otherwise `lein new kraken-works` will just download the latest version
> from Clojars.

From this directory, run:

```
> lein install
```

## License

Copyright © 2017 Democracy Works

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

![Kraken anatomy](http://proximospirits.s3.amazonaws.com/thekraken/book-page-8.png)
