DELETE FROM `user`;

INSERT INTO `user` (id, name, age, email, org_ids, update_time) VALUES
(1, 'Jone', 18, 'test1@baomidou.com','1,2', CURRENT_TIMESTAMP()),
(2, 'Jack', 20, 'test2@baomidou.com','3,4', CURRENT_TIMESTAMP()),
(3, 'Tom', 28, 'test3@baomidou.com','5,6', CURRENT_TIMESTAMP()),
(4, 'Sandy', 21, 'test4@baomidou.com','7,8', CURRENT_TIMESTAMP()),
(5, 'Billie', 24, 'test5@baomidou.com','9,10', CURRENT_TIMESTAMP());