# RabbitMQ启动

# 测试消息发送
curl http://localhost:8080/send1


参数说明：
-n：-n 参数指定了总请求数，这里是100次请求。
-c：-c 参数指定了并发数，这里是10个并发请求。这意味着ab命令会同时发起10个请求。

ab -n 100 -c 10 http://localhost:8080/send1
ab -n 100 -c 10 http://localhost:8080/send2
ab -n 100 -c 10 http://localhost:8080/send3

