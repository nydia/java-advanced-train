环境: docker, redis6.2.5


启动主从： 

redis-server /opt/softs/redis/conf/redis-6379.conf

redis-server /opt/softs/redis/conf/redis-6380.conf


主要配置属性：dir, pidfile, port, replicaof




启动sentinel哨兵集群：

1. 先启动主从

redis-server /opt/softs/redis/conf/redis-6379.conf

redis-server /opt/softs/redis/conf/redis-6380.conf


2. 在启动哨兵，window下面没有redis-sentinel 命令

redis-server /opt/softs/redis/conf/sentinel0.conf --sentinel

redis-server /opt/softs/redis/conf/sentinel1.conf --sentinel






cluster集群：




redis5和redis6的区别
1. ...