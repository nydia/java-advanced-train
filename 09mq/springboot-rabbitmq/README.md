# RabbitMQ启动

# 测试消息发送
curl http://localhost:8080/send1


参数说明：
-n：-n 参数指定了总请求数，这里是100次请求。
-c：-c 参数指定了并发数，这里是10个并发请求。这意味着ab命令会同时发起10个请求。

ab -n 100 -c 10 http://localhost:8080/send1
ab -n 100 -c 10 http://localhost:8080/send2
ab -n 100 -c 10 http://localhost:8080/send3

# rabbitmq安装

### docker-compose.yml

```yaml
version: '3.8'
services:
  rabbitmq:
    image: clf.lvhuaqiang.top/library/rabbitmq:4.0.2-management
    container_name: rabbitmq_standalone
    hostname: rabbitmq_standalone
    environment:
      RABBITMQ_DEFAULT_VHOST: admin_vhost
      RABBITMQ_DEFAULT_USER: admin
      RABBITMQ_DEFAULT_PASS: admin
      RABBITMQ_PLUGINS_DIR: '/plugins:/myplugins'
    ports:
      - "5672:5672"
      - "15672:15672"
    volumes:
      - /opt/rabbitmq/rabbitmq_standalone:/var/lib/rabbitmq
      - /opt/rabbitmq/rabbitmq_standalone/myplugins:/myplugins
    networks:
      - rabbitmq_standalone
    privileged: true
networks:
  rabbitmq_standalone:
    external: true
```

### 死刑队列安装
1.下载地址
官方地址：
https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases/download/v4.0.2/rabbitmq_delayed_message_exchange-4.0.2.ez

代理地址：
https://gh.lvhuaqiang.top/rabbitmq/rabbitmq-delayed-message-exchange/releases/download/v4.0.2/rabbitmq_delayed_message_exchange-4.0.2.ez
