package at.tuwien.ict.commonutils.simpleuserconsole;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestClient extends Thread {
	
	private static TestClient testClient;
	
	//private static final String VALUE = "value";
	
	//Logger
	protected final static Logger log = LoggerFactory.getLogger(TestClient.class);


	public TestClient(String configfilePath) throws IOException {		

	}
	
	public void doExit() {
		log.debug("Command received to doExit");
		System.exit(0);
	}
	
	/**
	 * Init profile client
	 * 
	 * @param path: Execution path
	 * @param ConfigFile: relative config file path
	 * @throws Exception 
	 */
	public void init() throws Exception {		
		//Start the profile client thread, which is kept alive by the started service, i.e. no running while loops
		super.start();
		log.info("Client> Connection to server successful");
	}
	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			TestClient.testClient = new TestClient("");
			TestClient.testClient.init();
		} catch (Exception e) {
			log.error("Could not initialize profile client thread", e);
			System.exit(-1);
		}
		
		TestClientUserConsole console = new TestClientUserConsole(log, TestClient.testClient);
		console.init();
	}

	public void restart() {
		try {
			
		} catch (Exception e1) {
			log.error("Cannot close profile client cleanly", e1);
		}
		
		
		try {
			log.info("Start Profile Client...");
			sleep(1000);
			TestClient.testClient = new TestClient("");
			TestClient.testClient.init();
		} catch (Exception e) {
			log.error("Cannot restart tapchanger");
			System.exit(-1);
		}
		
	}

	/**
	 * Use this method to set the command through the command prompt in the user interface
	 * 
	 * @param command
	 */
	public void setCommandLocally(String command) {
		log.debug("Command received={}", command);	
	}
	
	/**
	 *  Use this method to set the profile through the command prompt in the user interface
	 * 
	 * @param profile
	 */
	public void setProfileLocally(String profile) {
		log.debug("Command received={}", profile);	
	}
	
	public void setSystemTimeLocally(String minPerDay) {
		log.debug("Command received={}", minPerDay);		
	}

}
