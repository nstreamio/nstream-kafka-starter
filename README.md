# nstream-kafka-starter

A baseline Nstream application that processes Kafka-hosted source data.

## Component Overview

There are three components to this repository:

- An Nstream toolkit-empowered Swim server that consumes from Kafka topics and processes responses in Web Agents with minimal boilerplate (package [`nstream.starter`](/src/main/java/nstream/starter) in the Java code)
- A standalone Kafka broker ([`broker/`](/broker) directory)
- A means to populate the former with reasonably frequent messages (package [`nstream.starter.sim`](/src/main/java/nstream/starter/sim) in the Java code)

In practice, you will develop applications against an existing broker (or its spec).
Thus, the last two components mentioned are primarily for experimentation and come with limited warranty.

## Prerequisites

- Java Development Kit (JDK) 11+
   - See [`build.gradle`](/build.gradle) for application-specific Java dependencies 
- (broker only) [`docker-compose`](https://docs.docker.com/compose/)

## Run Instructions

1. Build the broker (working directory: `broker/`)
   ```
   docker build . -t nstream/kafka-starter-broker:0.1.0
   ```
2. Run the broker (working directory: `broker/`)
   ```
   docker-compose up
   ```
3. Run the Nstream server (working directory: this one)

   **\*nix Environment:**
   ```
   ./gradlew run 
   ```
   **Windows Environment:**
   ```
   .\gradlew.bat run 
   ```
4. Run the broker populator (working directory: this one)

   **\*nix Environment:**
   ```
   ./gradlew runSim
   ```
   **Windows Environment:**
   ```
   .\gradlew.bat runSim
   ```
Note that if you are working with your own broker, you only need step 3.
Otherwise, the aforementioned order is certainly the recommended one; use caution if you modify it.

## Shutdown Instructions

- Java processes can be terminated by a plain `SIGINT` (ctrl + C in most shells)
- To tear down the broker (and be okay with some persisted data), run `docker-compose down` from the `broker/` directory.
   - To additionally completely wipe persisted data, run `docker-compose down --volumes` instead (note that restarting the broker will take longer if you do this)
