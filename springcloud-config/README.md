# 项目介绍
springcloud-config

# SpringCloud http://localhost:8080/h2-console

## H2 http://localhost:8080/h2-console

# server
- http://localhost:8888/conf/config-info-dev.yaml

# client

# Spring Cloud Bus热刷新

刷新服务端：
- curl -X POST http://localhost:8888/actuator/busrefresh

刷新客户端：
- 测试配置 curl -X GET http://localhost:8082/getdata
- 刷新配置 curl -X POST  http://localhost:8082/actuator/refresh