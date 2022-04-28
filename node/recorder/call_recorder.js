import {Recorder} from "./recorder.js"

var my_recorder = new Recorder;
my_recorder.severity = "Info"
my_recorder.body = "Hello World"
my_recorder.resource_attributes = {"hello":"world","foo":"bar"}
my_recorder.log_attributes = {"hello":"world","foo":123}
my_recorder.request();
