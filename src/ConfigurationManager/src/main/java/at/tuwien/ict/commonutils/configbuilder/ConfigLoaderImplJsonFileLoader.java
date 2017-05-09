package at.tuwien.ict.commonutils.configbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class ConfigLoaderImplJsonFileLoader implements ConfigLoader {
	protected final Logger log = LoggerFactory.getLogger(ConfigLoaderImplJsonFileLoader.class);

	@Override
	public JsonObject getProperties(String filepath) {		
		JsonObject result = null;
		
		//Test input
		if (filepath == null || filepath.length() == 0) {
			return null;
		}
		
		File file = new File(filepath);
		if (!file.exists() || file.isDirectory()) {
			return null;
		}
		
		try {
			Reader reader = new InputStreamReader(new FileInputStream(file));
			try {
				//JSONParser parser = new JSONParser();
				//result = (JsonObject) parser.parse(reader);
				
				Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
				
				
				result = gson.fromJson(reader, JsonObject.class);
				
				//Properties result = new Properties();
				//result.load(reader);
				//return result;
			} finally {
				reader.close();
			}
		} catch (Throwable e) {
			throw new RuntimeException("Unable to read property file" + file.getAbsolutePath());
		}
		
		return result;
	}

}
