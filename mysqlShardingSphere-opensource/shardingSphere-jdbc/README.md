# ShardingSphere 20210823

## 题目

8-23 周一：JDBC  搞定ShardingSphere-5.0.0-beta的 jdbc版本，实现最简单的分库分表、读写分离（可以用假的从库）、加密功能演示。记录自己的过程，提交文档。

## 官方文档

- [数据分片](https://shardingsphere.apache.org/document/current/cn/features/sharding/)
- [读写分离](https://shardingsphere.apache.org/document/current/cn/features/readwrite-splitting/)
- [数据加密](https://shardingsphere.apache.org/document/current/cn/features/encrypt/)

## 前置准备

1. 源码准备
- fork shardingsphere项目的源码，然后下载下来，在master上新建一个分支5.0.0-beta。方便以后对源码的注解。（项目下载很慢可以拉个shi）
- 导入项目源码到IDEA。 (maven下载包的速度相当慢，可以去跑个步)
- 编译源码项目编译前烧个香，会有各种错误，需要坚持，坚持，在坚持。坚持不住就放弃（开玩笑，看在money的面子上也不能放弃）。
- 项目根目录执行命令： mvn clean install -DskipTests -Dmaven.javadoc.skip=true -Drat.skip=true。 （当然你有更好的编译命令，你也可以尝试，反正错了，你还会回来的）

注：下载项目源码是为了进步学习shardingsphere原理 (废话)。

2. 实战项目准备

自己结合了 shardingsphere-jdbc , mybatis, springboot2 对shardingsphere进行实战训练。关于springboot配置 shardingsphere的时候遇到的一些问题可以本节的后面的问题记录。

实战项目目录： https://github.com/nydia/JavaAdvancedTrain/tree/main/mysqlShardingSphere-opensource/shardingSphere-jdbc

## 分库分表

1. mysql准备
  - 准备数据库 shardingsphere_ds_0 和  shardingsphere_ds_1。
  - 订单表 t_order_0(order_id,user_id)
  - 订单表 t_order_1(order_id,user_id)
  - 订单项表 t_order_item_0 (order_item_id, order_id, user_id)
  - 订单项表 t_order_item_1 (order_item_id, order_id, user_id)
  - 字段user_id用于模2分库算法。字段order_id用于模2分表算法。

2. 此场景下的配置：
```yaml
server.port=8081

spring.shardingsphere.datasource.names=ds-0,ds-1

spring.shardingsphere.datasource.ds-0.jdbc-url=jdbc:mysql://localhost:3307/shardingsphere_ds_0?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8
spring.shardingsphere.datasource.ds-0.type=com.zaxxer.hikari.HikariDataSource
spring.shardingsphere.datasource.ds-0.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.ds-0.username=root
spring.shardingsphere.datasource.ds-0.password=123456

spring.shardingsphere.datasource.ds-1.jdbc-url=jdbc:mysql://localhost:3307/shardingsphere_ds_1?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8
spring.shardingsphere.datasource.ds-1.type=com.zaxxer.hikari.HikariDataSource
spring.shardingsphere.datasource.ds-1.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.ds-1.username=root
spring.shardingsphere.datasource.ds-1.password=123456

spring.shardingsphere.rules.sharding.default-database-strategy.standard.sharding-column=user_id
spring.shardingsphere.rules.sharding.default-database-strategy.standard.sharding-algorithm-name=database-inline
spring.shardingsphere.rules.sharding.binding-tables=t_order

# 分库不分表
# spring.shardingsphere.rules.sharding.tables.t_order.actual-data-nodes=ds-$->{0..1}.t_order
# 分库分表
spring.shardingsphere.rules.sharding.tables.t_order.actual-data-nodes=ds-$->{0..1}.t_order_$->{0..1}
# 分表策略
spring.shardingsphere.rules.sharding.tables.t_order.table-strategy.standard.sharding-column=order_id
spring.shardingsphere.rules.sharding.tables.t_order.table-strategy.standard.sharding-algorithm-name=t-order-inline

spring.shardingsphere.rules.sharding.tables.t_order.key-generate-strategy.column=order_id
spring.shardingsphere.rules.sharding.tables.t_order.key-generate-strategy.key-generator-name=snowflake

# 分库算法，对user_id模2算法分为两个库
spring.shardingsphere.rules.sharding.sharding-algorithms.database-inline.type=INLINE
spring.shardingsphere.rules.sharding.sharding-algorithms.database-inline.props.algorithm-expression=ds-$->{user_id % 2}
# 分表算法，对order_id模2算法分为两个表
spring.shardingsphere.rules.sharding.sharding-algorithms.t-order-inline.type=INLINE
spring.shardingsphere.rules.sharding.sharding-algorithms.t-order-inline.props.algorithm-expression=t_order_$->{order_id % 2}

# 订单id，order_id生成策略，雪花算法
spring.shardingsphere.rules.sharding.key-generators.snowflake.type=SNOWFLAKE
spring.shardingsphere.rules.sharding.key-generators.snowflake.props.worker-id=123

```

3. 准备数据： 向t_order插入10条数据， user_id分别为从1到10 ，order_id采用雪花算法生成。
  - 结果： user_id 为2，4，6，8，10的数据插入到 shardingsphere_ds_0库， user_id 为1，3，5，7，9的数据插入到 shardingsphere_ds_1库。

## 读写分离

1. 数据库表沿用上面的sql
2. 配置准备
```yaml
## 分库分表沿用上面的配置
## 读写分离
spring.shardingsphere.rules.readwrite-splitting.data-sources.ds-0.write-data-source-name=ds-0
spring.shardingsphere.rules.readwrite-splitting.data-sources.ds-0.read-data-source-names=ds-1
spring.shardingsphere.rules.readwrite-splitting.data-sources.ds-1.write-data-source-name=ds-0
spring.shardingsphere.rules.readwrite-splitting.data-sources.ds-1.read-data-source-names=ds-1

```  
3. 准备数据： 向t_order插入10条数据， user_id分别为从1到10 ，order_id采用雪花算法生成。
  - 结果： user_id 为1，3，5，7，9的数据插入到 shardingsphere_ds_1库的t_order_1， user_id 为2，4，6，8，10的数据插入到 shardingsphere_ds_1库的t_order_0。

## 数据加密

1. 数据库表沿用上面的sql
2. 配置准备
```yaml
## 分库分表和读写分离沿用上面的配置
## 数据加解密
spring.shardingsphere.rules.encrypt.encryptors.order-no-encryptor.type=AES
spring.shardingsphere.rules.encrypt.encryptors.order-no-encryptor.props.aes-key-value=123456abc
spring.shardingsphere.rules.encrypt.tables.t_order.columns.order_no.cipher-column=order_no
spring.shardingsphere.rules.encrypt.tables.t_order.columns.order_no.encryptor-name=order-no-encryptor
spring.shardingsphere.props.query-with-cipher-comlum=true
spring.shardingsphere.props.sql-show=true
```

3. 准备数据： 向t_order插入10条数据， user_id分别为从1到10 ，order_id采用雪花算法生成。(对订单号 order_no 加密)
  - 结果：订单号插入数据库是aes加密后的密文

## 知识点总结
1. binding-tables 和 broadcast-tables 概念，待look look
2. 读写分离和分库分表有优先级么？
3. 通过实战结合源码，对shardingsphere-jdbc的基础知识，概念有个认识。要进一步通过example对源码进行深入分析。

## 错误记录

1. 问题1：
  - 错误： Reason: Canonical names should be kebab-case ('-' separated), lowercase alpha-numeric characters and must start with a letter
  - 原因：springboot2.0配置里面不用驼峰规则，可以用 - 代替
2. 问题2：
  - 错误： Inline sharding algorithm expression cannot be null.
  - 解决：
    - database_inline 改为 database-inline
