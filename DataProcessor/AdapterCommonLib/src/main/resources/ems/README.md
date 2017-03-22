# EMS Scripting

## Set Environment Variables 
EMS_HOME is referenced in scripts
Example:  SET EMS_HOME=c:\tibco_fy17\ems\8.2

## May Need Modification
create_factories.conf contains URLs to JNDI resources which are set to localhost by default and may require changes based on your needs

## Single Commands
ems_cmd (.sh and .bat) allow you to submit a single command to tibemsadmin and get the outcome..

## Scripts
create_bridges (.sh and .bat) show how to run the script (create_bridges.conf).
The script is similar to DDL in Oracle etc.. where it drops then creates objects so that it can be re-run from release to release.

Error handling is not great as you really have to scan the output for errors if the script fails 
(which the .bat and .sh scripts attempt to notify of when tibemsd returns non-zero).

## Documentation
[TIBCO EMS User Guide - See Chapter 6](https://docs.tibco.com/pub/ems/8.3.0/doc/pdf/TIB_ems_8.3_users_guide.pdf)
