package com.mckesson.mhs.fhir;

/** 
 * bean for capturing data to map into Bundle 
 * 
 * @author e7nrlj4
 *
 */
public class BundleEntryFields {
	private String relativeReference;
	private String resourcePayloadXmlString;
	private String resourceFullUrl;

	public String getRelativeReference() {
		return relativeReference;
	}

	public void setRelativeReference(String relativeReference) {
		this.relativeReference = relativeReference;
	}

	public String getResourceFullUrl() {
		return resourceFullUrl;
	}

	public void setResourceFullUrl(String resourceFullUrl) {
		this.resourceFullUrl = resourceFullUrl;
	}

	public String getResourcePayloadXmlString() {
		return resourcePayloadXmlString;
	}

	public void setResourcePayload(String resourcePayload) {
		this.resourcePayloadXmlString = resourcePayload;
	}
}