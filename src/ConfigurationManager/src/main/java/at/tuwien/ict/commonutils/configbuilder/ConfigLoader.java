package at.tuwien.ict.commonutils.configbuilder;

import com.google.gson.JsonObject;

public interface ConfigLoader {	
	//Strategy pattern
	/**
	 * Load all propoerties from a file string
	 * 
	 * @param file
	 * @return
	 */
	JsonObject getProperties(String fileOrString);
}
