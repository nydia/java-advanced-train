# 访问
curl http://localhost:8082/healthCheck

ab -n 100 -c 10 http://localhost:8082/healthCheck



# 启动 apm
startup.cmd

# 启动 agent
idea

-javaagent:C:\tools\apache-skywalking-java-agent-8.12.0\skywalking-agent\skywalking-agent.jar=agent.service_name=skywalking-agent-demo,collector.backend_service=localhost:11800
