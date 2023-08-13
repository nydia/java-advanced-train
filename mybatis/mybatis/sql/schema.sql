create table `tbl_user` (
    `id` int not null auto_increment,
    `username` varchar(64) default null,
    `password` varchar(64) default null,
    primary key (`id`)
) engine=innodb auto_increment=1;

create table tbl_students (
  stu_id int not null,
  class_id varchar(10) not null,
  name varchar(30) not null,
  age int not null,
  primary key (stu_id, class_id)
)engine=innodb auto_increment=1;


insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('306','name1','123456','0');
insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('307','name2','123456','306');
insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('308','name3','123456','307');
insert into `tbl_user` (`id`, `username`, `password`, `parent_id`) values('309','name4','123456','307');