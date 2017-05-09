package at.tuwien.ict.commonutils.configbuilder;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import at.tuwien.ict.commonutils.configbuilder.ConfigBuilder;
import at.tuwien.ict.commonutils.configbuilder.ConfigBuilderImpl;

public class ConfigManagerTest {
	protected final Logger log = LoggerFactory.getLogger(ConfigManagerTest.class);
	
	private ConfigBuilder config = new ConfigBuilderImpl();
	private JsonObject sourceMerge;
	private JsonObject targetMerge;
	private JsonObject multipleProperties;

	@Before
	public void setUp() throws Exception {
		log.info("Setup test");
		this.config.init(log);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		//JSON file
				String sourceString = "{\"condition\": {"
						+ "\"triggeraction3\": { " 
								+ "\"name\": \"hallo3\", "
								+ "\"custom\": {"
									+ "\"sourceproperty\":\"triggeraction\", "
									+ "\"extrathing\":\"anything\""
								+ "} "
						+"},"
						+ "\"triggeraction2\": { " 
								+ "\"name\": \"hallo2\", "
								+ "\"custom\": {"
									+ "\"comparedatastructure\":\"ACTIONOFCALLERSOURCE\""
								+ "}"
						+"}"
					+"}"
				+"}";
						
				//JSON file
				String targetString = "{\"condition\": {"
						+ "\"triggeraction1\": { " 
								+ "\"name\": \"hallo1\", "
								+ "\"custom\": {"
									+ "\"sourceproperty\":\"triggeraction\", "
									+ "\"extrathing\":\"test\""
								+ "} "
						+"},"
						+ "\"triggeraction2\": { " 
								+ "\"name\": \"hallo2\", "
								+ "\"custom\": {"
									+ "\"comparedatastructure\":\"ACTIONOFCALLER\""
								+ "}"
						+"}"
					+"}"
				+"}";
				
				try {
					sourceMerge = gson.fromJson(sourceString, JsonObject.class);
					log.debug("Source={}", sourceString);
					targetMerge = gson.fromJson(targetString, JsonObject.class);		
					log.debug("target={}", targetString);
					this.multipleProperties = ConfigBuilderImpl.deepMerge(sourceMerge, targetMerge);
					log.debug("merge={}", this.multipleProperties);
					
				} catch (Exception e) {
					log.error("Cannot setup tests", e);
				}
	}

	@After
	public void tearDown() throws Exception {
		log.info("Close test");
	}

	
	@Test
	public void JsonMergeOverwriteValueTester() {
		log.info("Test Merge-Overwrite");
		//Test 2 things, overwrite of variable and adding of new variable
		String expectedOverwritevalue = "ACTIONOFCALLERSOURCE";
		
		try {
			
			JsonObject merge = ConfigBuilderImpl.deepMerge(sourceMerge, targetMerge);
			log.debug("merge={}", merge);

			//Test overwritten values
			String actualOverwrittenValue = merge.get("condition").getAsJsonObject()
					.get("triggeraction2").getAsJsonObject()
					.get("custom").getAsJsonObject()
					.get("comparedatastructure").getAsString();
			
			assertEquals(expectedOverwritevalue, actualOverwrittenValue);
			
		} catch (Exception e) {
			log.error("Cannot execute test", e);
			fail();
		}
	}
	
	@Test
	public void JsonMergeNewValueTester() {
		log.info("Test merge new");
		//Test 2 things, overwrite of variable and adding of new variable
		String expectedNewValue = "triggeraction";
		
		try {
			
			JsonObject merge = ConfigBuilderImpl.deepMerge(sourceMerge, targetMerge);
			log.debug("merge={}", merge);
			
			//Test new added values
			String actualNewValue = merge.get("condition").getAsJsonObject()
					.get("triggeraction3").getAsJsonObject()
					.get("custom").getAsJsonObject()
					.get("sourceproperty").getAsString();
			
			//assertEquals(expectedNewValue, actualNewValue);
			assertEquals(expectedNewValue, actualNewValue);
			
		} catch (Exception e) {
			log.error("Cannot execute test", e);
			fail();
		}
	}
	
	@Test
	public void getAllPropertiesOfCertainConditionsTester() {
		log.info("Test property extraction");
		//Expected is to find 2 attributes with source properties
		int expectedResult = 2; 	
		int actualResult  = 0;
		
		this.config.addProperties(multipleProperties);
		
		try {
			List<JsonObject> conditions = this.config.getSubObjectsFromConfigManager("condition");
			for (JsonObject condition : conditions) {
				JsonObject customProperties = ConfigBuilderImpl.getElementsOfSubObject("custom", condition);		
				
				if (customProperties.has("sourceproperty") && customProperties.get("sourceproperty").getAsString().equals("triggeraction")) {
					actualResult++;
					log.debug("Result found at object={}", condition);
				}
			}
			
			assertEquals(expectedResult, actualResult);
			
		} catch (Exception e) {
			log.error("Cannot execute test", e);
			fail();
		}
	}

}
