# 说明文档
https://gitee.com/nydia/mdfile/techs/框架/SpringBoot/architect-springboot-扩展接口-SpringBoot启动时自动执行指定方法的7种方法.md

# 测试SpringBoot的配置加载顺序
测试类： com.example.springbootdemo.order.TestSpringConfigLoadOrder

spring的扩展点加载顺序：
ApplicationContextInitializer # initialize
BeanFactoryAware # setBeanFactory
BeanFactoryAware # setBeanName
BeanFactoryAware # setBeanFactory
ApplicationContextAware # setApplicationContext
post-construct
InitializingBean # afterPropertiesSet
BeanDefinitionRegistryPostProcessor # postProcessBeanDefinitionRegistry
         LiveReload server is running on port 35729
SmartInitializingSingleton #
         Tomcat started on port(s): 8085 (http) with context path ''
Lifecycle # isRunning
Lifecycle # start
ApplicationListener # onApplicationEvent
         Started SpringbootDemoApplication2 in 4.099 seconds (JVM
CommandLineRunner # run