# gateway

## H2 

- SpringCloud http://localhost:8082/h2-console

- H2 http://localhost:8080/h2-conso*le

## Spring Cloud Gateway

### base request

curl http://localhost:8082/actuator/gateway/routes
curl http://localhost:8082/route1/healthCheck

### Route predicate

#### RoutePredicate1Controller

curl http://localhost:8082/base-api/routePredicate1/msg
curl http://localhost:8082/base-api/routePredicate1/afterRoute
curl http://localhost:8082/base-api/routePredicate1/beforeRoute
curl http://localhost:8082/base-api/routePredicate1/betweenRoute
curl http://localhost:8082/base-api/routePredicate1/cookieRoute --cookie "mycookie=testval"
curl http://localhost:8082/base-api/routePredicate1/headerRoute -H "token:T10000"
curl http://localhost:8082/base-api/routePredicate1/methodRoute
curl -X POST -d '{}' http://localhost:8082/base-api/routePredicate1/methodRoute
curl -X POST -d '{}' http://localhost:8082/routePredicate1/methodRoute

#### RoutePredicate2Controller

### Route Filter

#### RouteFilter1Controller

#### RouteFilter2Controller

### token

curl http://localhost:8082/base-api/route1/msg -H "Authorization:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjQ5ODc3NDl9.ESd_8rz90EMLbatFpAIGLMrcVBgg7IE8wjhTEalthkc"

## gateway流程
jwt鉴权 -> 路由route