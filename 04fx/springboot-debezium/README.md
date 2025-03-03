# debezium
Debezium解析mysql binlog

## 版本
debezium 3.0.7 + mysql-8.4.4 + springboot 3.4.1 + kafka_2.12-3.9.0 + jdk17

## mysql 配置
```ini
[mysqld]
server-id=1
basedir=D:/soft/mysql-8.4.4-winx64
datadir=D:/soft/mysql-8.4.4-winx64/data
port=3306
character-set-server=utf8mb4
default-time-zone = '+8:00'
binlog_format=ROW
binlog_row_image=FULL
[client]
default-character-set=utf8mb4
```

## kafka 配置修改
```shell
config下kafka server端口号修改
1、service.properties                                port = 19092    不指定的话，按照默认9092
listeners=PLAINTEXT://:19092
port=19092
2、connect-distributed.properties                    bootstrap.servers=localhost:19092
3、producer.properties                               bootstrap.servers=localhost:19092
4、connect-standalone.properties                     bootstrap.servers=localhost:19092
5、consumer.properties                               bootstrap.servers=localhost:19092
```

