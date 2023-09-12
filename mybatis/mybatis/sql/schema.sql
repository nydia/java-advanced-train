use test;
drop table if exists tbl_user;
create table `tbl_user` (
    `id` int(8) not null auto_increment,
    `username` varchar(64) default null,
    `password` varchar(64) default null,
    `parent_id` int(8) default null,
    primary key (`id`)
) engine=innodb auto_increment=1;

insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('306','name1','123456','0');
insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('307','name2','123456','306');
insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('308','name3','123456','307');
insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('309','name3','123456','307');
insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('311','name5','123456','306');
insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('312','name5','123456','306');
insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('313','name5','123456','306');
insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('314','name5','123456','306');
insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('315','name5','123456','306');
insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('316','name5','123456',NULL);
insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('317','name5','123456',NULL);
insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('318','name5','123456',NULL);


---------- 下面这些没用 ------------

drop table if exists tbl_user_book;
create table `tbl_user_book` (
    `id` int(8) not null auto_increment,
    `user_id` int(8) not null,
    `book_name` varchar(64) default '',
    primary key (`id`)
) engine=innodb auto_increment=1;

drop table if exists tbl_students;
create table tbl_students (
  stu_id int not null,
  class_id varchar(10) not null,
  name varchar(30) not null,
  age int not null,
  primary key (stu_id, class_id)
)engine=innodb auto_increment=1;

insert into `tbl_user_book` (`id`, `user_id`, `book_name`) values('1','309','架构');