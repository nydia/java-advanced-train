# 项目介绍
springcloud-config
组件：springcloud-config server, springcloud-config client, rabbitmq, springcloud actuator

# SpringCloud http://localhost:8080/h2-console

## H2 http://localhost:8080/h2-consol*e

# server
- http://localhost:8888/conf/config-info-dev.yaml

# client

# Spring Cloud Actuator 热刷新*

刷新客户端：
- 获取参数 curl -X GET http://localhost:8082/getdata
- 刷新配置 curl -X POST  http://localhost:8082/actuator/refresh

弊端：每个客户端都需要刷新数据