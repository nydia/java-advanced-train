package org.dromara.hmily.demo.dubbo.order.service.impl;

import com.alibaba.fastjson.JSON;
import org.dromara.hmily.demo.common.account.api.AccountService;
import org.dromara.hmily.demo.common.account.entity.AccountDO;
import org.dromara.hmily.demo.common.transter.dto.TransferDTO;
import org.dromara.hmily.demo.dubbo.order.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 转账Service
 */
@Service
public class TransferServiceImpl implements TransferService{

    @Resource(name = "accountServiceA")
    private AccountService accountServiceA;

    @Resource(name = "accountServiceB")
    private AccountService accountServiceB;

    @Override
    public AccountDO findAcct(TransferDTO transferDTO) {
        AccountDO accountDO_A = accountServiceA.findByUserIdAndAccountType("10000", "RMB");
        AccountDO accountDO_B = accountServiceB.findByUserIdAndAccountType("20000", "USD");
        System.out.println(JSON.toJSONString(accountDO_A));
        System.out.println(JSON.toJSONString(accountDO_B));
        return accountDO_A;
    }

    @Override
    public boolean transfer(TransferDTO transferDTO) {
        AccountDO accountDO_A = accountServiceA.findByUserIdAndAccountType(transferDTO.getFromUserId(), transferDTO.getAcctTye());
        AccountDO accountDO_B = accountServiceB.findByUserIdAndAccountType(transferDTO.getToUserId(), transferDTO.getAcctTye());
        System.out.println(JSON.toJSONString(accountDO_A));
        System.out.println(JSON.toJSONString(accountDO_B));
        return accountDO_A;
    }


}
