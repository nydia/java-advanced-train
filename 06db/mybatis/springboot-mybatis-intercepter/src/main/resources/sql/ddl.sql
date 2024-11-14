CREATE TABLE `tbl_user`
(
    `id`          int NOT NULL AUTO_INCREMENT,
    `create_time` timestamp NULL DEFAULT NULL,
    `username`    varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `password`    varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `uuid`        varchar(255) COLLATE utf8_bin DEFAULT NULL,
    UNIQUE KEY `tbl_user_idx` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8 COLLATE=utf8_bin
