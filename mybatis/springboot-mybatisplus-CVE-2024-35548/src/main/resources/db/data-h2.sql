DELETE FROM `user`;

INSERT INTO `user` (id, name, age, email, org_ids, update_time,create_by,version,amount,sex,del_f,tenant_id) VALUES
(1, 'Jone', 18, 'test1@baomidou.com','1,2', CURRENT_TIMESTAMP(),'1',1,1.12,'1','0',1),
(2, 'Jack', 20, 'test2@baomidou.com','3,4', CURRENT_TIMESTAMP(),'1',1,1.12,'1','0',1),
(3, 'Tom', 28, 'test3@baomidou.com','5,6', CURRENT_TIMESTAMP(),'1',1,1.12,'1','0',1),
(4, 'Sandy', 21, 'test4@baomidou.com','7,8', CURRENT_TIMESTAMP(),'1',1,1.12,'1','0',1),
(5, 'Billie', 24, 'test5@baomidou.com','9,10', CURRENT_TIMESTAMP(),'1',1,1.12,'2','0',1),
(6, 'Nydia', 24, 'test6@baomidou.com','11,12', CURRENT_TIMESTAMP(),'1',1,1.12,'2','0',1);