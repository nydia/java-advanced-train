package com.example.springbootdemo.aware;

import com.example.springbootdemo.service.ApiService;
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
public class ContextAwareDemo implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void getValue(){
        String[] beanNames = applicationContext.getBeanNamesForType(ApiService.class);
        for(String beanName : beanNames){
            String value = applicationContext.getBean(beanName,ApiService.class).getApiId();
            System.out.println(String.format("method result value : %s", value));
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

        getValue();
    }


}
