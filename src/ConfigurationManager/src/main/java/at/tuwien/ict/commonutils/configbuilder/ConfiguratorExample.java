package at.tuwien.ict.commonutils.configbuilder;

public class ConfiguratorExample {
	private static ConfiguratorExample instance = null;
	
	private ConfiguratorExample() {
		
	}
	
	public static ConfiguratorExample getInstance() {
		if (ConfiguratorExample.instance==null) {
			ConfiguratorExample.instance = new ConfiguratorExample();
		}
		
		return ConfiguratorExample.instance;
	}
	
	private String NAME = "";
	private String LOCATION = "";
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConfiguratorExample [NAME=");
		builder.append(NAME);
		builder.append(", LOCATION=");
		builder.append(LOCATION);
		builder.append("]");
		return builder.toString();
	}
	
	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getLOCATION() {
		return LOCATION;
	}

	public void setLOCATION(String lOCATION) {
		LOCATION = lOCATION;
	}
}
