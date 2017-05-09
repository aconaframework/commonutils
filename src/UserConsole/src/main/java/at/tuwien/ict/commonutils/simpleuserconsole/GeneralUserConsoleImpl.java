package at.tuwien.ict.commonutils.simpleuserconsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;

/**
 * This class shall be used as a general console in the projects. It is written in a way to allow function pointers to be run. Therefore it may look complicated. The main purpose
 * is to find out how function pointers work.
 * 
 * @author wendt
 *
 */
public class GeneralUserConsoleImpl extends Thread implements GeneralUserConsole {
	
	private final Logger log;
	private final String HELPCOMMAND = "help"; 
	//private TapChanger mainProgram;
	private HashMap<String, String> registeredCommands = new HashMap<String, String>();
	private FunctionPointer functions;
	
	public GeneralUserConsoleImpl(Logger log) {
		this.log = log;
		this.setName("UserConsole");
	}

	@Override
	public void registerFunction(String command, String description) {
		registeredCommands.put(command, description);
		
	}
	
	@Override
	public void init(FunctionPointer functions) {
		this.functions = functions;
		
	}
	
	@Override
	public void startUserConsole() {
		super.start();
	}
	
	private boolean runFunction(String command, String[] parameter) throws Exception {
		boolean result = false;
		if (registeredCommands.get(command)!=null) {
			try {
				functions.runFunction(command, parameter);
			} catch (Exception e) {
				log.error("Cannot run function");
				throw e;
			}
			result = true;
		} 

		return result;
	}
	
	public void run() {
		final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		try {
			while (true) {

				System.out.print("> ");

				String zeile = null;

				try {
					zeile = console.readLine();
				} catch (final IOException e1) {
					log.error("Could not read sign", e1);
				}
				
				//Get command
				String[] args = zeile.toLowerCase().split(" ");
				String command = args[0];
				//Get parameter
				String parameter = "";
				String parameterList[] = new String[args.length-1]; 
				if (args.length>1) {
					for (int i=1;i<args.length;i++) {
						parameterList[i-1] = args[i];
					}
				}
				
				boolean success=true;
				try {
					success = runFunction(command, parameterList);
				} catch (Exception e) {
					log.error("Cannot execute command");
				}
				if (command.equals(HELPCOMMAND)==true) {
					System.out.println("The following commands are available:");
				} else if (success==false) {
					System.out.println("Command not recognized. The following commands are available:");
				}
				
				if (success==false) {
					System.out.println("help" + ": " + "disply all commands");
					
					List<String> commandName = new ArrayList<String>(registeredCommands.keySet());
					Collections.sort(commandName, new Comparator<String>() {
						@Override
						public int compare(String arg0, String arg1) {
							return arg0.compareTo(arg1);
						}
					});
					
					for (String a: commandName) {
						System.out.println(a + ": " + registeredCommands.get(a));
					}
				}
			}
		} catch (Exception e) {
			log.error("Error in the user console", e);
		}
		
	}

}
