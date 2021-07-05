## 第九周作业  第7题

### 题目说明

7.（必做）结合 dubbo+hmily，实现一个 TCC 外汇交易处理，代码提交到 GitHub:

用户 A 的美元账户和人民币账户都在 A 库，使用 1 美元兑换 7 人民币 ;
用户 B 的美元账户和人民币账户都在 B 库，使用 7 人民币兑换 1 美元 ;
设计账户表，冻结资产表，实现上述两个本地事务的分布式事务。

### 准备

1. 启动zookeer服务，端口2181
2. 初始化sql

~~~sql
-- create schema hmily;
-- create schema demo_ds_0;
-- create schema demo_ds_1;


-- demo_ds_0 表初始化

USE `demo_ds_0`;

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(128) NOT NULL,
  `account_no` varchar(128) DEFAULT '0' COMMENT '账户号',
  `account_type` varchar(20) DEFAULT '0' COMMENT '账户类型 RMB-人民币 / USD-美元',
  `balance` decimal(10,0) DEFAULT 0 COMMENT '用户余额',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

insert  into `account`(`id`,`user_id`,`account_no`,`account_type`,`balance`,`create_time`,`update_time`) values (1,'10000','10000','RMB', 10000000,'2017-09-18 14:54:22',NULL);
insert  into `account`(`id`,`user_id`,`account_no`,`account_type`,`balance`,`create_time`,`update_time`) values (2,'10000','20000','USD', 10000000,'2017-09-18 14:54:22',NULL);

DROP TABLE IF EXISTS `account_freeze`;

CREATE TABLE `account_freeze` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一性id',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '用户id号',
  `account_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '账户号',
  `account_type` varchar(20) DEFAULT '0' COMMENT '账户类型 RMB-人民币 / USD-美元',
  `freeze_amt` decimal(10,0) DEFAULT 0 COMMENT '冻结金额',
  `tran_no` varchar(255) COLLATE utf8mb4_bin DEFAULT '' COMMENT '交易流水号',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='冻结资产表';

insert into `account_freeze` (`id`, `user_id`, `account_no`, `account_type`, `freeze_amt`, `tran_no`, `create_time`, `update_time`) values('1','10000','10000','RMB','0','','2021-07-05 17:36:34','2021-07-05 17:36:34');
insert into `account_freeze` (`id`, `user_id`, `account_no`, `account_type`, `freeze_amt`, `tran_no`, `create_time`, `update_time`) values('2','10000','20000','USD','0','','2021-07-05 17:36:40','2021-07-05 17:36:40');

-- demo_ds_1 表初始化

USE `demo_ds_1`;

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`user_id` varchar(128) NOT NULL,
`account_no` varchar(128) DEFAULT '0' COMMENT '账户号',
`account_type` varchar(20) DEFAULT '0' COMMENT '账户类型 RMB-人民币 / USD-美元',
`balance` decimal(10,0) DEFAULT 0 COMMENT '用户余额',
`create_time` datetime NOT NULL,
`update_time` datetime DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

insert  into `account`(`id`,`user_id`,`account_no`,`account_type`,`balance`,`create_time`,`update_time`) values (1,'20000','10000','RMB', 10000000,'2017-09-18 14:54:22',NULL);
insert  into `account`(`id`,`user_id`,`account_no`,`account_type`,`balance`,`create_time`,`update_time`) values (2,'10000','20000','USD', 10000000,'2017-09-18 14:54:22',NULL);

DROP TABLE IF EXISTS `account_freeze`;

CREATE TABLE `account_freeze` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一性id',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '用户id号',
  `account_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '账户号',
  `account_type` varchar(20) DEFAULT '0' COMMENT '账户类型 RMB-人民币 / USD-美元',
  `freeze_amt` decimal(10,0) DEFAULT 0 COMMENT '冻结金额',
  `tran_no` varchar(255) COLLATE utf8mb4_bin DEFAULT '' COMMENT '交易流水号',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='冻结资产表';

insert into `account_freeze` (`id`, `user_id`, `account_no`, `account_type`, `freeze_amt`, `tran_no`, `create_time`, `update_time`) values('1','20000','10000','RMB','0','','2021-07-05 17:36:34','2021-07-05 17:36:34');
insert into `account_freeze` (`id`, `user_id`, `account_no`, `account_type`, `freeze_amt`, `tran_no`, `create_time`, `update_time`) values('2','20000','20000','USD','0','','2021-07-05 17:36:40','2021-07-05 17:36:40');
~~~

### 详解

项目分模块：
- hmily-tcc-dubbo-account-a 账户A，连接A库，包含账户A表，冻结表
- hmily-tcc-dubbo-account-b 账户B，连接B库，包含账户B表，冻结表B
- hmily-tcc-dubbo-account-common 账户和冻结公用的实体，mapper，和service接口
- hmily-tcc-dubbo-account-settle 结算模块：负责转账的发起


流程说明：
1 .结算模块通过dubbo接口分别调用账户A模块，和账户B模块
2. 账户A和账户B系统内部分别采用hmily的事务提交或者回滚
