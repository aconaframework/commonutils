package at.tuwien.ict.commonutils.configbuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import org.slf4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ConfigBuilderImpl implements ConfigBuilder {
	protected Logger log;
	
	private JsonObject propertyHandler = new JsonObject();
	
	private String executionPath;

	
	public ConfigBuilderImpl() {
		
	}
	
	public void init(Logger log) throws IOException {
		this.log = log;
		//Get execution path
		//String executionPath = "";
		try {
			executionPath = new java.io.File(".").getCanonicalPath();
		} catch (IOException e) {
			log.error("Cannot get runtime path");
			throw e;
		}
	}
 	
	/* (non-Javadoc)
	 * @see at.tuwien.ict.configloader.ConfigManager#loadConfigFromFile(java.lang.String)
	 */
	@Override
	public void loadConfigFromFile(String relativePath) throws IOException {
		String configFilePath = executionPath + "/" + relativePath;
		
		this.loadConfigFromString(configFilePath);
	}
	
	public void loadConfigFromString(String completeString) {
		//Load config from file
		JsonObject loadedProperties = ConfigService.getConfig(completeString);
		if (log.isDebugEnabled()) {
			log.debug("Loaded configuration={}", loadedProperties);
		} else {
			log.info("Configuration loaded");
		}
		
		
		//Set propertyHandler
		this.addProperties(loadedProperties);
	}
	
	protected JsonObject getPropertyHandler() {
		return propertyHandler;
	}
	
	//Set properties
	/* (non-Javadoc)
	 * @see at.tuwien.ict.configloader.ConfigManager#setProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public void setProperty(String key, String value) {
		this.getPropertyHandler().addProperty(key, value);
		log.info("Added property {} : {}", key, value);
	}
	
	/* (non-Javadoc)
	 * @see at.tuwien.ict.configloader.ConfigManager#addProperties(java.util.Properties)
	 */
	@Override
	public void addProperties(final JsonObject properties) {		
		//Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//this.propertyHandler.
		
		//this.propertyHandler.putAll(properties);
		this.propertyHandler = deepMerge(properties, this.propertyHandler);
	}
	
	/**
	 * Merge "source" into "target". If fields have equal name, merge them recursively.
	 * @return the merged object (target).
	 */
	public static JsonObject deepMerge(JsonObject source, JsonObject target) {
	    for (Entry<String, JsonElement> e : source.entrySet()) {
	    	//JsonElement object = e.getValue();
	    	
	    	String key = e.getKey();
	    	JsonElement value = e.getValue();
	    	if (target.has(key)==false) {
	    		//If key does not exist, write e new key
	    		target.add(key, value);
	    	} else {	//key exists
	    		//If it is a JSON object
	    		if (value.isJsonObject() && target.get(key).isJsonObject()) {
	    			JsonObject subtargetObject = target.get(key).getAsJsonObject();
	    			JsonObject subsourceObject = value.getAsJsonObject();
	    			deepMerge(subsourceObject, subtargetObject);
	    		} else {
	    			target.add(e.getKey(), e.getValue());
	    		}
	    	}
	    }
		
		
//		for (String key: JsonObject.getNames(source)) {
//	            Object value = source.get(key);
//	            if (!target.has(key)) {
//	                // new value for "key":
//	                target.put(key, value);
//	            } else {
//	                // existing value for "key" - recursively deep merge:
//	                if (value instanceof JSONObject) {
//	                    JSONObject valueJson = (JSONObject)value;
//	                    deepMerge(valueJson, target.getJSONObject(key));
//	                } else {
//	                    target.put(key, value);
//	                }
//	            }
//	    }
	    
	    return target;
	}

	
	
	//Get properties
	/* (non-Javadoc)
	 * @see at.tuwien.ict.configloader.ConfigManager#getProperty(java.lang.String)
	 */
	@Override
	public String getProperty(String key) {
		return this.getPropertyWithDefaultValue(key, "");
	}
	
	/* (non-Javadoc)
	 * @see at.tuwien.ict.configloader.ConfigManager#getProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public String getPropertyWithDefaultValue(String key, String defaultValue) {
		final String erg = (String) this.propertyHandler.get(key).getAsString();
		String result=defaultValue;
		if (erg == null) {
			result = "";
		} else {
			result = erg;
		}
		
		return result;
	}
	
//	/* (non-Javadoc)
//	 * @see at.tuwien.ict.configloader.ConfigManager#getString(java.lang.String, int, java.lang.String)
//	 */
//	@Override
//	public String getString(String prefix, int number, String suffix) throws Exception {
//		String oResult = "";
//		
//		// Create String
//		String oOptionString = prefix + "." + number + "." + suffix;
//		oResult = this.getProperty(oOptionString);
//		
//		return oResult;
//	}
		
//	/* (non-Javadoc)
//	 * @see at.tuwien.ict.configloader.ConfigManager#getStringFromList(java.lang.String, int, java.lang.String, java.lang.String)
//	 */
//	@Override
//	public ArrayList<String> getStringFromList(String prefix, int number, String suffix, String splitChar) {
//		String oOptionString = prefix + "." + number + "." + suffix;
//		
//		String loadedOptions = this.propertyHandler.getProperty(oOptionString); 
//		ArrayList<String> oSplitStringAsArrayList = new ArrayList<String>();
//		if (loadedOptions!=null && loadedOptions.isEmpty()==false) {
//			String[] oSplitstring = loadedOptions.split(splitChar);
//			oSplitStringAsArrayList.addAll(Arrays.asList(oSplitstring));
//		}
//		return oSplitStringAsArrayList;
//	}
	
//	/* (non-Javadoc)
//	 * @see at.tuwien.ict.configloader.ConfigManager#getStringFromList(java.lang.String, java.lang.String)
//	 */
//	@Override
//	public ArrayList<String> getStringFromList(String address, String splitChar) {
//		String oOptionString = address;
//		
//		String loadedOptions = this.propertyHandler.getProperty(oOptionString); 
//		ArrayList<String> oSplitStringAsArrayList = new ArrayList<String>();
//		if (loadedOptions!=null && loadedOptions.isEmpty()==false) {
//			String[] oSplitstring = loadedOptions.split(splitChar);
//			oSplitStringAsArrayList.addAll(Arrays.asList(oSplitstring));
//		}
//		
//		return oSplitStringAsArrayList;
//	}
	
	/* (non-Javadoc)
	 * @see at.tuwien.ict.configloader.ConfigManager#propertyExists(java.lang.String)
	 */
	@Override
	public boolean propertyExists(String key) {
		//TODO: Check if exists works
		return this.propertyHandler.has(key);
	}
	
	@Override
	public List<JsonObject> getSubObjectsFromConfigManager(String keyForParentObject) {
		List<JsonObject> result = ConfigBuilderImpl.getElementsOfContainerObjects(keyForParentObject, this.propertyHandler);
		return result;
	}
	
	public static List<JsonObject> getElementsOfContainerObjects(String keyForSubObject, JsonObject parentOfContainerObject) {
		List<JsonObject> result = new ArrayList<JsonObject>();
		
		if (parentOfContainerObject.has(keyForSubObject) && parentOfContainerObject.get(keyForSubObject).isJsonObject()) {
			JsonObject containerObject = parentOfContainerObject.get(keyForSubObject).getAsJsonObject();
			List<JsonObject> subObjects = ConfigBuilderImpl.getSubObjects(containerObject);
			result = subObjects;
		}
		
		return result;
	}
	
	public static JsonObject getElementsOfSubObject(String keyForSubObject, JsonObject object) {
		JsonObject result = null;
		
		if (object.has(keyForSubObject) && object.get(keyForSubObject).isJsonObject()) {
			JsonObject subObject = object.get(keyForSubObject).getAsJsonObject();
			result = subObject;
		}
		
		return result;
		
	}
	
	/**
	 * Get all sub objects from an object
	 * 
	 * @param object
	 * @return
	 */
	public static List<JsonObject> getSubObjects(JsonObject object) {
		List<JsonObject> result = new ArrayList<JsonObject>();
		
		
		for (Entry<String, JsonElement> entry : object.entrySet()) {
			if (entry.getValue().isJsonObject()==true) {
				result.add(entry.getValue().getAsJsonObject());
			}
		}
		
		return result;
	}
	
	
	
	/* (non-Javadoc)
	 * @see at.tuwien.ict.configloader.ConfigManager#getAllMatchingStringProperties(java.lang.String)
	 */
	@Override
	public Hashtable<String, String> getAllMatchingStringProperties(String stringExpression) {
		Hashtable<String, String> result = new Hashtable<String, String>();
		
		for (Entry<String, JsonElement> e : this.propertyHandler.entrySet()) {
			if (e.getValue().isJsonPrimitive()==true && (e.getKey().contains(stringExpression) || stringExpression.equals(""))) {
				result.put(e.getKey(), e.getValue().getAsString());
			}
		}
		
		return result;
	}
	
//	/* (non-Javadoc)
//	 * @see at.tuwien.ict.configloader.ConfigManager#getUnknownAddressPart(java.lang.String, java.lang.String)
//	 */
//	@Override
//	public List<String> getNextSubkeys(String prefix, String splitchar) {
//		List<String> result = new ArrayList<String>();
//		
//		for (Object e : this.propertyHandler.keySet()) {
//			//If string starts with prefix then it is taken
//			String key = e.toString();
//			if (key.startsWith(prefix + splitchar)==true) {
//				String sub = key.substring(prefix.length()+1, key.length());
//				String[] sub2 =sub.split("\\.");
//				String unknownAddressString = sub2[0];
//				if (result.contains(unknownAddressString)==false) {
//					result.add(unknownAddressString);
//				}	
//			}
//		}
//		
//		return result;
//	}
	

//	@Override
//	public List<String> getLeafKeys(String prefix, String splitchar) {
//		List<String> result = new ArrayList<String>();
//		
//		throw new UnsupportedOperationException("Method not implemented");
////		
////		for (Object e : this.propertyHandler.keySet()) {
////			//If string starts with prefix then it is taken
////			String key = e.toString();
////			if (key.startsWith(prefix + splitchar)==true) {
////				String sub = key.substring(prefix.length()+1, key.length());
////				String[] sub2 =sub.split("\\.");
////				String unknownAddressString = sub2[0];
////				if (result.contains(unknownAddressString)==false) {
////					result.add(unknownAddressString);
////				}	
////			}
////		}
////		
//		//return result;
//	}

//	public int getSize() {
//		return this.propertyHandler.;
//	}
	
	public String toString() {
		return "Property handler. Content: " + this.propertyHandler.toString();
	}

	@Override
	public void clearAll() {
		this.propertyHandler = new JsonObject();
	}

	@Override
	public JsonObject getDatabase() {
		return this.propertyHandler;
	}

	@Override
	public <T> T toClass(Class<T> clazz) {
		Gson gson = new GsonBuilder().create();
		T result = gson.fromJson(this.getDatabase(), clazz);
		log.debug("Created config object={}", result);
		
		return result;
	}

}
