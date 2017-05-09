package at.tuwien.ict.commonutils.configbuilder.helper;

public class ConfiguratorInigrid {
	private static ConfiguratorInigrid instance = null;
	
	private Devicedriver devicedriver;
	private Osgidriver osgidriver;
	
	private ConfiguratorInigrid() {
		
	}
	
	public static ConfiguratorInigrid getInstance() {
		if (ConfiguratorInigrid.instance==null) {
			ConfiguratorInigrid.instance = new ConfiguratorInigrid();
		}
		
		return ConfiguratorInigrid.instance;
	}
	
	public Devicedriver getDevice() {
		return devicedriver;
	}

	public Osgidriver getOsgidriver() {
		return osgidriver;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Configurator [device=");
		builder.append(devicedriver);
		builder.append(", osgidriver=");
		builder.append(osgidriver);
		builder.append("]");
		return builder.toString();
	}

	

}
