# 项目介绍
springcloud-config-eureka
组件：springcloud-eureka , springcloud-config server, springcloud-config client, git, rabbitmq, springcloud actuator


# Eureka
http://127.0.0.1:8761/



# H2

## H2 http://localhost:8080/h2-console



# config-server
- http://localhost:8888/conf/config-info-dev.yaml


## Spring Cloud Bus热刷新

刷新服务端：
- 服务端刷新配置 curl -X POST http://localhost:8888/actuator/busrefresh


刷新客户端：
- 获取参数 curl -X GET http://localhost:8082/getdata
- 刷新配置 curl -X POST  http://localhost:8082/actuator/refresh



# client




