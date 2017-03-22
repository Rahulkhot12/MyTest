package com.mckesson.testutils;

import java.util.Scanner;
import java.util.regex.Pattern;

public class TestUtilities {

	public static boolean matchPattern(String testString, String testPattern) {
		boolean matches = false;
		// get rid of pretty print XML formatting etc
		testString = testString.replaceAll("\\s+", " ");
		matches = Pattern.matches(testPattern, testString);
		if (!matches) {
			System.err.println("Test String : " + testString
					+ "  - does not match test pattern..");
			System.err.println("Test Pattern: " + testPattern);
		}
		return matches;
	}

	// read in text files from src folder (they will be packaged in jar along
	// with this class etc)
	public static String readResourceToString(String resourcePath) {
		String result = "";
		Scanner scanner = null;
		try {
			scanner = new Scanner(
					TestUtilities.class.getResourceAsStream(resourcePath),
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
