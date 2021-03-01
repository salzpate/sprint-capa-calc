# Sprint Capacity Calculator
A tool for calculating sprint capacities in a front-end and back-end team.

The desktop application is written with JavaFX and is generated as native using GraalVM.

![SCC](https://user-images.githubusercontent.com/57270302/109551322-b564cb00-7ad0-11eb-8145-2cd784d36e3c.png "SCC")

## Features

- Set person days for a sprint with a backend and a frontend team.
- Create a task list and set the required man-days in total and for the front-end and back-end team.
- Automatic calculation of the front-end or back-end required time based on the total number of person-days.
- Possibility to take tasks out of the calculation.
- Calculation and display of the planned and the total time used.
- Copy the task list to the clipboard.


## Installtion

The application is delivered with executable binaries.

## Build

The Maven plugin for JavaFX can be used to start the application directly from the sources:

```shell
mvn javafx:run
```

The native image is generated via Gluon Client plugin for Maven. The GRAALVM_HOME variable must be set:

```sheel
mvn client:build
```

The binary is created in the *target/client/{osname}/* directory and can be started there directly. The Gluon Client plugin for Maven can also be used:

```sheel
mvn client:run
```

## Reference

- [GraalVM](https://www.graalvm.org)
- [Open Java FX](https://openjfx.io)
- [Maven plugin for JavaFX](https://github.com/openjfx/javafx-maven-plugin)
- [Gluon](https://gluonhq.com)
- [Gluon Client plugin for Maven](https://github.com/gluonhq/client-maven-plugin)

## License
Sprint Capacity Calculator is Open Source software released under the https://www.apache.org/licenses/LICENSE-2.0.html[Apache 2.0 license].
