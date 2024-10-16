# 灰度发布

一个请求者，两个相同服务名的提供者。

## 项目结构
springcloud-nacos-discovery-v2.1.7-1 Consumer
springcloud-nacos-discovery-v2.1.7-2 Provider-1
springcloud-nacos-discovery-v2.1.7-3 Provider-2

## 请求

#### 正常请求
curl http://localhost:8081/echo
curl -H "gray:false" http://localhost:8081/echo
#### 灰度请求
curl -H "gray:true" http://localhost:8081/echo
