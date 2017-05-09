package at.tuwien.ict.commonutils.configbuilder;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import at.tuwien.ict.commonutils.configbuilder.ConfigBuilder;
import at.tuwien.ict.commonutils.configbuilder.ConfigBuilderImpl;
import at.tuwien.ict.commonutils.configbuilder.helper.ConfiguratorExample;
import at.tuwien.ict.commonutils.configbuilder.helper.ConfiguratorInigrid;

public class ConfiguratorBuilderTester {
	protected final Logger log = LoggerFactory.getLogger(ConfigManagerTest.class);

	private ConfigBuilder builder;
	private static String jsonInput = "";
	//private static String sourceString = "";
	private static String jsonInigridInputStt800 = "";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//log.info("Setup before class");
		jsonInput = "{\"NAME\": \"Alexander\", \"LOCATION\":\"Wien\"}";
		jsonInigridInputStt800 = "{"+
			"\"osgidriver\":"+
				"{\"deviceaddress\":\"demoanlage/device/address/1\","+
				"\"driverid\":\"STT800driver\","+
				"\"drivername\":\"STT800\"},"+
			"\"devicedriver\":"+
				"{\"serial\":\"A601BOEV\","+
				"\"channeladdress\":\"demoanlage/channel/address/voltage\""+
				"}"+
		"}";
		
		
	}
	
	@Before
	public void setUp() {
		log.info("Setup test");
		this.builder = new ConfigBuilderImpl();
	}

	@After
	public void tearDown() throws Exception {
		log.info("Close test");
	}

	
	@Test
	public void configuratorBuildTestFile() {
		try {
			String expectedResult = "Alexander";
			
			ConfiguratorExample config = ConfiguratorExample.getInstance();
			log.debug("Config values={}", config);
			
			this.builder.init(log);
			this.builder.loadConfigFromString(jsonInput);
			config = this.builder.toClass(ConfiguratorExample.class);
			//JsonObject  db = this.builder.getDatabase();
			//Gson gson = new GsonBuilder().create();
			//ConfiguratorExample config = gson.fromJson(db, ConfiguratorExample.class);
			//gson.from
			
			log.debug("ConfiguratorExample = {}, Name={}", config, config.getNAME());
			
			assertEquals(expectedResult, config.getNAME());
		} catch (Exception e) {
			log.error("Cannot execute test", e);
			fail("Error");
		}
	}
	
	@Test
	public void configuratorBuilderInigridTest() {
		try {
			String expectedResult = "A601BOEV";
			
			ConfiguratorInigrid config = ConfiguratorInigrid.getInstance();
			log.debug("Config values={}", config);
			
			this.builder.init(log);
			this.builder.loadConfigFromString(jsonInigridInputStt800);
			config = this.builder.toClass(ConfiguratorInigrid.class);
			
			log.debug("ConfiguratorInigrid = {}, Name={}", config, config.getDevice().getSerial());
			
			assertEquals(expectedResult, config.getDevice().getSerial());
		} catch (Exception e) {
			log.error("Cannot execute test", e);
			fail("Error");
		}
	}
	
	@Test
	public void configuratorBuilderInigridFromFileTest() {
		try {
			String expectedResult = "0x0010";
			
			ConfiguratorInigrid config = ConfiguratorInigrid.getInstance();
			log.debug("Config values={}", config);
			
			this.builder.init(log);
			this.builder.loadConfigFromFile("conf/registertranslatorconfig.json");
			config = this.builder.toClass(ConfiguratorInigrid.class);
			
			log.debug("ConfiguratorInigrid = {}, Name={}", config, config.getDevice().getSerial());
			
			assertEquals(expectedResult, config.getDevice().getSerial());
		} catch (Exception e) {
			log.error("Cannot execute test", e);
			fail("Error");
		}
	}

}
