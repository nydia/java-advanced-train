# gateway

## gateway流程
jwt鉴权 -> 路由route

## H2 

- SpringCloud http://localhost:8082/h2-console

- H2 http://localhost:8080/h2-conso*le

## Spring Cloud Gateway

- curl http://localhost:8082/actuator/gateway/routes
- curl http://localhost:8082/base-api/route1/msg
- curl http://localhost:8082/base-api/route1/healthCheck
- curl http://localhost:8082/base-api/route2/msg


curl http://localhost:8082/base-api/route1/msg -H "Authorization:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjQ5ODc3NDl9.ESd_8rz90EMLbatFpAIGLMrcVBgg7IE8wjhTEalthkc"


curl http://localhost:8082/base-api/route1/msg -X POST -H "Authorization:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjQ5ODc3NDl9.ESd_8rz90EMLbatFpAIGLMrcVBgg7IE8wjhTEalthkc"