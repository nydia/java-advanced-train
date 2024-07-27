package com.example.springbootdemo.order;

import com.example.springbootdemo.pojo.Student;
import com.example.springbootdemo.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.PostConstruct;


/**
 * spring的扩展点加载顺序：
 * ApplicationContextInitializer # initialize
 * BeanFactoryAware # setBeanFactory
 * BeanFactoryAware # setBeanName
 * BeanFactoryAware # setBeanFactory
 * ApplicationContextAware # setApplicationContext
 * post-construct
 * InitializingBean # afterPropertiesSet
 * BeanDefinitionRegistryPostProcessor # postProcessBeanDefinitionRegistry
 *          LiveReload server is running on port 35729
 * SmartInitializingSingleton #
 *          Tomcat started on port(s): 8085 (http) with context path ''
 * Lifecycle # isRunning
 * Lifecycle # start
 * ApplicationListener # onApplicationEvent
 *          Started SpringbootDemoApplication2 in 4.099 seconds (JVM
 * CommandLineRunner # run
 */
@Slf4j
@Configuration
public class TestSpringConfigLoadOrder implements
        ApplicationContextAware,
        BeanFactoryAware,
        InitializingBean,
        SmartLifecycle,
        BeanNameAware,
        ApplicationListener<ContextRefreshedEvent>,
        CommandLineRunner,
        SmartInitializingSingleton,
        ApplicationContextInitializer,
        BeanDefinitionRegistryPostProcessor {

    //ApplicationContextInitializer：需要加上让这个扩展生效的配置
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        LogUtil.info(log, "ApplicationContextInitializer # initialize");
    }

    //InitializingBean
    @Override
    public void afterPropertiesSet() throws Exception {
        LogUtil.info(log, "InitializingBean # afterPropertiesSet");
    }

    //ApplicationContextAware
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LogUtil.info(log, "ApplicationContextAware # setApplicationContext");
    }

    //@PostConstruct
    @PostConstruct
    public void postConstruct() {
        LogUtil.info(log, "post-construct");
    }

    //BeanFactoryAware
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        LogUtil.info(log, "BeanFactoryAware # setBeanFactory");
    }

    //BeanFactoryAware
    @Override
    public void setBeanName(String s) {
        LogUtil.info(log, "BeanFactoryAware # setBeanName");
    }

    //SmartInitializingSingleton
    @Override
    public void afterSingletonsInstantiated() {
        LogUtil.info(log, "SmartInitializingSingleton # afterSingletonsInstantiated");
    }

    //CommandLineRunner
    @Override
    public void run(String... args) throws Exception {
        LogUtil.info(log, "CommandLineRunner # run");
    }

    //ApplicationListener
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LogUtil.info(log, "ApplicationListener # onApplicationEvent");
    }

    //Lifecycle
    @Override
    public void start() {
        LogUtil.info(log, "Lifecycle # start");
    }

    //Lifecycle
    @Override
    public void stop() {
        LogUtil.info(log, "Lifecycle # stop");
    }

    //Lifecycle
    @Override
    public boolean isRunning() {
        LogUtil.info(log, "Lifecycle # isRunning");
        return false;
    }

    //BeanDefinitionRegistryPostProcessor # postProcessBeanDefinitionRegistry
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        LogUtil.info(log, "BeanDefinitionRegistryPostProcessor # postProcessBeanDefinitionRegistry");
        //手工定义一个beanDefinition实例
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        //给beanDefinition填充属性
        beanDefinition.setBeanClass(Student.class);
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        PropertyValue propertyValue1 = new PropertyValue("name", "张三");
        PropertyValue propertyValue2 = new PropertyValue("age", "11");
        propertyValues.addPropertyValue(propertyValue1);
        propertyValues.addPropertyValue(propertyValue2);
        beanDefinition.setPropertyValues(propertyValues);
        //注册手工定义的beanDefinition
        registry.registerBeanDefinition("student", beanDefinition);
    }

    //BeanDefinitionRegistryPostProcessor # postProcessBeanFactory
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        LogUtil.info(log, "-----------postProcessBeanFactory start------------");
        //根据类名取出手工注册的beanDefinition
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("student");
        System.out.println(beanDefinition.getBeanClassName());
        //根据类从容器中取出手工注册的beanDefinition所描述的实例bean
        Student student = beanFactory.getBean(Student.class);
        LogUtil.info(log, student.getName());
        LogUtil.info(log, student.getAge());
        LogUtil.info(log, "-----------postProcessBeanFactory end------------");
    }
}