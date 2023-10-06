@echo off

REM Start the file-watcher utility
start java -jar darkmatter-watcher-1.0-ALPHA.jar src/main/cfscript process-resources

REM Start Quarkus
mvnw.cmd quarkus:dev
