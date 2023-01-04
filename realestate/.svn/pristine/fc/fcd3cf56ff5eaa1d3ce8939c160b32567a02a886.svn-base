#!/bin/bash
# this is a setup scripts for gtmap app
# author yangyang

CONFIG_FILE=/opt/app/app.conf

MIN_OPT="-Xms384m -Xmx384m -Xmn144m -XX:MetaspaceSize=20M -XX:MaxMetaspaceSize=384m -Xss512K"
MIDDLE_OPT="-Xms512m -Xmx512m -Xmn200m -XX:MetaspaceSize=20M -XX:MaxMetaspaceSize=512m -Xss512K"
MAX_OPT="-Xms1024m -Xmx1024m -Xmn400m -XX:MetaspaceSize=20M -XX:MaxMetaspaceSize=1024m -Xss512K"

APP_CENTER=""
JVM_SIZE=""
CONFIG_ENABLED=""
PROFILES_ACTIVE=""

JAVA_OPT=""
D_CONFIG=""
APP_NAME={project.name}-{project.version}.jar

check_config(){
	if [ -f "$CONFIG_FILE" ]; then 
		APP_CENTER=`sed '/^app.center=/!d;s/.*=//' $CONFIG_FILE`
                echo "app.center=$APP_CENTER"
                JVM_SIZE=`sed '/^jvm.mem=/!d;s/.*=//' $CONFIG_FILE`
                echo "jvm.mem=$JVM_SIZE"
                CONFIG_ENABLED=`sed '/^cloud.config.enabled=/!d;s/.*=//' $CONFIG_FILE`
                echo "cloud.config.enabled=$CONFIG_ENABLED"
		PROFILES_ACTIVE=`sed '/^profiles.active=/!d;s/.*=//' $CONFIG_FILE`
		echo "spring.profiles.active=$PROFILES_ACTIVE"
	fi 

}

start_app(){
	check_config

	if [ "$JVM_SIZE"x = "min"x ]; then
   		JAVA_OPT="${MIN_OPT}"
	elif [ "$JVM_SIZE"x = "middle"x ]; then
		JAVA_OPT="${MIDDLE_OPT}"
	else
   		JAVA_OPT="${MAX_OPT}"
	fi
	echo $JAVA_OPT
 
	if [ "$APP_CENTER"x = "x" ]; then
		echo "not set app.center configure!"
	else
		D_CONFIG="-Deureka.client.serviceUrl.defaultZone=${APP_CENTER}"	
	fi

	if [ "$PROFILES_ACTIVE"x = "x" ]; then
		echo "not set spring.profiles.active!"
	else
		D_CONFIG="${D_CONFIG} -Dspring.profiles.active=${PROFILES_ACTIVE}"	
	fi	

	if [ "$CONFIG_ENABLED" = "yes" ]; then
		D_CONFIG="${D_CONFIG} -Dspring.cloud.config.enabled=true -Dspring.cloud.config.discovery.enabled=true"
	fi

 	echo ${D_CONFIG}
 
	cd `dirname "$0"`

	java ${JAVA_OPT} -jar ${D_CONFIG} app/${APP_NAME} --spring.config.location=cfg/ --logging.path=logs/ --server.tomcat.basedir=tmp/
}

stop_app(){
   PID=$(ps -ef | grep "${APP_NAME}" | grep -v grep | awk '{ print $2 }')
   echo "start kill ${APP_NAME} ${PID}"
   kill -9 $PID
}


case $1 in
	start)
		start_app
		;;
	stop)
		stop_app
		;;
	restart)
		stop_app
		start_app
		;;
	*)
		start_app
		;;
esac
