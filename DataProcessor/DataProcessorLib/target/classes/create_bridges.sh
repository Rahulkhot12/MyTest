#!/bin/bash
#Just a note on why we setup the conf file to delete and add everything.
#tibemsadmin is capable of just doing pieces of the bridge, but then we don't have a "standard" file to run each time.
#So rather than make each execution a one off, we decided to always delete and recreate everything.
EMS_HOME=/c/tibco_fy17/ems/8.2
 
MINPARAMS=4;
if [ $# -lt "$MINPARAMS" ]; then
        echo "Usage: create_bridges.sh host port user pw";
        echo "example: create_bridges.sh localhost 7222 admin \"\"";
        exit -1
fi

HOST=$1;
PORT=$2;
USER=$3;
PASSWORD=$4;

RC=0;

echo "Results Bridge"
cat create_bridges.conf
${EMS_HOME}/bin/tibemsadmin -script create_bridges.conf -server "tcp://${HOST}:${PORT}" -user ${USER} -password "${PASSWORD}"
RC=$?

if [ $RC -gt 0 ]; then
    dateNow=`date`;
    msg="$dateNow ERROR: Failed to invoke tibemsadmin to run create_bridges.conf .. $RC";
    echo "";
    echo $msg;
    exitStatus=2;
fi
