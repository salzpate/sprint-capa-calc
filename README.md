# Sprint Capacity Calculator
A tool for calculating sprint capacities in a front-end and back-end team.

The desktop application is written with JavaFX and is generated as native using GraalVM.

![SCC](https://user-images.githubusercontent.com/57270302/110973591-b7931900-835d-11eb-91d5-5723fdfae766.png "SCC")

## Features

- Set person days for a sprint with a backend and a frontend team.
- Create a task list and set the required man-days in total and for the front-end and back-end team.
- Automatic calculation of the front-end or back-end required time based on the total number of person-days.
- Possibility to take tasks out of the calculation.
- Calculation and display of the planned and the total time used.
- Copy the task list to the clipboard.

### Jira Extension

The Jira extension can be activated via scc.properties. This will expand the Add input field. If a Jira ID is recognized after an input (only one word with -), the data record is loaded via the Jira API and the name and person Days are filled. Using the prefix jql: a JQL query can also serve as a basis. If no value is set for the person days, these will not be filled automatically. The open sprints of a configured project are displayed in a dialog and after selecting a sprint, the open issues are loaded and placed in the table. The Jira Issue can be opened in the browser via the context menu.

![SCC](https://user-images.githubusercontent.com/57270302/110973613-beba2700-835d-11eb-8c7b-3d26eded9da5.png "SCC JIRA")



*scc.properties*:

| Name                  | Default | Desciption                                                      | Example                           |
|-----------------------|---------|-----------------------------------------------------------------|-----------------------------------|
| jira.enabled          | false   | Enable Jira Extention                                           | true                              |
| jira.url              |         | Url of Jira                                                     | http://localhost:8080/rest/api/   |
| jira.agile.board      |         | Board ID for loading active and future sprints                  | 39                                |
| jira.agile.version    | latest  | Version of Jira rest agile API                                  | 1                                 |
| jira.api.project      |         | Default Project Name for loading open items of selected sprint  | DH                                |
| jira.api.version      | latest  | Version of Jira rest API                                        | 2                                 |
| jira.auth.method      |         | Authentifcation Method: Bearer or Basic                         | Basic                             |
| jira.auth.token       |         | Authentifcation Token, by Basic use Base64 of username:password | dXNlcm5hbWU6cGFzc3dvcmQ=          |
| jira.field.name       | summary | Name field of Jira rest response                                | summary                           |
| jira.field.key        | key     | Key field of Jira rest response                                 | key                               |
| jira.field.personDays |         | Name field of Jira rest response                                | customfield_10106                 |
| jira.browser          |         | Open Browser command for commandline                            | open (MacOS)                      |


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
- [Heroicons](https://heroicons.com)

## License
Sprint Capacity Calculator is Open Source software released under the https://www.apache.org/licenses/LICENSE-2.0.html[Apache 2.0 license].
