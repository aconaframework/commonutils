package logger;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Log4J filter that stops certain log messages from being logged, based on a
 * value in the MDC (See Log4J docs).
 */
public class Log4JMDCFilter extends Filter {

private String keyToMatch;
private String valueToMatch = null;
private boolean denyOnMatch = true;
private boolean includeEmpty=false;

/**
 * {@inheritDoc}
 */
public int decide(LoggingEvent event) {
    int result = NEUTRAL;
	
    //If key and value has been set in the filter
    if (keyToMatch != null && valueToMatch != null) {
    	if (event.getMDC(keyToMatch)==null) {
    		if (this.includeEmpty==true) {
    			result = ACCEPT;
    		} else {
    			result = NEUTRAL;
    		}
    	} else {
    		boolean anyMatch = false;
    		if (this.valueToMatch.contains((CharSequence) event.getMDC(keyToMatch))==true) {
    			anyMatch=true;
    		}
//    		for (int i=0;i<valueToMatch.length;i++) {
//    			if (valueToMatch[i].equals(event.getMDC(keyToMatch))==true) {
//    				anyMatch=true;
//    				break;
//    			}
//    		}
    		//event value matches key value
        	if (anyMatch==true) {
        		if (this.denyOnMatch==true) {
        			result = DENY;
        		} else {
        			result = ACCEPT;
        		}
        	} else {
        		if (this.denyOnMatch==true) {
        			result = ACCEPT;
        		} else {
        			result = DENY;
        		}
        	}
    	}
    	
    } 
//    else {
//    	result = ACCEPT;
//    }

    return result;
}

/**
 * The key on which to filter.
 * 
 * @return key on which to filter
 */
public String getKeyToMatch() {
    return keyToMatch;
}

/**
 * Sets the key on which to filter.
 * 
 * @param keyToMatch key on which to filter
 */
public void setKeyToMatch(String keyToMatch) {
    this.keyToMatch = keyToMatch;
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

public boolean isIncludeEmpty() {
	return includeEmpty;
}

public void setIncludeEmpty(boolean includeEmpty) {
	this.includeEmpty = includeEmpty;
}

}
