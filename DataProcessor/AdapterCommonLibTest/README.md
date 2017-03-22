# Adapter Common Library Test Application Module #

This is Tibco Application Module complements the AdapterCommonLib module in that it is used to test processses and resources in that module

### Process Inventory ###

* JMSSenderProcess - test processs that utilizes the shared JNDI and JMS connection configuration resources in AdapterCommonLib
* RestJMSTest - REST entry point that invokes the JMSSenderProcess test process - simple GET no-param invocation e.g., http://localhost:8080/restjmstest
                Note that this test assumes you have the appropriate administrative objects configured in EMS either locally or remotely.
                For convenience the Tibco EMS *.conf files containing the objects required for this test are in src/test/resources/ems
* GenerateJWTToken - process to generate a JWT token with the given scopes and sign with a private key
* ValidateJWTToken - process to validate a given JWT token with the public key
* VerifyAuthorizationInToken - process to check if a given scope is in the token or not after validating the token with the public key

### Resource Inventory ###

* TestHTTPConnectorResource.httpConnResource - Test HTTP Connection configuration 

### Schema Inventory ###

* simple.xsd - default simple schema
* TestResult - schema for simple single test result
* TestSuitResult - schema for complex multiple test result summary
* GenerateJWTToken - schema for GenerateJWTToken subprocess
* ValidateJWTToken - schema for ValidateJWTToken subprocess
* VerifyAuthorizationInToken - schema for VerifyAuthorizationInToken subprocess

###  Generate keys ###

* openssl genrsa -out my4key.pem 2048
* openssl pkcs8 -topk8 -inform PEM -outform PEM -in my4key.pem -out private4key.pem -nocrypt
* openssl rsa -in my4key.pem -pubout -outform DER -out public4key.der

###  ###