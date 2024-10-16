# Java进阶


## 目录

- java-advanced-train java技术知识的系统学习
	- 01jvm
	- 02nio nio技术
	- 03concurrency 并发编程
	- 04fx 混合技术
		- BaseTrain 基础知识
		- springcloud-nacos feign的请求参数：超时重试等分析
		- springboot-inlong inlong网关示例
		- springboot-mpush：springboot + 消息推送系统 mpush示例
		- springboot-native：springboot native示例
		- springboot-skywalking：skywalking的实际运用
		- springcloud-gateway：gateway的各种用法
		- springcloud-shenyu ： shenyu网关的各种用法
		- springboot-ruleengine：规则引擎
		- design-pattern-java：设计模式java实现
		- task 定时任务
		- springboot-ruleengine 规则引擎
	- 06db 数据库
		- mysql： 基础多数据源
		- mysql
			- mysql
			- mysqlInsert ： mysql的插入
		- mysqlMasterSlaveV0： 普通利用目录动态切换数据源
		- mysqlMasterSlaveV1： 利用AbstractRoutingDataSource切换数据源（暂不可用）
		- mysqlMasterSlaveV1.1  ： 利用AbstractRoutingDataSource切换数据源，读写分离
		- mysqlMasterSlaveV2： 利用sharding-jdbc-spring-boot-starter动态切换数据源读写分离
		- sharding-sphere
			- mysqlShardingSphere-proxy5.0.0-alpha： 未知
			- mysqlShardingSphere-proxy5.0.0-alpha-readwrite-splitting： 使用中间件 Shardingsphere-proxy读写分离
			- mysqlShardingSphere-proxy5.0.0-alpha-sharding： 使用中间件 Shardingsphere-proxy分库分表
			- mysqlShardingSphere-proxy5.0.0-alpha-shardingAndXa： 使用中间件 Shardingsphere-proxy分库分表和分布式事务
			- mysqlShardingSphere-jdbc5.0.0-beta-xa： 使用shardingsphere-jdbc实现分布式事务
		- hmily-tcc-springcloud： 微服务框架SpringCloud + 分布式事务中间件（hmily) 使用tcc协议模式保证事务的一致性
		- hmily-tcc-dubbo： 微服务框架（Dubbo) + 分布式事务中间件（hmily) 使用tcc协议模式保证事务的一致性
		- mybatis
			- mybatis mybatis示例
			- mybatis-masterAndSlaveDB mybatis主从数据库
			- springboot-mybatisplus mybatis-plus插件示例
		- druid 德鲁伊数据库连接池
	- 07rpc  rpc技术
	- 08cache 缓存技术
	- 09mq mq队列
		- springboot-kafka：springboot + kafka结合示例
		- springboot-rabbitmq

	
## jvm 二进制文件

1. 在Idea里面查看class的二级制字节码文件
  - idea 安装 HexView插件 -> 右击class查看十六进制编码
