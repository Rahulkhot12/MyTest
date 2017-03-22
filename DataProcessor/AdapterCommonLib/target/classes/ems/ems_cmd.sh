#!/bin/bash -e

EMS_HOME=/c/tibco_fy17/ems/8.2
if [ ! $# -eq 5 ]; then
	echo "Usage: $0 <ems-host> <ems-port> <ems-admin> <ems-pw> <script command(s)>"
	echo "example: $0 localhost 7222 admin \"\"  \"show queues\""
else
	echo "host: " $1
	echo "port: " $2
	echo "user: " $3
	echo "pw: " $4
	echo $5 > tmp.scr
	cat tmp.scr
	${EMS_HOME}/bin/tibemsadmin -server tcp://localhost:7222 -user admin -password "" -script tmp.scr
	rm tmp.scr
fi
