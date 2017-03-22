# EventProcessorTest

This is a BW6 shared module that implements integration framework event processing.  It accepts staged event summary messages from a staging queue and then 
aggregates constituent related events into a single aggregated event i.e., coelesces fine-grained events if they have an aggregation filter in place.  It then
looks up its approprate handler and assigns it, then it sends that message to the handler or the data refresh queue depending on the state of the property 
that drives that refresh.  It is the Event Processor piece in this workflow - PM Database --> Event Generator --> Event Processor --> FHIR Public Topic

Dependencies:

- Production --> Run the Tibco EMS configuration EventProcessorLib/src/main/resources/deploy_config.bat script against the configured Tibco EMS instance.  However, you must first
  run the Tibco EMS configuration integration-framework/PMIntegrationFrameworkLib/src/main/resources/deploy_config.bat script as it is dependent on the basic 
  configuration there as well. 
  
  -- dependent on AdminServices application at runtime

- Test -->  Same instructions except use EventProcessorLib/src/test/resources/deploy_config.bat instead of EventProcessorLib/src/main/resources/deploy_config.bat
  
  -- dependent on AdminServices application during test deployment