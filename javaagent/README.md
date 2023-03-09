
# bytebuddy

## 测试 vm options里面加入：
-javaagent:D:\workspace\idea2\JavaAdvancedTrain\javaagent\agent-bytebuddy\target\agent.jar

-javaagent:C:\temp\agent.jar

## agent-client build
docker build -t  agent-client:1.0 .

## run 
docker run -d -p 8081:8081 -it --name agent-demo agent-client:1.0 /bin/sh

## find images windows
docker image ls |findstr "agent"

## 访问
http://192.168.99.100:8081/demo

## rm
docker rm -f agent-demo
docker rmi -f agent-client:1.0