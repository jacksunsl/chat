#! /bin/sh
SERVER_NAME=hipishare-chat.jar
JAVA_OPTS='-Xms128m -Xmx1024m'
SERVER_DIR=$(pwd)
cd $SERVER_DIR

# start service
start(){
	java $JAVA_OPTS -jar $SERVER_DIR/$SERVER_NAME 2>&1 &
}

# stop service
stop(){
	ps -ef|grep $SERVER_NAME|grep -v grep|awk '{print $2}'|while read pid
	do
		kill -9 $pid & printf '['$SERVER_NAME']服务已经停止.\n'
	done
}

case "$1" in
start)
	start
	;;
stop)
	stop
	;;
restart)
	stop
	start
	;;
*)
	printf 'Usage: %s {start|stop|restart}\n' "$prog"
	exit 1
	;;
esac