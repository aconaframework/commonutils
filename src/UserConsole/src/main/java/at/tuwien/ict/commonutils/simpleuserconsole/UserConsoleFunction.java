package at.tuwien.ict.commonutils.simpleuserconsole;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.slf4j.Logger;

public abstract class UserConsoleFunction implements FunctionPointer {

	private HashMap<String, String> functionAssignments = new HashMap<String, String>();
	private HashMap<String, String> functionDescriptions = new HashMap<String, String>();
	private GeneralUserConsole console;
	private Class<?> clzz;
	
	protected final Logger log;
	
	public UserConsoleFunction(Logger log, Class<?> clzz) {
		this.log = log;
		this.clzz = clzz;
		//Set console
		console = new GeneralUserConsoleImpl(this.log);
		//Initialize
		console.init(this);
	}
	
	public void init() {
		//register functions before starting console
		registerFunctions();
				
		//Start it
		console.startUserConsole();
	}
	
	@Override
	public void runFunction(String functionName, String[] parameter) throws Exception {
		//Invoke function
		//final UserConsoleFunction user;
		final Method method;
		final String methodName = functionAssignments.get(functionName);
				

		if (parameter.length==0) {
			try {
				method = this.clzz.getDeclaredMethod(methodName);
				method.invoke(this);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				log.error("Cannot invoke method. ", e);	
				throw e;
			}
		} else {
			try {
				method = this.clzz.getDeclaredMethod(methodName, String[].class);
				method.invoke(this, (Object)parameter);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				log.error("Cannot invoke method. Maybe a parameter is used, where there should not be any parameter");	
				throw e;
			}
		}
		
	}
	
	protected abstract void registerFunctions();
	
	protected void register(String command, String description, String functionName) {
		console.registerFunction(command, description);
		functionAssignments.put(command, functionName);
		functionDescriptions.put(functionName, description);
	}
}
