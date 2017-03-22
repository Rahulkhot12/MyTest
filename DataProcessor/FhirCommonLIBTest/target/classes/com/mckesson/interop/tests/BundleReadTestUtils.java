/**
 * 
 */
package com.mckesson.interop.tests;

import java.util.List;
import org.hl7.fhir.instance.model.api.IBaseCoding;

import ca.uhn.fhir.context.ConfigurationException;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.MessageHeader;
import ca.uhn.fhir.parser.DataFormatException;

import com.mckesson.mhs.fhir.FhirContextSingleton;

/**
 * @author e2nmk2d
 *
 */
public class BundleReadTestUtils {
	
	public static boolean validateBundle(String testBundleString) {
		boolean matches = false;
		try {
			FhirContext ctx = FhirContextSingleton.getFhirContext();

			// Parse Bundle
			Bundle bundleIn = (Bundle) ctx.newXmlParser().parseResource(testBundleString);
			MessageHeader messageHeader = (MessageHeader) bundleIn.getEntry()
					.get(0).getResource();
			if(messageHeader.getResourceName().equalsIgnoreCase("MessageHeader")){
				matches=true;
			}
		} catch (ConfigurationException e) {
			System.err.println(e.getMessage()+" cause: "+e.getCause());
		} catch (DataFormatException e) {
			System.err.println(e.getMessage()+" cause: "+e.getCause());
		}
		
		
		return matches;
	}
	
	public static boolean validateQueryBundle(String testBundleString) {
		boolean matches = false;
		try {
			FhirContext ctx = FhirContextSingleton.getFhirContext();

			// Parse Bundle
			Bundle bundleIn = (Bundle) ctx.newXmlParser().parseResource(testBundleString);
			MessageHeader messageHeader = (MessageHeader) bundleIn.getEntry()
					.get(0).getResource();
			@SuppressWarnings("unchecked")
			List<IBaseCoding>  taglist =(List<IBaseCoding>) messageHeader.getMeta().getTag();
			for (IBaseCoding code:taglist){
				if(code.getSystem().equalsIgnoreCase("http://fhir.mckesson.com/mhs/vs/messagetypes-v1")){
					code.getCode().equalsIgnoreCase("read");
					matches=true;
				}
			}
			
		} catch (ConfigurationException e) {
			System.err.println(e.getMessage()+" cause: "+e.getCause());
		} catch (DataFormatException e) {
			System.err.println(e.getMessage()+" cause: "+e.getCause());
		}

		return matches;
	}


}
