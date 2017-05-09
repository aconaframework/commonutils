package at.tuwien.ict.commonutils.configbuilder.helper;

import java.util.Arrays;
import java.util.List;

public class Configurator {
	public static final String TYPESCADA = "scada";
	public static final String TYPESMARTMETER = "smartmeter";
	public static final String TYPESMARTBREAKER = "smartbreaker";
	
	private static Configurator instance = null;
	
	private Configurator() {
		
	}
	
	public static Configurator getInstance() {
		if (Configurator.instance==null) {
			Configurator.instance = new Configurator();
		}
		
		return Configurator.instance;
	}
	
	public static void setInstance(Configurator configurator) {
		Configurator.instance = configurator;
	}
	
	//=== Create all variables for the application ===//
	private int executioninterval = 5001;
	private int serverport = 10000;
	private DeviceConfig[] devices;
	

	public int getExecutioninterval() {
		return executioninterval;
	}

	public List<DeviceConfig> getDevices() {
		return Arrays.asList(devices);
	}

	public int getServerport() {
		return serverport;
	}

	


}
