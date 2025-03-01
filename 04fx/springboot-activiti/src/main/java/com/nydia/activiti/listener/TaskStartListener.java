package com.nydia.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2025/1/23 21:07
 * @Description: TaskStartListener
 */
@Component
public class TaskStartListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {

    }
}
