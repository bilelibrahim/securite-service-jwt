#!/bin/sh
SERVICE_NAME=SecurityService
PATH_TO_JAR=target/sec-service-0.0.1-SNAPSHOT.jar
PID_PATH_NAME=/tmp/SecurityService-pid
case $1 in
    start)
        echo "Starting $SERVICE_NAME ... "
        if [ ! -f $PID_PATH_NAME ]; then
            mohup java -jar $PATH_TO_JAR /tmp 2>> /dev/null >> /dev/null & echo $! > $PID_PATH_NAME
            echo "$SERVICE_NAME Started ..."
        else
            echo "$SERVICE_NAME is already runing ..."
        fi
    ;;
    stop)
        if [ ! -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME Stopping ..."
            kill $PID;
            echo "$SERVICE_NAME Stopped ..."
            rm $PID_PATH_NAME
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
    restart)
        if [ ! -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME Stopping ..."
            kill $PID;
            echo "$SERVICE_NAME Stopped ..."
            rm $PID_PATH_NAME
        else
            echo "$SERVICE_NAME is not running ..."
        fi
        echo "Starting $SERVICE_NAME ... "
        if [ ! -f $PID_PATH_NAME ]; then
            mohup java -jar $PATH_TO_JAR /tmp 2>> /dev/null >> /dev/null & echo $! > $PID_PATH_NAME
            echo "$SERVICE_NAME Started ..."
        else
            echo "$SERVICE_NAME is already runing ..."
        fi
    ;;
esac