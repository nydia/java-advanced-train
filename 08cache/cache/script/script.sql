create schema test;

drop table if EXISTS user;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `age` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

insert into user(name, age) values('test1', 1);
insert into user(name, age) values('test2', 2);
insert into user(name, age) values('test3', 3);
insert into user(name, age) values('test4', 4);
insert into user(name, age) values('test5', 5);
insert into user(name, age) values('test6', 6);