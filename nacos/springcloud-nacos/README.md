## 项目介绍
1. 测试feign的重试机制
2. 测试sentinel

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