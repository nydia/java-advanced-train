-- CREATE SCHEMA shardingsphere_ds_1;

--  t_order
drop table if exists t_order_0;
CREATE TABLE `t_order_0` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '订单编号',
  `amount` decimal(10,2) DEFAULT '0.00' COMMENT '金额',
  `status` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单';

drop table if exists t_order_1;
CREATE TABLE `t_order_1` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '订单编号',
  `amount` decimal(10,2) DEFAULT '0.00' COMMENT '金额',
  `status` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '状态',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单';


--  t_order_item

drop table if exists t_order_item_0;
CREATE TABLE `t_order_item_0` (
  `order_item_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单项id',
  `order_id` bigint(64) NOT NULL COMMENT '订单id',
  `good_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `good_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '商品名称',
  `price` decimal(10,2) DEFAULT 0 COMMENT '状态',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  PRIMARY KEY (`order_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单项表';

drop table if exists t_order_item_1;
CREATE TABLE `t_order_item_1` (
  `order_item_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单项id',
  `order_id` bigint(64) NOT NULL COMMENT '订单id',
  `good_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `good_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '商品名称',
  `price` decimal(10,2) DEFAULT 0 COMMENT '状态',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  PRIMARY KEY (`order_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单项表';