package at.tuwien.ict.commonutils.configbuilder;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.slf4j.Logger;

import com.google.gson.JsonObject;

public interface ConfigBuilder {

	public void init(Logger log) throws IOException;
	
	/**
	 * Load properties from a type of file (JSON or text) from the relative config file path
	 * 
	 * @param relativePath
	 * @throws IOException
	 */
	public void loadConfigFromFile(String absolutePathOrJsonString) throws IOException;
	
	public void loadConfigFromString(String JsonString);

	/**
	 * Set property as string
	 * 
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, String value);

	/**
	 * Add and replace properties to the current set of properties 
	 * 
	 * @param properties
	 */
	public void addProperties(JsonObject properties);

	/**
	 * Get properties as strings
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key);

	/**
	 * Get property but with a default value if the property is not found
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	
	public JsonObject getDatabase();
	
	public String getPropertyWithDefaultValue(String key, String defaultValue);

//	/**
//	 * Get options with this format: device.1.datapointaddressI
//	 * 
//	 * @param prefix the po prefix
//	 * @param number
//	 *          the pn number
//	 * @param suffix
//	 *          the po suffix
//	 * @return the string
//	 * @throws Exception
//	 *           the exception
//	 */
//	public String getString(String prefix, int number, String suffix) throws Exception;
//
//	/**
//	 * Get a list of properties
//	 * 
//	 * @param prefix
//	 * @param number
//	 * @param suffix
//	 * @param splitChar
//	 * @return
//	 */
//	ArrayList<String> getStringFromList(String prefix, int number, String suffix, String splitChar);
//
//	ArrayList<String> getStringFromList(String address, String splitChar);

	/**
	 * Check if a property exists.
	 * 
	 * @param optionName
	 *          the option name
	 * @return true, if successful
	 */
	public boolean propertyExists(String optionName);

	/**
	 * Get a list of all properties and keys, which contains the stringExpression
	 * 
	 * @param stringExpression
	 * @return
	 */
	public Hashtable<String, String> getAllMatchingStringProperties(String stringExpression);
	
	public List<JsonObject> getSubObjectsFromConfigManager(String keyForParentObject);
	
	public void clearAll();
	
	public <T extends Object> T toClass(Class<T> clazz);
	
	

}