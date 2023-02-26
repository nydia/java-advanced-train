
# 使用方式

trace com.example.demo.DemoService run

stack com.nydia.demo.DemoService hello

## 监控成功率
monitor -c 4 com.nydia.demo.DemoService hello

# JVM分析的参数示例
-server -Xms200m -Xmx1024m

# curl 
curl -X GET http://localhost:8085/hello

# 并发测试
ab -n 100 -c 10 http://localhost:8085/hello