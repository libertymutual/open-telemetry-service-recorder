import java.util.HashMap;

public class Main {
	public static void main(String args[]) throws InterruptedException {
		HashMap<String,String> resource_attributes = new HashMap<String,String>() {{
			put("hostname","virtualmachine01");
			put("ipaddress","10.1.1.5");
		}};
		HashMap<String,String> log_attributes = new HashMap<String,String>() {{
			put("servicename","service-a");
			put("environment","development");
		}};
		LogImpl client = new LogImpl("localhost:9090",resource_attributes,log_attributes,"Test Message Body","Test Severity Text");
		client.record();
	}
}
