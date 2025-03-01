package com.nydia.activiti.task;

import com.nydia.activiti.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程任务
 *
 * @author lvhq
 * @date 2025.01.14
 */
@Slf4j
@RestController
@RequestMapping(value = "task")
public class TaskController {


    //创建任务
    @RequestMapping(value = "create")
    public String create(
            @RequestParam("procId") String procId//实例id
    ) {
        // 获取流程引擎实例
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 获取任务服务组件
        TaskService taskService = engine.getTaskService();

        return CommonConstant.success;
    }

    //任务详情
    @RequestMapping(value = "detail")
    public String detail(
            @RequestParam("procId") String procId,//实例id
            @RequestParam("taskId") String taskId//任务id
    ) {
        // 获取流程引擎实例
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 获取任务服务组件
        TaskService taskService = engine.getTaskService();

        return CommonConstant.success;
    }

    //加签
    @RequestMapping(value = "resolveTask")
    public String resolveTask(
            @RequestParam("procId") String procId,//实例id
            @RequestParam("taskId") String taskId//任务id
    ) {
        // 获取流程引擎实例
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 获取任务服务组件
        TaskService taskService = engine.getTaskService();

        return CommonConstant.success;
    }

    //转签
    @RequestMapping(value = "trunTask")
    public String trunTask(
            @RequestParam("procId") String procId,//实例id
            @RequestParam("taskId") String taskId//任务id
    ) {
        // 获取流程引擎实例
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 获取任务服务组件
        TaskService taskService = engine.getTaskService();

        return CommonConstant.success;
    }

    //审批
    @RequestMapping(value = "approve")
    public String approve(
            @RequestParam("procId") String procId,//实例id
            @RequestParam("taskId") String taskId,//任务id
            @RequestParam("approveResult") String approveResult,//审批结果
            @RequestParam("comment") String comment //备注
    ) {
        // 获取流程引擎实例
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 获取任务服务组件
        TaskService taskService = engine.getTaskService();

        // 查找任务
        Task task = taskService.createTaskQuery().processInstanceId(procId).singleResult();
        // 调用complete方法完成任务，传入参数
        Map<String, Object> vars = new HashMap<>();
        vars.put("days", 2);
        // 设置临时的参数
        Map<String, Object> vars2 = new HashMap<>();
        vars2.put("temp", "temp var");
        taskService.complete(task.getId(), vars, vars2);
        // 再次查找任务
        task = taskService.createTaskQuery().processInstanceId(procId).singleResult();

        return CommonConstant.success;
    }


}
