package commonUtils;

import java.util.ArrayList;

public class TextTools {
	
	/**
	 * Create an alternative way of showing the arraylist
	 * 
	 * @param poList
	 * @return
	 */
	public static <E extends Object> String arrayListToString(ArrayList<E> poList) {
		String oResult = "";
		
		for (E oObject: poList) {
			oResult += oObject.toString() + ", \n";
		}
		
		return oResult;
	}
	
	
}
