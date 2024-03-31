package com.nydia.ruleengine.grools.service;

import com.nydia.ruleengine.grools.entity.People;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleService {

    @Autowired
    private KieBase kieBase;

    public void test(People people) {
        KieSession session = kieBase.newKieSession();     //获取KieSession
        session.insert(session);                          //插入
        session.fireAllRules();                           //执行规则
        session.dispose();                                //释放资源
    }
}