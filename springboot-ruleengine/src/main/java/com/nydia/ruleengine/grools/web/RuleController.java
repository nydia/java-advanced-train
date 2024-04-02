package com.nydia.ruleengine.grools.web;

import com.nydia.ruleengine.grools.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @RequestMapping("/test")
    public String creditCardApply() {
        return "";
    }
}