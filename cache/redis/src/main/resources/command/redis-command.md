### string
127.0.0.1:6379> set s1 v1
OK
127.0.0.1:6379> set s2 v2
OK
127.0.0.1:6379> set s3 v3
OK

### hash
127.0.0.1:6379> hset h1 f1 v1
(integer) 1
127.0.0.1:6379> hset h1 f2 v2
(integer) 1
127.0.0.1:6379> hset h2 j1 v1
(integer) 1
127.0.0.1:6379> hset h2 j2 v2
(integer) 1
127.0.0.1:6379> hset h3 m1 vv1
(integer) 1
127.0.0.1:6379> hset h3 m2 vv2
(integer) 1