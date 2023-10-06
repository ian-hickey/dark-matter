#!/bin/bash

# Start the file-watcher utility in the background
java -jar darkmatter-watcher-1.0-ALPHA.jar src/main/cfscript process-resources &

# Start Quarkus
./mvnw compile quarkus:dev
