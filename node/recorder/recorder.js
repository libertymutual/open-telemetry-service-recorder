import fetch from 'node-fetch';

export class Recorder{
  constructor(severity, body, log_attributes, resource_attributes, http_endpoint="localhost:4318") {
    this.severity = severity;
    this.body = body;
    this.log_attributes = log_attributes;
    this.resource_attributes = resource_attributes;
    this.http_endpoint = http_endpoint;
  };

  keyvalue(key, value){
    //determine if string or numeric
    if (typeof(value)=="string"){
      var attribute = {"key": key, "value": {"stringValue": value}}
    } else if (typeof(value)=="number") {
      var attribute = {"key": key, "value": {"doubleValue": value}}
    } else {
      var attribute = {"key": key, "value": {"stringValue": value}}
    }
      
  return attribute
  };
  async request(){
    var message = {
      "resourceLogs": [
        {
        "resource": {
          "attributes": []
        },
        "scopeLogs": [
          {
          "logRecords": [
            {
            "timeUnixNano": Date.now() * 1000000,
            "severityText": this.severity,
            "body": {
              "stringValue": this.body
            },
            "attributes": []
            }
          ]
          }
        ]
        }
      ]
      }
      
      for (const [ key, value ] of Object.entries(this.resource_attributes)) {
        message.resourceLogs[0].resource.attributes.push(this.keyvalue(key,value))
      };
      for (const [ key, value ] of Object.entries(this.log_attributes)) {
        message.resourceLogs[0].scopeLogs[0].logRecords[0].attributes.push(this.keyvalue(key,value))
      };

      console.log(JSON.stringify(message, null, 2));
      
      //Send message
      const response = await fetch("http://" + this.http_endpoint + "/v1/logs", {
    	  method: 'POST',
	      body: JSON.stringify(message, null, 2),
      	headers: {"Content-Type": "application/json"} 
      });
      //const data = await response.json();
      //console.log(data);

  }
};

