# 测试 vm options里面加入：
-javaagent:D:\workspace\idea2\JavaAdvancedTrain\javaagent\agent-bytebuddy\target\agent.jar

# agent-client build
docker build -t  agent-client:1.0 .

# find images
docker image ls |findstr "agent"