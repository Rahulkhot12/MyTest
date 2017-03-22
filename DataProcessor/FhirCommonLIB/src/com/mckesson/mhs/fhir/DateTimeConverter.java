package com.mckesson.mhs.fhir;

import java.util.Date;

public class DateTimeConverter {

	public static  Date longToDateTime(long datetimelong) {
		return new Date(datetimelong);
	}

}
