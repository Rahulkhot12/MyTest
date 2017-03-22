package com.mckesson.mhs.fhir;

import ca.uhn.fhir.validation.ValidationResult;

import com.mckesson.fhir.validation.FhirResourceValidator;

public class ValidationUtility {

	protected static final FhirResourceValidator validator = FhirResourceValidator.instance();

	public static String validateResource(String contentType, String resource)
			throws Exception {
		validator.setExtensionDefinitionRequired(true);
		validator.getIgnorableExtensionDomains().clear();

		ValidationResult result = validator.validateWithResult(resource);
		
		return validator.getContext().newXmlParser().setPrettyPrint(true)
				.encodeResourceToString(result.toOperationOutcome());
	}
}
