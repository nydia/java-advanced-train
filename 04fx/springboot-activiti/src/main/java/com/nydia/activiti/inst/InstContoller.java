package com.nydia.activiti.inst;

import com.nydia.activiti.constants.CommonConstant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程实例
 * @author lvhq
 * @date 2025.01.14
 */
@Slf4j
@RestController
@RequestMapping(value = "inst")
public class InstContoller {

    @Resource
    private RuntimeService runtimeService;


    @RequestMapping(value = "create")
    public String create() {
        // 获取流程引擎实例
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 获取运行服务组件
        RuntimeService runtimeService = engine.getRuntimeService();

        //TODO
        String processDefinitionId = "";

        String applyAssignee = "1";//申请人
        String approvalAssignee = "1";//审批人
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("apply_id_assignee", applyAssignee);
        paramMap.put("approval_id_assignee", approvalAssignee);
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, paramMap);
        log.info("流程实例id: {}", processInstance.getId());
        return CommonConstant.success;
    }

}
