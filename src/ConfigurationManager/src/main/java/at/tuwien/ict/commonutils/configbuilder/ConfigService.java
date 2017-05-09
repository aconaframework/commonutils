package at.tuwien.ict.commonutils.configbuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

/**
 * Config service shall receive configs from other sources
 * 
 * @author wendt
 *
 */
public class ConfigService {
	protected final Logger log = LoggerFactory.getLogger(ConfigService.class);
	
	private static ConfigLoader[] LOADERS = new ConfigLoader[] {
		new ConfigLoaderImplJSonString(),
		new ConfigLoaderImplJsonFileLoader(),
		
		
	};
	
	public static JsonObject getConfig(String file) {
		JsonObject result = null;
		
		for (ConfigLoader loader : LOADERS) {
			JsonObject config = loader.getProperties(file);
			if (config != null) {
				result = config;
				break;
			}
		}
		
		if (result == null) {
			throw new RuntimeException("Config file not found" + file);
		}
		
		return result;
	}
	
}
