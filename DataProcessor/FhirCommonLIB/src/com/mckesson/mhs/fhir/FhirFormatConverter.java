package com.mckesson.mhs.fhir;

import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.context.FhirContext;
/**
 * @author e7nrlj4
 */
public class FhirFormatConverter {

	public final String json2xml(String json) {
		FhirContext ctx = FhirContextSingleton.getFhirContext();
		IBaseResource resource = ctx.newJsonParser().parseResource(json);
		return ctx.newXmlParser().encodeResourceToString(resource);
	}

	public final String xml2json(String xml) {
		FhirContext ctx = FhirContextSingleton.getFhirContext();
		IBaseResource resource = ctx.newXmlParser().parseResource(xml);
		return ctx.newJsonParser().encodeResourceToString(resource);
	}
}
