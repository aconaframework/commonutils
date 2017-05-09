package at.tuwien.ict.commonutils.configbuilder.helper;

import java.util.Map;

public class DeviceConfig {
	
	private String type;
	private int id;
	private Map<String, String> channels;
	
	public DeviceConfig(String type, int id, Map<String, String> channels) {
		super();
		this.type = type;
		this.id = id;
		this.channels = channels;
	}
	
	public DeviceConfig() {
		
	}
	
	public String getType() {
		return type;
	}

	public int getId() {
		return id;
	}

	public Map<String, String> getChannels() {
		return channels;
	}

}
