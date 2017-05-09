package propertyhandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;

import org.slf4j.Logger;

public abstract class GeneralConfigLoader {
	//private static  GeneralConfigLoader conf=null;
	private String applicationExecutionPath;
	private String configFilePath;
	private Hashtable<String, String> sourceFromHashTable;
	private ConfigSourceType configSourceType = ConfigSourceType.FILE;
	
	protected final PropertyHandler propertyHandler = new PropertyHandler();
	
	protected Logger log;
	
//	public static GeneralConfigLoader getConfig() {
//		if (GeneralConfigLoader.conf==null) {
//			GeneralConfigLoader.conf = new GeneralConfigLoader();
//		}
//		
//		return GeneralConfigLoader.conf;
//	}
	
	public GeneralConfigLoader() {
		
	}
	
	public void setConfigSourceType(ConfigSourceType source) {
		this.configSourceType = source;
	}
	
	public void setRelativeConfigPath(String path) {
		this.configFilePath = path;
	}
	
	public void setSourceFromHashTable(Hashtable<String, String> source) {
		this.sourceFromHashTable = source;
	}
	
	public void init(Logger log) throws Exception {
		String path = "";
		try {
			path = new java.io.File(".").getCanonicalPath();
		} catch (IOException e3) {
			log.error("Cannot get runtime path");
			System.exit(-1);
		}
		
		this.applicationExecutionPath = path;
		
		this.log = log;
		this.configFilePath = this.applicationExecutionPath + "/" + configFilePath;
		
		//Init source
		if (this.configSourceType.equals(ConfigSourceType.FILE)) {
			try {
				this.propertyHandler.load(configFilePath);
				log.debug("Config file {} successfully loaded", configFilePath);
			} catch (IOException e) {
				log.error("Cannot load config file {}", this.configFilePath, e);
				throw new Exception(e.getMessage());
			}
		} else if (this.configSourceType.equals(ConfigSourceType.TABLE)) {
			this.propertyHandler.setOption(sourceFromHashTable);
		}
		
		//Instance init with special init
		this.specializedInit();
		
		//Load parameters from source into the instance
		this.loadParameters();
	}
	
	protected abstract void specializedInit() throws Exception;
	
	protected abstract void loadParameters() throws Exception;
	
	public String getApplicationExecutionPath() {
		return applicationExecutionPath;
	}

	public String getConfigFilePath() {
		return configFilePath;
	}
	
	protected HashMap<String, ArrayList<String>> loadCustomParameter(String customprefix, ArrayList<String> nonCustomAddresses) throws PropertyException {
		HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
		
		String eventCustomPath = customprefix; //prefix + "." + propertyIdentifier + "." + customsuffix;
		
		//Get all custom properties for the event
		ArrayList<String> eventCustomProperties = this.propertyHandler.getUnknownAddressPart(eventCustomPath, ".");
		for (String customProperty : eventCustomProperties) {
			String customPropertyPath = eventCustomPath + "." + customProperty;
			if (nonCustomAddresses.contains(customPropertyPath)==false) {
				try {
					String[] value = this.propertyHandler.getString(customPropertyPath).split(",");
					result.put(customProperty, new ArrayList<String>(Arrays.asList(value)));
				} catch (PropertyException e) {
					log.error("Cannot read custom property of the propertypath {}", customPropertyPath);
					throw e;
				}
			}
		}
		
		return result;
	}
	
}
