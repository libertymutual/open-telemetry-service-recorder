# Service Recorder
The service recorder is a simple tool that uses the OpenTelemetry Protobuf schema to create logs, metrics, and traces.  It then sends these to an OpenTelemetry (OTLP) endpoint using gRPC or HTTP.  While it may not have the full functionality of OpenTelemetry, it reduces the complexity of sending data upstream.

# Language Support
As of now Python is fully supported.  We are working to enable Node.JS and other languages.