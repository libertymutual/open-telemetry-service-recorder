# Service Recorder
The service recorder is a simple tool that uses the OpenTelemetry Protobuf schema to create logs, metrics, and traces.  It then sends these to an OpenTelemetry (OTLP) endpoint using gRPC.  While it may not have the full functionality of OpenTelemetry, it reduces the complexity of sending data upstream.

## Requirements
Copy this directory to your project and install the following libraries to your environment. 
```
pip install opentelemetry-proto grpcio
```

## Environment Variables
The recorder currently supports the variable OTLP_ENDPOINT.  By default it uses 'localhost:4317' but this can be overwritten.
```
export OTLP_ENDPOINT='localhost:4318'
```

## Example Usage
```
# Log Example 1
from recorder import Log
log = Log()
log.severity = "Info"
log.body = "Test Message"
log.log_attributes = {"servicename":"service-a", "environment":"development"}
log.resource_attributes = {"hostname":"virtualmachine01","ipaddress":"10.1.1.5"}
log.record()

# Log Example 2
from recorder import Log
log = Log("Info", "Test message", {"servicename":"service-a"}, {"hostname":"test"})
log.record()

# Metric Example
from recorder import Metric
metric = Metric('test_metric', 'description', '%', 100, {"resource_attribute":"test"})
metric.record()

# Trace Example
import time
from recorder import Trace
trace = Trace('test', int(time.time()*1000000000), int(time.time()*1000000000), {'hello':'world','foo':'bar'}, {'hello':'world','foo':'bar'})
trace.record()
```