package logger;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Log4J filter that stops certain log messages from being logged, based on a
 * value in the MDC (See Log4J docs).
 */
public class Log4JMessageStringFilter extends Filter {

private String valueToMatch = null;
private boolean denyOnMatch = true;

/**
 * {@inheritDoc}
 */
public int decide(LoggingEvent event) {
    int result = NEUTRAL;
	
    //If key and value has been set in the filter
    if (valueToMatch != null) {
    	if (event.getMessage().toString().toLowerCase().contains(valueToMatch.toLowerCase())==true) {
    		if (this.denyOnMatch==true) {
    			result = DENY;
    		} else {
    			result = ACCEPT;
    		}
    	} else {
//    		if (this.denyOnMatch==true) {
//        		result = ACCEPT;
//        	} else {
//        		result = DENY;
//        	}
    	}
    	
    }

    return result;
}

/**
 * Gets the value to match.
 * 
 * @return the value to match.
 */
public String getValueToMatch() {
    return valueToMatch;
}

/**
 * Sets the value to match.
 * 
 * @param valueToMatch the value to match.
 */
public void setValueToMatch(String valueToMatch) {
    this.valueToMatch = valueToMatch;
}

/**
 * Returns true if the log message should not be logged if a match is found.
 * 
 * @return true if the log message should not be logged if a match is found.
 */
public boolean isDenyOnMatch() {
    return denyOnMatch;
}

/**
 * Set this to "true" if you do not want log messages that match the given
 * key/value to be logged. False if you only want messages that match to be
 * logged.
 * 
 * @param denyOnMatch "true" if you do not want log messages that match the
 *        given key/value to be logged. False if you only want messages that
 *        match to be logged.
 */
public void setDenyOnMatch(boolean denyOnMatch) {
    this.denyOnMatch = Boolean.valueOf(denyOnMatch).booleanValue();
}

}
