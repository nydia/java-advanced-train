
spring cache:
cul http://localhost:8080/user/find?id=1

mybatis cache: 
curl http://localhost:8080/order/find?id=1

hibernate cache: 
curl http://localhost:8080/author/find?id=1
注：需要自己导入hibernate-redis 2.4.0