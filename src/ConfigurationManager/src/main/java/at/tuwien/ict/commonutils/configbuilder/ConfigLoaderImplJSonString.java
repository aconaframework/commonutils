package at.tuwien.ict.commonutils.configbuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class ConfigLoaderImplJSonString implements ConfigLoader {
	protected final Logger log = LoggerFactory.getLogger(ConfigLoaderImplJSonString.class);

//	{
//	    "name": "JSONParser",
//	    "version": "1.0.0",
//	    "description": "Parse some JSON data",
//	    "keywords": [
//	        "json",
//	        "parse",
//	        "request"
//	    ]
//	}
	
	@Override
	public JsonObject getProperties(String fileOrString) {
		JsonObject result = null;
		//Check if String is a JSON object
		boolean isJson = this.validateStringAsJson(fileOrString);
		if (isJson==true) {
			Gson gson = new GsonBuilder().create();
			result = gson.fromJson(fileOrString, JsonObject.class);
		}
		
		return result;
	}
	
	private boolean validateStringAsJson(String input) {
		boolean result = false;
		
		try {
			Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
			JsonObject obj = gson.fromJson(input, JsonObject.class);
			result = true;
		} catch (Exception e) {
			//log.error("Error", e);
			result = false;
		}
		
		return result;
	}
}
