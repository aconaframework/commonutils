package at.tuwien.ict.commonutils.simpleuserconsole;

import org.slf4j.Logger;


public class TestClientUserConsole extends UserConsoleFunction {
	
	private TestClient client;
	
	/**
	 * Set the device, which shall be used 
	 */
	//private DPAlgorithmV1 component;

	public TestClientUserConsole(Logger log, TestClient client) {
		//Set the name of the class here
		super(log, TestClientUserConsole.class);
		
		this.client = client;
	}
	
	/* (non-Javadoc)
	 * @see commonclasses.userconsole.UserConsoleFunction#registerFunctions()
	 */
	@Override
	protected void registerFunctions() {
		//Register all functions, which shall be used in the user console
		
		this.register("exit", "exit program", "exitProgram");
		this.register("restart", "restart tapchanger", "restart");
		this.register("set", "Set command. Syntax: set [START, STOP, PAUSE]", "setCommand");
		this.register("setprofile", "Set profile. Syntax: setprofile [Profilename in profileclient.ini]", "setProfile");
		this.register("settime", "Set system/profile time scaling factor. Unit [min/day], e.g. 2 min/day is 2 min for the execution of one day", "setTime");
		
	}
	
	//=== individual functions for the user console ===//
	
	public void exitProgram() {
		//client.shutdown();
		try {
			client.doExit();
		} catch (Exception e) {
			log.error("Cannot correctly exit tapchanger", e);
		}
	}
	
	public void restart() {
		client.restart();
	}
	
	public void setCommand(String[] command) {
		this.client.setCommandLocally(command[0]);
	}
	
	public void setProfile(String[] profilename) {
		this.client.setProfileLocally(profilename[0]);
	}
	
	public void setTime(String[] minperday) {
		this.client.setSystemTimeLocally(minperday[0]);
	}

}
