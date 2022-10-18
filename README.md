<img src="https://raw.githubusercontent.com/vincejv/fpi-framework/main/banner.png" alt="FPI Framework" width="500"/>

[![Maven Central version](https://img.shields.io/maven-central/v/com.abavilla/fpi-sms-api?logo=apache-maven)](https://search.maven.org/artifact/com.abavilla/fpi-sms-api-lib)
[![GitHub Workflow Status](https://img.shields.io/github/workflow/status/vincejv/fpi-sms-api/GCP%20Cloud%20Run%20CI%20Prod?label=CI/CD&logo=github)](https://github.com/vincejv/fpi-sms-api/actions?query=workflow%3A%22GCP+Cloud+Run+CI+Prod%22)
[![License](https://img.shields.io/github/license/vincejv/fpi-sms-api?logo=apache)](https://github.com/vincejv/fpi-sms-api/blob/main/LICENSE)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/m/vincejv/fpi-sms-api?label=commits&logo=git)](https://github.com/vincejv/fpi-sms-api/pulse)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=vincejv_fpi-sms-api&metric=alert_status)](https://sonarcloud.io/dashboard?id=vincejv_fpi-sms-api)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=vincejv_fpi-sms-api&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=vincejv_fpi-sms-api)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=vincejv_fpi-sms-api&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=vincejv_fpi-sms-api)
[![SonarCloud Bugs](https://sonarcloud.io/api/project_badges/measure?project=vincejv_fpi-sms-api&metric=bugs)](https://sonarcloud.io/component_measures/metric/reliability_rating/list?id=vincejv_fpi-sms-api)
[![SonarCloud Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=vincejv_fpi-sms-api&metric=vulnerabilities)](https://sonarcloud.io/component_measures/metric/security_rating/list?id=vincejv_fpi-sms-api)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=vincejv_fpi-sms-api&metric=security_rating)](https://sonarcloud.io/dashboard?id=vincejv_fpi-sms-api)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=vincejv_fpi-sms-api&metric=ncloc)](https://sonarcloud.io/dashboard?id=vincejv_fpi-sms-api)

# SMS API
This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- MongoDB client ([guide](https://quarkus.io/guides/mongodb)): Connect to MongoDB in either imperative or reactive style
- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A JAX-RS implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
