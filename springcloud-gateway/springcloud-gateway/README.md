# gateway
将spring的日志级别开到debug就可以看到完整的日志了。

## gateway流程
jwt鉴权 -> 路由route

## H2

- SpringCloud http://localhost:8082/h2-console

- H2 http://localhost:8080/h2-conso*le

## Spring Cloud Gateway

### base request

- curl http://localhost:8082/actuator/gateway/routes
- curl http://localhost:8082/base-api/route1/msg
- curl http://localhost:8082/base-api/route1/healthCheck
- curl http://localhost:8082/base-api/route2/msg

### Route predicate

#### RoutePredicate1Controller

curl http://localhost:8082/base-api/routePredicate1/afterRoute
curl http://localhost:8082/base-api/routePredicate1/beforeRoute
curl http://localhost:8082/base-api/routePredicate1/betweenRoute
curl http://localhost:8082/base-api/routePredicate1/cookieRoute --cookie "mycookie=testval"
curl http://localhost:8082/base-api/routePredicate1/headerRoute -H "token:T10000"
curl http://localhost:8082/base-api/routePredicate1/methodRoute
curl -X POST -d '{}' http://localhost:8082/base-api/routePredicate1/methodRoute
curl -X POST -d '{}' http://localhost:8082/routePredicate1/methodRoute
curl http://localhost:8082/base-api/routePredicate1/pathRoute
curl http://localhost:8082/base-api/routePredicate1/queryRoute?token=T10000
ab -n 100 -c 10 http://localhost:8082/base-api/routePredicate1/weithRoute

#### RoutePredicate2Controller

### Route Filter

#### RouteFilter1Controller

curl http://localhost:8082/base-api/routeFilter1/addRequestHeader -H "user:lottery"
curl http://localhost:8082/base-api/routeFilter1/AddResponseHeader

#### RouteFilter2Controller

### token

curl http://localhost:8082/base-api/route1/msg -H "Authorization:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjQ5ODc3NDl9.ESd_8rz90EMLbatFpAIGLMrcVBgg7IE8wjhTEalthkc"