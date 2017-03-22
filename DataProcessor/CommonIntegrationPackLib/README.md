# README #

This module contains common and handler-specific logic for the out-of-the-box common integration pack event handlers.  Processes in the 
CommonIntegrationPack BW application module use logic in this shared BW module to fulfill the bulk of their event-handler-specific internal 
to public FHIR event message translation.

### What is this repository for? ###

* This repo represents logic for out-of-the-box common integration pack event handlers
* "CommonIntegrationPackLib/commonintegrationpacklib.ValidateRefreshResponse" - common logic that checks the status of the data refresh and handles auditing 

### Deployment ###

* You must have Tibco EMS set up and configured with the scripts from IntegrationFrameworkLib, AdapterCommonLib, and CommonIntegrationPackLib - in the correct EMS environment.
* You must configure the shared resource properties for AdapterCommonLib for JMS and JDBC configuration as well as associated domain API hosts, security provider, and other common environment variables. 

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact