# Skywalking 启动
## oap和ui同时启动
startup.cmd

## 启动oap

## 启动ui

# 启动 skywalking agent
idea vm 添加参数

## v8.12.0
-javaagent:C:/tools/apache-skywalking-java-agent-8.12.0/skywalking-agent/skywalking-agent.jar -Dskywalking.agent.service_name=skywalking-agent-demo -Dskywalking.collector.backend_service=localhost:11800

## v9.3.0
-javaagent:C:/tools/apache-skywalking-java-agent-9.3.0/skywalking-agent/skywalking-agent.jar -Dskywalking.agent.service_name=skywalking-agent-demo -Dskywalking.collector.backend_service=localhost:11800

 
# 测试skywalking 监控性能
curl http://localhost:8082/healthCheck
ab -n 100 -c 10 http://localhost:8082/healthCheck