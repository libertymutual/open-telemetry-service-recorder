# Service Recorder (Node)
The Node.JS service recorder sends OpenTelemetry (OTel) log JSON payloads to an OTel collector via a HTTP web request.  The OTel collector can then route the data to numerous endpoints including Kafka and Splunk.  

## Requirement
This module uses node-fetch and esm.  You will need to add these to your project via NPM.
```
// Installing ESM
npm install --save esm

//Installing node-fetch
npm install node-fetch
```
For esm you will also need to add "type": "module" to your package.json config file.  Please see the example included.

## Example Usage
```
import {Recorder} from "./recorder.js"

var my_recorder = new Recorder;
my_recorder.severity = "Info"
my_recorder.body = "Hello World"
my_recorder.resource_attributes = {"hello":"world","foo":"bar"}
my_recorder.log_attributes = {"hello":"world","foo":123}
my_recorder.request();
```