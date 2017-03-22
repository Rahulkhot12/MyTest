package com.mckesson.mhs.fhir;

import ca.uhn.fhir.context.FhirContext;

public class FhirContextSingleton {

	private static Object ctxGuard = new Object();
	private static FhirContext ctx = null;

	public static FhirContext getFhirContext() {
		synchronized (ctxGuard) {
			if (ctx == null) {
				// Hapi docs say FhirContext is 'spensive.. so creating once if
				// possible..
				ctx = FhirContext.forDstu2();
			}
		}
		return ctx;
	}
}
