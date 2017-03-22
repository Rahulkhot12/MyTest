@echo off
        
	SET /A ARGS_COUNT=0
	FOR %%A in (%*) DO SET /A ARGS_COUNT+=1
	echo number of params passed in is %ARGS_COUNT%
	if not "%ARGS_COUNT%" == "5" goto :USAGE
	echo host: %1
	echo port: %2
	echo user: %3
	echo pw:   %4
	echo command: %5
	echo %~5 > tmp.scr
	%EMS_HOME%\bin\tibemsadmin -server tcp://%1:%2 -user %3 -password %4 -script tmp.scr
	del tmp.scr
	goto :DONE

:USAGE
 	echo Usage: %0 ems-host ems-port ems-admin ems-pw "script command(s)"
 	echo example: %0 localhost 7222 admin "" "show queues"
goto :DONE
:DONE
