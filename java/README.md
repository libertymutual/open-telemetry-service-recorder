This is a minimal example of sending a OpenTelemetry Log packet via gRPC.
# Instructions for use:
- JDK 1.8 installed
- Running an Open Telemetry gRPC server on `localhost:9090` with plaintext enabled
- `./gradlew shadowJar`
- `java -jar build/libs/minimal-servicerecorder-java-1.0-SNAPSHOT-all.jar`

This will cause an OT Log packet to be sent via gRPC for each time you run the jar.

# Module Author
James Desmond, Liberty Mutual