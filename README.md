# Java进阶训练营实战

## 目录

- mysql： 基础多数据源
- JavaAdvancedTrain : Java基础，nio，JVM，guava，lombok，java8（Stream），设计模式
- mysqlInsert ： mysql的插入
- mysqlMasterSlaveV0： 普通利用目录动态切换数据源
- mysqlMasterSlaveV1： 利用AbstractRoutingDataSource切换数据源（暂不可用）
- mysqlMasterSlaveV1.1  ： 利用AbstractRoutingDataSource切换数据源，读写分离
- mysqlMasterSlaveV2： 利用sharding-jdbc-spring-boot-starter动态切换数据源读写分离
- mysqlShardingSphere-proxy5.0.0-alpha： 未知
- mysqlShardingSphere-proxy5.0.0-alpha-readwrite-splitting： 使用中间件 Shardingsphere-proxy读写分离
- mysqlShardingSphere-proxy5.0.0-alpha-sharding： 使用中间件 Shardingsphere-proxy分库分表
- mysqlShardingSphere-proxy5.0.0-alpha-shardingAndXa： 使用中间件 Shardingsphere-proxy分库分表和分布式事务
- mysqlShardingSphere-jdbc5.0.0-beta-xa： 使用shardingsphere-jdbc实现分布式事务
- hmily-tcc-springcloud： 微服务框架SpringCloud + 分布式事务中间件（hmily) 使用tcc协议模式保证事务的一致性
- hmily-tcc-dubbo： 微服务框架（Dubbo) + 分布式事务中间件（hmily) 使用tcc协议模式保证事务的一致性

## 1. Jvm实战

1. 在Idea里面查看class的二级制字节码文件

idea 安装 HexView插件 -> 右击class查看十六进制编码

## 2. NIO 
1. nio 阻塞nio

2. nio2 非负责nio
