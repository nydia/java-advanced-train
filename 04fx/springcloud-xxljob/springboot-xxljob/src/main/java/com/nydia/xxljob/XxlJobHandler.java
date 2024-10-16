package com.nydia.xxljob;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

@Component
public class XxlJobHandler {

    @XxlJob("commonJobHandler")
    public void execute() {
        try {
            // 任务逻辑
            System.out.println("执行我的任务");

            // 获取参数
            String param = XxlJobHelper.getJobParam();
            System.out.println("接收調度中心参数: " + param);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}