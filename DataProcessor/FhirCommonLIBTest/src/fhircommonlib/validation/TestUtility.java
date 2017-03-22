package fhircommonlib.validation;

import java.util.Scanner;

public class TestUtility {

	// read in text files from src folder (they will be packaged in jar along
	// with this class etc)
	public static String readResourceToString(String resourcePath) {
		String result = "";
		Scanner scanner = null;
		try {
			scanner = new Scanner(
					TestUtility.class.getResourceAsStream(resourcePath),
					"UTF-8");
			result = scanner.useDelimiter("\\A").next();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		return result;
	}
}
