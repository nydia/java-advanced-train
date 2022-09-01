# 中间件
## mysql
C:\tools\mysql-8.0.22-winx64\bin>mysqld --console

## zookeeper
C:\tools\apache-zookeeper-3.6.1-bin\bin>zkServer.cmd

## rocketmq （如果不接入中间件的话，就不用启动mq）
cd C:\tools\rocketmq-all-4.6.0-bin-release

.\bin\mqnamesrv.cmd

启动另外窗口：
.\bin\mqbroker.cmd -n localhost:9876 -c conf/standalone/broker-a.properties

## rocketmq-dashboard （如果不接入中间件的话，就不用启动mq）
C:\files\workspace\java\rocketmq-dashboard\target>java -jar rocketmq-dashboard-2.0.0.jar

