package Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public static Long generatedUniqueLong() {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
		String datetime = ft.format(dNow);
		return Long.parseLong(datetime);
		
	}
}
