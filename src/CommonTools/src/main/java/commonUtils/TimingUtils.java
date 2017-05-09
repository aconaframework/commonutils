package commonUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimingUtils {

	public static String msDateToString(long t) {
		Date currentdate = new Date(t);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return dateFormat.format(currentdate);
	}
}
