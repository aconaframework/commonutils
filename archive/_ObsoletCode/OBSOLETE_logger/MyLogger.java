/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import propertyhandler.PropertyHandler;


/**
 *
 * @author gunther
 */
public class MyLogger {
	private static boolean ansiSet = false;
    
    private MyLogger() {
    	
    }
    
    private static void useAnsiSystemInstall() {
    	PropertyHandler ph = new PropertyHandler();
    	try {
			ph.load("conf/logger.conf");
			boolean useSystemInstall = ph.getBoolean("usesysteminstall");
			if (useSystemInstall==true) {
				AnsiConsole.systemInstall();
			}
			
		} catch (IOException e) {
			System.out.println("Could not load logger options");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Cannot read value for the use of ansi systeminstall");
			e.printStackTrace();
		}
    	
    }
    
    private static void setSystemPropertyForANSI() {
        //Set this property, in order to activate JAnsi correctly
        System.setProperty("jansi.passthrough", "true");
    }
    
    public static Logger getLog(String loggerName) {
        if (ansiSet==false) {
        	//AnsiConsole.systemInstall();
        	useAnsiSystemInstall();
            setSystemPropertyForANSI();
            PropertyConfigurator.configure("conf/log4j.properties");
            ansiSet=true;
        }
        
        return LoggerFactory.getLogger(loggerName);
    }	
}
