package at.tuwien.ict.commonutils.configbuilder;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Maintester {
	
	private static final Logger log = LoggerFactory.getLogger(Maintester.class);
	
	public static void main(String[] args) {
		String jsonInput = "";
		jsonInput = "{\"NAME\": \"Alexander\", \"LOCATION\":\"Wien\"}";
//		String jsonInigridInputStt800 = "{"+
//			"\"osgidriver\":"+
//				"{\"deviceaddress\":\"demoanlage/device/address/1\","+
//				"\"driverid\":\"STT800driver\","+
//				"\"drivername\":\"STT800\"},"+
//			"\"devicedriver\":"+
//				"{\"serial\":\"A601BOEV\","+
//				"\"channeladdress\":\"demoanlage/channel/address/voltage\""+
//				"}"+
//		"}";
		
		log.info("Setup test");
		ConfigBuilder builder = new ConfigBuilderImpl();
		
		String expectedResult = "A601BOEV";
		
		ConfiguratorExample config = ConfiguratorExample.getInstance();
		log.debug("Config values={}", config);
		
		try {
			builder.init(log);
			builder.loadConfigFromString(jsonInput);
			config = builder.toClass(ConfiguratorExample.class);
			
			log.debug("ConfiguratorInigrid = {}, Name={}", config, config.getNAME());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
