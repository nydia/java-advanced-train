package com.nydia.xxljob;

import java.util.Date;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

/**
 * @Author: nydia
 * @Date: 2024-6-22 9:26
 * @Description:
 */
@RestController
public class XxlJobController {

    private String xxlJobAdminUrl  = "http://127.0.0.1:8080/xxl-job-admin";
    private String xxlJobAdminUser  = "admin";
    private String xxlJobAdminPass  = "123456";

    @RequestMapping(value = "/pageList",method = RequestMethod.GET)
    public Object pageList() throws IOException {
        //int jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author
        JSONObject test=new JSONObject();
        test.put("length",10);
        XxlJobUtil.login(xxlJobAdminUrl,xxlJobAdminUser,xxlJobAdminPass);
        JSONObject response = XxlJobUtil.pageList(xxlJobAdminUrl, test);
        return  response.get("data");
    }

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public void add() throws IOException {
        XxlJobInfo xxlJobInfo=new XxlJobInfo();
        xxlJobInfo.setJobCron("0/5 * * * * ?");
        xxlJobInfo.setJobGroup(3);
        xxlJobInfo.setJobDesc("我来试试");
        xxlJobInfo.setAddTime(new Date());
        xxlJobInfo.setUpdateTime(new Date());
        xxlJobInfo.setAuthor("nydia");
        xxlJobInfo.setAlarmEmail("nydia_lvhq@sina.cn");
        xxlJobInfo.setScheduleType("CRON");
        xxlJobInfo.setScheduleConf("0/5 * * * * ?");
        xxlJobInfo.setMisfireStrategy("DO_NOTHING");
        xxlJobInfo.setExecutorRouteStrategy("FIRST");
        xxlJobInfo.setExecutorHandler("clockInJobHandler");
        xxlJobInfo.setExecutorParam("att");
        xxlJobInfo.setExecutorBlockStrategy("SERIAL_EXECUTION");
        xxlJobInfo.setExecutorTimeout(0);
        xxlJobInfo.setExecutorFailRetryCount(1);
        xxlJobInfo.setGlueType("BEAN");
        xxlJobInfo.setGlueSource("");
        xxlJobInfo.setGlueRemark("GLUE代码初始化");
        xxlJobInfo.setGlueUpdatetime(new Date());
        JSONObject test = (JSONObject) JSONObject.toJSON(xxlJobInfo);
        XxlJobUtil.login(xxlJobAdminUrl,xxlJobAdminUser,xxlJobAdminPass);
        JSONObject response = XxlJobUtil.addJob(xxlJobAdminUrl, test);
        if (response.containsKey("code") && 200 == (Integer) response.get("code")) {
            System.out.println("新增成功");
        } else {
            System.out.println("新增失败");
        }

    }

    @RequestMapping(value = "/stop/{jobId}",method = RequestMethod.GET)
    public void stop(@PathVariable("jobId") Integer jobId) throws IOException {

        XxlJobUtil.login(xxlJobAdminUrl,xxlJobAdminUser,xxlJobAdminPass);
        JSONObject response = XxlJobUtil.stopJob(xxlJobAdminUrl, jobId);
        if (response.containsKey("code") && 200 == (Integer) response.get("code")) {
            System.out.println("任务停止成功");
        } else {
            System.out.println("任务停止失败");
        }
    }

    @RequestMapping(value = "/delete/{jobId}",method = RequestMethod.GET)
    public void delete(@PathVariable("jobId") Integer jobId) throws IOException {

        XxlJobUtil.login(xxlJobAdminUrl,xxlJobAdminUser,xxlJobAdminPass);
        JSONObject response = XxlJobUtil.deleteJob(xxlJobAdminUrl, jobId);
        if (response.containsKey("code") && 200 == (Integer) response.get("code")) {
            System.out.println("任务移除成功");
        } else {
            System.out.println("任务移除失败");
        }
    }


    @RequestMapping(value = "/start/{jobId}",method = RequestMethod.GET)
    public void start(@PathVariable("jobId") Integer jobId) throws IOException {

        XxlJobUtil.login(xxlJobAdminUrl,xxlJobAdminUser,xxlJobAdminPass);
        JSONObject response = XxlJobUtil.startJob(xxlJobAdminUrl, jobId);
        if (response.containsKey("code") && 200 == (Integer) response.get("code")) {
            System.out.println("任务启动成功");
        } else {
            System.out.println("任务启动失败");
        }
    }
    
}