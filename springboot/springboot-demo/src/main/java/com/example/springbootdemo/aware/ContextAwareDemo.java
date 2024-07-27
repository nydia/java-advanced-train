package com.example.springbootdemo.aware;

import com.example.springbootdemo.service.ApiService;
import com.example.springbootdemo.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Auther: nydia_lhq@hotmail.com
 * @Date: 2024/5/26 00:42
 * @Description: ContextAwareDemo
 */
@Component
@Slf4j
public class ContextAwareDemo implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

        getValue();
    }

    public void getValue(){
        String[] beanNames = applicationContext.getBeanNamesForType(ApiService.class);
        for(String beanName : beanNames){
            String value = applicationContext.getBean(beanName,ApiService.class).getApiId();
            LogUtil.info(log, String.format("method result value : %s", value));
        }

    }
}
