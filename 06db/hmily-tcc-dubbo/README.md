## 第九周作业  第7题

### 题目说明

7.（必做）结合 dubbo+hmily，实现一个 TCC 外汇交易处理，代码提交到 GitHub:

用户 A 的美元账户和人民币账户都在 A 库，使用 1 美元兑换 7 人民币 ;
用户 B 的美元账户和人民币账户都在 B 库，使用 7 人民币兑换 1 美元 ;
设计账户表，冻结资产表，实现上述两个本地事务的分布式事务。

### 思路

1. 启动两个账户系统，一个单独的转账系统。两个转账系统分表连接A库和B库
2. 两个账户系统共用相同的 注册接口（通过group名称来区分A账户和B账户）
3. 在结算系统里面分表调用A账户的转账任务和B账户的转账任务

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
- hmily-tcc-dubbo-account-a 账户A，连接A库，包含账户A表，冻结表A。提供账户服务A：accountServiceA 
- hmily-tcc-dubbo-account-b 账户B，连接B库，包含账户B表，冻结表B。提供账户服务B：accountServiceB
- hmily-tcc-dubbo-account-common 账户和冻结公用的实体，mapper，和service接口
- hmily-tcc-dubbo-account-settle 结算模块：负责转账的发起,通过dubbo分别调用账户A和账号B服务。账户A和账号B的转账由结算中心发起，在账户A系统和账户B系统里面处理，并采用hmily的tcc协议模式保证分布式事务的一致性


流程说明：

1. 结算发起转账服务：

~~~
@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTest {

    @Autowired
    private TransferService transferService;

    /**
     *  用户 A 的美元账户和人民币账户都在 A 库，使用 1 美元兑换 7 人民币
     */
    @Test
    public void transferA() {

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setOutAmount(new BigDecimal(-1));
        transferDTO.setOutAcctTye("USD");
        transferDTO.setInAmount(new BigDecimal(7));
        transferDTO.setInAcctTye("RMB");
        transferDTO.setUerId("10000");
        System.out.println(transferService.transferA(transferDTO));

    }

    /**
     * 用户 B 的美元账户和人民币账户都在 B 库，使用 7 人民币兑换 1 美元 ;
     */
    @Test
    public void transferB() {

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setOutAmount(new BigDecimal(-7));
        transferDTO.setOutAcctTye("RMB");
        transferDTO.setInAmount(new BigDecimal(1));
        transferDTO.setInAcctTye("USD");
        transferDTO.setUerId("20000");
        System.out.println(transferService.transferB(transferDTO));

    }

}
~~~

通过注册的Dubbo接口调用账户系统：

~~~

<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://code.alibabatech.com/schema/dubbo
       http://code.alibabatech.com/schema/dubbo/dubbo.xsd">

    <dubbo:application name="order_service"/>


    <dubbo:registry protocol="zookeeper" address="localhost:2181"/>

    <dubbo:protocol name="dubbo" port="20886"
                    server="netty" client="netty"
                    charset="UTF-8" threadpool="fixed" threads="500"
                    queues="0" buffer="8192" accepts="0" payload="8388608"/>

    <dubbo:reference timeout="500000000"
                     interface="org.dromara.hmily.demo.common.account.api.AccountService"
                     id="accountServiceA"
                     retries="0" check="false" actives="20" loadbalance="hmilyRandom" group="A"/>
    <dubbo:reference timeout="500000000"
                     interface="org.dromara.hmily.demo.common.account.api.AccountService"
                     id="accountServiceB"
                     retries="0" check="false" actives="20" loadbalance="hmilyRandom" group="B"/>
</beans>
~~~

账户系统做转账操作，成功就提交，失败回滚：

~~~
@Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean transfer(AccountDTO accountDTO) {
        int count =  accountMapper.update(accountDTO);
        if (count <= 0) {
            throw new HmilyRuntimeException("账户扣减异常！");
        }

        FreezeDTO freezeDTO = FreezeDTO.builder()
                .userId(accountDTO.getUserId())
                .accountType(accountDTO.getAccountType())
                .freezeAmt(new BigDecimal(accountDTO.getAmount().doubleValue()).abs())
                .build();
        count = freezeMapper.update(freezeDTO);
        if (count <= 0) {
            throw new HmilyRuntimeException("账户扣减异常！");
        }
        return true;
    }

    /**
     * Confirm boolean.
     *
     * @param accountDTO the account dto
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean confirm(AccountDTO accountDTO) {
        LOGGER.info("============dubbo tcc 执行确认付款接口===============");
        FreezeDTO freezeDTO = FreezeDTO.builder()
                .userId(accountDTO.getUserId())
                .accountType(accountDTO.getAccountType())
                .freezeAmt(new BigDecimal(accountDTO.getAmount().doubleValue()).abs())
                .build();
        freezeMapper.confirm(freezeDTO);
        final int i = confrimCount.incrementAndGet();
        LOGGER.info("调用了account confirm " + i + " 次");
        return Boolean.TRUE;
    }
    
    /**
     * Cancel boolean.
     *
     * @param accountDTO the account dto
     * @return the boolean
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean cancel(AccountDTO accountDTO) {
        LOGGER.info("============ dubbo tcc 执行取消付款接口===============");
        accountMapper.cancel(accountDTO);

        FreezeDTO freezeDTO = FreezeDTO.builder()
                .userId(accountDTO.getUserId())
                .accountType(accountDTO.getAccountType())
                .freezeAmt(new BigDecimal(accountDTO.getAmount().doubleValue()).abs())
                .build();
        freezeMapper.confirm(freezeDTO);

        return Boolean.TRUE;
    }
~~~