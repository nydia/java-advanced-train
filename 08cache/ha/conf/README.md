# env : docker, redis6.2.5

1. redis tar -zxvf
2. make prefix=/opt/softs/redis install
3. redis建立的日志根目录logs, 存储stores, 进程根目录pid（每个redis启动之后会生成一个pid文件，用于锁住进程）

# redi主从

1. 配置
- 主要配置参数：dir, pidfile, port, replicaof
-	stores根目录下面建立：redis1 redis2



2. 启动：
- redis-server /opt/softs/redis/conf/redis-6379.conf
- redis-server /opt/softs/redis/conf/redis-6380.conf


# sentinel哨兵

1. 配置：
- stores根目录下面建立：sentinel0 sentinel1

2. sentinel cluster start:
	1. start master and slaveof:
	- redis-server /opt/softs/redis/conf/redis-6379.conf
	- redis-server /opt/softs/redis/conf/redis-6380.conf
	2. start sentinel:
	- redis-server /opt/softs/redis/conf/sentinel0.conf --sentinel
	- redis-server /opt/softs/redis/conf/sentinel1.conf --sentinel

# cluster分片集群
1. 配置
- stores根目录下面建立：cluster7000 cluster7001  cluster7002  cluster7100 cluster7101 cluster7102

2. 搭建Redis Cluster主要步骤：
	1.配置开启节点
	2.meet
	3.指派槽
	4.主从关系分配

3. 配置 cluster-master7000.conf, 其他类似
~~~
port 7000
daemonize yes, 其他类似
dir '/opt/softs/redis/stores/cluster7000/'
logfile '/opt/softs/redis/logs/redis_7000.log'
dbfilename 'redis_7000.data'
cluster-enabled yes
cluster-config-file nodes-7000.conf
cluster-require-full-coverage no
~~~
3. 启动：
```sh
redis-server /opt/softs/redis/conf/cluster-master7000.conf
redis-server /opt/softs/redis/conf/cluster-master7001.conf
redis-server /opt/softs/redis/conf/cluster-master7002.conf
redis-server /opt/softs/redis/conf/cluster-salveof7100.conf
redis-server /opt/softs/redis/conf/cluster-salveof7101.conf
redis-server /opt/softs/redis/conf/cluster-salveof7102.conf
```
4. 用meet把节点加入到集群
```sh
redis-cli -p 7000 culster meet 127.0.0.1 7001
redis-cli -p 7000 culster meet 127.0.0.1 7002
redis-cli -p 7000 culster meet 127.0.0.1 7100
redis-cli -p 7000 culster meet 127.0.0.1 7101
redis-cli -p 7000 culster meet 127.0.0.1 7102
```
5. 设置槽点，下面的槽位可以同时设置
- 用脚本设置槽点，脚本如下：
```sh
#!/bin/bash
start=$1
end=$2
port=$3
for slot in `seq ${start} ${end}`
do
	echo "slot:${slot}"
	redis-cli -p ${port} cluster addslots ${slot}
done
```
- 服务器上执行下面脚本：
```sh
sh add_slots.sh 0 5461 7000        # 运行add_slots.sh脚本，把0到5461号槽分配给127.0.0.1:7000的redis server节点
sh add_slots.sh 5462 10922 7001    # 运行add_slots.sh脚本，把5462号到10922号槽分配给127.0.0.1:7001端口运行的redis server
sh add_slots.sh 10923 16383 7002   # 运行add_slots.sh脚本，把10923号到16383号槽分配给127.0.0.1:7002端口运行的redis server			
```
6. 设置主从
```sh
redis-cli -p 7100 cluster replicate f8536ac3337bd12971629f42699294ef44043845 # 7100作为7000的从节点
redis-cli -p 7101 cluster replicate bc58cc1e08330bea2d7f66d96031c505437aa3d2 # 7101作为7001的从节点
redis-cli -p 7102 cluster replicate c51042f43bc75cee64de2d875aef0df708414c1a # 7102作为7002的从节点
```
7. 向集群写入数据

```sh
[root@e90dec803b8c conf]# redis-cli -c -p 7000
127.0.0.1:7000> set s 1
OK
127.0.0.1:7000> get s
"1"
127.0.0.1:7000>
```
8. 集群扩容：增加主节点



# 其他：
1. redis5和redis6对比
2. nodepad去掉#开头的字符：正则匹配
```sh
 ^#.*$   去掉空格行：^\s+
```

3. 查看redis节点： - ps aux|grep cluster
