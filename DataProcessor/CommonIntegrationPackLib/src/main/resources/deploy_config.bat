@echo off 
	SET /A ARGS_COUNT=0
	FOR %%A in (%*) DO SET /A ARGS_COUNT+=1
	echo number of params passed in is %ARGS_COUNT%
	if not "%ARGS_COUNT%" == "4" goto :USAGE
	echo host: %1
	echo port: %2
	echo user: %3
	echo pw:   %4
	
	echo "Calling EMS configuration script(s)"
	
	call create_factories.bat %1 %2 %3 %4
	call create_queues.bat %1 %2 %3 %4
    call create_topics.bat %1 %2 %3 %4
	call create_groups.bat %1 %2 %3 %4
	call create_users.bat %1 %2 %3 %4
	call add_members.bat %1 %2 %3 %4
	call grant_queues.bat %1 %2 %3 %4
    call grant_topics.bat %1 %2 %3 %4
	call grant_admin.bat %1 %2 %3 %4

if %ERRORLEVEL% == 0 (
            echo No errors detected..
        ) else if %ERRORLEVEL% == 1 (
            echo ErrorLevel is one
            SET ERR_LVL=1
            goto ERRORHANDLING
        ) else (
            echo ErrorLevel is > 1
            SET ERR_LVL=%ERRORLEVEL%
            goto ERRORHANDLING
        )
goto :DONE

:USAGE
	echo Usage: %0 host port user pw
	echo example: %0 localhost 7222 admin ""
goto :DONE

:ERRORHANDLING
	echo ERROR: Failed to execute EMS configuration script(s) RC=%ERR_LVL%

goto :DONE

:DONE