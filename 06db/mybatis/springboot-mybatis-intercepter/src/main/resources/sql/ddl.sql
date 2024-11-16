CREATE TABLE `tbl_user` (
    `id` int NOT NULL AUTO_INCREMENT,
    `create_time` timestamp NULL DEFAULT NULL,
    `create_by` varchar(100) COLLATE utf8_bin DEFAULT NULL,
    `username` varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `uuid` varchar(255) COLLATE utf8_bin DEFAULT NULL,
    UNIQUE KEY `tbl_user_idx` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=208 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `tbl_user_book` (
    `id` varchar(100) COLLATE utf8_bin NOT NULL,
    `create_time` timestamp NULL DEFAULT NULL,
    `create_by` varchar(100) COLLATE utf8_bin DEFAULT NULL,
    `user_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
    `book_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
    UNIQUE KEY `tbl_user_idx` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;