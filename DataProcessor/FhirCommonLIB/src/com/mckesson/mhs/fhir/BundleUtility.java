package com.mckesson.mhs.fhir;

import java.util.ArrayList;
import java.util.List;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.IResource;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.MessageHeader;

public class BundleUtility {

	/**
	 * Merges a list of FHIR Resources XML docs into a Bundle XML doc..
	 * 
	 * Pass in an XML Bundle String and a list of BundleEntry objects with XML
	 * Resource strings as payloads
	 * 
	 * @param bundle
	 * @param entries
	 * @return
	 */
	public static String mergeBundle(String bundleXmlString,
			List<BundleEntryFields> entriesXml) {
		String resultBundle;
		FhirContext ctx = FhirContextSingleton.getFhirContext();

		// Parse Bundle
		Bundle bundleIn = (Bundle) ctx.newXmlParser().parseResource(
				bundleXmlString);
		MessageHeader messageHeader = (MessageHeader) bundleIn.getEntry()
				.get(0).getResource();

		for (BundleEntryFields bundleEntry : entriesXml) {
			// set relative URL in messageHeader
			ResourceReferenceDt refDt = messageHeader.addData();
			refDt.setReference(bundleEntry.getRelativeReference());

			// Add Entry for resource
			Bundle.Entry entry = bundleIn.addEntry();
			if (bundleEntry.getResourceFullUrl() != null
					&& !"".equals(bundleEntry.getResourceFullUrl())) {
				entry.setFullUrl(bundleEntry.getResourceFullUrl());
			}
			entry.setResource((IResource) ctx.newXmlParser().parseResource(
					bundleEntry.getResourcePayloadXmlString()));
		}

		resultBundle = ctx.newXmlParser().encodeResourceToString(bundleIn);

		return resultBundle;

	}

	public static List<BundleEntryFields> initList() {
		return new ArrayList<BundleEntryFields>();
	}

	public static List<BundleEntryFields> buildOnList(
			List<BundleEntryFields> entries, String relativeReference,
			String fullReference, String referenceXmlPayload) {

		BundleEntryFields fields = new BundleEntryFields();
		fields.setRelativeReference(relativeReference);
		fields.setResourceFullUrl(fullReference);
		fields.setResourcePayload(referenceXmlPayload);

		entries.add(fields);
		return entries;
	}

}
