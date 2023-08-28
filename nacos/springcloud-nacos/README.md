## 项目介绍
1. 测试feign的重试机制
2. 测试sentinel
3. 测试不通springboot版本下的nacos的服务发现和配置关联
    springboot 2.5.5 和 2.6.13 下nacos的配置不同 

### 项目组件说明
1. springclouddemo-client 2.5.5
2. springclouddemo-server 2.5.5
2. springclouddemo-server2 2.5.5
3. springclouddemo-server3 2.6.13

## 错误

1. nacos报错：java.lang.IllegalArgumentException: Param ‘serviceName‘ is illegal, serviceName is blank

- 加上注解

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bootstrap</artifactId>
</dependency>
```


## 测试
curl http://127.0.0.1:8081/getdata
curl http://127.0.0.1:8090/getdata