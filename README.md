![Java Checks Workflow](https://github.com/vinsce/RemoteControlServer/actions/workflows/java_ci.yml/badge.svg)

# RemoteControlServer

Desktop Remote Control gRPC server for [Wear Remote](https://github.com/vinsce/WearRemote) Wear OS application.

### Features

- Remote touchpad
- Remote power actions (shutdown, reboot, sleep)

## Usage

Requirements:

- Java 11

### Using pre-packaged server

1. Download the latest JAR package from the [GitHub Maven package repository](https://github.com/vinsce/RemoteControlServer/packages/984873)
2. Run the server JAR: `java -jar /path/to/server.jar`

### Using the server source code

1. Clone the repository
2. Run the `.\gradlew run` command from the repository directory

> Note: the project is work in progress and as of today it is intended for experimenting with gRPC and Android Wear OS.
> Contributions are accepted, as always :)
