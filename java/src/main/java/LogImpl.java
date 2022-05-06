import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.opentelemetry.proto.collector.logs.v1.ExportLogsServiceRequest;
import io.opentelemetry.proto.collector.logs.v1.ExportLogsServiceResponse;
import io.opentelemetry.proto.collector.logs.v1.LogsServiceGrpc;
import io.opentelemetry.proto.common.v1.AnyValue;
import io.opentelemetry.proto.common.v1.KeyValue;
import io.opentelemetry.proto.logs.v1.ScopeLogs;
import io.opentelemetry.proto.logs.v1.LogRecord;
import io.opentelemetry.proto.logs.v1.ResourceLogs;
import io.opentelemetry.proto.logs.v1.ResourceLogsOrBuilder;
import io.opentelemetry.proto.resource.v1.Resource;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class LogImpl {
	//Define Blocking Stub
	private LogsServiceGrpc.LogsServiceBlockingStub blockingStub;
	private HashMap<String,String> resource_attributes;
	private HashMap<String,String> log_attributes;
	private String body;
	private String severity_text;
	// Blocking stub constructor that takes in channel
	public LogImpl(String target, HashMap<String,String> resource_attributes, HashMap<String,String> log_attributes,String body,String severity_text) {
		ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
				// Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
				// needing certificates.
				.usePlaintext()
				.build();
		blockingStub = LogsServiceGrpc.newBlockingStub(channel);
		this.resource_attributes = resource_attributes;
		this.log_attributes = log_attributes;
		this.body = body;
		this.severity_text = severity_text;
	}
	public KeyValue create_key_value(String key, String value) {
		return KeyValue.newBuilder()
				.setKey(key)
				.setValue(AnyValue.newBuilder().setStringValue(value).build()).build();
	}
	public ArrayList<KeyValue> create_attributes(HashMap<String,String> pairs) {
		ArrayList<KeyValue> result = new ArrayList<KeyValue>();
		for (Map.Entry<String, String> entry : pairs.entrySet()) {
			String key = entry.getKey();
			String val = entry.getValue();
			result.add(create_key_value(key,val));
		}
		return result;
	}

	public void record() {
		final ExportLogsServiceRequest my_exportlogsservicerequest = ExportLogsServiceRequest.newBuilder()
				.addResourceLogs(ResourceLogs.newBuilder()
						.addScopeLogs(ScopeLogs.newBuilder()
								.addLogRecords(LogRecord.newBuilder()
										.addAllAttributes(create_attributes(this.log_attributes))
										.setBody(AnyValue.newBuilder()
												.setStringValue(this.body)
												.build())
										.setSeverityText(this.severity_text)
										.setTimeUnixNano((long) ((System.currentTimeMillis() / 1000.0) * 1000000000))
										.build())
								.build())
						.setResource(Resource.newBuilder()
								.addAllAttributes(create_attributes(resource_attributes))
								.build())
						.build())
				.build();
		try {
			ExportLogsServiceResponse response = blockingStub.export(my_exportlogsservicerequest);
			System.out.println(response);
		} catch (StatusRuntimeException e) {
			return;
		}
	}


}
