package com.nydia.activiti.model;

import com.nydia.activiti.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  流程模型
 * @author lvhq
 * @date 2025.01.14
 */
@RestController
@RequestMapping(value = "model")
public class ModelController {

    Logger log = LoggerFactory.getLogger(ModelController.class);

    @RequestMapping(value = "deploy")
    public String deploy(){
        // 获取默认的ProcessEngine实例
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("bpm/admin1.bpmn20.xml")
                .deploy();
        log.info("模型发布后的id: {}", deploy.getId());
        return CommonConstant.success;
    }

}
